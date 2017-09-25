package timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aman
 *
 */

public class SchedulerListener implements ServletContextListener {

	SchedulerProvider provider = SchedulerProvider.getInstance();
	Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			provider.shutDown();
			log.info("*** wavity scheduler shutdown successfully ***");
		} catch (SchedulerException e) {
			log.error("*** wavity scheduler shutdown failed ***");
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		provider.newSchedulerService();
		log.info("*** wavity search provider instance initialized ***");
	}
}
