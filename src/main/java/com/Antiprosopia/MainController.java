package com.Antiprosopia;

import com.Antiprosopia.car.CarDTO;
import com.Antiprosopia.car.CarService;
import com.Antiprosopia.citizen.CitizenDTO;
import com.Antiprosopia.citizen.CitizenService;
import com.Antiprosopia.dealership.DealershipDTO;
import com.Antiprosopia.dealership.DealershipService;
import com.Antiprosopia.reservation.ReservationDTO;
import com.Antiprosopia.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/car/renew/{carId}")
    public ResponseEntity<CarDTO> updateCarQuantity(@PathVariable Integer carId, @RequestParam Integer quantity) {
        CarDTO updatedCar = carService.updateCar(carId, quantity);
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
    public ResponseEntity<ReservationDTO> reserveTestDrive(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO reservedTestDrive = reservationService.createReservation(reservationDTO);
        return ResponseEntity.ok(reservedTestDrive);
    }

    // Αγορά Αυτοκινήτου (κατόπιν ελέγχου διαθεσιμότητας)
    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseCar(@RequestParam Integer citizenId, @RequestParam Integer carId) {
        boolean isAvailable = carService.checkCarAvailability(carId);
        if (isAvailable) {
            carService.purchaseCar(citizenId, carId);
            return ResponseEntity.ok("Purchase successful");
        } else {
            return ResponseEntity.status(400).body("Car is not available for purchase");
        }
    }
}
