package timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aman
 *
 */

public class SchedulerListener implements ServletContextListener {

	Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		SchedulerProvider provider = SchedulerProvider.getInstance();
		SchedulerService service = provider.newSchedulerService();
		log.info("*** wavity search provider instance initialized ***");
	}
}
