package com.arka.microservice.inventory.infraestructure.driven.r2dbc.mapper;

import com.arka.microservice.inventory.domain.models.WarehouseAddressModel;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.entity.WarehouseAddressEntity;
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
public interface IWarehouseEntityMapper {

    WarehouseAddressEntity toEntity(WarehouseAddressModel model);

    @InheritInverseConfiguration
    WarehouseAddressModel toModel(WarehouseAddressEntity entity);
}
