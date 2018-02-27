package com.er.fin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PerExcuse.
 */
@Entity
@Table(name = "per_excuse")
@Document(indexName = "perexcuse")
public class PerExcuse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Max(value = 15)
    @Column(name = "start_ders_no", nullable = false)
    private Integer startDersNo;

    @NotNull
    @Column(name = "finish_date", nullable = false)
    private LocalDate finishDate;

    @NotNull
    @Max(value = 15)
    @Column(name = "finish_ders_no", nullable = false)
    private Integer finishDersNo;

    @NotNull
    @Column(name = "is_excuse", nullable = false)
    private Boolean isExcuse;

    @ManyToOne(optional = false)
    @NotNull
    private PerPerson person;

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

    public PerExcuse startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getStartDersNo() {
        return startDersNo;
    }

    public PerExcuse startDersNo(Integer startDersNo) {
        this.startDersNo = startDersNo;
        return this;
    }

    public void setStartDersNo(Integer startDersNo) {
        this.startDersNo = startDersNo;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public PerExcuse finishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getFinishDersNo() {
        return finishDersNo;
    }

    public PerExcuse finishDersNo(Integer finishDersNo) {
        this.finishDersNo = finishDersNo;
        return this;
    }

    public void setFinishDersNo(Integer finishDersNo) {
        this.finishDersNo = finishDersNo;
    }

    public Boolean isIsExcuse() {
        return isExcuse;
    }

    public PerExcuse isExcuse(Boolean isExcuse) {
        this.isExcuse = isExcuse;
        return this;
    }

    public void setIsExcuse(Boolean isExcuse) {
        this.isExcuse = isExcuse;
    }

    public PerPerson getPerson() {
        return person;
    }

    public PerExcuse person(PerPerson perPerson) {
        this.person = perPerson;
        return this;
    }

    public void setPerson(PerPerson perPerson) {
        this.person = perPerson;
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
        PerExcuse perExcuse = (PerExcuse) o;
        if (perExcuse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perExcuse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerExcuse{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", startDersNo=" + getStartDersNo() +
            ", finishDate='" + getFinishDate() + "'" +
            ", finishDersNo=" + getFinishDersNo() +
            ", isExcuse='" + isIsExcuse() + "'" +
            "}";
    }
}
