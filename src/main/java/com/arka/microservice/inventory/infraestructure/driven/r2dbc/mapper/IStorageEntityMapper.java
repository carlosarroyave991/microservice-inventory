package com.arka.microservice.inventory.infraestructure.driven.r2dbc.mapper;

import com.arka.microservice.inventory.domain.models.StorageModel;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.entity.StorageEntity;
import org.mapstruct.*;

/**
 * Funcion para convertir de modelo a entidad y viceversa
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface IStorageEntityMapper {

    StorageEntity toEntity(StorageModel model);

    @InheritInverseConfiguration
    StorageModel toModel(StorageEntity entity);
}
