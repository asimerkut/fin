package com.er.fin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmCins;

import com.er.fin.domain.enumeration.EnmMedeni;

/**
 * A PerPerson.
 */
@Entity
@Table(name = "per_person")
@Document(indexName = "perperson")
public class PerPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "cins")
    private EnmCins cins;

    @Enumerated(EnumType.STRING)
    @Column(name = "medeni")
    private EnmMedeni medeni;

    @ManyToOne
    private PerCompany okul;

    @ManyToOne
    private DefItem hizmt;

    @ManyToOne
    private DefItem brans;

    @ManyToOne
    private DefItem unvan;

    @ManyToOne
    private DefItem kadro;

    @ManyToOne
    private DefItem karyr;

    @ManyToOne
    private DefItem konum;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public PerPerson code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public PerPerson name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public PerPerson isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getEmail() {
        return email;
    }

    public PerPerson email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public PerPerson phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EnmCins getCins() {
        return cins;
    }

    public PerPerson cins(EnmCins cins) {
        this.cins = cins;
        return this;
    }

    public void setCins(EnmCins cins) {
        this.cins = cins;
    }

    public EnmMedeni getMedeni() {
        return medeni;
    }

    public PerPerson medeni(EnmMedeni medeni) {
        this.medeni = medeni;
        return this;
    }

    public void setMedeni(EnmMedeni medeni) {
        this.medeni = medeni;
    }

    public PerCompany getOkul() {
        return okul;
    }

    public PerPerson okul(PerCompany perCompany) {
        this.okul = perCompany;
        return this;
    }

    public void setOkul(PerCompany perCompany) {
        this.okul = perCompany;
    }

    public DefItem getHizmt() {
        return hizmt;
    }

    public PerPerson hizmt(DefItem defItem) {
        this.hizmt = defItem;
        return this;
    }

    public void setHizmt(DefItem defItem) {
        this.hizmt = defItem;
    }

    public DefItem getBrans() {
        return brans;
    }

    public PerPerson brans(DefItem defItem) {
        this.brans = defItem;
        return this;
    }

    public void setBrans(DefItem defItem) {
        this.brans = defItem;
    }

    public DefItem getUnvan() {
        return unvan;
    }

    public PerPerson unvan(DefItem defItem) {
        this.unvan = defItem;
        return this;
    }

    public void setUnvan(DefItem defItem) {
        this.unvan = defItem;
    }

    public DefItem getKadro() {
        return kadro;
    }

    public PerPerson kadro(DefItem defItem) {
        this.kadro = defItem;
        return this;
    }

    public void setKadro(DefItem defItem) {
        this.kadro = defItem;
    }

    public DefItem getKaryr() {
        return karyr;
    }

    public PerPerson karyr(DefItem defItem) {
        this.karyr = defItem;
        return this;
    }

    public void setKaryr(DefItem defItem) {
        this.karyr = defItem;
    }

    public DefItem getKonum() {
        return konum;
    }

    public PerPerson konum(DefItem defItem) {
        this.konum = defItem;
        return this;
    }

    public void setKonum(DefItem defItem) {
        this.konum = defItem;
    }

    public User getUser() {
        return user;
    }

    public PerPerson user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PerPerson perPerson = (PerPerson) o;
        if (perPerson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perPerson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerPerson{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", cins='" + getCins() + "'" +
            ", medeni='" + getMedeni() + "'" +
            "}";
    }
}
