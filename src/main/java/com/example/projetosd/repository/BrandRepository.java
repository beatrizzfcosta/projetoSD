package com.example.projetosd.repository;

import com.example.projetosd.model.Brand;
import com.example.projetosd.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {}
