package com.Antiprosopia;

import com.Antiprosopia.car.Car;
import com.Antiprosopia.car.CarDTO;
import com.Antiprosopia.car.CarService;
import com.Antiprosopia.citizen.CitizenDTO;
import com.Antiprosopia.citizen.CitizenService;
import com.Antiprosopia.dealership.DealershipDTO;
import com.Antiprosopia.dealership.DealershipService;
import com.Antiprosopia.reservation.ReservationDTO;
import com.Antiprosopia.reservation.ReservationException;
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

    @PostMapping("/citizen/signup")
    public ResponseEntity<CitizenDTO> signUpCitizen(@RequestBody CitizenDTO citizenDTO) {
        CitizenDTO registeredCitizen = citizenService.createCitizen(citizenDTO);
        return ResponseEntity.ok(registeredCitizen);
    }

    @PostMapping("/dealership/signup")
    public ResponseEntity<DealershipDTO> signUpDealership(@RequestBody DealershipDTO dealershipDTO) {
        DealershipDTO registeredDealership = dealershipService.createDealership(dealershipDTO);
        return ResponseEntity.ok(registeredDealership);
    }


    @PreAuthorize("hasRole('ROLE_CITIZEN') or hasRole('ROLE_DEALERSHIP')")
    @GetMapping("/car/search")
    public ResponseEntity<List<CarDTO>> searchCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Double maxPrice) {
        List<CarDTO> cars = carService.searchCars(brand, model, maxPrice);
        return ResponseEntity.ok(cars);
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
