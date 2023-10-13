package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.TotalConsumption;
import com.mycompany.myapp.service.dto.TotalConsumptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TotalConsumption} and its DTO {@link TotalConsumptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TotalConsumptionMapper extends EntityMapper<TotalConsumptionDTO, TotalConsumption> {}
