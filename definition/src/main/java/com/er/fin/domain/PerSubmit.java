package com.er.fin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmDersGrup;

/**
 * A PerSubmit.
 */
@Entity
@Table(name = "per_submit")
@Document(indexName = "persubmit")
public class PerSubmit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "submit_date", nullable = false)
    private LocalDate submitDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ders_grup", nullable = false)
    private EnmDersGrup dersGrup;

    @NotNull
    @Max(value = 15)
    @Column(name = "ders_sira", nullable = false)
    private Integer dersSira;

    @NotNull
    @Max(value = 15)
    @Column(name = "ders_adet", nullable = false)
    private Integer dersAdet;

    @ManyToOne(optional = false)
    @NotNull
    private PerPerson person;

    @ManyToOne(optional = false)
    @NotNull
    private DefItem ders;

    @ManyToOne
    private PerExcuse excuse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public PerSubmit submitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
        return this;
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }

    public EnmDersGrup getDersGrup() {
        return dersGrup;
    }

    public PerSubmit dersGrup(EnmDersGrup dersGrup) {
        this.dersGrup = dersGrup;
        return this;
    }

    public void setDersGrup(EnmDersGrup dersGrup) {
        this.dersGrup = dersGrup;
    }

    public Integer getDersSira() {
        return dersSira;
    }

    public PerSubmit dersSira(Integer dersSira) {
        this.dersSira = dersSira;
        return this;
    }

    public void setDersSira(Integer dersSira) {
        this.dersSira = dersSira;
    }

    public Integer getDersAdet() {
        return dersAdet;
    }

    public PerSubmit dersAdet(Integer dersAdet) {
        this.dersAdet = dersAdet;
        return this;
    }

    public void setDersAdet(Integer dersAdet) {
        this.dersAdet = dersAdet;
    }

    public PerPerson getPerson() {
        return person;
    }

    public PerSubmit person(PerPerson perPerson) {
        this.person = perPerson;
        return this;
    }

    public void setPerson(PerPerson perPerson) {
        this.person = perPerson;
    }

    public DefItem getDers() {
        return ders;
    }

    public PerSubmit ders(DefItem defItem) {
        this.ders = defItem;
        return this;
    }

    public void setDers(DefItem defItem) {
        this.ders = defItem;
    }

    public PerExcuse getExcuse() {
        return excuse;
    }

    public PerSubmit excuse(PerExcuse perExcuse) {
        this.excuse = perExcuse;
        return this;
    }

    public void setExcuse(PerExcuse perExcuse) {
        this.excuse = perExcuse;
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
        PerSubmit perSubmit = (PerSubmit) o;
        if (perSubmit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perSubmit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerSubmit{" +
            "id=" + getId() +
            ", submitDate='" + getSubmitDate() + "'" +
            ", dersGrup='" + getDersGrup() + "'" +
            ", dersSira=" + getDersSira() +
            ", dersAdet=" + getDersAdet() +
            "}";
    }
}
