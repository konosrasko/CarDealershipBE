package com.Antiprosopia.dealership;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DealershipRepository extends JpaRepository<Dealership, Integer> {
    Optional<Dealership> findByAfm(String afm);
}
