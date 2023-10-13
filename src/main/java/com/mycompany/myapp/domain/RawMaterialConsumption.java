package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A RawMaterialConsumption.
 */
@Entity
@Table(name = "raw_material_consumption")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RawMaterialConsumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "production_line")
    private Long productionLine;

    @Column(name = "raw_material_id")
    private Long rawMaterialId;

    @Column(name = "quantity_consumed")
    private Double quantityConsumed;

    @Column(name = "scrap_generated")
    private Double scrapGenerated;

    @Column(name = "total_material_cost")
    private Double totalMaterialCost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RawMaterialConsumption id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public RawMaterialConsumption projectId(Long projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public RawMaterialConsumption productId(Long productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductionLine() {
        return this.productionLine;
    }

    public RawMaterialConsumption productionLine(Long productionLine) {
        this.setProductionLine(productionLine);
        return this;
    }

    public void setProductionLine(Long productionLine) {
        this.productionLine = productionLine;
    }

    public Long getRawMaterialId() {
        return this.rawMaterialId;
    }

    public RawMaterialConsumption rawMaterialId(Long rawMaterialId) {
        this.setRawMaterialId(rawMaterialId);
        return this;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public Double getQuantityConsumed() {
        return this.quantityConsumed;
    }

    public RawMaterialConsumption quantityConsumed(Double quantityConsumed) {
        this.setQuantityConsumed(quantityConsumed);
        return this;
    }

    public void setQuantityConsumed(Double quantityConsumed) {
        this.quantityConsumed = quantityConsumed;
    }

    public Double getScrapGenerated() {
        return this.scrapGenerated;
    }

    public RawMaterialConsumption scrapGenerated(Double scrapGenerated) {
        this.setScrapGenerated(scrapGenerated);
        return this;
    }

    public void setScrapGenerated(Double scrapGenerated) {
        this.scrapGenerated = scrapGenerated;
    }

    public Double getTotalMaterialCost() {
        return this.totalMaterialCost;
    }

    public RawMaterialConsumption totalMaterialCost(Double totalMaterialCost) {
        this.setTotalMaterialCost(totalMaterialCost);
        return this;
    }

    public void setTotalMaterialCost(Double totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterialConsumption)) {
            return false;
        }
        return id != null && id.equals(((RawMaterialConsumption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialConsumption{" +
            "id=" + getId() +
            ", projectId=" + getProjectId() +
            ", productId=" + getProductId() +
            ", productionLine=" + getProductionLine() +
            ", rawMaterialId=" + getRawMaterialId() +
            ", quantityConsumed=" + getQuantityConsumed() +
            ", scrapGenerated=" + getScrapGenerated() +
            ", totalMaterialCost=" + getTotalMaterialCost() +
            "}";
    }
}
