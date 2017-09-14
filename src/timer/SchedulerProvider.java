package timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerProvider {
	Logger log = LoggerFactory.getLogger(getClass());

	public SchedulerService newSchedulerService() {
		SchedulerServiceImpl service = new SchedulerServiceImpl();
		log.info("Created SchedulerServiceImpl");
		return service;
	}

	public static SchedulerProvider getInstance() {
		return SchedulerProviderHolder.instance;
	}

	private static class SchedulerProviderHolder {
		static final SchedulerProvider instance = new SchedulerProvider();
	}

}