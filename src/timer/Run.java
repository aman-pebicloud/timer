package timer;

import java.util.Arrays;
import java.util.Date;

import org.quartz.SchedulerException;

public class Run {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		new SchedulerService().run(AnotherJob.class, "job1", "group1", Arrays.asList(""), new Date(1505126744));
		new SchedulerService().run(TaskJob.class, "job2", "group1", Arrays.asList("First Task"), new Date());
		new SchedulerService().run(TaskJob.class, "job3", "group1", Arrays.asList("Second Task"), new Date());
	}

}
