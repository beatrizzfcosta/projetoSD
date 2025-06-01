package com.example.projetosd.controller;

import com.example.projetosd.logic.InvoiceDTO;
import com.example.projetosd.logic.InvoiceService;

import com.example.projetosd.logic.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    PdfService pdfService = new PdfService();

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping()
    public ResponseEntity<byte[]> downloadInvoiceFromBody(@RequestBody InvoiceDTO request) {
        Integer purchaseId = request.getPurchaseId();
        InvoiceDTO invoice = invoiceService.generateInvoice(purchaseId);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayOutputStream baos = pdfService.run(invoice);
        if (baos == null) {
            return ResponseEntity.internalServerError().build();
        }

        byte[] pdfBytes = baos.toByteArray();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"fatura_" + purchaseId + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }




}
