package com.arka.microservice.inventory.infraestructure.driven.r2dbc.mapper;


import com.arka.microservice.inventory.domain.models.SupplyModel;
import com.arka.microservice.inventory.infraestructure.driven.r2dbc.entity.SupplyEntity;
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
public interface ISupplyEntityMapper {
    SupplyModel toModel(SupplyEntity entity);

    @InheritInverseConfiguration
    SupplyEntity toEntity(SupplyModel model);
}
