package dds.tp.memcache;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class MemoriaCache {
	
	private String direccion = "localhost";
	private int puerto = 27017;
	
	private Document crearDocForDB(Indicador indicador, Empresa empresa, Balance balance, Double resultado) {
		Document doc = new Document("indicador", indicador.getNombre().toLowerCase())
                .append("empresa", empresa.getNombre().toLowerCase())
                .append("balance", balance.getPeriodoNombre().toLowerCase())
                .append("resultado", resultado.toString().toLowerCase());
		return doc;
	}
	
	public void guardarIndiacorPrecalculado(MongoCollection<Document> collection, Indicador indicador, Empresa empresa, Balance balance, Double resultado) {
		Document doc = this.crearDocForDB(indicador, empresa, balance, resultado);
		collection.insertOne(doc);
	}
	
	public void actualizarIndiacorPrecalculado(MongoCollection<Document> collection, Indicador indicador, Empresa empresa, Balance balance, Double resultado) {
		Document docNuevo = this.crearDocForDB(indicador, empresa, balance, resultado);
		collection.findOneAndReplace(
					Filters.and(
						Filters.eq("indicador", indicador.getNombre().toLowerCase()), 
						Filters.eq("empresa", empresa.getNombre().toLowerCase()), 
						Filters.eq("balance", balance.getPeriodoNombre().toLowerCase())),docNuevo);
	}
	
	public Double getValorPrecalculado(String indicador, String empresa, String balance) {
		MongoClient mongoClient = new MongoClient(this.direccion,this.puerto);
		MongoDatabase database = mongoClient.getDatabase("indicadoresPrecalculados");
		MongoCollection<Document> collection = database.getCollection("indicadores");
		Document doc = collection.find(
									Filters.and(
											Filters.eq("indicador", indicador.toLowerCase()), 
											Filters.eq("empresa", empresa.toLowerCase()), 
											Filters.eq("balance", balance.toLowerCase()))
									).first();
		mongoClient.close();
		return Double.valueOf(doc.getString("resultado"));
	}
	
	public boolean existePrecalculo(String indicador, String empresa, String balance) {
		try {
			this.getValorPrecalculado(indicador, empresa, balance);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
	
	public void seCreoNuevoIndicador(Indicador indicador, List<Empresa> empresas, RepositorioIndicadores repoIndicadores) {
		MongoClient mongoClient = new MongoClient("localhost",27017);
		MongoDatabase database = mongoClient.getDatabase("indicadoresPrecalculados");
		MongoCollection<Document> collection = database.getCollection("indicadores");
		empresas.stream().forEach(empre->empre.getTodosLosBalances().stream().forEach(bal->{
													if(indicador.puedeEvaluar(bal, repoIndicadores))
													{
														this.guardarIndiacorPrecalculado(collection, indicador, empre,
																bal, indicador.evaluar(bal, repoIndicadores));
													}
												}));
		mongoClient.close();
	}
	
	public void inicializarCache(List<Empresa> empresas, RepositorioIndicadores repoIndicadores) {
		List<Indicador> indicadores = new ArrayList<>(repoIndicadores.getIndicadores());
		indicadores.stream().forEach(indi->this.seCreoNuevoIndicador(indi, empresas, repoIndicadores));
	}
	
	
}
