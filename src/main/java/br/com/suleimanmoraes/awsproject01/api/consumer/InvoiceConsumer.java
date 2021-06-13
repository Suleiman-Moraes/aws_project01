package br.com.suleimanmoraes.awsproject01.api.consumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.suleimanmoraes.awsproject01.api.model.Invoice;
import br.com.suleimanmoraes.awsproject01.api.model.SnsMessage;
import br.com.suleimanmoraes.awsproject01.api.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InvoiceConsumer {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	InvoiceRepository repository;

	@Autowired
	AmazonS3 amazonS3;

	@JmsListener(destination = "${moraes.aws.sqs-queue-invoice-events-name}")
	public void receiveS3Event(TextMessage textMessage) throws Exception {
		SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);
		S3EventNotification eventNotification = objectMapper.readValue(snsMessage.getMessage(),
				S3EventNotification.class);
		processInvoiceNotification(eventNotification);
	}

	private void processInvoiceNotification(S3EventNotification eventNotification) throws IOException {
		if (!CollectionUtils.isEmpty(eventNotification.getRecords())) {
			for (S3EventNotification.S3EventNotificationRecord r : eventNotification.getRecords()) {
				S3EventNotification.S3Entity s3Entity = r.getS3();
				final String bucketName = s3Entity.getBucket().getName();
				final String objectKey = s3Entity.getObject().getKey();
				final String invoiceFile = downloadObject(bucketName, objectKey);
				Invoice invoice = objectMapper.readValue(invoiceFile, Invoice.class);
				log.info("Invoice received {}", invoice.getInvoiceNumber());
				repository.save(invoice);
				amazonS3.deleteObject(bucketName, objectKey);
			}
		}
	}

	private String downloadObject(String bucketName, String objectKey) throws IOException {
		S3Object s3Object = amazonS3.getObject(bucketName, objectKey);
		StringBuilder tudo = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
		String content = null;
		while((content = bufferedReader.readLine()) != null) {
			tudo.append(content);
		}
		return tudo.toString();
	}
}
