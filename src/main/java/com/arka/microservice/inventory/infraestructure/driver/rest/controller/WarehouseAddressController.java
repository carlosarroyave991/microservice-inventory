package com.arka.microservice.inventory.infraestructure.driver.rest.controller;

import com.arka.microservice.inventory.domain.models.WarehouseAddressModel;
import com.arka.microservice.inventory.domain.ports.in.IWarehouseAddressPortUseCase;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.req.WarehouseRequestDto;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.resp.WarehouseResponseDto;
import com.arka.microservice.inventory.infraestructure.driver.rest.mapper.IWarehouseMapperDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
@Tag(name = "Warehouse Address Controller", description = "Endpoint para la gestion de las direcciones del storage")
public class WarehouseAddressController {
    private final IWarehouseMapperDto mapper;
    private final IWarehouseAddressPortUseCase service;

    /**
     * Endpoint para crear una relacion entre storage y address
     * @param request datos de la conexion
     * @return objeto mono o mono error 
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<WarehouseResponseDto> createWarehouse(WarehouseRequestDto request){
        WarehouseAddressModel model = mapper.toModel(request);
        return service.createWarehouse(model)
                .map(mapper::toResponse);
    }
}
