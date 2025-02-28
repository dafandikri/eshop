package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
public class Car extends Vehicle {
    @NotBlank(message = "Car color cannot be blank")
    private String carColor;
    
    // For backward compatibility - delegates to parent methods
    public String getCarId() {
        return getId();
    }
    
    public void setCarId(String id) {
        setId(id);
    }
    
    public String getCarName() {
        return getName();
    }
    
    public void setCarName(String name) {
        setName(name);
    }
    
    public int getCarQuantity() {
        return getQuantity();
    }
    
    public void setCarQuantity(int quantity) {
        setQuantity(quantity);
    }
}
