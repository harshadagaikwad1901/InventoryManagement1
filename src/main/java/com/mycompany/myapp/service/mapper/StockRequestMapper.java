package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.StockRequest;
import com.mycompany.myapp.service.dto.StockRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockRequest} and its DTO {@link StockRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface StockRequestMapper extends EntityMapper<StockRequestDTO, StockRequest> {}
