package dds.tp.tests;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.tp.model.RepositorioEmpresas;
import dds.tp.ui.vm.CargarCuentasViewModel;

public class CargaDeArchivoTest {
	
	CargarCuentasViewModel vm;
	String path;
	
	@Before
	public void init(){
		RepositorioEmpresas empresas = new RepositorioEmpresas();
		vm = new CargarCuentasViewModel(empresas);
		File arch = new File("cuentas.txt");
		path = arch.getAbsoluteFile().getPath();
	}
	
	@Test
	public void cargarEmpresasConUnArchivoValido(){
		vm.setPath(path);
		vm.cargar();
		Assert.assertNotNull(vm.getGuardadorEmpresas());
	}
	
}
