package dds.tp.batch;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ProgramadorJobs {
	
	public void correrJobImportCloud(){
		
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = null;		
		JobDetail jobDetail = JobBuilder.newJob(JobArchivosBatch.class).withIdentity("jobArchivosBatch", "myGroup").build();
		SimpleTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "myGroup")
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInSeconds(60)
                                .repeatForever()
                                )
                .build();
		
		try {
			scheduler = factory.getScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
				
	}

}
