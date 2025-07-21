package com.arka.microservice.inventory.application.usecases;

import com.arka.microservice.inventory.domain.models.WarehouseAddressModel;
import com.arka.microservice.inventory.domain.ports.in.IWarehouseAddressPortUseCase;
import com.arka.microservice.inventory.domain.ports.out.WarehouseAddressPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Clase usada para implementar la logica de negocio sobre cada funcion
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseUseCaseImpl implements IWarehouseAddressPortUseCase {
    private final WarehouseAddressPersistencePort service;

    /** Servicio usado para crear un objeto de forma reactiva
     * @param model objeto con los parametros necesarios para la creacion
     * @return retorna un mono o un mono error
     */
    @Transactional
    @Override
    public Mono<WarehouseAddressModel> createWarehouse(WarehouseAddressModel model) {
        return service.save(model);
    }
}
