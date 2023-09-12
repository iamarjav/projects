package com.example.demox;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

@Configuration
class KafkaConfiguration {

	@Value("${aws.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${aws.kafka.accessKey}")
	private String accessKey;

	@Value("${aws.kafka.secret}")
	private String secret;

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		System.setProperty("aws.accessKeyId", accessKey);
		System.setProperty("aws.secretKey", secret);

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put("security.protocol", "SASL_SSL");
		configProps.put(SaslConfigs.SASL_MECHANISM, "AWS_MSK_IAM");
		configProps.put(SaslConfigs.SASL_JAAS_CONFIG, "software.amazon.msk.auth.iam.IAMLoginModule required awsProfileName=kafkadev;");
		configProps.put(SaslConfigs.SASL_CLIENT_CALLBACK_HANDLER_CLASS,
				"software.amazon.msk.auth.iam.IAMClientCallbackHandler");
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
