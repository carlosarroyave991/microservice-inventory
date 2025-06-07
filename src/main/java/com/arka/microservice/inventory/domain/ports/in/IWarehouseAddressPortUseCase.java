package com.arka.microservice.inventory.domain.ports.in;

import com.arka.microservice.inventory.domain.models.WarehouseAddressModel;
import reactor.core.publisher.Mono;

/**
 * Se definen las operaciones que pueden utilizara para interactuar
 * con el nucleo del sistema
 */
public interface IWarehouseAddressPortUseCase {
    Mono<WarehouseAddressModel> createWarehouse(WarehouseAddressModel model);
}
