package dds.tp.batch;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
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

public class AdapterGoogleCloud {
	
	public ArrayList<ArchivoBatch> obtengoArchivosPendientes() throws IOException, GeneralSecurityException {
				
		String jsonResponse = obtengoContenidoURL(LectorConfiguraciones.obtenerConfiguracion("urlArchivosPendientes"));		
		ArrayList<ArchivoBatch> archivos = new ArrayList<ArchivoBatch>();
		JSONObject jsonObject = new JSONObject(jsonResponse);
		JSONArray jsonArray = null;

		try {
			jsonArray = jsonObject.getJSONArray("items");
		} catch (JSONException e) {
			return archivos;
		}

		for (int i = 0, size = jsonArray.length(); i < size; i++) {

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

	public String obtengoContenidoURL(String strUrl) throws IOException, GeneralSecurityException {
		ClassLoader cl = JobArchivosBatch.class.getClassLoader();
		InputStream stream = cl.getResourceAsStream(LectorConfiguraciones.obtenerConfiguracion("nombreKeyJsonGoogle"));
		GoogleCredential credential = GoogleCredential.fromStream(stream).createScoped(Collections.singleton(StorageScopes.DEVSTORAGE_READ_ONLY));
		String uri = strUrl;
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
		GenericUrl url = new GenericUrl(uri);
		HttpRequest request = requestFactory.buildGetRequest(url);
		HttpResponse response = request.execute();
		String content = response.parseAsString();
		return content;
	}

	public String obtengoContenidoFile(String fileName) throws IOException, GeneralSecurityException {
		return this.obtengoContenidoURL(LectorConfiguraciones.obtenerConfiguracion("urlStorageEnGoogleCloud") + URLEncoder.encode(fileName, "UTF-8"));
	}

}
