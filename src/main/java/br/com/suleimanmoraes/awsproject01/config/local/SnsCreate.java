package br.com.suleimanmoraes.awsproject01.config.local;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.Topic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("local")
@Configuration
public class SnsCreate {

	private final String productEventsTopic;

	private final AmazonSNS snsClient;

	public SnsCreate() {
		this.snsClient = AmazonSNSClient.builder()
				.withEndpointConfiguration(
						new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", Regions.US_EAST_1.name()))
				.withCredentials(new DefaultAWSCredentialsProviderChain()).build();
		CreateTopicRequest createTopicRequest = new CreateTopicRequest("product-events");
		this.productEventsTopic = this.snsClient.createTopic(createTopicRequest).getTopicArn();

		log.info("SNS topic ARN : {}", this.productEventsTopic);
	}

	@Bean
	public AmazonSNS snsClient() {
		return this.snsClient;
	}

	@Bean(name = "productEventsTopic")
	public Topic snsProductEventTopic() {
		return new Topic().withTopicArn(productEventsTopic);
	}
}
