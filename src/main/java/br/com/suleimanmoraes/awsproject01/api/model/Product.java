package br.com.suleimanmoraes.awsproject01.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "product", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 32, nullable = false)
	private String name;

	@Column(length = 24, nullable = false)
	private String model;
	
	@Column(length = 8, nullable = false)
	private String code;

	@Column(length = 12)
    private String color;

	private Float price;
}
