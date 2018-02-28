package com.er.fin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmParam;

/**
 * A DefRelation.
 */
@Entity
@Table(name = "def_relation")
@Document(indexName = "defrelation")
public class DefRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "parameter")
    private EnmParam parameter;

    @ManyToOne(optional = false)
    @NotNull
    private DefType typeSource;

    @ManyToOne
    private DefType typeTarget;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnmParam getParameter() {
        return parameter;
    }

    public DefRelation parameter(EnmParam parameter) {
        this.parameter = parameter;
        return this;
    }

    public void setParameter(EnmParam parameter) {
        this.parameter = parameter;
    }

    public DefType getTypeSource() {
        return typeSource;
    }

    public DefRelation typeSource(DefType defType) {
        this.typeSource = defType;
        return this;
    }

    public void setTypeSource(DefType defType) {
        this.typeSource = defType;
    }

    public DefType getTypeTarget() {
        return typeTarget;
    }

    public DefRelation typeTarget(DefType defType) {
        this.typeTarget = defType;
        return this;
    }

    public void setTypeTarget(DefType defType) {
        this.typeTarget = defType;
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
        DefRelation defRelation = (DefRelation) o;
        if (defRelation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), defRelation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DefRelation{" +
            "id=" + getId() +
            ", parameter='" + getParameter() + "'" +
            "}";
    }

    public String getCode(){
        return this.typeSource.getCode()+":"+
            (this.typeTarget==null?"?":this.typeTarget.getCode())+":"+
                (this.parameter==null?"?":this.parameter.name());

    }

}
