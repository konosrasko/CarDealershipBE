package com.Antiprosopia.dealership;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Dealership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dealershipId;

    @Column(nullable = false, unique = true, length = 15)
    private String afm;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 150)
    private String owner;

    @Column(nullable = false, length = 255)
    private String password;
}
