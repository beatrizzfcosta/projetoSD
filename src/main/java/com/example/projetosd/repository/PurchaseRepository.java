package com.example.projetosd.repository;

import com.example.projetosd.model.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
}
