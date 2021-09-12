package com.example.spring.producer.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.producer.dto.ProductVO;
import com.example.spring.producer.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Products")
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@Operation(summary = "inserir products")
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody ProductVO vo) {
		this.service.insert(vo);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "encontrar todos products")
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<?> findAll() {
		List<ProductVO> products = this.service.findAll();
		products.forEach(o -> o.add(linkTo(methodOn(ProductController.class).findById(o.getId())).withSelfRel()));
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@Operation(summary = "encontrar products filtrados")
	@GetMapping(value = "/search", produces = { "application/json" })
	public ResponseEntity<?> search(@RequestParam(name = "q", required = false) String q,
			@RequestParam(name = "min_price", required = false) BigDecimal minPrice,
			@RequestParam(name = "max_price", required = false) BigDecimal maxPrice) {
		List<ProductVO> products = this.service.findAll(q, minPrice, maxPrice);
		products.forEach(o -> o.add(linkTo(methodOn(ProductController.class).findById(o.getId())).withSelfRel()));
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@Operation(summary = "atualizar product")
	@PutMapping(value = "/{id}", produces = { "application/json" })
	public ResponseEntity<?> put(@PathVariable String id, @RequestBody ProductVO vo) {
		this.service.put(id, vo);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "encontrar product por id")
	@GetMapping(value = "/{id}", produces = { "application/json" })
	public ProductVO findById(@PathVariable String id) {
		ProductVO vo = this.service.findById(id);
		vo.add(linkTo(methodOn(ProductController.class).findById(vo.getId())).withSelfRel());
		return vo;
	}

	@Operation(summary = "deletar product por id")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		this.service.delete(id);
		return ResponseEntity.ok().build();
	}
}
