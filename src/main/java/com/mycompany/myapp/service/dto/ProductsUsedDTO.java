package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProductsUsed} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductsUsedDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productConsumed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductConsumed() {
        return productConsumed;
    }

    public void setProductConsumed(Long productConsumed) {
        this.productConsumed = productConsumed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductsUsedDTO)) {
            return false;
        }

        ProductsUsedDTO productsUsedDTO = (ProductsUsedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productsUsedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductsUsedDTO{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", productConsumed=" + getProductConsumed() +
            "}";
    }
}
