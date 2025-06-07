package com.arka.microservice.inventory.infraestructure.driver.rest.controller;

import com.arka.microservice.inventory.domain.models.StorageModel;
import com.arka.microservice.inventory.domain.ports.in.IStoragePortUseCase;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.req.StorageRequestDto;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.resp.StorageResponseDto;
import com.arka.microservice.inventory.infraestructure.driver.rest.mapper.IStorageMapperDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
@Tag(name = "Storage Controller", description = "Endpoint para la gestion el almacen")
public class StorageController {
    private final IStoragePortUseCase service;
    private final IStorageMapperDto mapper;

    /**
     * Endpoint para obtener todos los storages
     * @return flux que emite los storages transformados en dto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<StorageResponseDto> getAllStorage(){
        return service.getAllStorage()
                .map(mapper::toResponse);
    }

    /**
     * Endpoint para obtener un objeto especifico
     * @param id identificador del objeto
     * @return objeto creado en forma de dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<StorageResponseDto> getStorageById(@PathVariable("id") Long id){
        return service.getStorageById(id)
                .map(mapper::toResponse);
    }

    /**
     * Endpoint para crear un storage
     * La validación de datos (por ejemplo, @Valid) se realiza en el DTO recibido.
     * @param request datos del storage
     * @return mono creado en forma de dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<StorageResponseDto> createStorage(@Valid @RequestBody StorageRequestDto request){
        StorageModel model = mapper.toModel(request);
        return service.createStorage(model)
                .map(mapper::toResponse);
    }

    /**
     * Endpoint para actualizar un storage
     * @param id identificador del objeto
     * @param request datos del storage
     * @return objeto actualizado en forma de dto
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<StorageResponseDto> updateStorage(@PathVariable("id") Long id, @Valid @RequestBody StorageRequestDto request){
        StorageModel model = mapper.toModel(request);
        return service.updateStorage(model, id)
                .map(mapper::toResponse);
    }

    /**
     * Endpoint para eliminar un storage
     * @param id identificador del objeto
     * @return Un Mono vacío que indica que la operación se completó.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteStorage(@PathVariable("id") Long id){
        return service.deleteStorage(id);
    }
}
