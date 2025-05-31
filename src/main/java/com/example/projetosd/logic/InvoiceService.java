package com.example.projetosd.logic;

import com.example.projetosd.logic.InvoiceDTO;
import com.example.projetosd.logic.InvoiceItemDTO;
import com.example.projetosd.repository.InvoiceItemRepository;
import com.example.projetosd.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final PurchaseRepository purchaseRepository;

    public InvoiceService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public InvoiceDTO generateInvoice(Integer purchaseId) {
        List<InvoiceItemRepository> projections = purchaseRepository.findInvoiceDataByPurchaseId(purchaseId);

        if (projections.isEmpty()) {
            throw new RuntimeException("Fatura não encontrada");
        }

        InvoiceItemRepository first = projections.get(0);

        List<InvoiceItemDTO> items = projections.stream().map(p -> {
            BigDecimal totalPrice = p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity()));
            InvoiceItemDTO item = new InvoiceItemDTO();
            item.setProductName(p.getProductName());
            item.setProductDescription(p.getProductDescription());
            item.setQuantity(p.getQuantity());
            item.setUnitPrice(p.getPrice());
            item.setTotalPrice(totalPrice);
            return item;
        }).collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(InvoiceItemDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setPurchaseId(first.getPurchaseId());
        invoice.setDate(first.getDate().toLocalDateTime());
        invoice.setCustomerName(first.getCustomerName());
        invoice.setCustomerEmail(first.getCustomerEmail());
        invoice.setCustomerTelemovel(first.getCustomerTelefone());
        invoice.setCustomerNif(first.getCustomerNif());
        invoice.setItems(items);
        invoice.setTotalAmount(total.toString());

        return invoice;
    }

}
