package com.example.projetosd.controller;

import com.example.projetosd.logic.InvoiceDTO;
import com.example.projetosd.logic.InvoiceService;

import com.example.projetosd.logic.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    PdfService pdfService = new PdfService();

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable Integer purchaseId) {
        InvoiceDTO invoice = invoiceService.generateInvoice(purchaseId);

        // Gerar o PDF logo depois de criar o DTO
        pdfService.run(invoice);

        return ResponseEntity.ok(invoice);
    }
}
