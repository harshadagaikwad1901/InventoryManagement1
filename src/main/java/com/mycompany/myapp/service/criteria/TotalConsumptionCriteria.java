package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.TotalConsumption} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TotalConsumptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /total-consumptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TotalConsumptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter projectId;

    private LongFilter productId;

    private DoubleFilter totalMaterialCost;

    private DoubleFilter totalProductsCost;

    private DoubleFilter finalCost;

    private Boolean distinct;

    public TotalConsumptionCriteria() {}

    public TotalConsumptionCriteria(TotalConsumptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.projectId = other.projectId == null ? null : other.projectId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.totalMaterialCost = other.totalMaterialCost == null ? null : other.totalMaterialCost.copy();
        this.totalProductsCost = other.totalProductsCost == null ? null : other.totalProductsCost.copy();
        this.finalCost = other.finalCost == null ? null : other.finalCost.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TotalConsumptionCriteria copy() {
        return new TotalConsumptionCriteria(this);
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

    public DoubleFilter getTotalProductsCost() {
        return totalProductsCost;
    }

    public DoubleFilter totalProductsCost() {
        if (totalProductsCost == null) {
            totalProductsCost = new DoubleFilter();
        }
        return totalProductsCost;
    }

    public void setTotalProductsCost(DoubleFilter totalProductsCost) {
        this.totalProductsCost = totalProductsCost;
    }

    public DoubleFilter getFinalCost() {
        return finalCost;
    }

    public DoubleFilter finalCost() {
        if (finalCost == null) {
            finalCost = new DoubleFilter();
        }
        return finalCost;
    }

    public void setFinalCost(DoubleFilter finalCost) {
        this.finalCost = finalCost;
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
        final TotalConsumptionCriteria that = (TotalConsumptionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(projectId, that.projectId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(totalMaterialCost, that.totalMaterialCost) &&
            Objects.equals(totalProductsCost, that.totalProductsCost) &&
            Objects.equals(finalCost, that.finalCost) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, productId, totalMaterialCost, totalProductsCost, finalCost, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TotalConsumptionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (projectId != null ? "projectId=" + projectId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (totalMaterialCost != null ? "totalMaterialCost=" + totalMaterialCost + ", " : "") +
            (totalProductsCost != null ? "totalProductsCost=" + totalProductsCost + ", " : "") +
            (finalCost != null ? "finalCost=" + finalCost + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
