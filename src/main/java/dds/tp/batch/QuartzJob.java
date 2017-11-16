package dds.tp.batch;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dds.tp.model.repositorios.RepositorioEmpresas;
import dds.tp.ui.vm.CargarCuentasViewModel;

public class QuartzJob implements Job {

	private static List<ArchivoBatch> archivosYaProcesados = null;
	private static List<ArchivoBatch> archivosPendientes = null;
	private static List<ArchivoBatch> archivosParaProcesar = null;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		repoEmpresas.cargarEmpresasGuardadas();
		
		try {
			archivosPendientes = obtengoArchivosDesdeCloud();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		if (archivosPendientes.stream().count() > 0) {
			archivosYaProcesados = obtengoArchivosDesdeBaseDatos();
			archivosParaProcesar = new ArrayList<ArchivoBatch>(archivosPendientes);		
			archivosParaProcesar.removeIf(archivo -> archivosYaProcesados.stream().anyMatch(yaprocesado -> yaprocesado.getNombre().equals(archivo.getNombre())));
			archivosParaProcesar.forEach(archivo -> procesoArchivo(archivo, repoEmpresas));
		}	
		
	}
	
	private static void procesoArchivo(ArchivoBatch archivo, RepositorioEmpresas repoEmpresas) {
		String contenido = null;
		
		try {
			contenido = getGoogleCloudURL("https://storage.googleapis.com/stockappdds/"+ URLEncoder.encode(archivo.getNombre(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		List<String> lineas = new ArrayList<String>(Arrays.asList(contenido.split("\n")));
		lineas.forEach(linea -> guardarCuenta(linea, repoEmpresas));
		
		RepositorioArchivosBatch repositorio = new RepositorioArchivosBatch();
		repositorio.inicializar();
		repositorio.addArchivo(archivo);
		repositorio.guardarArchivo(archivo);
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

	private static void guardarCuenta(String linea, RepositorioEmpresas repoEmpresas) {
		RepositorioEmpresas repoTemporal = new RepositorioEmpresas();
		LectorCuentas lector = new LectorCuentas("");
		lector.convertAndAddCuenta(linea, repoTemporal);		
		repoEmpresas.guardarEmpresas(repoTemporal.getEmpresas());
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

	private static List<ArchivoBatch> obtengoArchivosDesdeBaseDatos() {
		RepositorioArchivosBatch repositorio = new RepositorioArchivosBatch();
		repositorio.inicializar();
		return repositorio.getArchivos();
	}
	
	public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	}

}
