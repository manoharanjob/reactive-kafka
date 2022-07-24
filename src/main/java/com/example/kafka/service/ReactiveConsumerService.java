package com.example.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.example.kafka.dto.EmployeeDTO;

@Service
public class ReactiveConsumerService implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ReactiveConsumerService.class);

	@Autowired
	private ReactiveKafkaConsumerTemplate<String, EmployeeDTO> reactiveKafkaConsumerTemplate;

	@Override
	public void run(String... args) {
		// we have to trigger consumption
		reactiveKafkaConsumerTemplate.receiveAutoAck()
			// .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
			.doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}", consumerRecord.key(), consumerRecord.value(), consumerRecord.topic(), consumerRecord.offset()))
			.map(ConsumerRecord::value)
			.doOnNext(employeeDTO -> log.info("successfully consumed {}={}", EmployeeDTO.class.getSimpleName(),	employeeDTO))
			.doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()))
			.subscribe();
	}
	
//	@Autowired
//	private ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplateString;
//
//	@Override
//	public void run(String... args) {
//		// we have to trigger consumption
//		reactiveKafkaConsumerTemplateString.receiveAutoAck()
//			// .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
//			.doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}", consumerRecord.key(), consumerRecord.value(), consumerRecord.topic(), consumerRecord.offset()))
//			.map(ConsumerRecord::value)
//			.doOnNext(message -> log.info("successfully consumed {}={}", message))
//			.doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()))
//			.subscribe();
//	}
	
}