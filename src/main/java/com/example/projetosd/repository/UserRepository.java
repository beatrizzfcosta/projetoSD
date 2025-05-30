package com.example.projetosd.repository;

import com.example.projetosd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByMail(String mail);
    User findByNif(String nif);
}
