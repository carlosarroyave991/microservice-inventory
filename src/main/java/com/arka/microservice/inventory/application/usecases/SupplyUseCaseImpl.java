package com.arka.microservice.inventory.application.usecases;

import com.arka.microservice.inventory.domain.exception.DataAccessException;
import com.arka.microservice.inventory.domain.exception.DuplicateResourceException;
import com.arka.microservice.inventory.domain.models.StockUpdateModel;
import com.arka.microservice.inventory.domain.models.SupplyModel;
import com.arka.microservice.inventory.domain.ports.in.ISupplyPortUseCase;
import com.arka.microservice.inventory.domain.ports.out.SupplyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Date;

import static com.arka.microservice.inventory.domain.exception.error.CommonErrorCode.*;

/**
 * Clase usada para implementar la logica de negocio sobre cada funcion
 */
@Service
@RequiredArgsConstructor
public class SupplyUseCaseImpl implements ISupplyPortUseCase {
    private final SupplyPersistencePort service;
    private final WebClient productWebClient;

    /**
     * Servicio para eiminar un objeto de forma reactiva
     * @return retrna un Mono<Void> o emite un Mono error
     */
    @Override
    public Mono<Void> deleteSupplyById(Long id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(new DuplicateResourceException(ID_NOT_FOUND)))
                .flatMap(supply -> service.deleteById(supply.getId()));
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
                .flatMap(existing -> {
                    // Guardar la cantidad anterior para calcular la diferencia
                    Integer oldQuantity = existing.getQuantity();

                    // Actualizar los campos del modelo existente
                    existing.setId(id);
                    if (supplyModel.getProductId() != null) existing.setProductId(supplyModel.getProductId());
                    if (supplyModel.getStorageId() != null) existing.setStorageId(supplyModel.getStorageId());
                    if (supplyModel.getQuantity() != null) existing.setQuantity(supplyModel.getQuantity());
                    if (supplyModel.getSupplyDate() != null) existing.setSupplyDate(supplyModel.getSupplyDate());

                    // Guardar los cambios en la base de datos
                    return service.update(existing)
                            .flatMap(savedSupply -> {
                                // Solo actualizar el stock si la cantidad ha cambiado
                                if (supplyModel.getQuantity() != null && !supplyModel.getQuantity().equals(oldQuantity)) {
                                    // Calcular la diferencia para actualizar el stock
                                    Integer quantityDifference = supplyModel.getQuantity() - oldQuantity;

                                    System.out.println("Actualizando stock en productos: " +
                                            "ProductID: " + existing.getProductId() +
                                            ", Diferencia: " + quantityDifference);

                                    return productWebClient.put()
                                            .uri("/api/product/{id}/stock", existing.getProductId())
                                            .bodyValue(new StockUpdateModel(quantityDifference))
                                            .retrieve()
                                            .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                                    response -> response.bodyToMono(String.class)
                                                            .flatMap(errorBody -> {
                                                                System.out.println("Error en la llamada al microservicio: " + response.statusCode());
                                                                System.out.println("Cuerpo del error: " + errorBody);
                                                                return Mono.error(new DuplicateResourceException(WEBCLIENT));
                                                            }))
                                            .bodyToMono(Void.class)
                                            .doOnSuccess(v -> System.out.println("Actualización de stock exitosa"))
                                            .thenReturn(savedSupply);
                                }
                                return Mono.just(savedSupply);
                            });
                })
                .onErrorResume(error -> {
                    System.out.println("Error al actualizar supply: " + error.getMessage());
                    error.printStackTrace();
                    return Mono.error(new DuplicateResourceException(WEBCLIENT));
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
    @Transactional
    @Override
    public Mono<SupplyModel> createSupply(SupplyModel supplyModel) {
        supplyModel.setSupplyDate(LocalDate.now());
        return service.save(supplyModel)
                .flatMap(savedSupply -> {
                    System.out.println("Enviando actualización de stock al microservicio de productos");
                    System.out.println("ProductID: " + supplyModel.getProductId() + ", Quantity: " + supplyModel.getQuantity());
                    
                    return productWebClient.put()
                            .uri("/api/product/{id}/stock", supplyModel.getProductId())
                            .bodyValue(new StockUpdateModel(supplyModel.getQuantity()))
                            .retrieve()
                            .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                    response -> response.bodyToMono(String.class)
                                            .flatMap(errorBody -> {
                                                System.out.println("Error en la llamada al microservicio: " + response.statusCode());
                                                System.out.println("Cuerpo del error: " + errorBody);
                                                return Mono.error(new DuplicateResourceException(WEBCLIENT));
                                            }))
                            .bodyToMono(Void.class)
                            .doOnSuccess(v -> System.out.println("Actualización de stock exitosa"))
                            .thenReturn(savedSupply);
                })
                .onErrorResume(error -> {
                    System.out.println("Error al crear supply: " + error.getMessage());
                    error.printStackTrace();
                    return Mono.error(new DuplicateResourceException(WEBCLIENT));
                });
    }
}
