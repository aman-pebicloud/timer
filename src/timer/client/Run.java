package timer.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import timer.SchedulerService;
import timer.SchedulerServiceImpl;
import timer.client.jobs.AnotherJob;
import timer.client.jobs.TaskJob;

public class Run {

	public static void main(String[] args) throws SchedulerException, InterruptedException, ParseException {
		SchedulerService schedulerService = new SchedulerServiceImpl();
		
		String task1startDateString = "09-14-2017 14:43:00";
		Date startDate1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:SS").parse(task1startDateString);

		String task2startDateString = "09-14-2017 14:43:00";
		Date startDate2 = new SimpleDateFormat("MM-dd-yyyy HH:mm:SS").parse(task2startDateString);
		
		schedulerService.createJobDetailWithDate("job1", "group1", TaskJob.class, startDate1, Arrays.asList("First Task", "Task param1", "Task Param2"));

		schedulerService.createJobDetailWithDate("job2", "group2", TaskJob.class, startDate2, Arrays.asList("Second Task", "Task param1", "Task Param2"));
		
		schedulerService.createJobDetailWithCron("job3", "group2", AnotherJob.class, "0 0/2 * 1/1 * ? *",  Arrays.asList(""));
	}
}