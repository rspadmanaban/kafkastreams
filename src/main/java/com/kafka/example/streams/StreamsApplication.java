package com.kafka.example.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.Stores;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Properties;

@SpringBootApplication
public class StreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamsApplication.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void startStream(){
		Properties props = new Properties();
		System.out.println("Starting Stream Application");
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "StreamsApplication");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, String> textLines = builder.stream("test");
		textLines.foreach((key,value) ->{
			System.out.println("key: "+key +" Values: "+value);
		});

		KafkaStreams kafkaStreams = new KafkaStreams(builder.build(),props);
		kafkaStreams.start();

	}

}
