package com.Antiprosopia.reservation;


import com.Antiprosopia.car.CarRepository;
import com.Antiprosopia.citizen.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private CarRepository carRepository;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setCitizen(citizenRepository.findById(reservationDTO.getCitizenId()).orElseThrow(() -> new NullPointerException()));
        reservation.setCar(carRepository.findById(reservationDTO.getCarId()).orElseThrow(() -> new NullPointerException()));
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setReservationTime(reservationDTO.getReservationTime());
        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToDTO(savedReservation);
    }

    @Override
    public ReservationDTO getReservationById(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return mapToDTO(reservation);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationId(reservation.getReservationId());
        reservationDTO.setCitizenId(reservation.getCitizen().getCitizenId());
        reservationDTO.setCarId(reservation.getCar().getCarId());
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setReservationTime(reservation.getReservationTime());
        return reservationDTO;
    }
}
