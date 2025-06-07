package com.arka.microservice.inventory.infraestructure.driver.rest.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class StorageRequestDto {
    //El id debe ser nulo para una operación de creación
    @Null(message = "The id must be null for a create operation")
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String email;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
