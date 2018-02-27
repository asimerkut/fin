package com.er.fin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PerCompany.
 */
@Entity
@Table(name = "per_company")
@Document(indexName = "percompany")
public class PerCompany implements Serializable {

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

    @Max(value = 8)
    @Column(name = "mesai_oo")
    private Integer mesaiOo;

    @Max(value = 8)
    @Column(name = "mesai_os")
    private Integer mesaiOs;

    @Max(value = 8)
    @Column(name = "mesai_gc")
    private Integer mesaiGc;

    @ManyToOne
    private DefItem sehir;

    @ManyToOne
    private DefItem tipi;

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

    public PerCompany code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public PerCompany name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMesaiOo() {
        return mesaiOo;
    }

    public PerCompany mesaiOo(Integer mesaiOo) {
        this.mesaiOo = mesaiOo;
        return this;
    }

    public void setMesaiOo(Integer mesaiOo) {
        this.mesaiOo = mesaiOo;
    }

    public Integer getMesaiOs() {
        return mesaiOs;
    }

    public PerCompany mesaiOs(Integer mesaiOs) {
        this.mesaiOs = mesaiOs;
        return this;
    }

    public void setMesaiOs(Integer mesaiOs) {
        this.mesaiOs = mesaiOs;
    }

    public Integer getMesaiGc() {
        return mesaiGc;
    }

    public PerCompany mesaiGc(Integer mesaiGc) {
        this.mesaiGc = mesaiGc;
        return this;
    }

    public void setMesaiGc(Integer mesaiGc) {
        this.mesaiGc = mesaiGc;
    }

    public DefItem getSehir() {
        return sehir;
    }

    public PerCompany sehir(DefItem defItem) {
        this.sehir = defItem;
        return this;
    }

    public void setSehir(DefItem defItem) {
        this.sehir = defItem;
    }

    public DefItem getTipi() {
        return tipi;
    }

    public PerCompany tipi(DefItem defItem) {
        this.tipi = defItem;
        return this;
    }

    public void setTipi(DefItem defItem) {
        this.tipi = defItem;
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
        PerCompany perCompany = (PerCompany) o;
        if (perCompany.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perCompany.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerCompany{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", mesaiOo=" + getMesaiOo() +
            ", mesaiOs=" + getMesaiOs() +
            ", mesaiGc=" + getMesaiGc() +
            "}";
    }
}
