package br.com.suleimanmoraes.awsproject01.api.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.suleimanmoraes.awsproject01.api.enums.EventType;
import br.com.suleimanmoraes.awsproject01.api.model.Product;
import br.com.suleimanmoraes.awsproject01.api.repository.ProductRepository;
import br.com.suleimanmoraes.awsproject01.api.service.ProductPublish;

@RestController
@RequestMapping("api/products")
public class ProductController {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductPublish productPublish;

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable long id) {
		final Optional<Product> optional = repository.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@PostMapping("all")
	public ResponseEntity<List<Product>> save(@RequestBody List<Product> products) throws URISyntaxException {
		if(!CollectionUtils.isEmpty(products)) {
			for(int i = 0; i < products.size(); i++) {
				products.set(i, saveOne(products.get(i)));
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(products);
		}
		return ResponseEntity.badRequest().body(null);
	}

	@PostMapping
	public ResponseEntity<Product> save(@RequestBody Product product) throws URISyntaxException {
		return ResponseEntity.status(HttpStatus.CREATED).body(saveOne(product));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> save(@RequestBody Product product, @PathVariable long id) throws URISyntaxException {
		if (repository.existsById(id)) {
			product.setId(id);
			product = repository.save(product);
			productPublish.publishProcutcEvent(product, EventType.UPDATE, "Maria das Couve");
			return ResponseEntity.status(HttpStatus.CREATED).body(product);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Product> delete(@PathVariable long id) {
		final Optional<Product> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.delete(optional.get());
			productPublish.publishProcutcEvent(optional.get(), EventType.DELETED, "Alberto Roberto");
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/bycode")
	public ResponseEntity<Product> findById(@RequestParam String code) {
		final Optional<Product> optional = repository.findTopByCode(code);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	private Product saveOne(Product product) {
		product = repository.save(product);
		productPublish.publishProcutcEvent(product, EventType.CREATED, "João das Couve");
		return product;
	}
}
