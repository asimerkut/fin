package com.er.fin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DefItem.
 */
@Entity
@Table(name = "def_item")
@Document(indexName = "defitem")
public class DefItem implements Serializable {

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
    @Max(value = 9)
    @Column(name = "item_level", nullable = false)
    private Integer itemLevel;

    @NotNull
    @Column(name = "is_select", nullable = false)
    private Boolean isSelect;

    @NotNull
    @Column(name = "is_const", nullable = false)
    private Boolean isConst;

    @ManyToOne(optional = false)
    @NotNull
    private DefType type;

    @ManyToOne
    private DefItem parent;

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

    public DefItem code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public DefItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getItemLevel() {
        return itemLevel;
    }

    public DefItem itemLevel(Integer itemLevel) {
        this.itemLevel = itemLevel;
        return this;
    }

    public void setItemLevel(Integer itemLevel) {
        this.itemLevel = itemLevel;
    }

    public Boolean isIsSelect() {
        return isSelect;
    }

    public DefItem isSelect(Boolean isSelect) {
        this.isSelect = isSelect;
        return this;
    }

    public void setIsSelect(Boolean isSelect) {
        this.isSelect = isSelect;
    }

    public Boolean isIsConst() {
        return isConst;
    }

    public DefItem isConst(Boolean isConst) {
        this.isConst = isConst;
        return this;
    }

    public void setIsConst(Boolean isConst) {
        this.isConst = isConst;
    }

    public DefType getType() {
        return type;
    }

    public DefItem type(DefType defType) {
        this.type = defType;
        return this;
    }

    public void setType(DefType defType) {
        this.type = defType;
    }

    public DefItem getParent() {
        return parent;
    }

    public DefItem parent(DefItem defItem) {
        this.parent = defItem;
        return this;
    }

    public void setParent(DefItem defItem) {
        this.parent = defItem;
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
        DefItem defItem = (DefItem) o;
        if (defItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), defItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DefItem{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", itemLevel=" + getItemLevel() +
            ", isSelect='" + isIsSelect() + "'" +
            ", isConst='" + isIsConst() + "'" +
            "}";
    }
}
