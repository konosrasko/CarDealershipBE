package com.Antiprosopia.car;


import java.util.List;

public interface CarService {
    List<CarDTO> searchCars(String brand, String model, Double maxPrice);

    CarDTO createCar(CarDTO carDTO);
    CarDTO updateCar(Integer carId, Car carUpdated);
    Car getCarById(Integer carId);
    List<CarDTO> getAllCars();
    List<Car> getAllCarsForDealership(Integer dealerId);
    void deleteCar(Integer carId);

    boolean isCarOutOfStock(Integer carId);

    void purchaseCar(Integer carId);
}
