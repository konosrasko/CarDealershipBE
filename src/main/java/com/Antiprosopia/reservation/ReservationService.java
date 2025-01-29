package com.Antiprosopia.reservation;


import java.util.List;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO getReservationById(Integer reservationId);
    List<Reservation> getAllCitizensReservations(Integer citizenId);
    void deleteReservation(Integer reservationId);
    boolean checkIfReservationExist(ReservationDTO reservationDTO);
}
