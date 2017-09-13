package timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import timer.jobs.TaskJob;

public class Run {

	public static void main(String[] args) throws SchedulerException, InterruptedException, ParseException {
		SchedulerServiceImpl schedulerService = new SchedulerServiceImpl();
		
		String task1startDateString = "09-12-2017 19:37:00";
		Date startDate1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:SS").parse(task1startDateString);

		String task2startDateString = "09-12-2017 19:37:00";
		Date startDate2 = new SimpleDateFormat("MM-dd-yyyy HH:mm:SS").parse(task2startDateString);
		
		JobDetail jobDetail = schedulerService.createJobDetail("job1", "group1", TaskJob.class, Arrays.asList("First Task", "Task param1", "Task Param2"));
		SimpleTrigger trigger = schedulerService.buildExactTimeTrigger("job1", "group1", startDate1);

		JobDetail jobDetail2 = schedulerService.createJobDetail("job2", "group2", TaskJob.class, Arrays.asList("Second Task", "Task param1", "Task Param2"));
		SimpleTrigger trigger2 = schedulerService.buildExactTimeTrigger("job2", "group2", startDate2);
		
		schedulerService.schedule(jobDetail, trigger);
		schedulerService.schedule(jobDetail2, trigger2);
	}
}