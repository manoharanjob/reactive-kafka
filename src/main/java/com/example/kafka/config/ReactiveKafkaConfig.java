package com.example.kafka.config;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import com.example.kafka.dto.EmployeeDTO;

import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class ReactiveKafkaConfig {

	@Bean
	public ReceiverOptions<String, EmployeeDTO> kafkaReceiverOptions(
			@Value(value = "${app.kafka.consumer.topic.avro}") String topic, KafkaProperties kafkaProperties) {
		ReceiverOptions<String, EmployeeDTO> basicReceiverOptions = ReceiverOptions
				.create(kafkaProperties.buildConsumerProperties());
		return basicReceiverOptions.subscription(Collections.singletonList(topic));
	}

	@Bean
	public ReactiveKafkaConsumerTemplate<String, EmployeeDTO> reactiveKafkaConsumerTemplate(
			ReceiverOptions<String, EmployeeDTO> kafkaReceiverOptions) {
		return new ReactiveKafkaConsumerTemplate<String, EmployeeDTO>(kafkaReceiverOptions);
	}
	
	@Bean
	public ReactiveKafkaProducerTemplate<String, EmployeeDTO> reactiveKafkaProducerTemplate(
			KafkaProperties properties) {
		Map<String, Object> props = properties.buildProducerProperties();
		return new ReactiveKafkaProducerTemplate<String, EmployeeDTO>(SenderOptions.create(props));
	}
	
//	@Bean
//	public ReceiverOptions<String, String> kafkaReceiverOptionsString(
//			@Value(value = "${app.kafka.consumer.topic.string}") String topic, KafkaProperties kafkaProperties) {
//		ReceiverOptions<String, String> basicReceiverOptions = ReceiverOptions
//				.create(kafkaProperties.buildConsumerProperties());
//		return basicReceiverOptions.subscription(Collections.singletonList(topic));
//	}
//
//	@Bean
//	public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplateString(
//			ReceiverOptions<String, String> kafkaReceiverOptionsString) {
//		return new ReactiveKafkaConsumerTemplate<String, String>(kafkaReceiverOptionsString);
//	}
//
//	@Bean
//	public ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplateString(
//			KafkaProperties properties) {
//		Map<String, Object> props = properties.buildProducerProperties();
//		return new ReactiveKafkaProducerTemplate<String, String>(SenderOptions.create(props));
//	}

}