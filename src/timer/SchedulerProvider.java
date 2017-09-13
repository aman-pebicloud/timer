package timer;

public class SchedulerProvider {

	public SchedulerService newSchedulerService() {
		SchedulerServiceImpl service = new SchedulerServiceImpl();
		return service;
	}

	public static SchedulerProvider getInstance() {
		return SchedulerProviderHolder.instance;
	}

	private static class SchedulerProviderHolder {
		static final SchedulerProvider instance = new SchedulerProvider();
	}

}