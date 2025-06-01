package com.example.projetosd.repository;

import com.example.projetosd.model.Product;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import com.example.projetosd.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product getByProductId(Long id);
    List<Product> findByBrand_BrandName(String brandName);
    List<Product> findByType_TypeName(String typeName);

}
