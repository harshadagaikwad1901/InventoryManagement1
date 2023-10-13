package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Unit;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.RawMaterial} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RawMaterialDTO implements Serializable {

    private Long id;

    private String name;

    private String barcode;

    private Double quantity;

    private Double unitPrice;

    private Unit unitMeasure;

    private Double gstPercentage;

    private Double reorderPoint;

    private Long warehouseId;

    private Set<ProductsDTO> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Unit getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(Unit unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Double getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(Double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public Double getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(Double reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Set<ProductsDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductsDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterialDTO)) {
            return false;
        }

        RawMaterialDTO rawMaterialDTO = (RawMaterialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rawMaterialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", unitMeasure='" + getUnitMeasure() + "'" +
            ", gstPercentage=" + getGstPercentage() +
            ", reorderPoint=" + getReorderPoint() +
            ", warehouseId=" + getWarehouseId() +
            ", products=" + getProducts() +
            "}";
    }
}
