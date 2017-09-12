package timer;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerService {

	Scheduler scheduler;

	public void schedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException, InterruptedException {
		Logger logger = Logger.getAnonymousLogger();
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		logger.info("scheduler started");
		if (!jobExists(jobDetail))
			scheduler.scheduleJob(jobDetail, trigger);
		else
			logger.warning("Job already exist");
	}

	public <T extends Job> JobDetail createJobDetail(String jobName, String group, Class<T> myjob,
			List<String> invocationArgs) {
		String taskName = invocationArgs.get(0);
		JobDetail jobDetail = newJob(myjob).withIdentity(jobName, group).usingJobData("taskName", taskName).build();
		return jobDetail;
	}

	public SimpleTrigger buildExactTimeTrigger(final String jobName, final String group, final Date when) {
		SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity(jobName, group).startAt(when).build();
		return trigger;
	}

	public boolean jobExists(final JobDetail job) {
		try {
			return this.scheduler.getJobDetail(job.getKey()) != null;
		} catch (SchedulerException e) {
			throw new IllegalStateException("Failed to find Job.", e);
		}
	}

	public void deleteJob(TriggerKey triggerKey, JobKey jobKey) {
		try {
			this.scheduler.unscheduleJob(triggerKey);
			this.scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			throw new IllegalStateException("Failed to delete the Job.", e);
		}
	}

}