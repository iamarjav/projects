package com.example.demox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

	@Autowired
	ProducerKafkaDemo ob;

	@GetMapping(value = "/")
	public void hitProducer() {
		ob.publishMsg();

	}

}
