package com.arka.microservice.inventory.application.usecases;

import com.arka.microservice.inventory.domain.exception.DuplicateResourceException;
import com.arka.microservice.inventory.domain.models.SupplyModel;
import com.arka.microservice.inventory.domain.ports.in.ISupplyPortUseCase;
import com.arka.microservice.inventory.domain.ports.out.SupplyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.arka.microservice.inventory.domain.exception.error.CommonErrorCode.DB_EMPTY;
import static com.arka.microservice.inventory.domain.exception.error.CommonErrorCode.ID_NOT_FOUND;

/**
 * Clase usada para implementar la logica de negocio sobre cada funcion
 */
@Service
@RequiredArgsConstructor
public class SupplyUseCaseImpl implements ISupplyPortUseCase {
    private final SupplyPersistencePort service;

    /**
     * Servicio para eiminar un objeto de forma reactiva
     * @return retrna un Mono<Void> o emite un Mono error
     */
    @Override
    public Mono<Void> deleteSupplyById(Long id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(new DuplicateResourceException(ID_NOT_FOUND)))
                .then(service.deleteById(id));
    }

    /**
     * Servicio que obtiene todas las address existentes de forma reactiva.
     * @return retorna un Flux que emite cada address o error si la lista está vacía.
     */
    @Override
    public Flux<SupplyModel> getAllSupply() {
        return service.findAll()
                .switchIfEmpty(Flux.error(new DuplicateResourceException(DB_EMPTY)));
    }

    /** Servicio para actualizar un objeto
     * @param supplyModel model objeto con los parametros
     * @param id identificador del objeto a buscar
     * @return retorna un mono o un mono error en caso de vacio
     */
    @Override
    public Mono<SupplyModel> updateSupply(SupplyModel supplyModel, Long id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(new DuplicateResourceException(ID_NOT_FOUND)))
                .flatMap(existing ->{
                    existing.setId(id);
                    if (supplyModel.getProductId() != null)existing.setProductId(supplyModel.getProductId());
                    if (supplyModel.getStorageId() != null)existing.setStorageId(supplyModel.getStorageId());
                    if (supplyModel.getQuantity() != null)existing.setQuantity(supplyModel.getQuantity());
                    if (supplyModel.getSupplyDate() != null)existing.setSupplyDate(supplyModel.getSupplyDate());

                    return service.save(existing);
                });
    }

    /** Servicio que busca por un objeto por identificador
     * @param id identificador del objeto a buscar
     * @return retorna un mono o un mono error en caso de vacio
     */
    @Override
    public Mono<SupplyModel> getSupplyById(Long id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(new DuplicateResourceException(ID_NOT_FOUND)));
    }

    /** Servicio para crear un objeto de forma reactiva
     * @param supplyModel objeto con los parametros
     * @return retorna un mono o un mono vacio en caso de error
     */
    @Override
    public Mono<SupplyModel> createSupply(SupplyModel supplyModel) {
        return service.save(supplyModel);
    }
}
