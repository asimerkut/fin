package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmMasrafTipi;

/**
 * A HopMasraf.
 */
@Entity
@Table(name = "hop_masraf")
@Document(indexName = "hopmasraf")
public class HopMasraf extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Column(name = "tutar", precision=10, scale=2)
    private BigDecimal tutar;

    @Enumerated(EnumType.STRING)
    @Column(name = "masraf_tipi")
    private EnmMasrafTipi masrafTipi;

    @Column(name = "tarih")
    private LocalDate tarih;

    @ManyToOne
    private HopDosya dosya;

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

    public HopMasraf kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public HopMasraf tutar(BigDecimal tutar) {
        this.tutar = tutar;
        return this;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public EnmMasrafTipi getMasrafTipi() {
        return masrafTipi;
    }

    public HopMasraf masrafTipi(EnmMasrafTipi masrafTipi) {
        this.masrafTipi = masrafTipi;
        return this;
    }

    public void setMasrafTipi(EnmMasrafTipi masrafTipi) {
        this.masrafTipi = masrafTipi;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public HopMasraf tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public HopDosya getDosya() {
        return dosya;
    }

    public HopMasraf dosya(HopDosya hopDosya) {
        this.dosya = hopDosya;
        return this;
    }

    public void setDosya(HopDosya hopDosya) {
        this.dosya = hopDosya;
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
        HopMasraf hopMasraf = (HopMasraf) o;
        if (hopMasraf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hopMasraf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HopMasraf{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", tutar=" + getTutar() +
            ", masrafTipi='" + getMasrafTipi() + "'" +
            ", tarih='" + getTarih() + "'" +
            "}";
    }
}
