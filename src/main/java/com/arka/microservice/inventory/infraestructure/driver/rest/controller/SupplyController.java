package com.arka.microservice.inventory.infraestructure.driver.rest.controller;

import com.arka.microservice.inventory.domain.models.StorageModel;
import com.arka.microservice.inventory.domain.models.SupplyModel;
import com.arka.microservice.inventory.domain.ports.in.ISupplyPortUseCase;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.req.SupplyRequestDto;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.resp.StorageResponseDto;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.resp.SupplyResponseDto;
import com.arka.microservice.inventory.infraestructure.driver.rest.mapper.ISupplyMapperDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/supply")
@RequiredArgsConstructor
@Tag(name = "Supply Controller", description = "Endpoint para la gestion del abastecimiento")
public class SupplyController {
    private final ISupplyPortUseCase service;
    private final ISupplyMapperDto mapper;

    /**
     * Endpoint para obtener todos los supply
     * @return flux que emite los storages transformados en dto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<SupplyResponseDto> getAll(){
        return service.getAllSupply()
                .map(mapper::toResponse);
    }

    /**
     * Endpoint para obtener un objeto especifico
     * @param id identificador del objeto
     * @return objeto creado en forma de dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<SupplyResponseDto> findSupplyById(@PathVariable("id")Long id){
        return service.getSupplyById(id)
                .map(mapper::toResponse);
    }

    /**
     * Endpoint para crear un supply
     * La validación de datos (por ejemplo, @Valid) se realiza en el DTO recibido.
     * @param request datos del supply
     * @return mono creado en forma de dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SupplyResponseDto> createSupply(@Valid @RequestBody SupplyRequestDto request){
        SupplyModel model = mapper.toModel(request);
        return service.createSupply(model)
                .map(mapper::toResponse);
    }

    /**
     * Endpoint para actualizar un supply
     * @param id identificador del objeto
     * @param request datos del supply
     * @return objeto actualizado en forma de dto
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<SupplyResponseDto> updateSupply(@RequestBody SupplyRequestDto request, @PathVariable("id")Long id){
        SupplyModel model = mapper.toModel(request);
        return service.updateSupply(model, id)
                .map(mapper::toResponse);
    }

    /**
     * Endpoint para eliminar un supply
     * @param id identificador del objeto
     * @return Un Mono vacío que indica que la operación se completó.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteSupply(@PathVariable("id")Long id){
        return service.deleteSupplyById(id);
    }
}