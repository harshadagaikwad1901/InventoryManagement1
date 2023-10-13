package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.ClientType;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Clients.
 */
@Entity
@Table(name = "clients")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sname")
    private String sname;

    @Column(name = "semail")
    private String semail;

    @Column(name = "mobile_no")
    private Long mobileNo;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_contact_no")
    private Long companyContactNo;

    @Column(name = "address")
    private String address;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "city")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_type")
    private ClientType clientType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Clients id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSname() {
        return this.sname;
    }

    public Clients sname(String sname) {
        this.setSname(sname);
        return this;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSemail() {
        return this.semail;
    }

    public Clients semail(String semail) {
        this.setSemail(semail);
        return this;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public Long getMobileNo() {
        return this.mobileNo;
    }

    public Clients mobileNo(Long mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Clients companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyContactNo() {
        return this.companyContactNo;
    }

    public Clients companyContactNo(Long companyContactNo) {
        this.setCompanyContactNo(companyContactNo);
        return this;
    }

    public void setCompanyContactNo(Long companyContactNo) {
        this.companyContactNo = companyContactNo;
    }

    public String getAddress() {
        return this.address;
    }

    public Clients address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public Clients pinCode(String pinCode) {
        this.setPinCode(pinCode);
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return this.city;
    }

    public Clients city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ClientType getClientType() {
        return this.clientType;
    }

    public Clients clientType(ClientType clientType) {
        this.setClientType(clientType);
        return this;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clients)) {
            return false;
        }
        return id != null && id.equals(((Clients) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clients{" +
            "id=" + getId() +
            ", sname='" + getSname() + "'" +
            ", semail='" + getSemail() + "'" +
            ", mobileNo=" + getMobileNo() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyContactNo=" + getCompanyContactNo() +
            ", address='" + getAddress() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", city='" + getCity() + "'" +
            ", clientType='" + getClientType() + "'" +
            "}";
    }
}
