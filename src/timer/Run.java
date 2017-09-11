package timer;

import java.util.Arrays;

import org.quartz.SchedulerException;

public class Run {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		new SchedulerService().run(AnotherJob.class, "job1", "group1", null);
		new SchedulerService().run(TaskJob.class, "job2", "group1", Arrays.asList("First Task"));
		new SchedulerService().run(TaskJob.class, "job2", "group1", Arrays.asList("Second Task"));
	}

}
