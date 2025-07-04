package com.arka.microservice.inventory.application.usecases;

import com.arka.microservice.inventory.domain.exception.DuplicateResourceException;
import com.arka.microservice.inventory.domain.exception.ValidationException;
import com.arka.microservice.inventory.domain.models.StorageModel;
import com.arka.microservice.inventory.domain.ports.out.StoragePersistencePort;
import com.arka.microservice.inventory.domain.service.PhoneValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageUseCaseImplTest {

    @Mock
    private StoragePersistencePort storagePersistencePort;

    @Mock
    private PhoneValidationService phoneValidationService;

    @InjectMocks
    private StorageUseCaseImpl storageUseCase;

    private StorageModel storageModel;

    @BeforeEach
    void setUp() {
        storageModel = StorageModel.builder()
                .id(1L)
                .name("Test Storage")
                .email("test@example.com")
                .phone("1234567890")
                .build();
    }

    @Test
    void getAllStorage_ShouldReturnAllStorages_WhenStoragesExist() {
        when(storagePersistencePort.findAll()).thenReturn(Flux.just(storageModel));

        StepVerifier.create(storageUseCase.getAllStorage())
                .expectNext(storageModel)
                .verifyComplete();
    }

    @Test
    void getAllStorage_ShouldThrowException_WhenNoStoragesExist() {
        when(storagePersistencePort.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(storageUseCase.getAllStorage())
                .expectError(DuplicateResourceException.class)
                .verify();
    }

    @Test
    void createStorage_ShouldCreateStorage_WhenPhoneIsValid() {
        when(phoneValidationService.isValidPhone(storageModel.getPhone())).thenReturn(true);
        when(storagePersistencePort.save(any(StorageModel.class))).thenReturn(Mono.just(storageModel));

        StepVerifier.create(storageUseCase.createStorage(storageModel))
                .expectNext(storageModel)
                .verifyComplete();
    }

    @Test
    void createStorage_ShouldThrowValidationException_WhenPhoneIsInvalid() {
        when(phoneValidationService.isValidPhone(storageModel.getPhone())).thenReturn(false);

        StepVerifier.create(storageUseCase.createStorage(storageModel))
                .expectError(ValidationException.class)
                .verify();
    }

    @Test
    void updateStorage_ShouldUpdateStorage_WhenStorageExists() {
        StorageModel updateModel = StorageModel.builder()
                .name("Updated Storage")
                .email("updated@example.com")
                .phone("0987654321")
                .build();

        when(storagePersistencePort.findById(1L)).thenReturn(Mono.just(storageModel));
        when(storagePersistencePort.update(any(StorageModel.class))).thenReturn(Mono.just(storageModel));

        StepVerifier.create(storageUseCase.updateStorage(updateModel, 1L))
                .expectNext(storageModel)
                .verifyComplete();
    }

    @Test
    void updateStorage_ShouldThrowException_WhenStorageNotFound() {
        StorageModel updateModel = StorageModel.builder().name("Updated Storage").build();
        when(storagePersistencePort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(storageUseCase.updateStorage(updateModel, 1L))
                .expectError(DuplicateResourceException.class)
                .verify();
    }

    @Test
    void getStorageById_ShouldReturnStorage_WhenStorageExists() {
        when(storagePersistencePort.findById(1L)).thenReturn(Mono.just(storageModel));

        StepVerifier.create(storageUseCase.getStorageById(1L))
                .expectNext(storageModel)
                .verifyComplete();
    }

    @Test
    void getStorageById_ShouldThrowException_WhenStorageNotFound() {
        when(storagePersistencePort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(storageUseCase.getStorageById(1L))
                .expectError(DuplicateResourceException.class)
                .verify();
    }

    @Test
    void deleteStorage_ShouldDeleteStorage_WhenStorageExists() {
        when(storagePersistencePort.findById(1L)).thenReturn(Mono.just(storageModel));
        when(storagePersistencePort.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(storageUseCase.deleteStorage(1L))
                .verifyComplete();
    }

    @Test
    void deleteStorage_ShouldThrowException_WhenStorageNotFound() {
        when(storagePersistencePort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(storageUseCase.deleteStorage(1L))
                .expectError(DuplicateResourceException.class)
                .verify();
    }
}