package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductsUsedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsUsedDTO.class);
        ProductsUsedDTO productsUsedDTO1 = new ProductsUsedDTO();
        productsUsedDTO1.setId(1L);
        ProductsUsedDTO productsUsedDTO2 = new ProductsUsedDTO();
        assertThat(productsUsedDTO1).isNotEqualTo(productsUsedDTO2);
        productsUsedDTO2.setId(productsUsedDTO1.getId());
        assertThat(productsUsedDTO1).isEqualTo(productsUsedDTO2);
        productsUsedDTO2.setId(2L);
        assertThat(productsUsedDTO1).isNotEqualTo(productsUsedDTO2);
        productsUsedDTO1.setId(null);
        assertThat(productsUsedDTO1).isNotEqualTo(productsUsedDTO2);
    }
}
