package com.Antiprosopia.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByCitizen_CitizenId(Integer citizenId);
    List<Reservation> findByCar_CarId(Integer carId);
    @Query("SELECT r FROM Reservation r WHERE r.car.carId = :carId AND r.reservationDate = :date AND r.reservationTime = :time")
    List<Reservation> findIfThereIsReservation(Integer carId, LocalDate date, LocalTime time);
}
