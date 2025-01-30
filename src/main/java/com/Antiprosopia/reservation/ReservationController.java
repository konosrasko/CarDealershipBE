package com.Antiprosopia.reservation;

import com.Antiprosopia.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations(@RequestParam Integer citizenID) {
        return reservationService.getAllCitizensReservations(citizenID);
    }

    // Κράτηση Test Drive (κατόπιν ελέγχου διαθεσιμότητας)
    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    @PostMapping("/reservation/test-drive")
    public ResponseEntity<ResponseDTO> reserveTestDrive(@RequestBody ReservationDTO reservationDTO) {
        if (! reservationService.checkIfReservationExist(reservationDTO)) {
            throw new ReservationException("Reservation cannot be done. Please select another date.");
        }
        reservationService.createReservation(reservationDTO);
        return ResponseEntity.ok(new ResponseDTO("Reservation successful"));
    }
}
