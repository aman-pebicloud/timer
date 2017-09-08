package timer;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerService {

	public <T extends Job> void run(Class<T> myjob, String jobName, String group) throws SchedulerException, InterruptedException {
		Logger logger = Logger.getAnonymousLogger();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		logger.info("scheduler started");
		
		simpleSchedule().withIntervalInSeconds(2);
		Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10)).build();

		logger.info("trigger initialised, will end at");
		scheduler.scheduleJob(createJobDetail(jobName, group, myjob), trigger);
		;
		System.out.println("main exits");
		logger.info("thread sleeeps");
		Thread.sleep(10000);
		scheduler.shutdown();
		logger.warning("scheduler stops");

	}

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		new SchedulerService().run(AnotherJob.class, "job1", "group1");
		new SchedulerService().run(MyJob.class, "job2", "group1");
	}

	private <T extends Job> JobDetail createJobDetail(String jobName, String group, Class<T> myjob) {
		JobDetail jobDetail = newJob(myjob).withIdentity(jobName, group).build();
		return jobDetail;
	}
}
