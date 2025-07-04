package com.arka.microservice.inventory.application.usecases;

import com.arka.microservice.inventory.domain.models.WarehouseAddressModel;
import com.arka.microservice.inventory.domain.ports.out.WarehouseAddressPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarehouseUseCaseImplTest {

    @Mock
    private WarehouseAddressPersistencePort warehouseAddressPersistencePort;

    @InjectMocks
    private WarehouseUseCaseImpl warehouseUseCase;

    private WarehouseAddressModel warehouseAddressModel;

    @BeforeEach
    void setUp() {
        warehouseAddressModel = WarehouseAddressModel.builder()
                .id(1L)
                .storageId(1L)
                .addressId(1L)
                .build();
    }

    @Test
    void createWarehouse_ShouldCreateWarehouse_WhenValidModel() {
        when(warehouseAddressPersistencePort.save(any(WarehouseAddressModel.class)))
                .thenReturn(Mono.just(warehouseAddressModel));

        StepVerifier.create(warehouseUseCase.createWarehouse(warehouseAddressModel))
                .expectNext(warehouseAddressModel)
                .verifyComplete();
    }
}