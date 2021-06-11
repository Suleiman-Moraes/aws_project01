package br.com.suleimanmoraes.awsproject01.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.suleimanmoraes.awsproject01.api.enums.EventType;
import br.com.suleimanmoraes.awsproject01.api.model.Envelope;
import br.com.suleimanmoraes.awsproject01.api.model.Product;
import br.com.suleimanmoraes.awsproject01.api.model.ProductEvent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductPublish {

	@Autowired
	private AmazonSNS snsClient;

	@Autowired
	@Qualifier("productEventsTopic")
	private Topic productEventsTopic;

	@Autowired
	private ObjectMapper objectMapper;

	public void publishProcutcEvent(Product product, EventType eventType, String username) {
		try {
			final ProductEvent productEvent = new ProductEvent(product.getId(), product.getCode(), username);
			final Envelope envelope = Envelope.builder().eventType(eventType)
					.data(objectMapper.writeValueAsString(productEvent)).build();
			final PublishResult publishResult = snsClient.publish(productEventsTopic.getTopicArn(),
					objectMapper.writeValueAsString(envelope));
			log.info("publishProcutcEvent - MessageId: {}", publishResult.getMessageId());
		} catch (JsonProcessingException e) {
			log.warn("publishProcutcEvent " + e.getMessage());
		}
	}
}
