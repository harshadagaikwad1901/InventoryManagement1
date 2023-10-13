package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Unit;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A RawMaterial.
 */
@Entity
@Table(name = "raw_material")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RawMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_measure")
    private Unit unitMeasure;

    @Column(name = "gst_percentage")
    private Double gstPercentage;

    @Column(name = "reorder_point")
    private Double reorderPoint;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @ManyToMany
    @JoinTable(
        name = "rel_raw_material__products",
        joinColumns = @JoinColumn(name = "raw_material_id"),
        inverseJoinColumns = @JoinColumn(name = "products_id")
    )
    @JsonIgnoreProperties(value = { "rawMaterials" }, allowSetters = true)
    private Set<Products> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RawMaterial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RawMaterial name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public RawMaterial barcode(String barcode) {
        this.setBarcode(barcode);
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public RawMaterial quantity(Double quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public RawMaterial unitPrice(Double unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Unit getUnitMeasure() {
        return this.unitMeasure;
    }

    public RawMaterial unitMeasure(Unit unitMeasure) {
        this.setUnitMeasure(unitMeasure);
        return this;
    }

    public void setUnitMeasure(Unit unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Double getGstPercentage() {
        return this.gstPercentage;
    }

    public RawMaterial gstPercentage(Double gstPercentage) {
        this.setGstPercentage(gstPercentage);
        return this;
    }

    public void setGstPercentage(Double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public Double getReorderPoint() {
        return this.reorderPoint;
    }

    public RawMaterial reorderPoint(Double reorderPoint) {
        this.setReorderPoint(reorderPoint);
        return this;
    }

    public void setReorderPoint(Double reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Long getWarehouseId() {
        return this.warehouseId;
    }

    public RawMaterial warehouseId(Long warehouseId) {
        this.setWarehouseId(warehouseId);
        return this;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Set<Products> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }

    public RawMaterial products(Set<Products> products) {
        this.setProducts(products);
        return this;
    }

    public RawMaterial addProducts(Products products) {
        this.products.add(products);
        products.getRawMaterials().add(this);
        return this;
    }

    public RawMaterial removeProducts(Products products) {
        this.products.remove(products);
        products.getRawMaterials().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterial)) {
            return false;
        }
        return id != null && id.equals(((RawMaterial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterial{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", unitMeasure='" + getUnitMeasure() + "'" +
            ", gstPercentage=" + getGstPercentage() +
            ", reorderPoint=" + getReorderPoint() +
            ", warehouseId=" + getWarehouseId() +
            "}";
    }
}
