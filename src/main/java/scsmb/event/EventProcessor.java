package scsmb.event;

import scsmb.model.Event;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;

public class EventProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(EventProcessor.class);

    private HashMap<String, Long> eventTimeStamps;

    public EventProcessor() {
        this.eventTimeStamps = new HashMap<>();
    }

    public void processEvent(Event event) {

        if (StringUtils.isEmpty(event.id)) return;

        if (eventTimeStamps.containsKey(event.id)) {
            long msDiff = calculateTimeDiff(event);
            if (msDiff > 4) {
                LOG.info("Processing of event {} took longer then 4 ms. ", event.id);
                //Update DB
            }
            eventTimeStamps.remove(event.id);
        } else {
            eventTimeStamps.put(event.id, event.timestamp);
        }
    }

    private long calculateTimeDiff(Event event) {
        return Math.abs(event.timestamp - eventTimeStamps.get(event.id).longValue());
    }
}
