package com.Antiprosopia.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByDealership_DealershipId(Integer dealershipId);
    List<Car> findByBrand(String brand);

    @Query("SELECT c FROM Car c WHERE " +
            "(LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%')) OR :brand IS NULL) AND " +
            "(LOWER(c.model) LIKE LOWER(CONCAT('%', :model, '%')) OR :model IS NULL) AND " +
            "(c.price <= :maxPrice OR :maxPrice IS NULL)")
    List<Car> searchCars(@Param("brand") String brand,
                         @Param("model") String model,
                         @Param("maxPrice") Double maxPrice);
}

