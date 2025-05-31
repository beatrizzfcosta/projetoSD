package com.example.projetosd.logic;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDTO {

    private Integer purchaseId;
    private LocalDateTime date;
    private String customerName;
    private String customerEmail;
    private String customerTelemovel;
    private String customerNif;
    private List<InvoiceItemDTO> items;

    private String totalAmount;

    public String getCustomerTelemovel() {
        return customerTelemovel;
    }

    public void setCustomerTelemovel(String customerTelemovel) {
        this.customerTelemovel = customerTelemovel;
    }

    public String getCustomerNif() {
        return customerNif;
    }

    public void setCustomerNif(String customerNif) {
        this.customerNif = customerNif;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<InvoiceItemDTO> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemDTO> items) {
        this.items = items;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
