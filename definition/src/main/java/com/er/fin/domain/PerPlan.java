package com.er.fin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmDay;

import com.er.fin.domain.enumeration.EnmDersGrup;

/**
 * A PerPlan.
 */
@Entity
@Table(name = "per_plan")
@Document(indexName = "perplan")
public class PerPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_no", nullable = false)
    private EnmDay dayNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ders_grup", nullable = false)
    private EnmDersGrup dersGrup;

    @NotNull
    @Max(value = 15)
    @Column(name = "ders_no", nullable = false)
    private Integer dersNo;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public PerPlan startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public EnmDay getDayNo() {
        return dayNo;
    }

    public PerPlan dayNo(EnmDay dayNo) {
        this.dayNo = dayNo;
        return this;
    }

    public void setDayNo(EnmDay dayNo) {
        this.dayNo = dayNo;
    }

    public EnmDersGrup getDersGrup() {
        return dersGrup;
    }

    public PerPlan dersGrup(EnmDersGrup dersGrup) {
        this.dersGrup = dersGrup;
        return this;
    }

    public void setDersGrup(EnmDersGrup dersGrup) {
        this.dersGrup = dersGrup;
    }

    public Integer getDersNo() {
        return dersNo;
    }

    public PerPlan dersNo(Integer dersNo) {
        this.dersNo = dersNo;
        return this;
    }

    public void setDersNo(Integer dersNo) {
        this.dersNo = dersNo;
    }

    public Integer getDersAdet() {
        return dersAdet;
    }

    public PerPlan dersAdet(Integer dersAdet) {
        this.dersAdet = dersAdet;
        return this;
    }

    public void setDersAdet(Integer dersAdet) {
        this.dersAdet = dersAdet;
    }

    public PerPerson getPerson() {
        return person;
    }

    public PerPlan person(PerPerson perPerson) {
        this.person = perPerson;
        return this;
    }

    public void setPerson(PerPerson perPerson) {
        this.person = perPerson;
    }

    public DefItem getDers() {
        return ders;
    }

    public PerPlan ders(DefItem defItem) {
        this.ders = defItem;
        return this;
    }

    public void setDers(DefItem defItem) {
        this.ders = defItem;
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
        PerPlan perPlan = (PerPlan) o;
        if (perPlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perPlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerPlan{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", dayNo='" + getDayNo() + "'" +
            ", dersGrup='" + getDersGrup() + "'" +
            ", dersNo=" + getDersNo() +
            ", dersAdet=" + getDersAdet() +
            "}";
    }
}
