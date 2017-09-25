package timer;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
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

	public SchedulerServiceImpl() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			logger.info("Created SchedulerServiceImpl");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	// Method to schedule using JobDetail and Trigger objects from client
	public void schedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException, InterruptedException {
		scheduler.start();
		if (!jobExists(jobDetail)) {
			scheduler.scheduleJob(jobDetail, trigger);
			logger.info("*** Job created and scheduled ***");
		} else
			logger.warn("Job already exist");
	}

	// Method to create JobDetail and schedule at give Date
	public <T extends Job> JobDetail scheduleJobDetailWithDate(final String triggerName, final String jobName,
			final String group, final Class<T> myjob, final Date when, final List<String> invocationArgs) {
		String jobData = invocationArgs.get(0);
		JobDetail jobDetail = newJob(myjob).withIdentity(jobName, group).usingJobData("jobData", jobData).build();
		SimpleTrigger simpleTrigger = buildExactTimeTrigger(triggerName, jobName, group, when);
		try {
			schedule(jobDetail, simpleTrigger);
			logger.info("*** Job scheduled with jobData {} ***", jobData);
		} catch (SchedulerException | InterruptedException e) {
			logger.info("error occured in scheduling job with Date, {}", e.getMessage());
			e.printStackTrace();
		}
		return jobDetail;
	}

	public <T extends Job> JobDetail scheduleJobDetailWithDate(final Class<T> myjob, final Date when,
			String jobData) {
		JobDetail jobDetail = newJob().usingJobData("jobData", jobData ).build();
		SimpleTrigger simpleTrigger = (SimpleTrigger) newTrigger().forJob(jobDetail).startAt(when).build();
		try {
			schedule(jobDetail, simpleTrigger);
		} catch (SchedulerException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method to create JobDetail and schedule as cron
	public <T extends Job> JobDetail scheduleJobDetailWithCron(final String triggerName, final String jobName,
			final String group, final Class<T> myjob, final String cronExpression, final List<String> invocationArgs) {
		String jobData = invocationArgs.get(0);
		JobDetail jobDetail = newJob(myjob).withIdentity(jobName, group).usingJobData("jobData", jobData).build();
		CronTrigger cronTrigger = buildCronTrigger(triggerName, jobName, group, cronExpression);
		try {
			schedule(jobDetail, cronTrigger);
			logger.info("*** Job scheduled with jobData {} ***", jobData);
		} catch (SchedulerException | InterruptedException e) {
			logger.info("error occured in scheduling job with Cron, {}", e.getMessage());
			e.printStackTrace();
		}
		return jobDetail;
	}

	private SimpleTrigger buildExactTimeTrigger(final String triggerName, final String jobName, final String group,
			final Date when) {
		SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity(triggerName, group).forJob(jobName, group)
				.startAt(when).build();
		logger.info("*** Trigger created with exact date, {} ***", when);
		return trigger;
	}

	private CronTrigger buildCronTrigger(final String triggerName, final String jobName, final String group,
			final String cronExpression) {
		CronTrigger trigger = (CronTrigger) newTrigger().withIdentity(jobName, group)
				.withSchedule(cronSchedule(cronExpression)).forJob(jobName, group).build();
		logger.info("*** Trigger created with cron expression ***");
		return trigger;
	}

	public boolean jobExists(final JobDetail job) {
		try {
			return this.scheduler.getJobDetail(job.getKey()) != null;
		} catch (SchedulerException e) {
			throw new IllegalStateException("Failed to find Job.", e);
		}
	}

	public List<String> getGroupDetails() {
		List<String> list = null;
		try {
			list = this.scheduler.getJobGroupNames();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return list;
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

	protected void shutdown() throws SchedulerException {
		this.scheduler.shutdown();
	}

	@Override
	public List<String> getJobDetails() {
		List<String> list = null;
		try {
			list = this.scheduler.getTriggerGroupNames();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return list;
	}
}