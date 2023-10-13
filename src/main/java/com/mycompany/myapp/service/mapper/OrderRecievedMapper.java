package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.OrderRecieved;
import com.mycompany.myapp.service.dto.OrderRecievedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderRecieved} and its DTO {@link OrderRecievedDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderRecievedMapper extends EntityMapper<OrderRecievedDTO, OrderRecieved> {}
