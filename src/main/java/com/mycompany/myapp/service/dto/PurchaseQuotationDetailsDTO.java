package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PurchaseQuotationDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PurchaseQuotationDetailsDTO implements Serializable {

    private Long id;

    private Double qtyOrdered;

    private Integer gstTaxPercentage;

    private Double pricePerUnit;

    private Double totalPrice;

    private Double discount;

    private Long purchaseQuotationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(Double qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public Integer getGstTaxPercentage() {
        return gstTaxPercentage;
    }

    public void setGstTaxPercentage(Integer gstTaxPercentage) {
        this.gstTaxPercentage = gstTaxPercentage;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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
        if (!(o instanceof PurchaseQuotationDetailsDTO)) {
            return false;
        }

        PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO = (PurchaseQuotationDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, purchaseQuotationDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseQuotationDetailsDTO{" +
            "id=" + getId() +
            ", qtyOrdered=" + getQtyOrdered() +
            ", gstTaxPercentage=" + getGstTaxPercentage() +
            ", pricePerUnit=" + getPricePerUnit() +
            ", totalPrice=" + getTotalPrice() +
            ", discount=" + getDiscount() +
            ", purchaseQuotationId=" + getPurchaseQuotationId() +
            "}";
    }
}
