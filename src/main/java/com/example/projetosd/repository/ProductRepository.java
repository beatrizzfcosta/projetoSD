package com.example.projetosd.repository;

import com.example.projetosd.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
