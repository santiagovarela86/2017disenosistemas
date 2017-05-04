package dds.tp.ui.vm;

import java.io.File;

import org.uqbar.commons.utils.Observable;
import dds.tp.model.IOArchivoCuentas;
import dds.tp.ui.complementos.ViewModel;
@Observable
public class PantallaPrincipalViewModel implements ViewModel {
	
	private File miArchivo = new File("cuentas.txt");
	private IOArchivoCuentas lector = new IOArchivoCuentas(miArchivo.getAbsolutePath());
	
	public IOArchivoCuentas getLector(){
		return lector;
	}
}
