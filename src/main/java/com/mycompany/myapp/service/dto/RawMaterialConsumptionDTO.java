package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.RawMaterialConsumption} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RawMaterialConsumptionDTO implements Serializable {

    private Long id;

    private Long projectId;

    private Long productId;

    private Long productionLine;

    private Long rawMaterialId;

    private Double quantityConsumed;

    private Double scrapGenerated;

    private Double totalMaterialCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(Long productionLine) {
        this.productionLine = productionLine;
    }

    public Long getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public Double getQuantityConsumed() {
        return quantityConsumed;
    }

    public void setQuantityConsumed(Double quantityConsumed) {
        this.quantityConsumed = quantityConsumed;
    }

    public Double getScrapGenerated() {
        return scrapGenerated;
    }

    public void setScrapGenerated(Double scrapGenerated) {
        this.scrapGenerated = scrapGenerated;
    }

    public Double getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(Double totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterialConsumptionDTO)) {
            return false;
        }

        RawMaterialConsumptionDTO rawMaterialConsumptionDTO = (RawMaterialConsumptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rawMaterialConsumptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialConsumptionDTO{" +
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
