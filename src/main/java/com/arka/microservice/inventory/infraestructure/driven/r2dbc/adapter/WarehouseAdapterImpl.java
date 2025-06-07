package com.arka.microservice.inventory.infraestructure.driven.r2dbc.adapter;

import com.arka.microservice.inventory.domain.models.WarehouseAddressModel;
import com.arka.microservice.inventory.domain.ports.out.WarehouseAddressPersistencePort;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.entity.WarehouseAddressEntity;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.mapper.IWarehouseEntityMapper;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.repository.IWarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * El adaptador se encarga de conectar ambas capas, pasando la informacion de la entidad
 * al modelo de dominio
 */
@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class WarehouseAdapterImpl implements WarehouseAddressPersistencePort {
    private final IWarehouseRepository repository;
    private final IWarehouseEntityMapper mapper;

    /** Funcion que guarda un objeto en la base de datos
     * @param model objeto a guardar
     * @return retorna un objeto mapeado para el dominio
     */
    @Override
    public Mono<WarehouseAddressModel> save(WarehouseAddressModel model) {
        WarehouseAddressEntity entity = mapper.toEntity(model);
        return repository.save(entity)
                .map(mapper::toModel);
    }
}
