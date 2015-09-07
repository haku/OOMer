package oomer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter implements ServletContextListener {

    private static final Logger LOG = LoggerFactory.getLogger(Starter.class);

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        new Thread(new SleepAndOom()).start();
        LOG.info("OOM scheduled.");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {}

    private static class SleepAndOom implements Runnable {

        public SleepAndOom() {}

        @Override
        public void run() {
            try {
                LOG.info("Sleeping...");
                Thread.sleep(60000);
                LOG.info("Going to OOM...");
                final List<byte[]> refs = new ArrayList<byte[]>();
                while (true) {
                    refs.add(new byte[Integer.MAX_VALUE]);
                }
            }
            catch (final Exception e) {
                LOG.warn("Oh dear.", e);
            }
        }

    }

}
