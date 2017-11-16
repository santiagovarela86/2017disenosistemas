package dds.tp.batch;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

@WebListener
public class QuartzListener implements ServletContextListener{

	Scheduler scheduler = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
            try {
                    JobDetail job = newJob(QuartzJob.class).withIdentity(
                                    "CargaArchivosBatch", "group1").build();
                    
                    Trigger trigger = newTrigger()
                        .withIdentity("myTrigger", "group1")
                        .startNow()
                        .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(60)
                            .repeatForever())            
                        .build();

                    scheduler = new StdSchedulerFactory().getScheduler();
                    scheduler.start();
                    scheduler.scheduleJob(job, trigger);
            }
            catch (SchedulerException e) {
                    e.printStackTrace();
            }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContext) {
            try 
            {
                    scheduler.shutdown();
            } 
            catch (SchedulerException e) 
            {
                    e.printStackTrace();
            }
    }

}
