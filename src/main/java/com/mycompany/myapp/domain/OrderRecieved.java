package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A OrderRecieved.
 */
@Entity
@Table(name = "order_recieved")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderRecieved implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "or_date")
    private Instant orDate;

    @Column(name = "qty_ordered")
    private Double qtyOrdered;

    @Column(name = "qty_recieved")
    private Double qtyRecieved;

    @Column(name = "manufacturing_date")
    private Instant manufacturingDate;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "qty_approved")
    private Double qtyApproved;

    @Column(name = "qty_rejected")
    private Double qtyRejected;

    @Column(name = "purchase_quotation_id")
    private Long purchaseQuotationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderRecieved id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    public OrderRecieved referenceNumber(String referenceNumber) {
        this.setReferenceNumber(referenceNumber);
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Instant getOrDate() {
        return this.orDate;
    }

    public OrderRecieved orDate(Instant orDate) {
        this.setOrDate(orDate);
        return this;
    }

    public void setOrDate(Instant orDate) {
        this.orDate = orDate;
    }

    public Double getQtyOrdered() {
        return this.qtyOrdered;
    }

    public OrderRecieved qtyOrdered(Double qtyOrdered) {
        this.setQtyOrdered(qtyOrdered);
        return this;
    }

    public void setQtyOrdered(Double qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public Double getQtyRecieved() {
        return this.qtyRecieved;
    }

    public OrderRecieved qtyRecieved(Double qtyRecieved) {
        this.setQtyRecieved(qtyRecieved);
        return this;
    }

    public void setQtyRecieved(Double qtyRecieved) {
        this.qtyRecieved = qtyRecieved;
    }

    public Instant getManufacturingDate() {
        return this.manufacturingDate;
    }

    public OrderRecieved manufacturingDate(Instant manufacturingDate) {
        this.setManufacturingDate(manufacturingDate);
        return this;
    }

    public void setManufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public OrderRecieved expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getQtyApproved() {
        return this.qtyApproved;
    }

    public OrderRecieved qtyApproved(Double qtyApproved) {
        this.setQtyApproved(qtyApproved);
        return this;
    }

    public void setQtyApproved(Double qtyApproved) {
        this.qtyApproved = qtyApproved;
    }

    public Double getQtyRejected() {
        return this.qtyRejected;
    }

    public OrderRecieved qtyRejected(Double qtyRejected) {
        this.setQtyRejected(qtyRejected);
        return this;
    }

    public void setQtyRejected(Double qtyRejected) {
        this.qtyRejected = qtyRejected;
    }

    public Long getPurchaseQuotationId() {
        return this.purchaseQuotationId;
    }

    public OrderRecieved purchaseQuotationId(Long purchaseQuotationId) {
        this.setPurchaseQuotationId(purchaseQuotationId);
        return this;
    }

    public void setPurchaseQuotationId(Long purchaseQuotationId) {
        this.purchaseQuotationId = purchaseQuotationId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRecieved)) {
            return false;
        }
        return id != null && id.equals(((OrderRecieved) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderRecieved{" +
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
