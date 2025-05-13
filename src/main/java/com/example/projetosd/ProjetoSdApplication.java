package com.example.projetosd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
public class ProjetoSdApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoSdApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "index"; // Procura pelo arquivo templates/index.html
    }

    @GetMapping("/comprar")
    public String mostrarPaginaComprar() {
        return "comprar"; // Renderiza templates/comprar.html
    }

    @GetMapping("/sobre")
    public String mostrarSobreNos() {
        return "sobre"; // Renderiza templates/sobre.html
    }

}



