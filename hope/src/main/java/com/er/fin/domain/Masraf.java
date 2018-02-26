package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Masraf.
 */
@Entity
@Table(name = "masraf")
@Document(indexName = "masraf")
public class Masraf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id", insertable=false, updatable = false)
    private String kod;

    @Column(name = "masraf_tarihi")
    private LocalDate masrafTarihi;

    @Column(name = "orjinal_masraf_tutari", precision=10, scale=2)
    private BigDecimal orjinalMasrafTutari;

    @ManyToOne
    private Dosya dosya;

    @ManyToOne
    private MasrafTipi masrafTipi;

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

    public Masraf kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public LocalDate getMasrafTarihi() {
        return masrafTarihi;
    }

    public Masraf masrafTarihi(LocalDate masrafTarihi) {
        this.masrafTarihi = masrafTarihi;
        return this;
    }

    public void setMasrafTarihi(LocalDate masrafTarihi) {
        this.masrafTarihi = masrafTarihi;
    }

    public BigDecimal getOrjinalMasrafTutari() {
        return orjinalMasrafTutari;
    }

    public Masraf orjinalMasrafTutari(BigDecimal orjinalMasrafTutari) {
        this.orjinalMasrafTutari = orjinalMasrafTutari;
        return this;
    }

    public void setOrjinalMasrafTutari(BigDecimal orjinalMasrafTutari) {
        this.orjinalMasrafTutari = orjinalMasrafTutari;
    }

    public Dosya getDosya() {
        return dosya;
    }

    public Masraf dosya(Dosya dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(Dosya dosya) {
        this.dosya = dosya;
    }

    public MasrafTipi getMasrafTipi() {
        return masrafTipi;
    }

    public Masraf masrafTipi(MasrafTipi masrafTipi) {
        this.masrafTipi = masrafTipi;
        return this;
    }

    public void setMasrafTipi(MasrafTipi masrafTipi) {
        this.masrafTipi = masrafTipi;
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
        Masraf masraf = (Masraf) o;
        if (masraf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), masraf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Masraf{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", masrafTarihi='" + getMasrafTarihi() + "'" +
            ", orjinalMasrafTutari=" + getOrjinalMasrafTutari() +
            "}";
    }
}
