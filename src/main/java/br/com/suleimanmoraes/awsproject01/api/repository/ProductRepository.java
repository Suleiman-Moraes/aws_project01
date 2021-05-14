package br.com.suleimanmoraes.awsproject01.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.suleimanmoraes.awsproject01.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findTopByCode(String code);
}
