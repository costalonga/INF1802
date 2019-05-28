import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class TweetProducer {

    private static Logger logger = LoggerFactory.getLogger(TweetProducer.class.getName());

    public static void main(String[] args){

        // Criar as propriedades do produtor
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerializer.class.getName());

        // Criar o produtor
        KafkaProducer<String,User> producer = new KafkaProducer<>(properties);

        // Enviar as mensagens
        for (int i=0; i<10; i++) {
            String name = "user"+i;
            LocalDate birthday = LocalDate.now().plusDays(i);
            User user = new User ("user"+i, birthday );
            ProducerRecord<String, User> record = new ProducerRecord<>("kafka_consumer_topic", user);
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        logger.info("Exibindo os meta-dados sobre o envio da mensagem. \n" +
                                "Topico: " + recordMetadata.topic() + "\n" +
                                "Partição: " + recordMetadata.partition() + "\n" +
                                "Offset" + recordMetadata.offset());
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
