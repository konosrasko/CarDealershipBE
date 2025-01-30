package com.Antiprosopia.car;

import com.Antiprosopia.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CarContoller {

    @Autowired
    private CarService carService;

    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    @GetMapping("/cars")
    public List<CarDTO> getCars() {
        return carService.getAllCars();
    }

    @GetMapping("/carsForDelearship")
    public List<Car> getCarsForDealer(@RequestParam Integer dealerID) {
        return carService.getAllCarsForDealership(dealerID);
    }

    @PreAuthorize("hasRole('ROLE_DEALERSHIP')")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCar(@RequestParam Integer carId) {
        carService.deleteCar(carId);
        return ResponseEntity.ok(new ResponseDTO("Deleted successfully"));
    }

    @GetMapping("/carData")
    public Car getSingleCarData(@RequestParam Integer carID) {
        return carService.getCarById(carID);
    }

    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    @PostMapping("/purchase")
    public ResponseEntity<ResponseDTO> purchaseCar(@RequestParam Integer carId) {
        carService.purchaseCar(carId);
        if (carService.isCarOutOfStock(carId)) {
            carService.deleteCar(carId);
        }
        return ResponseEntity.ok(new ResponseDTO("Purchase successful"));
    }

    @PreAuthorize("hasRole('ROLE_DEALERSHIP')")
    @PostMapping("/car/add")
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
        CarDTO addedCar = carService.createCar(carDTO);
        return ResponseEntity.ok(addedCar);
    }

    @PreAuthorize("hasRole('ROLE_DEALERSHIP')")
    @PutMapping("/car/update")
    public ResponseEntity<CarDTO> updateCar(@RequestParam Integer carId,@RequestBody Car carUpdated) {
        CarDTO updatedCar = carService.updateCar(carId, carUpdated);
        return ResponseEntity.ok(updatedCar);
    }
}
