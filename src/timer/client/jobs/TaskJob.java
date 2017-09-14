package timer.client.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TaskJob implements Job {
	
	private String taskName;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		System.out.println("job executed : " + jobDataMap.getString("taskName"));
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
