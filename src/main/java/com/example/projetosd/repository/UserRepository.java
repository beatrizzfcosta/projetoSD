package com.example.projetosd.repository;

import com.example.projetosd.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
