package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DefPivot.
 */
@Entity
@Table(name = "def_pivot")
@Document(indexName = "defpivot")
public class DefPivot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Lob
    @Column(name = "pvt_sql")
    private String pvtSql;

    @Column(name = "pvt_val")
    private String pvtVal;

    @Column(name = "pvt_col")
    private String pvtCol;

    @Column(name = "pvt_row")
    private String pvtRow;

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

    public DefPivot code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPvtSql() {
        return pvtSql;
    }

    public DefPivot pvtSql(String pvtSql) {
        this.pvtSql = pvtSql;
        return this;
    }

    public void setPvtSql(String pvtSql) {
        this.pvtSql = pvtSql;
    }

    public String getPvtVal() {
        return pvtVal;
    }

    public DefPivot pvtVal(String pvtVal) {
        this.pvtVal = pvtVal;
        return this;
    }

    public void setPvtVal(String pvtVal) {
        this.pvtVal = pvtVal;
    }

    public String getPvtCol() {
        return pvtCol;
    }

    public DefPivot pvtCol(String pvtCol) {
        this.pvtCol = pvtCol;
        return this;
    }

    public void setPvtCol(String pvtCol) {
        this.pvtCol = pvtCol;
    }

    public String getPvtRow() {
        return pvtRow;
    }

    public DefPivot pvtRow(String pvtRow) {
        this.pvtRow = pvtRow;
        return this;
    }

    public void setPvtRow(String pvtRow) {
        this.pvtRow = pvtRow;
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
        DefPivot defPivot = (DefPivot) o;
        if (defPivot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), defPivot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DefPivot{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", pvtSql='" + getPvtSql() + "'" +
            ", pvtVal='" + getPvtVal() + "'" +
            ", pvtCol='" + getPvtCol() + "'" +
            ", pvtRow='" + getPvtRow() + "'" +
            "}";
    }
}
