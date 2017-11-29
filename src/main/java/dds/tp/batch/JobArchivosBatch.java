package dds.tp.batch;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import dds.tp.memcache.MemoriaCache;
import dds.tp.model.Empresa;
import dds.tp.model.LectorCuentas;
import dds.tp.model.repositorios.RepositorioArchivosBatch;
import dds.tp.model.repositorios.RepositorioEmpresas;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class JobArchivosBatch implements Job {

	private static List<ArchivoBatch> archivosPendientes = null;
	private static List<ArchivoBatch> archivosParaProcesar = null;
	RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
	RepositorioArchivosBatch repoArchivos = new RepositorioArchivosBatch();
	AdapterGoogleCloud adapter = new AdapterGoogleCloud();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			archivosPendientes = adapter.obtengoArchivosPendientes();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		if (!archivosPendientes.isEmpty()) {
			repoArchivos.inicializar();
			archivosParaProcesar = new ArrayList<ArchivoBatch>(archivosPendientes);
			archivosParaProcesar.removeIf(archivoNuevo -> repoArchivos.contieneArchivo(archivoNuevo.getNombre()));
			archivosParaProcesar.forEach(archivo -> procesoArchivo(archivo, repoEmpresas, repoArchivos));
		}

	}

	private void procesoArchivo(ArchivoBatch archivo, RepositorioEmpresas repoEmpresas,	RepositorioArchivosBatch repoArchivos) {
		String contenido = null;

		try {
			contenido = adapter.obtengoContenidoFile(archivo.getNombre());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		repoEmpresas.cargarEmpresasGuardadas();
		repoEmpresas.refrescarEmpresas();
		repoEmpresas.refrescarBalances();

		List<String> lineas = new ArrayList<String>(Arrays.asList(contenido.split("\n")));
		guardarCuentas(lineas, repoEmpresas);

		repoArchivos.addArchivo(archivo);
		repoArchivos.guardarArchivo(archivo);
	}

	private void guardarCuentas(List<String> lineas, RepositorioEmpresas repoEmpresas) {
		List<Empresa> aIngresar = new LectorCuentas("").obtenerDatos(lineas);
		repoEmpresas.addEmpresas(aIngresar);
		repoEmpresas.guardarEmpresas(repoEmpresas.getEmpresas());
		new MemoriaCache().nuevasEmpresas(aIngresar);
	}

}
