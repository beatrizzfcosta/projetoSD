package com.example.projetosd.repository;

import com.example.projetosd.model.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {

    @Query(value = """
    SELECT 
        p.purchaseId AS purchaseId,
        p.date AS date,
        u.nome AS customerName,
        u.mail AS customerEmail,
        u.nif AS customerNif,
        u.telefone AS customerTelefone,
        pr.name AS productName,
        pr.description AS productDescription,
        pp.quantity AS quantity,
        pr.price AS price
    FROM purchases p
    JOIN users u ON p.userId = u.userId
    JOIN purchaseProducts pp ON p.purchaseId = pp.purchaseId
    JOIN products pr ON pp.productId = pr.productId
    WHERE p.purchaseId = :purchaseId
    """, nativeQuery = true)
    List<InvoiceItemRepository> findInvoiceDataByPurchaseId(@Param("purchaseId") Integer purchaseId);

}
