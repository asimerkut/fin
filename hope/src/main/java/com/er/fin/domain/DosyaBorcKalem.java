package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DosyaBorcKalem.
 */
@Entity
@Table(name = "dosya_borc_kalem")
@Document(indexName = "dosyaborckalem")
public class DosyaBorcKalem implements Serializable {

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
    private DosyaBorc dosyaBorc;

    @ManyToOne
    private BorcKalem borcKalem;

    @ManyToOne
    private Borc borc;

    @ManyToOne
    private Masraf masraf;

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

    public DosyaBorcKalem kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public BigDecimal getOrjinalBorcTutari() {
        return orjinalBorcTutari;
    }

    public DosyaBorcKalem orjinalBorcTutari(BigDecimal orjinalBorcTutari) {
        this.orjinalBorcTutari = orjinalBorcTutari;
        return this;
    }

    public void setOrjinalBorcTutari(BigDecimal orjinalBorcTutari) {
        this.orjinalBorcTutari = orjinalBorcTutari;
    }

    public DosyaBorc getDosyaBorc() {
        return dosyaBorc;
    }

    public DosyaBorcKalem dosyaBorc(DosyaBorc dosyaBorc) {
        this.dosyaBorc = dosyaBorc;
        return this;
    }

    public void setDosyaBorc(DosyaBorc dosyaBorc) {
        this.dosyaBorc = dosyaBorc;
    }

    public BorcKalem getBorcKalem() {
        return borcKalem;
    }

    public DosyaBorcKalem borcKalem(BorcKalem borcKalem) {
        this.borcKalem = borcKalem;
        return this;
    }

    public void setBorcKalem(BorcKalem borcKalem) {
        this.borcKalem = borcKalem;
    }

    public Borc getBorc() {
        return borc;
    }

    public DosyaBorcKalem borc(Borc borc) {
        this.borc = borc;
        return this;
    }

    public void setBorc(Borc borc) {
        this.borc = borc;
    }

    public Masraf getMasraf() {
        return masraf;
    }

    public DosyaBorcKalem masraf(Masraf masraf) {
        this.masraf = masraf;
        return this;
    }

    public void setMasraf(Masraf masraf) {
        this.masraf = masraf;
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
        DosyaBorcKalem dosyaBorcKalem = (DosyaBorcKalem) o;
        if (dosyaBorcKalem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dosyaBorcKalem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DosyaBorcKalem{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", orjinalBorcTutari=" + getOrjinalBorcTutari() +
            "}";
    }
}
