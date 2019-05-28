import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoKeys {

    private static Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class.getName());

    public static void main(String[] args) {

        // Criar as propriedades do produtor
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Criar o produtor
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        // Enviar as mensagens
        for (int i=0; i<10; i++) {
            String topic = "kafka_consumer_topic";
            String value = "bom dia " + i + "!";
            final String key = "id_"+i;
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        logger.info("Key :" + key + " -- Partição: " + recordMetadata.partition());
                    } else {
                        logger.error("Erro no envio da mensagem", e);
                    }
                }
            }); // Envio assíncrono
        }

        // Fecha o produtor
        producer.close();

    }
}
