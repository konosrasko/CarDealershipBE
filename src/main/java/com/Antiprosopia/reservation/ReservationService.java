package com.Antiprosopia.reservation;


import java.util.List;

public interface ReservationService {
    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO getReservationById(Integer reservationId);
    List<ReservationDTO> getAllReservations();
    void deleteReservation(Integer reservationId);
}
