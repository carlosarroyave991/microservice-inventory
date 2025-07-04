package com.arka.microservice.inventory.application.usecases;

import com.arka.microservice.inventory.domain.exception.DuplicateResourceException;
import com.arka.microservice.inventory.domain.exception.ValidationException;
import com.arka.microservice.inventory.domain.models.StorageModel;
import com.arka.microservice.inventory.domain.ports.in.IStoragePortUseCase;
import com.arka.microservice.inventory.domain.ports.out.StoragePersistencePort;
import com.arka.microservice.inventory.domain.service.PhoneValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.arka.microservice.inventory.domain.exception.error.CommonErrorCode.*;

/**
 * Clase usada para implementar los casos de uso de la entidad Storage
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StorageUseCaseImpl implements IStoragePortUseCase {
    private final StoragePersistencePort service;
    private final PhoneValidationService phoneValidationService;

    /** Servicio que obtiene todos los objetos de forma reactiva
     * @return un flux que emite o un flux error si la lista es vacia
     */
    @Override
    public Flux<StorageModel> getAllStorage() {
        return service.findAll()
                .switchIfEmpty(Flux.error(new DuplicateResourceException(DB_EMPTY)));
    }

    /** Servicio para crear un objeto de forma reactiva
     * @param model objeto con los parametros
     * @return retorna un mono o un mono vacio en caso de error
     */
    @Override
    public Mono<StorageModel> createStorage(StorageModel model) {
        if (!phoneValidationService.isValidPhone(model.getPhone())){
            return Mono.error(new ValidationException(INVALID_PHONE));
        }
        return service.save(model);
    }

    /** Servicio para actualizar un objeto
     * @param model model objeto con los parametros
     * @param storageId identificador del objeto a buscar
     * @return retorna un mono o un mono error en caso de vacio
     */
    @Override
    public Mono<StorageModel> updateStorage(StorageModel model, Long storageId) {
        return service.findById(storageId)
                .switchIfEmpty(Mono.error(new DuplicateResourceException(ID_NOT_FOUND)))
                .flatMap(existing ->{
                    existing.setId(storageId);
                    if (model.getName() != null)existing.setName(model.getName());
                    if (model.getEmail() != null)existing.setEmail(model.getEmail());
                    if (model.getPhone() != null)existing.setPhone(model.getPhone());
                    return service.update(existing);
                });
    }

    /** Servicio que busca por un objeto por identificador
     * @param storageId identificador del objeto a buscar
     * @return retorna un mono o un mono error en caso de vacio
     */
    @Override
    public Mono<StorageModel> getStorageById(Long storageId) {
        return service.findById(storageId)
                .switchIfEmpty(Mono.error(new DuplicateResourceException(ID_NOT_FOUND)));
    }

    /**
     * Servicio para eliminar un objeto
     * @param storageId identificador del objeto
     * @return un mono void o emite un mono error
     */
    @Override
    public Mono<Void> deleteStorage(Long storageId) {
        return service.findById(storageId)
                .switchIfEmpty(Mono.error(new DuplicateResourceException(ID_NOT_FOUND)))
                .flatMap(storage -> service.deleteById(storage.getId()));
    }
}
