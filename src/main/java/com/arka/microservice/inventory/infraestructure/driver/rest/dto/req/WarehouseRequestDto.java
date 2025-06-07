package com.arka.microservice.inventory.infraestructure.driver.rest.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class WarehouseRequestDto {
    //El id debe ser nulo para una operación de creación
    @Null(message = "the id must be null for a create operation")
    private Long id;// Opcional para 'save', necesario para 'update'
    @NotNull
    private Long storageId;
    @NotNull
    private Long addressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }
}
