package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.PurchaseRequest} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PurchaseRequestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /purchase-requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PurchaseRequestCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter qtyRequired;

    private InstantFilter requestDate;

    private InstantFilter expectedDate;

    private StatusFilter status;

    private StringFilter rawMaterialName;

    private Boolean distinct;

    public PurchaseRequestCriteria() {}

    public PurchaseRequestCriteria(PurchaseRequestCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.qtyRequired = other.qtyRequired == null ? null : other.qtyRequired.copy();
        this.requestDate = other.requestDate == null ? null : other.requestDate.copy();
        this.expectedDate = other.expectedDate == null ? null : other.expectedDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.rawMaterialName = other.rawMaterialName == null ? null : other.rawMaterialName.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PurchaseRequestCriteria copy() {
        return new PurchaseRequestCriteria(this);
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

    public DoubleFilter getQtyRequired() {
        return qtyRequired;
    }

    public DoubleFilter qtyRequired() {
        if (qtyRequired == null) {
            qtyRequired = new DoubleFilter();
        }
        return qtyRequired;
    }

    public void setQtyRequired(DoubleFilter qtyRequired) {
        this.qtyRequired = qtyRequired;
    }

    public InstantFilter getRequestDate() {
        return requestDate;
    }

    public InstantFilter requestDate() {
        if (requestDate == null) {
            requestDate = new InstantFilter();
        }
        return requestDate;
    }

    public void setRequestDate(InstantFilter requestDate) {
        this.requestDate = requestDate;
    }

    public InstantFilter getExpectedDate() {
        return expectedDate;
    }

    public InstantFilter expectedDate() {
        if (expectedDate == null) {
            expectedDate = new InstantFilter();
        }
        return expectedDate;
    }

    public void setExpectedDate(InstantFilter expectedDate) {
        this.expectedDate = expectedDate;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StringFilter getRawMaterialName() {
        return rawMaterialName;
    }

    public StringFilter rawMaterialName() {
        if (rawMaterialName == null) {
            rawMaterialName = new StringFilter();
        }
        return rawMaterialName;
    }

    public void setRawMaterialName(StringFilter rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
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
        final PurchaseRequestCriteria that = (PurchaseRequestCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(qtyRequired, that.qtyRequired) &&
            Objects.equals(requestDate, that.requestDate) &&
            Objects.equals(expectedDate, that.expectedDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(rawMaterialName, that.rawMaterialName) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, qtyRequired, requestDate, expectedDate, status, rawMaterialName, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseRequestCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (qtyRequired != null ? "qtyRequired=" + qtyRequired + ", " : "") +
            (requestDate != null ? "requestDate=" + requestDate + ", " : "") +
            (expectedDate != null ? "expectedDate=" + expectedDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (rawMaterialName != null ? "rawMaterialName=" + rawMaterialName + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
