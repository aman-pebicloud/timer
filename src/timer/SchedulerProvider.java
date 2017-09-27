package timer;

import org.quartz.SchedulerException;

public class SchedulerProvider {
	SchedulerServiceImpl service;

	public boolean newSchedulerService() {
		service = new SchedulerServiceImpl();
		return service != null;
	}

	public static SchedulerProvider getInstance() {
		return SchedulerProviderHolder.instance;
	}

	private static class SchedulerProviderHolder {
		static final SchedulerProvider instance = new SchedulerProvider();
	}

	public void shutDown() throws SchedulerException {
		this.service.shutdown();
	}
}