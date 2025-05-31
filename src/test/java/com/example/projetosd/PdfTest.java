package com.example.projetosd;

import com.example.projetosd.logic.InvoiceDTO;
import com.example.projetosd.logic.InvoiceItemDTO;
import com.example.projetosd.logic.PdfService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PdfTest {
    public static void main(String[] args) {
        // Cria instância do serviço
        PdfService pdfService = new PdfService();

        // Criar objetos InvoiceItemDTO com setters
        InvoiceItemDTO item1 = new InvoiceItemDTO();
        item1.setProductName("Ford Mustang 1965");
        item1.setProductDescription("Carro clássico");
        item1.setQuantity(1);
        item1.setUnitPrice(new BigDecimal("30000"));
        item1.setTotalPrice(new BigDecimal("30000"));

        InvoiceItemDTO item2 = new InvoiceItemDTO();
        item2.setProductName("Chevrolet Camaro 1969");
        item2.setProductDescription("Muscle car");
        item2.setQuantity(2);
        item2.setUnitPrice(new BigDecimal("28000"));
        item2.setTotalPrice(new BigDecimal("56000"));

        // Criar a fatura
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setPurchaseId(200);
        invoiceDTO.setDate(LocalDateTime.now());
        invoiceDTO.setCustomerName("Jorge Rodrigues");
        invoiceDTO.setCustomerEmail("jorge@example.com");
        invoiceDTO.setItems(List.of(item1, item2));
        invoiceDTO.setTotalAmount("86,000");

        // Gerar o PDF
        pdfService.run(invoiceDTO);
    }
}
