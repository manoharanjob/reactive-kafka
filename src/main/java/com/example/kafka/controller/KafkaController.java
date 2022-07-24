package com.example.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.dto.EmployeeDTO;
import com.example.kafka.service.ReactiveProducerService;

@RestController
public class KafkaController {

	private static final Logger log = LoggerFactory.getLogger(KafkaController.class);
	
	@Autowired
	private ReactiveProducerService reactiveProducerService;
	
//	@GetMapping("/produce/{message}")
//	public void produceMessageGet(@PathVariable String message) {
//		log.info("Message:{}", message);
//		reactiveProducerService.send(message);
//	}
	
	@GetMapping("/produce/{id}/{name}/{age}")
	public void produceMessageGet(@PathVariable long id, @PathVariable String name, @PathVariable int age) {
		log.info("Id:{},Name:{},Age:{}", id, name, age);
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(id);
		employeeDTO.setName(name);
		employeeDTO.setAge(age);
		reactiveProducerService.send(employeeDTO);
	}

	@PostMapping("/produce")
	public void produceMessagePost(@RequestBody EmployeeDTO employeeDTO) {
		log.info("Message:{}", employeeDTO);
		reactiveProducerService.send(employeeDTO);
	}

}