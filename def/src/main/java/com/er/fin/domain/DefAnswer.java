package com.er.fin.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DefAnswer.
 */
@Entity
@Table(name = "def_answer")
@Document(indexName = "defanswer")
public class DefAnswer extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 20)
    @Column(name = "answer", length = 20)
    private String answer;

    @ManyToOne(optional = false)
    @NotNull
    private DefRelation relation;

    @ManyToOne(optional = false)
    @NotNull
    private DefItem itemSource;

    @ManyToOne
    private DefItem itemTarget;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public DefAnswer answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public DefRelation getRelation() {
        return relation;
    }

    public DefAnswer relation(DefRelation defRelation) {
        this.relation = defRelation;
        return this;
    }

    public void setRelation(DefRelation defRelation) {
        this.relation = defRelation;
    }

    public DefItem getItemSource() {
        return itemSource;
    }

    public DefAnswer itemSource(DefItem defItem) {
        this.itemSource = defItem;
        return this;
    }

    public void setItemSource(DefItem defItem) {
        this.itemSource = defItem;
    }

    public DefItem getItemTarget() {
        return itemTarget;
    }

    public DefAnswer itemTarget(DefItem defItem) {
        this.itemTarget = defItem;
        return this;
    }

    public void setItemTarget(DefItem defItem) {
        this.itemTarget = defItem;
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
        DefAnswer defAnswer = (DefAnswer) o;
        if (defAnswer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), defAnswer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DefAnswer{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
