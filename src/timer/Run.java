package timer;

import org.quartz.SchedulerException;

public class Run {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		new SchedulerService().run(AnotherJob.class, "job1", "group1", null);
		new SchedulerService().run(TaskJob.class, "job2", "group1", "First task");
		new SchedulerService().run(TaskJob.class, "job2", "group1", "Second task");
	}

}
