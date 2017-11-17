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
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class MemoriaCache {
	
	private String direccion = "localhost";
	private int puerto = 27017;
	
	private Document crearDocForDB(Indicador indicador, Empresa empresa, Balance balance, Double resultado, Usuario user) {
		Document doc = new Document("indicador", indicador.getNombre().toLowerCase())
                .append("empresa", empresa.getNombre().toLowerCase())
                .append("balance", balance.getPeriodoNombre().toLowerCase())
                .append("resultado", resultado.toString().toLowerCase())
                .append("usuario", user.getNombre().toLowerCase());
		return doc;
	}
	
	public void guardarIndiacorPrecalculado(MongoCollection<Document> collection, Indicador indicador, Empresa empresa, Balance balance, Double resultado, Usuario user) {
		Document doc = this.crearDocForDB(indicador, empresa, balance, resultado,user);
		collection.insertOne(doc);
	}
	
	public void actualizarIndiacorPrecalculado(MongoCollection<Document> collection, Indicador indicador, Empresa empresa, Balance balance, Double resultado, Usuario user) {
		Document docNuevo = this.crearDocForDB(indicador, empresa, balance, resultado,user);
		collection.findOneAndReplace(
					Filters.and(
						Filters.eq("indicador", indicador.getNombre().toLowerCase()), 
						Filters.eq("empresa", empresa.getNombre().toLowerCase()), 
						Filters.eq("balance", balance.getPeriodoNombre().toLowerCase()),
						Filters.eq("usuario", user.getNombre().toLowerCase())),docNuevo);
	}
	
	public Double getValorPrecalculado(String indicador, String empresa, String balance, Usuario usuario) {
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
	
	public boolean existePrecalculo(String indicador, String empresa, String balance, Usuario user) {
		try {
			this.getValorPrecalculado(indicador, empresa, balance, user);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
	
	public void seCreoNuevoIndicador(Indicador indicador, List<Empresa> empresas, RepositorioIndicadores repoIndicadores, Usuario user) {
		MongoClient mongoClient = new MongoClient("direccion",puerto);
		MongoDatabase database = mongoClient.getDatabase("indicadoresPrecalculados");
		MongoCollection<Document> collection = database.getCollection("indicadores");
		empresas.stream().forEach(empre->empre.getTodosLosBalances().stream().forEach(bal->{
													if(indicador.puedeEvaluar(bal, repoIndicadores))
													{
														this.guardarIndiacorPrecalculado(collection, indicador, empre,
																bal, indicador.evaluar(bal, repoIndicadores),user);
													}
												}));
		mongoClient.close();
	}
	
	public void inicializarCache(List<Empresa> empresas, RepositorioIndicadores repoIndicadores,Usuario user) {
		List<Indicador> indicadores = new ArrayList<>(repoIndicadores.getIndicadores());
		indicadores.stream().forEach(indi->this.seCreoNuevoIndicador(indi, empresas, repoIndicadores, user));
	}
	
	
	public void nuevasEmpresas(List<Empresa> empresas) {
		MongoClient mongoClient = new MongoClient(direccion,puerto);
		MongoDatabase database = mongoClient.getDatabase("indicadoresPrecalculados");
		MongoCollection<Document> collection = database.getCollection("indicadores");
		RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.addIndicadores(repoIndicadores.cargarIndicadores());
		repoIndicadores.getIndicadores().forEach(ind->
								empresas.forEach(empre->
			 empre.getTodosLosBalances().forEach(balan->{
				 	if (!ind.puedeEvaluar(balan, repoIndicadores))
				 		{/*NO HAGO NADA*/}
				 	else if(this.existePrecalculo(ind.getNombre().toLowerCase(), empre.getNombre().toLowerCase(), balan.getPeriodoNombre().toLowerCase(), ind.getUsuario()))
				 		{this.actualizarIndiacorPrecalculado(collection, ind, empre, balan, ind.evaluar(balan, repoIndicadores), ind.getUsuario());}
				 	else
				 		{this.guardarIndiacorPrecalculado(collection, ind, empre, balan, ind.evaluar(balan, repoIndicadores), ind.getUsuario());}
			 			})));
		mongoClient.close();
	}
	
}
