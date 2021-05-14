package br.com.suleimanmoraes.awsproject01.api.model;

import br.com.suleimanmoraes.awsproject01.api.enums.EventType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Envelope {
	private EventType eventType;
	private String data;
}
