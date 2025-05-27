package com.example.projetosd.repository;

import com.example.projetosd.model.Color;
import com.example.projetosd.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {}
