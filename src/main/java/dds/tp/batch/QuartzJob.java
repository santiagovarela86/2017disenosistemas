package dds.tp.batch;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.storage.StorageScopes;

import dds.tp.model.Empresa;
import dds.tp.model.LectorCuentas;
import dds.tp.model.repositorios.RepositorioArchivosBatch;
import dds.tp.model.repositorios.RepositorioEmpresas;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class QuartzJob implements Job {

	private static List<ArchivoBatch> archivosPendientes = null;
	private static List<ArchivoBatch> archivosParaProcesar = null;
	RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
	RepositorioArchivosBatch repoArchivos = new RepositorioArchivosBatch();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			archivosPendientes = obtengoArchivosDesdeCloud();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		if (archivosPendientes.stream().count() > 0) {
			
			repoArchivos.inicializar();
			
			archivosParaProcesar = new ArrayList<ArchivoBatch>(archivosPendientes);					
			archivosParaProcesar.removeIf(archivoNuevo -> repoArchivos.contieneArchivo(archivoNuevo.getNombre()));
			archivosParaProcesar.forEach(archivo -> procesoArchivo(archivo, repoEmpresas, repoArchivos));
		}	
		
	}
	
	private static void procesoArchivo(ArchivoBatch archivo, RepositorioEmpresas repoEmpresas, RepositorioArchivosBatch repoArchivos) {
		String contenido = null;
		
		try {
			contenido = getGoogleCloudURL("https://storage.googleapis.com/stockappdds/"+ URLEncoder.encode(archivo.getNombre(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		repoEmpresas.cargarEmpresasGuardadas();
		repoEmpresas.inicializarEmpresas();
		repoEmpresas.inicializarTodosLosbalances();
		
		List<String> lineas = new ArrayList<String>(Arrays.asList(contenido.split("\n")));
		guardarCuentas(lineas, repoEmpresas);
		
		repoArchivos.addArchivo(archivo);
		repoArchivos.guardarArchivo(archivo);
	}
	
	public static String getGoogleCloudURL(String strUrl) throws IOException, GeneralSecurityException{
		ClassLoader cl = QuartzJob.class.getClassLoader();
		InputStream stream = cl.getResourceAsStream("cloudStockApp-0e8784ae94b8.json");
		GoogleCredential credential = GoogleCredential.fromStream(stream)
				.createScoped(Collections.singleton(StorageScopes.DEVSTORAGE_READ_ONLY));   
		String uri = strUrl;
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
		GenericUrl url = new GenericUrl(uri);
		HttpRequest request = requestFactory.buildGetRequest(url);
		HttpResponse response = request.execute();
		String content = response.parseAsString();
		return content;
	}
	
	private static void guardarCuentas(List<String> lineas, RepositorioEmpresas repoEmpresas){
		List<Empresa> aIngresar = new LectorCuentas("").obtenerDatos(lineas);
		repoEmpresas.addEmpresas(aIngresar);
		repoEmpresas.guardarEmpresas(repoEmpresas.getEmpresas());
	}

	private static ArrayList<ArchivoBatch> obtengoArchivosDesdeCloud() throws IOException, GeneralSecurityException {
		String jsonResponse = getGoogleCloudURL("https://www.googleapis.com/storage/v1/b/stockappdds/o?prefix=ArchivoBatch"); 
		ArrayList<ArchivoBatch> archivos = new ArrayList<ArchivoBatch>();		
	    JSONObject jsonObject = new JSONObject(jsonResponse);
	    JSONArray jsonArray = null;
	    
		try {
			jsonArray = jsonObject.getJSONArray("items");
		} catch (JSONException e) {
			return archivos;
		}
	    
	    for (int i = 0, size = jsonArray.length(); i < size; i++)
	    {
	      JSONObject objectInArray = jsonArray.getJSONObject(i);
	      
	      ObjectMapper mapper = new ObjectMapper();
	      ArchivoBatch archivo = null;
	      
		try {
			archivo = mapper.readValue(objectInArray.toString(), ArchivoBatch.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			archivos.add(archivo);
	    }
	    
	    return archivos;
	}

}
