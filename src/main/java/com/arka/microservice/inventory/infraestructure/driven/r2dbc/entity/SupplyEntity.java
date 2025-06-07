package com.arka.microservice.inventory.infraestructure.driven.r2dbc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "supply")
public class SupplyEntity {
    @Id
    private Long id;
    @Column(value = "storage_id")
    private Long storageId;
    @Column(value = "product_id")
    private Long productId;
    @Column(value = "quantity_supply")
    private Integer quantity;
    @Column("supply_date")
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
