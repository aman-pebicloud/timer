package timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.quartz.SchedulerException;

public class Run {

	public static void main(String[] args) throws SchedulerException, InterruptedException, ParseException {
		String task1startDateString = "09-11-2017 18:48:00";
		Date startDate1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:SS").parse(task1startDateString);

		String task2startDateString = "09-11-2017 18:47:00";
		Date startDate2 = new SimpleDateFormat("MM-dd-yyyy HH:mm:SS").parse(task2startDateString);

		new SchedulerService().run(AnotherJob.class, "job1", "group1", Arrays.asList(""), new Date());
		new SchedulerService().run(TaskJob.class, "job2", "group1", Arrays.asList("First Task"), startDate1);
		new SchedulerService().run(TaskJob.class, "job3", "group1", Arrays.asList("Second Task"), startDate2);
	}
}