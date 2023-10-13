package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PurchaseRequest;
import com.mycompany.myapp.service.dto.PurchaseRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseRequest} and its DTO {@link PurchaseRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface PurchaseRequestMapper extends EntityMapper<PurchaseRequestDTO, PurchaseRequest> {}
