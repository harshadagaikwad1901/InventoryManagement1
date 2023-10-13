package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.RawMaterialConsumption;
import com.mycompany.myapp.service.dto.RawMaterialConsumptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RawMaterialConsumption} and its DTO {@link RawMaterialConsumptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RawMaterialConsumptionMapper extends EntityMapper<RawMaterialConsumptionDTO, RawMaterialConsumption> {}
