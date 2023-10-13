package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A StockRequest.
 */
@Entity
@Table(name = "stock_request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StockRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "qty_required")
    private Double qtyRequired;

    @Column(name = "req_date")
    private Instant reqDate;

    @Column(name = "is_prod")
    private Boolean isProd;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "raw_material_id")
    private Long rawMaterialId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "production_line_id")
    private Long productionLineId;

    @Column(name = "project_id")
    private Long projectId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StockRequest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQtyRequired() {
        return this.qtyRequired;
    }

    public StockRequest qtyRequired(Double qtyRequired) {
        this.setQtyRequired(qtyRequired);
        return this;
    }

    public void setQtyRequired(Double qtyRequired) {
        this.qtyRequired = qtyRequired;
    }

    public Instant getReqDate() {
        return this.reqDate;
    }

    public StockRequest reqDate(Instant reqDate) {
        this.setReqDate(reqDate);
        return this;
    }

    public void setReqDate(Instant reqDate) {
        this.reqDate = reqDate;
    }

    public Boolean getIsProd() {
        return this.isProd;
    }

    public StockRequest isProd(Boolean isProd) {
        this.setIsProd(isProd);
        return this;
    }

    public void setIsProd(Boolean isProd) {
        this.isProd = isProd;
    }

    public Status getStatus() {
        return this.status;
    }

    public StockRequest status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getRawMaterialId() {
        return this.rawMaterialId;
    }

    public StockRequest rawMaterialId(Long rawMaterialId) {
        this.setRawMaterialId(rawMaterialId);
        return this;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public StockRequest productId(Long productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductionLineId() {
        return this.productionLineId;
    }

    public StockRequest productionLineId(Long productionLineId) {
        this.setProductionLineId(productionLineId);
        return this;
    }

    public void setProductionLineId(Long productionLineId) {
        this.productionLineId = productionLineId;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public StockRequest projectId(Long projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockRequest)) {
            return false;
        }
        return id != null && id.equals(((StockRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockRequest{" +
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
