package com.arka.microservice.inventory.domain.ports.in;

import com.arka.microservice.inventory.domain.models.SupplyModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Se definen las operaciones que pueden utilizara para interactuar
 * con el nucleo del sistema
 */
public interface ISupplyPortUseCase {
    Mono<SupplyModel> getSupplyById(Long id);
    Mono<SupplyModel> createSupply(SupplyModel supplyModel);
    Mono<SupplyModel> updateSupply(SupplyModel supplyModel, Long id);
    Flux<SupplyModel> getAllSupply();
    Mono<Void> deleteSupplyById(Long id);
}
