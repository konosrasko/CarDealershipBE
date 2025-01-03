package com.Antiprosopia.car;

import com.Antiprosopia.dealership.Dealership;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 50)
    private String fuelType;

    @Column(nullable = false, length = 50)
    private String engineCapacity;

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false, precision = 10)
    private Double price;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "dealership_id", nullable = false)
    private Dealership dealership;

}
