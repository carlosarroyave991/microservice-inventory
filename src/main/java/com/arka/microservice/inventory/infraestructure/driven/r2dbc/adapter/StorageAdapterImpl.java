package com.arka.microservice.inventory.infraestructure.driven.r2dbc.adapter;


import com.arka.microservice.inventory.domain.models.StorageModel;
import com.arka.microservice.inventory.domain.ports.out.StoragePersistencePort;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.entity.StorageEntity;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.mapper.IStorageEntityMapper;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.repository.IStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * El adaptador se encargara de conectar ambas capas, pasando la informacion de la entidad
 * al modelo de dominio.
 */
@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class StorageAdapterImpl implements StoragePersistencePort {
    private final IStorageRepository repository;
    private final IStorageEntityMapper mapper;

    /**
     * Funcion que consulta todos los objetos y los mapea a modelo.
     * @return retorna todos los objetos mapeados para el dominio
     */
    @Override
    public Flux<StorageModel> findAll() {
        return repository.findAll()
                .map(mapper::toModel);
    }

    /**
     * Funcion que consulta un objeto por id
     * @param id identificador del objeto a buscar
     * @return retorna un objeto mapeado para dominio
     */
    @Override
    public Mono<StorageModel> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toModel);
    }

    /**
     * Funcion que guarda un objeto
     * @param model objeto a guardar
     * @return retorna un objeto mapeado para el dominio
     */
    @Override
    public Mono<StorageModel> save(StorageModel model) {
        StorageEntity entity = mapper.toEntity(model);
        return repository.save(entity)
                .map(mapper::toModel);
    }

    /**
     * Funcion actualiza un objeto existente. Se busca por id
     * @param model objeto a actualizar, contiene un id en sus atributos
     * @return retorna un objeto mapeado para el dominio
     */
    @Override
    public Mono<StorageModel> update(StorageModel model) {
        StorageEntity entity = mapper.toEntity(model);
        return repository.save(entity)
                .map(mapper::toModel);
    }

    /**
     * Funcion que eliminar un objeto dado su id y retorna el Mono<Void> correspondiente.
     * @param id identificador del objeto a eliminar
     */
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}
