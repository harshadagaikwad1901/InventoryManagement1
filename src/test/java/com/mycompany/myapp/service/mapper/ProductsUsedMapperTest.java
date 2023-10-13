package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductsUsedMapperTest {

    private ProductsUsedMapper productsUsedMapper;

    @BeforeEach
    public void setUp() {
        productsUsedMapper = new ProductsUsedMapperImpl();
    }
}
