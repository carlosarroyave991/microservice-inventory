package com.arka.microservice.inventory.infraestructure.driver.rest.mapper;

import com.arka.microservice.inventory.domain.models.SupplyModel;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.req.SupplyRequestDto;
import com.arka.microservice.inventory.infraestructure.driver.rest.dto.resp.SupplyResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Interfaz que se encarga de convertir de:
 * req -> model
 * model -> resp
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ISupplyMapperDto {
    SupplyModel toModel(SupplyRequestDto requestDto);

    SupplyResponseDto toResponse(SupplyModel model);
}
