package com.arka.microservice.inventory.infraestructure.driven.r2dbc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "storage")
public class StorageEntity {
    @Id
    private Long id;
    private String name;
    private String phone;
    private String email;

    @Transient
    @MappedCollection(idColumn = "storage_id")
    private List<SupplyEntity> supplyEntityList;

    @Transient
    @MappedCollection(idColumn = "storage_id")
    private List<WarehouseAddressEntity> warehouseAddressEntityList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<WarehouseAddressEntity> getWarehouseAddressEntityList() {
        return warehouseAddressEntityList;
    }

    public void setWarehouseAddressEntityList(List<WarehouseAddressEntity> warehouseAddressEntityList) {
        this.warehouseAddressEntityList = warehouseAddressEntityList;
    }

    public List<SupplyEntity> getSupplyEntityList() {
        return supplyEntityList;
    }

    public void setSupplyEntityList(List<SupplyEntity> supplyEntityList) {
        this.supplyEntityList = supplyEntityList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
