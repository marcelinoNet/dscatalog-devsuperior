package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	private Long exintingId;
	private Long nonExintingId;
	private Long countTotalProducts;

	@Autowired
	private ProductRepository repository;
	
	@BeforeEach
	void setUp() throws Exception{
		exintingId = 1L;
		nonExintingId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void saveShouldPerstistWithAutoIncrementWhenIdIsNull() {
		
		Product product =  Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1,product.getId());
		
	}
	
	@Test
	public void findByIdShouldReturnAnObjectWhenIdExists() {
		
		 Optional<Product> result =  repository.findById(exintingId);
		 
		 Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnANullObjectWhenIdNotExists() {
		
		 Optional<Product> result =  repository.findById(nonExintingId);
		 
		 Assertions.assertFalse(result.isPresent());
	}
	
	
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		repository.deleteById(exintingId);
		
		Optional<Product> result = repository.findById(exintingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			
			repository.deleteById(nonExintingId);			
		});
		
	}
	
	
	
	
	
	
	
	
	
}
