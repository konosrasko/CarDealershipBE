package com.Antiprosopia.car;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;


    @Override
    public List<CarDTO> searchCars(String brand, String model, Double maxPrice) {
        List<Car> cars = carRepository.searchCars(brand, model, maxPrice);
        return cars.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Car car = new Car();
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setFuelType(carDTO.getFuelType());
        car.setEngineCapacity(carDTO.getEngineCapacity());
        car.setSeats(carDTO.getSeats());
        car.setPrice(carDTO.getPrice());
        car.setAdditionalInfo(carDTO.getAdditionalInfo());
        car.setQuantity(carDTO.getQuantity());
        car.setDealershipId(carDTO.getDealershipId());
        Car savedCar = carRepository.save(car);
        return mapToDTO(savedCar);
    }

    @Override
    public CarDTO updateCar(Integer carId, CarDTO carDTO) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setFuelType(carDTO.getFuelType());
        car.setEngineCapacity(carDTO.getEngineCapacity());
        car.setSeats(carDTO.getSeats());
        car.setPrice(carDTO.getPrice());
        car.setAdditionalInfo(carDTO.getAdditionalInfo());
        car.setQuantity(carDTO.getQuantity());
        car.setDealershipId(carDTO.getDealershipId());
        Car updatedCar = carRepository.save(car);
        return mapToDTO(updatedCar);
    }

    @Override
    public CarDTO getCarById(Integer carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        return mapToDTO(car);
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Integer carId) {
        carRepository.deleteById(carId);
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
