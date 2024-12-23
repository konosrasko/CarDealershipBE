package com.Antiprosopia.citizen;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
    Optional<Citizen> findByAfm(String afm);
    Optional<Citizen> findByEmail(String email);
}
