package com.arka.microservice.inventory.domain.ports.out;

import com.arka.microservice.inventory.domain.models.WarehouseAddressModel;
import reactor.core.publisher.Mono;

/**
 * Se encarga de persistir los datos en la base de datos
 */
public interface WarehouseAddressPersistencePort {
    Mono<WarehouseAddressModel> save(WarehouseAddressModel model);
}
