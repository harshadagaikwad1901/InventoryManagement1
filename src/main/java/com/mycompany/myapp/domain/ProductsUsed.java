package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ProductsUsed.
 */
@Entity
@Table(name = "products_used")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductsUsed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_consumed")
    private Long productConsumed;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductsUsed id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public ProductsUsed productId(Long productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductConsumed() {
        return this.productConsumed;
    }

    public ProductsUsed productConsumed(Long productConsumed) {
        this.setProductConsumed(productConsumed);
        return this;
    }

    public void setProductConsumed(Long productConsumed) {
        this.productConsumed = productConsumed;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductsUsed)) {
            return false;
        }
        return id != null && id.equals(((ProductsUsed) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductsUsed{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", productConsumed=" + getProductConsumed() +
            "}";
    }
}
