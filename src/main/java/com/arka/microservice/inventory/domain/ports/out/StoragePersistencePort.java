package com.arka.microservice.inventory.domain.ports.out;

import com.arka.microservice.inventory.domain.models.StorageModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para la persistencia de Storage
 */
public interface StoragePersistencePort {
    Flux<StorageModel> findAll();
    Mono<StorageModel> findById(Long id);
    Mono<StorageModel> save(StorageModel model);
    Mono<StorageModel> update(StorageModel model);
    Mono<Void> deleteById(Long id);
}