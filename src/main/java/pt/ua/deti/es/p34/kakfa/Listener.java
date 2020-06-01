package pt.ua.deti.es.p34.kakfa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import pt.ua.deti.es.p34.utils.GeoDB;

@Component
public class Listener {

    private static final Logger LOG = LoggerFactory.getLogger(Listener.class);

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private GeoDB geodb;

    @StreamListener(target = "esp34event")
    public void processMessage(GeoDB.Event event){
        LOG.debug("Event {}", event);
        this.geodb.insert_event(event);
        this.template.convertAndSend("/topic/event", event);
    }
}
