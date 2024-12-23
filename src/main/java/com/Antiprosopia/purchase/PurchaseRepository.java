package com.Antiprosopia.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCitizen_CitizenId(Integer citizenId);
    List<Purchase> findByCar_CarId(Integer carId);
}
