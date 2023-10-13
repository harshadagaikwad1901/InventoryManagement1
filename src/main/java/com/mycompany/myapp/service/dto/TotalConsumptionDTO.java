package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TotalConsumption} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TotalConsumptionDTO implements Serializable {

    private Long id;

    private Long projectId;

    private Long productId;

    private Double totalMaterialCost;

    private Double totalProductsCost;

    private Double finalCost;

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

    public Double getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(Double totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public Double getTotalProductsCost() {
        return totalProductsCost;
    }

    public void setTotalProductsCost(Double totalProductsCost) {
        this.totalProductsCost = totalProductsCost;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Double finalCost) {
        this.finalCost = finalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TotalConsumptionDTO)) {
            return false;
        }

        TotalConsumptionDTO totalConsumptionDTO = (TotalConsumptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, totalConsumptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TotalConsumptionDTO{" +
            "id=" + getId() +
            ", projectId=" + getProjectId() +
            ", productId=" + getProductId() +
            ", totalMaterialCost=" + getTotalMaterialCost() +
            ", totalProductsCost=" + getTotalProductsCost() +
            ", finalCost=" + getFinalCost() +
            "}";
    }
}
