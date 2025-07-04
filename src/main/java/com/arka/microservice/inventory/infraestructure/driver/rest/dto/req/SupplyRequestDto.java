package com.arka.microservice.inventory.infraestructure.driver.rest.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
public class SupplyRequestDto {
    //El id debe ser nulo para una operación de creación
    @Null(message = "the id must be null for a create operation")
    private Long id;// Opcional para 'save', necesario para 'update'
    @NotNull
    private Long storageId;
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }
}
