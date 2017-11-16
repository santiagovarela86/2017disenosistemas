package dds.tp.main;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dds.tp.memcache.MemoriaCache;
import dds.tp.model.BalanceAnual;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.periodos.Anual;

public class ProbandoRedis {

	static MemoriaCache memCache;
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient("localhost",27017);
		MongoDatabase database = mongoClient.getDatabase("indicadoresPrecalculados");
		MongoCollection<Document> collection = database.getCollection("indicadores");
		memCache = new MemoriaCache();
		memCache.guardarIndiacorPrecalculado(collection, new Indicador("Roe", "5+5", null), new Empresa("Nike", 2017), new BalanceAnual(new Anual(2015)), (double) 5);
		System.out.println(memCache.getValorPrecalculado(collection, "Roe", "Nike", new BalanceAnual(new Anual(2015)).getPeriodoNombre()).toString());
		memCache.actualizarIndiacorPrecalculado(collection, new Indicador("Roe", "5+5", null), new Empresa("Nike", 2017), new BalanceAnual(new Anual(2015)), (double) 15);
		System.out.println(memCache.getValorPrecalculado(collection,"Roe", "Nike", new BalanceAnual(new Anual(2015)).getPeriodoNombre()).toString());
		mongoClient.close();
	}
	
}
