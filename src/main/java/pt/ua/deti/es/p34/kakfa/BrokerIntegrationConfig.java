package pt.ua.deti.es.p34.kakfa;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBinding(IBrokerChannel.class)
public class BrokerIntegrationConfig {

}