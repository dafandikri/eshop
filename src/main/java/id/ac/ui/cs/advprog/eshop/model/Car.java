package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
public class Car {
    private String carId;
    
    @NotBlank(message = "Car name cannot be blank")
    private String carName;
    
    @NotBlank(message = "Car color cannot be blank")
    private String carColor;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be at least 0")
    private int carQuantity;
}
