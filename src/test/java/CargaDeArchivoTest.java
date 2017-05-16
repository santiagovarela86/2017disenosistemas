
import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.tp.model.GuardadorEmpresas;
import dds.tp.ui.vm.CargarCuentasViewModel;

public class CargaDeArchivoTest {
	
	CargarCuentasViewModel vm;
	String path;
	
	@Before
	public void init(){
		GuardadorEmpresas empresas = new GuardadorEmpresas();
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
