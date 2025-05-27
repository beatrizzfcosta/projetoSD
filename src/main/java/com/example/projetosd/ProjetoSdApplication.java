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

@Component
class ColorViewer {

    private final ColorRepository colorRepository;

    public ColorViewer(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @PostConstruct
    public void init() {
        List<Color> cores = colorRepository.findAll();
        System.out.println("Cores na tabela:");
        cores.forEach(cor -> System.out.println("ID: " + cor.getColorId() + ", Nome: " + cor.getColorName()));
    }
}

