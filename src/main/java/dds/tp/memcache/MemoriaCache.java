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
	
	private Document crearDocForDB(Indicador indicador, Empresa empresa, Balance balance, Double resultado) {
		Document doc = new Document("indicador", indicador.getNombre())
                .append("empresa", empresa.getNombre())
                .append("balance", balance.getPeriodoNombre())
                .append("resultado", resultado.toString());
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
						Filters.eq("indicador", indicador.getNombre()), 
						Filters.eq("empresa", empresa.getNombre()), 
						Filters.eq("balance", balance.getPeriodoNombre())),docNuevo);
	}
	
	public Double getValorPrecalculado(MongoCollection<Document> collection, String indicador, String empresa, String balance) {
		Document doc = collection.find(
									Filters.and(
											Filters.eq("indicador", indicador), 
											Filters.eq("empresa", empresa), 
											Filters.eq("balance", balance))
									).first();
		return Double.valueOf(doc.getString("resultado"));
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
