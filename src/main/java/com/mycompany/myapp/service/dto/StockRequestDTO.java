package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.StockRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StockRequestDTO implements Serializable {

    private Long id;

    private Double qtyRequired;

    private Instant reqDate;

    private Boolean isProd;

    private Status status;

    private Long rawMaterialId;

    private Long productId;

    private Long productionLineId;

    private Long projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQtyRequired() {
        return qtyRequired;
    }

    public void setQtyRequired(Double qtyRequired) {
        this.qtyRequired = qtyRequired;
    }

    public Instant getReqDate() {
        return reqDate;
    }

    public void setReqDate(Instant reqDate) {
        this.reqDate = reqDate;
    }

    public Boolean getIsProd() {
        return isProd;
    }

    public void setIsProd(Boolean isProd) {
        this.isProd = isProd;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductionLineId() {
        return productionLineId;
    }

    public void setProductionLineId(Long productionLineId) {
        this.productionLineId = productionLineId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockRequestDTO)) {
            return false;
        }

        StockRequestDTO stockRequestDTO = (StockRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stockRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockRequestDTO{" +
            "id=" + getId() +
            ", qtyRequired=" + getQtyRequired() +
            ", reqDate='" + getReqDate() + "'" +
            ", isProd='" + getIsProd() + "'" +
            ", status='" + getStatus() + "'" +
            ", rawMaterialId=" + getRawMaterialId() +
            ", productId=" + getProductId() +
            ", productionLineId=" + getProductionLineId() +
            ", projectId=" + getProjectId() +
            "}";
    }
}
