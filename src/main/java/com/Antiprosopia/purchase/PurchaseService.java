package com.Antiprosopia.purchase;


import java.util.List;

public interface PurchaseService {
    PurchaseDTO createPurchase(PurchaseDTO purchaseDTO);
    PurchaseDTO getPurchaseById(Long purchaseId);
    List<PurchaseDTO> getAllPurchases();
}
