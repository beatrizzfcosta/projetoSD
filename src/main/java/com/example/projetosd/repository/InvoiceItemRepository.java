package com.example.projetosd.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface InvoiceItemRepository {

    Integer getPurchaseId();
    Timestamp getDate();
    String getCustomerName();
    String getCustomerEmail();
    String getCustomerNif();
    String getCustomerTelefone();
    String getProductName();
    String getProductDescription(); // NOVO
    Integer getQuantity();
    BigDecimal getPrice();
}
