package br.com.suleimanmoraes.awsproject01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Profile("!local")
@Configuration
public class S3Config {

	@Autowired
	private ApiProperty apiProperty;

	@Bean
	public AmazonS3 amazonS3Client() {
		return AmazonS3ClientBuilder.standard().withRegion(apiProperty.getAws().getRegion())
				.withCredentials(new DefaultAWSCredentialsProviderChain()).build();
	}
}
