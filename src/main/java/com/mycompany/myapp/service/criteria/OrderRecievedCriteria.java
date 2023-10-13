package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.OrderRecieved} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.OrderRecievedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-recieveds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderRecievedCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter referenceNumber;

    private InstantFilter orDate;

    private DoubleFilter qtyOrdered;

    private DoubleFilter qtyRecieved;

    private InstantFilter manufacturingDate;

    private InstantFilter expiryDate;

    private DoubleFilter qtyApproved;

    private DoubleFilter qtyRejected;

    private LongFilter purchaseQuotationId;

    private Boolean distinct;

    public OrderRecievedCriteria() {}

    public OrderRecievedCriteria(OrderRecievedCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.referenceNumber = other.referenceNumber == null ? null : other.referenceNumber.copy();
        this.orDate = other.orDate == null ? null : other.orDate.copy();
        this.qtyOrdered = other.qtyOrdered == null ? null : other.qtyOrdered.copy();
        this.qtyRecieved = other.qtyRecieved == null ? null : other.qtyRecieved.copy();
        this.manufacturingDate = other.manufacturingDate == null ? null : other.manufacturingDate.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.qtyApproved = other.qtyApproved == null ? null : other.qtyApproved.copy();
        this.qtyRejected = other.qtyRejected == null ? null : other.qtyRejected.copy();
        this.purchaseQuotationId = other.purchaseQuotationId == null ? null : other.purchaseQuotationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrderRecievedCriteria copy() {
        return new OrderRecievedCriteria(this);
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

    public StringFilter getReferenceNumber() {
        return referenceNumber;
    }

    public StringFilter referenceNumber() {
        if (referenceNumber == null) {
            referenceNumber = new StringFilter();
        }
        return referenceNumber;
    }

    public void setReferenceNumber(StringFilter referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public InstantFilter getOrDate() {
        return orDate;
    }

    public InstantFilter orDate() {
        if (orDate == null) {
            orDate = new InstantFilter();
        }
        return orDate;
    }

    public void setOrDate(InstantFilter orDate) {
        this.orDate = orDate;
    }

    public DoubleFilter getQtyOrdered() {
        return qtyOrdered;
    }

    public DoubleFilter qtyOrdered() {
        if (qtyOrdered == null) {
            qtyOrdered = new DoubleFilter();
        }
        return qtyOrdered;
    }

    public void setQtyOrdered(DoubleFilter qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public DoubleFilter getQtyRecieved() {
        return qtyRecieved;
    }

    public DoubleFilter qtyRecieved() {
        if (qtyRecieved == null) {
            qtyRecieved = new DoubleFilter();
        }
        return qtyRecieved;
    }

    public void setQtyRecieved(DoubleFilter qtyRecieved) {
        this.qtyRecieved = qtyRecieved;
    }

    public InstantFilter getManufacturingDate() {
        return manufacturingDate;
    }

    public InstantFilter manufacturingDate() {
        if (manufacturingDate == null) {
            manufacturingDate = new InstantFilter();
        }
        return manufacturingDate;
    }

    public void setManufacturingDate(InstantFilter manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public InstantFilter getExpiryDate() {
        return expiryDate;
    }

    public InstantFilter expiryDate() {
        if (expiryDate == null) {
            expiryDate = new InstantFilter();
        }
        return expiryDate;
    }

    public void setExpiryDate(InstantFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public DoubleFilter getQtyApproved() {
        return qtyApproved;
    }

    public DoubleFilter qtyApproved() {
        if (qtyApproved == null) {
            qtyApproved = new DoubleFilter();
        }
        return qtyApproved;
    }

    public void setQtyApproved(DoubleFilter qtyApproved) {
        this.qtyApproved = qtyApproved;
    }

    public DoubleFilter getQtyRejected() {
        return qtyRejected;
    }

    public DoubleFilter qtyRejected() {
        if (qtyRejected == null) {
            qtyRejected = new DoubleFilter();
        }
        return qtyRejected;
    }

    public void setQtyRejected(DoubleFilter qtyRejected) {
        this.qtyRejected = qtyRejected;
    }

    public LongFilter getPurchaseQuotationId() {
        return purchaseQuotationId;
    }

    public LongFilter purchaseQuotationId() {
        if (purchaseQuotationId == null) {
            purchaseQuotationId = new LongFilter();
        }
        return purchaseQuotationId;
    }

    public void setPurchaseQuotationId(LongFilter purchaseQuotationId) {
        this.purchaseQuotationId = purchaseQuotationId;
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
        final OrderRecievedCriteria that = (OrderRecievedCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(referenceNumber, that.referenceNumber) &&
            Objects.equals(orDate, that.orDate) &&
            Objects.equals(qtyOrdered, that.qtyOrdered) &&
            Objects.equals(qtyRecieved, that.qtyRecieved) &&
            Objects.equals(manufacturingDate, that.manufacturingDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(qtyApproved, that.qtyApproved) &&
            Objects.equals(qtyRejected, that.qtyRejected) &&
            Objects.equals(purchaseQuotationId, that.purchaseQuotationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            referenceNumber,
            orDate,
            qtyOrdered,
            qtyRecieved,
            manufacturingDate,
            expiryDate,
            qtyApproved,
            qtyRejected,
            purchaseQuotationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderRecievedCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (referenceNumber != null ? "referenceNumber=" + referenceNumber + ", " : "") +
            (orDate != null ? "orDate=" + orDate + ", " : "") +
            (qtyOrdered != null ? "qtyOrdered=" + qtyOrdered + ", " : "") +
            (qtyRecieved != null ? "qtyRecieved=" + qtyRecieved + ", " : "") +
            (manufacturingDate != null ? "manufacturingDate=" + manufacturingDate + ", " : "") +
            (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
            (qtyApproved != null ? "qtyApproved=" + qtyApproved + ", " : "") +
            (qtyRejected != null ? "qtyRejected=" + qtyRejected + ", " : "") +
            (purchaseQuotationId != null ? "purchaseQuotationId=" + purchaseQuotationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
