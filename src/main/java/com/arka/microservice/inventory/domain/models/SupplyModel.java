package com.arka.microservice.inventory.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

/**
 * Entidad creada para el dominio.
 * La idea es que sea inmutable para garantizar consistencia.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplyModel {
    private Long id;
    private Long storageId;
    private Long productId;
    private Integer quantity;
    private LocalDate supplyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(LocalDate supplyDate) {
        this.supplyDate = supplyDate;
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
