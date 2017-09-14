package timer;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerServiceImpl implements SchedulerService {

	Scheduler scheduler;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void schedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException, InterruptedException {
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		if (!jobExists(jobDetail)) {
			scheduler.scheduleJob(jobDetail, trigger);
			logger.info("*** Job created and scheduled ***");
		} else
			logger.warn("Job already exist");
	}

	public <T extends Job> JobDetail createJobDetailWithDate(final String jobName, final String group,
			final Class<T> myjob, final Date when, final List<String> invocationArgs) {
		String taskName = invocationArgs.get(0);
		JobDetail jobDetail = newJob(myjob).withIdentity(jobName, group).usingJobData("taskName", taskName).build();
		SimpleTrigger simpleTrigger = buildExactTimeTrigger(jobName, group, when);
		try {
			schedule(jobDetail, simpleTrigger);
			logger.info("*** Job created ***");
		} catch (SchedulerException | InterruptedException e) {
			logger.info("error occured in creating job with Date, {}", e.getMessage());
			e.printStackTrace();
		}
		return jobDetail;
	}

	private SimpleTrigger buildExactTimeTrigger(final String jobName, final String group, final Date when) {
		SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity(jobName, group).startAt(when).build();
		logger.info("*** Trigger created with exact date, {} ***", when);
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

	@Override
	public void reschedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException, InterruptedException {
		// TODO logic for reschedule a job

	}
}