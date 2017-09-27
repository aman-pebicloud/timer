package timer.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;

import timer.SchedulerService;
import timer.SchedulerServiceImpl;
import timer.client.jobs.AnotherJob;
import timer.client.jobs.TaskJob;

public class Run {

	public static void main(String[] args) throws SchedulerException, InterruptedException, ParseException {
		SchedulerService schedulerService = new SchedulerServiceImpl();

		long task1startDateString = 1505828040000l;
		Date startDate1 = new Date(task1startDateString);
		System.out.println(startDate1);
		
		long task2startDateString = 1505828040000l;
		Date startDate2 = new Date(task2startDateString);
		System.out.println(startDate2);
		
		schedulerService.scheduleJobDetailWithDate("trigger1", "job1", "group1", TaskJob.class, startDate1,
				Arrays.asList("First Task", "Task param1", "Task Param2"));

		schedulerService.scheduleJobDetailWithDate("trigger2", "job2", "group1", TaskJob.class, startDate2,
				Arrays.asList("Second Task", "Task param1", "Task Param2"));

		schedulerService.scheduleJobDetailWithCron("trigger2", "job3", "group2", AnotherJob.class, "0 0/2 * 1/1 * ? *",
				Arrays.asList(""));
		
//		schedulerService.deleteJob(new TriggerKey("job1", "group1"), new JobKey("job3", "group2") );
//		schedulerService.deleteJob(new TriggerKey("job3", "group2"), new JobKey("job2", "group1") );
	
		
/*		List<String> list = schedulerService.getGroupDetails();
		for (String string : list) {
			System.out.println(string);
		}
		
		List<String> jobslist = schedulerService.getJobDetails();
		for (String string : jobslist) {
			System.out.println(string);
		}
*/		
	}
}