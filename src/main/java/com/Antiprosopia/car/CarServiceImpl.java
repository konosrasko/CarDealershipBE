package com.Antiprosopia.car;


import com.Antiprosopia.dealership.Dealership;
import com.Antiprosopia.dealership.DealershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DealershipRepository dealershipRepository;


    @Override
    public List<CarDTO> searchCars(String brand, String model, Double maxPrice) {
        List<Car> cars = carRepository.searchCars(brand, model, maxPrice);
        return cars.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Dealership dealership = dealershipRepository.findById(carDTO.getDealershipId())
                .orElseThrow(() -> new IllegalArgumentException("Dealership ID is invalid"));
        Car car = new Car();
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setFuelType(carDTO.getFuelType());
        car.setEngineCapacity(carDTO.getEngineCapacity());
        car.setSeats(carDTO.getSeats());
        car.setPrice(carDTO.getPrice());
        car.setAdditionalInfo(carDTO.getAdditionalInfo());
        car.setQuantity(carDTO.getQuantity());
        car.setDealership(dealership);
        Car savedCar = carRepository.save(car);
        return mapToDTO(savedCar);
    }

    @Override
    public CarDTO updateCar(Integer carId, Car carUpdated) {
        Car updatedCar = carRepository.save(carUpdated);
        return mapToDTO(updatedCar);
    }

    @Override
    public Car getCarById(Integer carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> getAllCarsForDealership(Integer dealerId) {
        return carRepository.getAllCarsForDealership(dealerId);
    }

    @Override
    public void deleteCar(Integer carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarException("Car with ID " + carId + " not found"));
        carRepository.delete(car);
    }

    @Override
    public boolean isCarOutOfStock(Integer carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        return car.getQuantity() == 0;
    }

    @Override
    public void purchaseCar(Integer carId) {
        Car car = carRepository.findById(carId).get();
        car.setQuantity(car.getQuantity() - 1);
        carRepository.save(car);
    }


    private CarDTO mapToDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setCarId(car.getCarId());
        carDTO.setBrand(car.getBrand());
        carDTO.setModel(car.getModel());
        carDTO.setFuelType(car.getFuelType());
        carDTO.setEngineCapacity(car.getEngineCapacity());
        carDTO.setSeats(car.getSeats());
        carDTO.setPrice(car.getPrice());
        carDTO.setAdditionalInfo(car.getAdditionalInfo());
        carDTO.setQuantity(car.getQuantity());
        carDTO.setDealershipId(car.getDealership().getDealershipId());
        return carDTO;
    }
}
