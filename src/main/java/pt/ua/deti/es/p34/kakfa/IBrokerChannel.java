package pt.ua.deti.es.p34.kakfa;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface IBrokerChannel {
    @Input("esp34event")
    SubscribableChannel inbound();
}
