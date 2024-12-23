package com.Antiprosopia.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByCitizen_CitizenId(Integer citizenId);
    List<Reservation> findByCar_CarId(Integer carId);
}
