package com.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.example.kafka.dto.EmployeeDTO;

@Service
public class ReactiveProducerService {

	private static final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);

	@Value(value = "${app.kafka.producer.topic.avro}")
	private String topic;
	private int key = 0;

	@Autowired
	private ReactiveKafkaProducerTemplate<String, EmployeeDTO> reactiveKafkaProducerTemplate;

	public void send(EmployeeDTO employeeDTO) {
		log.info("send to topic={}, {}={},", topic, EmployeeDTO.class.getSimpleName(), employeeDTO);
		reactiveKafkaProducerTemplate.send(topic, "key" + key++, employeeDTO)
				.doOnNext(senderResult -> log.info("sent {} offset : {}", employeeDTO, senderResult.recordMetadata().offset()))
				.doOnSuccess(senderResult -> log.info("sent success {} offset : {}", employeeDTO, senderResult.recordMetadata().offset()))
				.subscribe();
	}
	
//	@Value(value = "${app.kafka.producer.topic.string}")
//	private String topic;
//
//	@Autowired
//	private ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplateString;
//
//	public void send(String message) {
//		log.info("send to topic={}, {}={},", topic, message);
//		reactiveKafkaProducerTemplateString.send(topic, message)
//				.doOnSuccess(senderResult -> log.info("sent {} offset : {}", message, senderResult.recordMetadata().offset()))
//				.subscribe();
//	}
	
}