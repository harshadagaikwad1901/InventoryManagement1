package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductsUsedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsUsed.class);
        ProductsUsed productsUsed1 = new ProductsUsed();
        productsUsed1.setId(1L);
        ProductsUsed productsUsed2 = new ProductsUsed();
        productsUsed2.setId(productsUsed1.getId());
        assertThat(productsUsed1).isEqualTo(productsUsed2);
        productsUsed2.setId(2L);
        assertThat(productsUsed1).isNotEqualTo(productsUsed2);
        productsUsed1.setId(null);
        assertThat(productsUsed1).isNotEqualTo(productsUsed2);
    }
}
