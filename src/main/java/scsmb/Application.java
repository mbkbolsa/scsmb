package scsmb;


import scsmb.service.AnalyseSCSMEventLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired AnalyseSCSMEventLogs analyseSCSMEventLogs;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

        if (args.length == 0) {
            LOG.info("Missing parameter. Provide path to file with logs");
            return;
        }

        analyseSCSMEventLogs.processEventsFromFile(args[0]);
    }
}
