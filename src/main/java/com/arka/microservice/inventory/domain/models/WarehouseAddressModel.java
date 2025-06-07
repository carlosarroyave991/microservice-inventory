package com.arka.microservice.inventory.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

/**
 * Entidad creada para el dominio.
 * La idea es que sea inmutable para garantizar consistencia.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseAddressModel {
    private Long id;
    private Long storageId;
    private Long addressId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
