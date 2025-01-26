package com.Antiprosopia.car;

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

    @GetMapping("/cars")
    public List<CarDTO> getCars() {
        return carService.getAllCars();
    }

    @PreAuthorize("hasRole('ROLE_DEALERSHIP')")
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteCar(@RequestParam Integer carId) {
        try {
            carService.deleteCar(carId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle specific exceptions if necessary
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
