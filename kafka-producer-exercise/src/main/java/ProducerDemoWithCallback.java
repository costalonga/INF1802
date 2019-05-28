import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {

    private static Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class.getName());

    public static void main(String[] args){

        // Criar as propriedades do produtor
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Criar o produtor
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        // Enviar as mensagens
        for (int i=0; i<10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("kafka_consumer_topic",
                    "bom dia " + i + "!");
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
