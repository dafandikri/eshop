package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepository implements CarRepositoryInterface {
    private List<Car> carData = new ArrayList<>();
    
    @Autowired
    private IdGenerator idGenerator;

    @Override
    public Car create(Car car) {
        if (car.getId() == null) { // Updated to use parent class method
            car.setId(idGenerator.generateId());
        }
        carData.add(car);
        return car;
    }

    @Override
    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    @Override
    public Car findById(String id) {
        for (Car car : carData) {
            if (car.getId().equals(id)) { // Updated to use parent class method
                return car;
            }
        }
        return null;
    }

    @Override
    public Car update(String id, Car updatedCar) {
        for (int i = 0; i < carData.size(); i++) {
            Car car = carData.get(i);
            if (car.getId().equals(id)) { // Updated to use parent class method
                // Update using parent class methods
                car.setName(updatedCar.getName());
                car.setQuantity(updatedCar.getQuantity());
                car.setCarColor(updatedCar.getCarColor());
                return car;
            }
        }
        return null; // Handle the case where the car is not found
    }

    @Override
    public void delete(String id) {
        carData.removeIf(car -> car.getId().equals(id)); // Updated to use parent class method
    }
}
