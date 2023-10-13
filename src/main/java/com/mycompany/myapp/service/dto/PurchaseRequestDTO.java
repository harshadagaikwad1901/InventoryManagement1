package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PurchaseRequest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PurchaseRequestDTO implements Serializable {

    private Long id;

    private Double qtyRequired;

    private Instant requestDate;

    private Instant expectedDate;

    private Status status;

    private String rawMaterialName;

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

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Instant getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Instant expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseRequestDTO)) {
            return false;
        }

        PurchaseRequestDTO purchaseRequestDTO = (PurchaseRequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, purchaseRequestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseRequestDTO{" +
            "id=" + getId() +
            ", qtyRequired=" + getQtyRequired() +
            ", requestDate='" + getRequestDate() + "'" +
            ", expectedDate='" + getExpectedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", rawMaterialName='" + getRawMaterialName() + "'" +
            "}";
    }
}
