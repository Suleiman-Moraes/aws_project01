package br.com.suleimanmoraes.awsproject01.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.suleimanmoraes.awsproject01.api.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	Optional<Invoice> findTopByInvoiceNumber(String invoiceNumber);

	List<Invoice> findAllByCustomerName(String customerName);
}
