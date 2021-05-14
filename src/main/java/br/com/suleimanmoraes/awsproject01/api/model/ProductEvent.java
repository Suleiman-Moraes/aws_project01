package br.com.suleimanmoraes.awsproject01.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {
	private Long id;
	private String code;
	private String username;
}
