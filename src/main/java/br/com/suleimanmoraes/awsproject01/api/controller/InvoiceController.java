package br.com.suleimanmoraes.awsproject01.api.controller;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import br.com.suleimanmoraes.awsproject01.api.model.Invoice;
import br.com.suleimanmoraes.awsproject01.api.model.UrlResponse;
import br.com.suleimanmoraes.awsproject01.api.repository.InvoiceRepository;
import br.com.suleimanmoraes.awsproject01.config.ApiProperty;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

	@Autowired
	private ApiProperty apiProperty;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private InvoiceRepository repository;

	@PostMapping
	public ResponseEntity<UrlResponse> createInvoiceUrl() {
		final String processId = UUID.randomUUID().toString();
		final Instant expirationTime = Instant.now().plus(Duration.ofMinutes(5));
		final GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				apiProperty.getAws().getS3BucketInvoiceName(), processId).withMethod(com.amazonaws.HttpMethod.PUT)
						.withExpiration(Date.from(expirationTime));
		final UrlResponse urlResponse = new UrlResponse(
				amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString(), expirationTime.getEpochSecond());
		return ResponseEntity.ok(urlResponse);
	}

	@GetMapping
	public ResponseEntity<Iterable<Invoice>> findAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping(path = "/customername")
	public ResponseEntity<Iterable<Invoice>> findAllByCustomerName(@RequestParam String customerName) {
		return ResponseEntity.ok(repository.findAllByCustomerName(customerName));
	}
}
