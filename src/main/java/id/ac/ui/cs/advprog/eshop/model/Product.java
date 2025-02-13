package id.ac.ui.cs.advprog.eshop.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String productId;

    @NotBlank(message = "Name cannot be blank")
    private String productName;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer productQuantity;
}