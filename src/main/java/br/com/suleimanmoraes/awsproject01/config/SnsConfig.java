package br.com.suleimanmoraes.awsproject01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;

@Profile("!local")
@Configuration
public class SnsConfig {

	@Autowired
	private ApiProperty apiProperty;

	@Bean
	public AmazonSNS snsClient() {
		return AmazonSNSClientBuilder.standard().withRegion(apiProperty.getAws().getRegion())
				.withCredentials(new DefaultAWSCredentialsProviderChain()).build();
	}

	@Bean(name = "productEventsTopic")
	public Topic snsProductEventTopic() {
		return new Topic().withTopicArn(apiProperty.getAws().getSnsTopicProductEventsArn());
	}
}
