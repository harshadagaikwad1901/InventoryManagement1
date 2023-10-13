package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.RawMaterialConsumption} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RawMaterialConsumptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /raw-material-consumptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RawMaterialConsumptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter projectId;

    private LongFilter productId;

    private LongFilter productionLine;

    private LongFilter rawMaterialId;

    private DoubleFilter quantityConsumed;

    private DoubleFilter scrapGenerated;

    private DoubleFilter totalMaterialCost;

    private Boolean distinct;

    public RawMaterialConsumptionCriteria() {}

    public RawMaterialConsumptionCriteria(RawMaterialConsumptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.productionLine = other.productionLine == null ? null : other.productionLine.copy();
        this.rawMaterialId = other.rawMaterialId == null ? null : other.rawMaterialId.copy();
        this.quantityConsumed = other.quantityConsumed == null ? null : other.quantityConsumed.copy();
        this.scrapGenerated = other.scrapGenerated == null ? null : other.scrapGenerated.copy();
        this.totalMaterialCost = other.totalMaterialCost == null ? null : other.totalMaterialCost.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RawMaterialConsumptionCriteria copy() {
        return new RawMaterialConsumptionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public LongFilter projectId() {
        if (projectId == null) {
            projectId = new LongFilter();
        }
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getProductionLine() {
        return productionLine;
    }

    public LongFilter productionLine() {
        if (productionLine == null) {
            productionLine = new LongFilter();
        }
        return productionLine;
    }

    public void setProductionLine(LongFilter productionLine) {
        this.productionLine = productionLine;
    }

    public LongFilter getRawMaterialId() {
        return rawMaterialId;
    }

    public LongFilter rawMaterialId() {
        if (rawMaterialId == null) {
            rawMaterialId = new LongFilter();
        }
        return rawMaterialId;
    }

    public void setRawMaterialId(LongFilter rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public DoubleFilter getQuantityConsumed() {
        return quantityConsumed;
    }

    public DoubleFilter quantityConsumed() {
        if (quantityConsumed == null) {
            quantityConsumed = new DoubleFilter();
        }
        return quantityConsumed;
    }

    public void setQuantityConsumed(DoubleFilter quantityConsumed) {
        this.quantityConsumed = quantityConsumed;
    }

    public DoubleFilter getScrapGenerated() {
        return scrapGenerated;
    }

    public DoubleFilter scrapGenerated() {
        if (scrapGenerated == null) {
            scrapGenerated = new DoubleFilter();
        }
        return scrapGenerated;
    }

    public void setScrapGenerated(DoubleFilter scrapGenerated) {
        this.scrapGenerated = scrapGenerated;
    }

    public DoubleFilter getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public DoubleFilter totalMaterialCost() {
        if (totalMaterialCost == null) {
            totalMaterialCost = new DoubleFilter();
        }
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(DoubleFilter totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RawMaterialConsumptionCriteria that = (RawMaterialConsumptionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(productionLine, that.productionLine) &&
            Objects.equals(rawMaterialId, that.rawMaterialId) &&
            Objects.equals(quantityConsumed, that.quantityConsumed) &&
            Objects.equals(scrapGenerated, that.scrapGenerated) &&
            Objects.equals(totalMaterialCost, that.totalMaterialCost) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            projectId,
            productId,
            productionLine,
            rawMaterialId,
            quantityConsumed,
            scrapGenerated,
            totalMaterialCost,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialConsumptionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (productionLine != null ? "productionLine=" + productionLine + ", " : "") +
            (rawMaterialId != null ? "rawMaterialId=" + rawMaterialId + ", " : "") +
            (quantityConsumed != null ? "quantityConsumed=" + quantityConsumed + ", " : "") +
            (scrapGenerated != null ? "scrapGenerated=" + scrapGenerated + ", " : "") +
            (totalMaterialCost != null ? "totalMaterialCost=" + totalMaterialCost + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
