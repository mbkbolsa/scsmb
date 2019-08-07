package scsmb.service;

import com.google.gson.Gson;
import scsmb.event.EventProcessor;
import scsmb.model.Event;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class AnalyseSCSMEventLogs {

    private static final Logger LOG = LoggerFactory.getLogger(AnalyseSCSMEventLogs.class);

    private Gson gson;
    private EventProcessor eventProcessor;

    public AnalyseSCSMEventLogs() {
        eventProcessor = new EventProcessor();
        gson = new Gson();
    }

    public void processEventsFromFile(String path) {
        LOG.info("Started processing of the file ", path);
        LineIterator it = null;

        try {
            it = FileUtils.lineIterator(new File(path));
            while (it.hasNext()) {
                Event event = gson.fromJson(it.nextLine(), Event.class);
                eventProcessor.processEvent(event);
            }
        } catch (IOException e) {
            LOG.error("Unable to read file ", path);
        } finally {
            LineIterator.closeQuietly(it);
            LOG.info("Finished processing of the file ", path);
        }
    }



}
