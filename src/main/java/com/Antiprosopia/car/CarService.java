package com.Antiprosopia.car;


import java.util.List;

public interface CarService {
    List<CarDTO> searchCars(String brand, String model, Double maxPrice);

    CarDTO createCar(CarDTO carDTO);
    CarDTO updateCar(Integer carId, CarDTO carDTO);
    CarDTO getCarById(Integer carId);
    List<CarDTO> getAllCars();
    void deleteCar(Integer carId);
}
