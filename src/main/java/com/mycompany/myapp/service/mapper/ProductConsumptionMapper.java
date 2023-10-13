package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ProductConsumption;
import com.mycompany.myapp.service.dto.ProductConsumptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductConsumption} and its DTO {@link ProductConsumptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductConsumptionMapper extends EntityMapper<ProductConsumptionDTO, ProductConsumption> {}
