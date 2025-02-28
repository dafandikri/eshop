package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
public class Car extends Vehicle {
    private String carId;
    
    @NotBlank(message = "Car name cannot be blank")
    private String carName;
    
    @NotBlank(message = "Car color cannot be blank")
    private String carColor;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be at least 0")
    private int carQuantity;
    
    // Override to synchronize with parent class
    @Override
    public String getId() {
        return carId;
    }
    
    @Override
    public void setId(String id) {
        this.carId = id;
    }
    
    @Override
    public String getName() {
        return carName;
    }
    
    @Override
    public void setName(String name) {
        this.carName = name;
    }
    
    @Override
    public int getQuantity() {
        return carQuantity;
    }
    
    @Override
    public void setQuantity(int quantity) {
        this.carQuantity = quantity;
    }
}
