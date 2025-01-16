package com.Antiprosopia.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarContoller {

    @Autowired
    private CarService carService;

    @GetMapping("/api/cars")
    public List<CarDTO> getCars() {
        return carService.getAllCars();
    }

}
