package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A HopDosyaBorcKalem.
 */
@Entity
@Table(name = "hop_dosya_borc_kalem")
@Document(indexName = "hopdosyaborckalem")
public class HopDosyaBorcKalem extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Column(name = "tutar", precision=10, scale=2)
    private BigDecimal tutar;

    @ManyToOne
    private HopDosyaBorc dosyaBorc;

    @ManyToOne
    private HopBorc borc;

    @ManyToOne
    private HopMasraf masraf;

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

    public HopDosyaBorcKalem kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public HopDosyaBorcKalem tutar(BigDecimal tutar) {
        this.tutar = tutar;
        return this;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public HopDosyaBorc getDosyaBorc() {
        return dosyaBorc;
    }

    public HopDosyaBorcKalem dosyaBorc(HopDosyaBorc hopDosyaBorc) {
        this.dosyaBorc = hopDosyaBorc;
        return this;
    }

    public void setDosyaBorc(HopDosyaBorc hopDosyaBorc) {
        this.dosyaBorc = hopDosyaBorc;
    }

    public HopBorc getBorc() {
        return borc;
    }

    public HopDosyaBorcKalem borc(HopBorc hopBorc) {
        this.borc = hopBorc;
        return this;
    }

    public void setBorc(HopBorc hopBorc) {
        this.borc = hopBorc;
    }

    public HopMasraf getMasraf() {
        return masraf;
    }

    public HopDosyaBorcKalem masraf(HopMasraf hopMasraf) {
        this.masraf = hopMasraf;
        return this;
    }

    public void setMasraf(HopMasraf hopMasraf) {
        this.masraf = hopMasraf;
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
        HopDosyaBorcKalem hopDosyaBorcKalem = (HopDosyaBorcKalem) o;
        if (hopDosyaBorcKalem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hopDosyaBorcKalem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HopDosyaBorcKalem{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", tutar=" + getTutar() +
            "}";
    }
}
