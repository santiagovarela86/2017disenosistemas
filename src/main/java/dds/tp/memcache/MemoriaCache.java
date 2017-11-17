package dds.tp.memcache;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import dds.tp.model.Balance;
import dds.tp.model.Empresa;
import dds.tp.model.Indicador;
import dds.tp.model.Usuario;
import dds.tp.model.repositorios.RepositorioIndicadores;

public class MemoriaCache {
	
	private String direccion = "mongodb://G15:123456@ds259855.mlab.com:59855/indicadoresprecalculados";
	
	private Document crearDocForDB(Indicador indicador, Empresa empresa, Balance balance, Double resultado, Usuario user) {
		Document doc = new Document("indicador", indicador.getNombre().toLowerCase())
                .append("empresa", empresa.getNombre().toLowerCase())
                .append("balance", balance.getPeriodoNombre().toLowerCase())
                .append("resultado", resultado.toString().toLowerCase())
                .append("usuario", user.getNombre().toLowerCase());
		return doc;
	}
	
	public void guardarIndicadorPrecalculado(MongoCollection<Document> collection, Indicador indicador, Empresa empresa, Balance balance, Double resultado, Usuario user) {
		Document doc = this.crearDocForDB(indicador, empresa, balance, resultado,user);
		collection.insertOne(doc);
	}
	
	public void actualizarIndicadorPrecalculado(MongoCollection<Document> collection, Indicador indicador, Empresa empresa, Balance balance, Double resultado, Usuario user) {
		Document docNuevo = this.crearDocForDB(indicador, empresa, balance, resultado,user);
		collection.findOneAndReplace(
					Filters.and(
						Filters.eq("indicador", indicador.getNombre().toLowerCase()), 
						Filters.eq("empresa", empresa.getNombre().toLowerCase()), 
						Filters.eq("balance", balance.getPeriodoNombre().toLowerCase()),
						Filters.eq("usuario", user.getNombre().toLowerCase())),docNuevo);
	}
	
	public Double getValorPrecalculado(String indicador, String empresa, String balance, Usuario usuario) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(direccion));
		MongoDatabase database = mongoClient.getDatabase("indicadoresprecalculados");
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
		MongoClient mongoClient = new MongoClient(new MongoClientURI(direccion));
		MongoDatabase database = mongoClient.getDatabase("indicadoresprecalculados");
		MongoCollection<Document> collection = database.getCollection("indicadores");
		empresas.stream().forEach(empre->empre.getTodosLosBalances().stream().forEach(bal->{
													if(indicador.puedeEvaluar(bal, repoIndicadores))
													{
														this.guardarIndicadorPrecalculado(collection, indicador, empre,
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
		MongoClient mongoClient = new MongoClient(new MongoClientURI(direccion));
		MongoDatabase database = mongoClient.getDatabase("indicadoresprecalculados");
		MongoCollection<Document> collection = database.getCollection("indicadores");
		RepositorioIndicadores repoIndicadores = new RepositorioIndicadores();
		repoIndicadores.addIndicadores(repoIndicadores.cargarIndicadores());
		repoIndicadores.getIndicadores().forEach(ind->
								empresas.forEach(empre->
			 empre.getTodosLosBalances().forEach(balan->{
				 	if (!ind.puedeEvaluar(balan, repoIndicadores))
				 		{/*NO HAGO NADA*/}
				 	else if(this.existePrecalculo(ind.getNombre().toLowerCase(), empre.getNombre().toLowerCase(), balan.getPeriodoNombre().toLowerCase(), ind.getUsuario()))
				 		{this.actualizarIndicadorPrecalculado(collection, ind, empre, balan, ind.evaluar(balan, repoIndicadores), ind.getUsuario());}
				 	else
				 		{this.guardarIndicadorPrecalculado(collection, ind, empre, balan, ind.evaluar(balan, repoIndicadores), ind.getUsuario());}
			 			})));
		mongoClient.close();
	}
	
}
