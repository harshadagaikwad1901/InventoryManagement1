package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ProductsUsed;
import com.mycompany.myapp.service.dto.ProductsUsedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductsUsed} and its DTO {@link ProductsUsedDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductsUsedMapper extends EntityMapper<ProductsUsedDTO, ProductsUsed> {}
