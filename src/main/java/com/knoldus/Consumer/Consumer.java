
package com.knoldus.Consumer;
import com.knoldus.Model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        ConsumerListener c = new ConsumerListener();
        Thread thread = new Thread(c);
        thread.start();
    }
    public static void consumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "com.knoldus.Deserializer.UserDeserializer");
        properties.put("group.id", "test-group");

        KafkaConsumer<String, User> kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("users");
        kafkaConsumer.subscribe(topics);
        try{
            // Message1
            FileWriter file = new FileWriter("Output.txt");
            while (true){
                FileWriter fileWriter = new FileWriter("Output.txt",true);
                ConsumerRecords<String, User> records = kafkaConsumer.poll(Duration.ofMillis(1000));
                ObjectMapper mapper = new ObjectMapper();
                for (ConsumerRecord<String, User> record: records){

                    System.out.println(mapper.writeValueAsString(record.value()));
                    fileWriter.append(mapper.writeValueAsString(record.value())+"\n");
                    // method that would be writing this value to a file.
                }
                fileWriter.close();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {

            kafkaConsumer.close();
        }
    }
}

class ConsumerListener implements Runnable {
    @Override
    public void run() {
        Consumer.consumer();
    }
}

