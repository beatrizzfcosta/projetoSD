package com.example.projetosd;

import com.example.projetosd.logic.PdfService;


public class PdfTest {
    public static void main(String[] args) {
        // Cria instância do serviço
        PdfService pdfService = new PdfService();

        // Call Function
        pdfService.run();
    }
}
