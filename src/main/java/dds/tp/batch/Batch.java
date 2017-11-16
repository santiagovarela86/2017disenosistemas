package dds.tp.batch;

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

public class Batch {
	
	private static List<ArchivoBatch> archivosYaProcesados = null;
	private static List<ArchivoBatch> archivosPendientes = null;
	private static List<ArchivoBatch> archivosParaProcesar = null;
	
	public static void main(String[] args){
		
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		repoEmpresas.cargarEmpresasGuardadas();
		
		archivosPendientes = obtengoArchivosDesdeCloud();
		archivosYaProcesados = obtengoArchivosDesdeBaseDatos();
		archivosParaProcesar = new ArrayList<ArchivoBatch>(archivosPendientes);		
		archivosParaProcesar.removeIf(archivo -> archivosYaProcesados.stream().anyMatch(yaprocesado -> yaprocesado.getNombre().equals(archivo.getNombre())));
		archivosParaProcesar.forEach(archivo -> procesoArchivo(archivo, repoEmpresas));
		System.exit(0);
		
	}
	
	private static void procesoArchivo(ArchivoBatch archivo, RepositorioEmpresas repoEmpresas) {
		String contenido = null;
		
		try {
			contenido = obtenerContenidoArchivoCloud(archivo.getNombre());
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
	
	public static String obtenerContenidoArchivoCloud(String archivo) throws IOException, GeneralSecurityException {
		GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("cloudStockApp-0e8784ae94b8.json"))
				.createScoped(Collections.singleton(StorageScopes.DEVSTORAGE_READ_ONLY));  
		String uri = "https://storage.googleapis.com/stockappdds/"+ URLEncoder.encode(archivo, "UTF-8");
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

	private static ArrayList<ArchivoBatch> obtengoArchivosDesdeCloud() {
		ArrayList<ArchivoBatch> archivos = new ArrayList<ArchivoBatch>();
		String jsonResponse = null;
		try {
			jsonResponse = getHTML("https://www.googleapis.com/storage/v1/b/stockappdds/o?prefix=ArchivoBatch");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    JSONObject jsonObject = new JSONObject(jsonResponse);
	    JSONArray jsonArray = jsonObject.getJSONArray("items");
	    
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
