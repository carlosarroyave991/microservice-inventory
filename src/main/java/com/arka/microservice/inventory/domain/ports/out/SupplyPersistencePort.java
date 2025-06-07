package com.arka.microservice.inventory.domain.ports.out;


import com.arka.microservice.inventory.domain.models.SupplyModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para la persistencia de Storage
 */
public interface SupplyPersistencePort {
    Flux<SupplyModel> findAll();
    Mono<SupplyModel> findById(Long id);
    Mono<SupplyModel> save(SupplyModel model);
    Mono<SupplyModel> update(SupplyModel model);
    Mono<Void> deleteById(Long id);
}
