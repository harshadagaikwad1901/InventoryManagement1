package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductConsumptionMapperTest {

    private ProductConsumptionMapper productConsumptionMapper;

    @BeforeEach
    public void setUp() {
        productConsumptionMapper = new ProductConsumptionMapperImpl();
    }
}
