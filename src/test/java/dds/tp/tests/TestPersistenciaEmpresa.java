package dds.tp.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dds.tp.model.Balance;
import dds.tp.model.BalanceAnual;
import dds.tp.model.BalanceSemestral;
import dds.tp.model.Cuenta;
import dds.tp.model.Empresa;
import dds.tp.model.periodos.Anual;
import dds.tp.model.periodos.Periodo;
import dds.tp.model.periodos.Semestral;
import dds.tp.model.repositorios.RepositorioEmpresas;

public class TestPersistenciaEmpresa {

	RepositorioEmpresas repoEmpresa;
	
	@Before
	public void inicializar() {
		repoEmpresa = new RepositorioEmpresas();
	}
	
	//@Test
	public void testGuardadoDeEmpresaSinBalances()  {	
		try {
			repoEmpresa.guardarEmpresa(new Empresa("Hola2", 1980));
			repoEmpresa.guardarEmpresa(new Empresa("Hola3", 1980));
			repoEmpresa.guardarEmpresa(new Empresa("Hola7890", 1980));
			assertEquals(repoEmpresa.cargarEmpresas().size(),3);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGuardadoDeEmpresaConBalancesYCuentas()  {	
		Empresa empresaJojo = new Empresa("Jojo", 9999);
		empresaJojo.addBalance(new BalanceAnual(new Anual(2017)));
		empresaJojo.getBalance("2017").addCuenta(new Cuenta("Roe",55.56));
		empresaJojo.addBalance(new BalanceSemestral(new Semestral(2,2018)));
		empresaJojo.getBalance(new Semestral(2,2018)).addCuenta(new Cuenta("Deuda",987d));
		try {
			repoEmpresa.guardarEmpresa(empresaJojo);
			empresaJojo = repoEmpresa.cargarEmpresas("Jojo").get(0);
			assertEquals(empresaJojo.getNombre(),"Jojo");
			repoEmpresa.inicializarBalances(empresaJojo);
			assertEquals(empresaJojo.getBalance("2017").getCuenta("Roe").getValor(), (Double)55.56d);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
