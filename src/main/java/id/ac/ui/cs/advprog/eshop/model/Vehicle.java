package id.ac.ui.cs.advprog.eshop.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Vehicle {
    private String id;
    
    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be at least 0")
    private int quantity;
    
    // Common behaviors for all vehicles
    public boolean isAvailable() {
        return quantity > 0;
    }
}
