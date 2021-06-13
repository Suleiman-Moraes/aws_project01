package br.com.suleimanmoraes.awsproject01.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@ConfigurationProperties("moraes")
public class ApiProperty {

	private final Swagger swagger = new Swagger();

	private final Aws aws = new Aws();

	@Getter
	@Setter
	public static class Swagger {
		private boolean show = true;
	}

	@Getter
	@Setter
	public static class Aws {
		private String region;
		private String snsTopicProductEventsArn;
		private String s3BucketInvoiceName;
		private String sqsQueueInvoiceEventsName;
	}
}
