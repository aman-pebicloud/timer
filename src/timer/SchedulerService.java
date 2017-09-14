package timer;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public interface SchedulerService {
	public void schedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException, InterruptedException;

	public <T extends Job> JobDetail createJobDetailWithDate(String jobName, String group, Class<T> myjob, Date when,
			List<String> invocationArgs);

	public boolean jobExists(final JobDetail job);

	public void deleteJob(TriggerKey triggerKey, JobKey jobKey);
	
	public void reschedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException, InterruptedException;
}
