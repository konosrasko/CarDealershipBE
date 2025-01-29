package com.Antiprosopia;

import com.Antiprosopia.car.Car;
import com.Antiprosopia.car.CarDTO;
import com.Antiprosopia.car.CarService;
import com.Antiprosopia.citizen.CitizenDTO;
import com.Antiprosopia.citizen.CitizenService;
import com.Antiprosopia.dealership.DealershipDTO;
import com.Antiprosopia.dealership.DealershipService;
import com.Antiprosopia.reservation.ReservationDTO;
import com.Antiprosopia.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private DealershipService dealershipService;

    @Autowired
    private CarService carService;

    @Autowired
    private ReservationService reservationService;

    // Εγγραφή Πολίτη
    @PostMapping("/citizen/signup")
    public ResponseEntity<CitizenDTO> signUpCitizen(@RequestBody CitizenDTO citizenDTO) {
        CitizenDTO registeredCitizen = citizenService.createCitizen(citizenDTO);
        return ResponseEntity.ok(registeredCitizen);
    }

    // Εγγραφή Αντιπροσωπείας
    @PostMapping("/dealership/signup")
    public ResponseEntity<DealershipDTO> signUpDealership(@RequestBody DealershipDTO dealershipDTO) {
        DealershipDTO registeredDealership = dealershipService.createDealership(dealershipDTO);
        return ResponseEntity.ok(registeredDealership);
    }

    // Προσθήκη Αυτοκινήτου (Αντιπροσωπεία)
    @PreAuthorize("hasRole('ROLE_DEALERSHIP')")
    @PostMapping("/car/add")
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
        CarDTO addedCar = carService.createCar(carDTO);
        return ResponseEntity.ok(addedCar);
    }

    // Ανανέωση Αριθμού Αυτοκινήτου (για συγκεκριμένο μοντέλο)
    @PreAuthorize("hasRole('ROLE_DEALERSHIP')")
    @PutMapping("/car/update")
    public ResponseEntity<CarDTO> updateCar(@RequestParam Integer carId,@RequestBody Car carUpdated) {
        CarDTO updatedCar = carService.updateCar(carId, carUpdated);
        return ResponseEntity.ok(updatedCar);
    }

    // Αναζήτηση Αυτοκινήτου με πολλαπλά κριτήρια
    @PreAuthorize("hasRole('ROLE_CITIZEN') or hasRole('ROLE_DEALERSHIP')")
    @GetMapping("/car/search")
    public ResponseEntity<List<CarDTO>> searchCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Double maxPrice) {
        List<CarDTO> cars = carService.searchCars(brand, model, maxPrice);
        return ResponseEntity.ok(cars);
    }

    // Κράτηση Test Drive (κατόπιν ελέγχου διαθεσιμότητας)
    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    @PostMapping("/reservation/test-drive")
    public ResponseEntity<String> reserveTestDrive(@RequestBody ReservationDTO reservationDTO) {
        if (reservationService.checkIfReservationExist(reservationDTO)) {
            reservationService.createReservation(reservationDTO);
            return ResponseEntity.ok().body("{\"message\": \"Reservation successful\"}");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Reservation can not be done. Please Select another Date\"}");

    }

    // Αγορά Αυτοκινήτου (κατόπιν ελέγχου διαθεσιμότητας)
    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseCar(@RequestParam Integer carId) {
            carService.purchaseCar(carId);
            if (carService.isCarOutOfStock(carId)){
                carService.deleteCar(carId);
            }
            return ResponseEntity.ok().body("{\"message\": \"Purchase successful\"}");
    }

    @PreAuthorize("hasRole('ROLE_CITIZEN') or hasRole('ROLE_DEALERSHIP')")
    @GetMapping("/get-id")
    public Integer getId(@RequestParam String afm) {
        if(citizenService.findByAfm(afm) != null){
            return citizenService.findByAfm(afm).getCitizenId();
        }else{
            return dealershipService.findByAfm(afm).getDealershipId();
        }
    }
}
