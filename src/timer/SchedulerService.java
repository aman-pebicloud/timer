package timer;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerService {

//    private final Scheduler scheduler;
    
/*    public SchedulerService(final Scheduler scheduler) {
    	this.scheduler = scheduler;
	}
*/
	public <T extends Job> void run(Class<T> myjob, String jobName, String group, List<String> invocationArgs, Date when) throws SchedulerException, InterruptedException {
		Logger logger = Logger.getAnonymousLogger();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		logger.info("scheduler started");
		
		Trigger trigger = buildExactTimeTrigger(jobName, group, when);

		scheduler.scheduleJob(createJobDetail(jobName, group, myjob, invocationArgs), trigger);
//		scheduler.shutdown();
//		logger.warning("scheduler stops");

	}

	private <T extends Job> JobDetail createJobDetail(String jobName, String group, Class<T> myjob, List<String> invocationArgs) {
		String taskName = invocationArgs.get(0);
		JobDetail jobDetail = newJob(myjob).withIdentity(jobName, group).usingJobData("taskName", taskName).build();
		return jobDetail;
	}

    private SimpleTrigger buildExactTimeTrigger(final String jobName, final String group, final Date when)
    {
        SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity(jobName, group).startAt(when).build();
        return trigger;
    }
}