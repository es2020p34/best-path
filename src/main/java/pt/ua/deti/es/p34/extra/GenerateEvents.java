package pt.ua.deti.es.p34.extra;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ua.deti.es.p34.utils.GeoDB.Event;
import pt.ua.deti.es.p34.utils.Utils;

public class GenerateEvents {
    private static final Logger LOG = LoggerFactory.getLogger(GenerateEvents.class);

    /**
     * Create the topic list and insert it into kafka.
     * <p>
     * It creates a single topic for the delays.
     */
    public static void createTopics() {
        final Properties properties = Utils.loadProperties("kafka.properties");
        final AdminClient adminClient = AdminClient.create(properties);
        final List<NewTopic> newTopics = new ArrayList<NewTopic>();
        final NewTopic newTopic = new NewTopic("esp34event", 1, (short) 1);
        newTopics.add(newTopic);
        adminClient.createTopics(newTopics);
        adminClient.close();
    }

    /**
     * Generates vehicular events and publish them
     */
    public static void publishData(final Producer<String, String> producer) {
        LOG.debug("publishData");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
        Event event = new Event(uuid, timestamp, 40.635013, -8.651136, 50);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String value = mapper.writeValueAsString(event);
            ProducerRecord<String, String> record = new ProducerRecord<>("esp34event", value);
            producer.send(record);
        } catch (JsonProcessingException e) {
            LOG.error("publishData", e);
        }
    }

    public static void main(final String[] args) {
        // create topics into kafka
        createTopics();

        // create a kafka producer
        final Properties properties = Utils.loadProperties("kafka.properties");
        final Producer<String, String> producer = new KafkaProducer<String, String>(properties);

        // setup the scheduler
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> publishData(producer), 0, 30, TimeUnit.SECONDS);

        // Lambda used to catch ctrl+c (interrupt signal)
        // and stop the scheduled executor
        Runtime.getRuntime().addShutdownHook(new Thread(() -> executorService.shutdown()));

        try {
            // wait for the scheduled executor to terminate
            while (!executorService.awaitTermination(24L, TimeUnit.HOURS))
                ;
        } catch (InterruptedException e) {
            LOG.error("Generate events", e);
        } finally {
            // close the kafka producer
            producer.close();
        }
    }
}