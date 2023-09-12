package com.example.demox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProducerKafkaDemo {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplateOb;

	@Scheduled(cron = "*/10 * * * * *")
	public void publishMsg() {
		kafkaTemplateOb.send("demo", "1234");
	}
}
