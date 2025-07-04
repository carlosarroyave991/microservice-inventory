package com.arka.microservice.inventory.application.usecases;

import com.arka.microservice.inventory.domain.exception.DuplicateResourceException;
import com.arka.microservice.inventory.domain.models.SupplyModel;
import com.arka.microservice.inventory.domain.ports.out.SupplyPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupplyUseCaseImplTest {

    @Mock
    private SupplyPersistencePort supplyPersistencePort;

    @Mock
    private WebClient productWebClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private SupplyUseCaseImpl supplyUseCase;

    private SupplyModel supplyModel;

    @BeforeEach
    void setUp() {
        supplyModel = SupplyModel.builder()
                .id(1L)
                .storageId(1L)
                .productId(1L)
                .quantity(100)
                .supplyDate(LocalDate.now())
                .build();
    }

    @Test
    void getAllSupply_ShouldReturnAllSupplies_WhenSuppliesExist() {
        when(supplyPersistencePort.findAll()).thenReturn(Flux.just(supplyModel));

        StepVerifier.create(supplyUseCase.getAllSupply())
                .expectNext(supplyModel)
                .verifyComplete();
    }

    @Test
    void getAllSupply_ShouldThrowException_WhenNoSuppliesExist() {
        when(supplyPersistencePort.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(supplyUseCase.getAllSupply())
                .expectError(DuplicateResourceException.class)
                .verify();
    }

    @Test
    void createSupply_ShouldCreateSupply_WhenWebClientSucceeds() {
        when(supplyPersistencePort.save(any(SupplyModel.class))).thenReturn(Mono.just(supplyModel));
        when(productWebClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/api/product/{id}/stock", 1L)).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        StepVerifier.create(supplyUseCase.createSupply(supplyModel))
                .expectNext(supplyModel)
                .verifyComplete();
    }

    @Test
    void getSupplyById_ShouldReturnSupply_WhenSupplyExists() {
        when(supplyPersistencePort.findById(1L)).thenReturn(Mono.just(supplyModel));

        StepVerifier.create(supplyUseCase.getSupplyById(1L))
                .expectNext(supplyModel)
                .verifyComplete();
    }

    @Test
    void getSupplyById_ShouldThrowException_WhenSupplyNotFound() {
        when(supplyPersistencePort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(supplyUseCase.getSupplyById(1L))
                .expectError(DuplicateResourceException.class)
                .verify();
    }

    @Test
    void updateSupply_ShouldUpdateSupply_WhenSupplyExists() {
        SupplyModel updateModel = SupplyModel.builder()
                .quantity(150)
                .build();

        when(supplyPersistencePort.findById(1L)).thenReturn(Mono.just(supplyModel));
        when(supplyPersistencePort.update(any(SupplyModel.class))).thenReturn(Mono.just(supplyModel));
        when(productWebClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/api/product/{id}/stock", 1L)).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        StepVerifier.create(supplyUseCase.updateSupply(updateModel, 1L))
                .expectNext(supplyModel)
                .verifyComplete();
    }

    @Test
    void updateSupply_ShouldThrowException_WhenSupplyNotFound() {
        SupplyModel updateModel = SupplyModel.builder().quantity(150).build();
        when(supplyPersistencePort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(supplyUseCase.updateSupply(updateModel, 1L))
                .expectError(DuplicateResourceException.class)
                .verify();
    }

    @Test
    void deleteSupplyById_ShouldDeleteSupply_WhenSupplyExists() {
        when(supplyPersistencePort.findById(1L)).thenReturn(Mono.just(supplyModel));
        when(supplyPersistencePort.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(supplyUseCase.deleteSupplyById(1L))
                .verifyComplete();
    }

    @Test
    void deleteSupplyById_ShouldThrowException_WhenSupplyNotFound() {
        when(supplyPersistencePort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(supplyUseCase.deleteSupplyById(1L))
                .expectError(DuplicateResourceException.class)
                .verify();
    }
}