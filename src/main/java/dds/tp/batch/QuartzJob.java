package dds.tp.batch;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dds.tp.ui.vm.CargarCuentasViewModel;

public class QuartzJob implements Job {

	CargarCuentasViewModel cargarCuentasViewModel;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Java web application + Quartz 2.2.1");
		
		cargarCuentasViewModel.setPath("C:\\Users\\LBritez\\git\\2017-vn-group-15\\cuentas.txt");
		cargarCuentasViewModel.cargar();
		
	}

}
