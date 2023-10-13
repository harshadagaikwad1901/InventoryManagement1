package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.OrderRecieved} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderRecievedDTO implements Serializable {

    private Long id;

    private String referenceNumber;

    private Instant orDate;

    private Double qtyOrdered;

    private Double qtyRecieved;

    private Instant manufacturingDate;

    private Instant expiryDate;

    private Double qtyApproved;

    private Double qtyRejected;

    private Long purchaseQuotationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Instant getOrDate() {
        return orDate;
    }

    public void setOrDate(Instant orDate) {
        this.orDate = orDate;
    }

    public Double getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(Double qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public Double getQtyRecieved() {
        return qtyRecieved;
    }

    public void setQtyRecieved(Double qtyRecieved) {
        this.qtyRecieved = qtyRecieved;
    }

    public Instant getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getQtyApproved() {
        return qtyApproved;
    }

    public void setQtyApproved(Double qtyApproved) {
        this.qtyApproved = qtyApproved;
    }

    public Double getQtyRejected() {
        return qtyRejected;
    }

    public void setQtyRejected(Double qtyRejected) {
        this.qtyRejected = qtyRejected;
    }

    public Long getPurchaseQuotationId() {
        return purchaseQuotationId;
    }

    public void setPurchaseQuotationId(Long purchaseQuotationId) {
        this.purchaseQuotationId = purchaseQuotationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRecievedDTO)) {
            return false;
        }

        OrderRecievedDTO orderRecievedDTO = (OrderRecievedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderRecievedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderRecievedDTO{" +
            "id=" + getId() +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", orDate='" + getOrDate() + "'" +
            ", qtyOrdered=" + getQtyOrdered() +
            ", qtyRecieved=" + getQtyRecieved() +
            ", manufacturingDate='" + getManufacturingDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", qtyApproved=" + getQtyApproved() +
            ", qtyRejected=" + getQtyRejected() +
            ", purchaseQuotationId=" + getPurchaseQuotationId() +
            "}";
    }
}
