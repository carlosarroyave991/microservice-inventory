package com.arka.microservice.inventory.domain.ports.in;

import com.arka.microservice.inventory.domain.models.StorageModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Se definen las operaciones que pueden utilizara para interactuar
 * con el nucleo del sistema
 */
public interface IStoragePortUseCase {
    Flux<StorageModel> getAllStorage();
    Mono<StorageModel> createStorage(StorageModel model);
    Mono<StorageModel> updateStorage(StorageModel model, Long storageId);
    Mono<StorageModel> getStorageById(Long storageId);
    Mono<Void> deleteStorage(Long storageId);
}
