package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Borc.
 */
@Entity
@Table(name = "borc")
@Document(indexName = "borc")
public class Borc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id", insertable=false, updatable = false)
    private String kod;

    @Column(name = "orjinal_borc_tutari", precision=10, scale=2)
    private BigDecimal orjinalBorcTutari;

    @ManyToOne
    @JoinColumn(name="icra_id")
    private Dosya dosya;

    @ManyToOne
    private BorcTipi borcTipi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKod() {
        return kod;
    }

    public Borc kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public BigDecimal getOrjinalBorcTutari() {
        return orjinalBorcTutari;
    }

    public Borc orjinalBorcTutari(BigDecimal orjinalBorcTutari) {
        this.orjinalBorcTutari = orjinalBorcTutari;
        return this;
    }

    public void setOrjinalBorcTutari(BigDecimal orjinalBorcTutari) {
        this.orjinalBorcTutari = orjinalBorcTutari;
    }

    public Dosya getDosya() {
        return dosya;
    }

    public Borc dosya(Dosya dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(Dosya dosya) {
        this.dosya = dosya;
    }

    public BorcTipi getBorcTipi() {
        return borcTipi;
    }

    public Borc borcTipi(BorcTipi borcTipi) {
        this.borcTipi = borcTipi;
        return this;
    }

    public void setBorcTipi(BorcTipi borcTipi) {
        this.borcTipi = borcTipi;
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
        Borc borc = (Borc) o;
        if (borc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), borc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Borc{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", orjinalBorcTutari=" + getOrjinalBorcTutari() +
            "}";
    }
}
