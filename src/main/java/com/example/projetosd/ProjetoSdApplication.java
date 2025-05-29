package com.example.projetosd;

import jakarta.annotation.PostConstruct;
import com.example.projetosd.model.Color;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import com.example.projetosd.repository.ColorRepository;

import java.util.List;

@SpringBootApplication
public class ProjetoSdApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjetoSdApplication.class, args);
    }
}