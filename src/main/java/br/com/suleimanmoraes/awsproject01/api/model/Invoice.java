package br.com.suleimanmoraes.awsproject01.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 32, nullable = false, unique = true, name = "invoice_number")
	private String invoiceNumber;

	@Column(length = 32, nullable = false, name = "customer_name")
	private String customerName;

	@Column(name = "total_value")
	private Float totalValue;

	@Column(name = "product_id")
	private Long productId;

	private Integer quantity;
}
