package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmIslemKodu;

/**
 * A HopFinansalHareket.
 */
@Entity
@Table(name = "hop_finansal_hareket")
@Document(indexName = "hopfinansalhareket")
public class HopFinansalHareket extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Enumerated(EnumType.STRING)
    @Column(name = "islem_kodu")
    private EnmIslemKodu islemKodu;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Column(name = "tutar", precision=10, scale=2)
    private BigDecimal tutar;

    @ManyToOne
    private HopDosya dosya;

    @ManyToOne
    private HopFinansalHareket ilgi;

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

    public HopFinansalHareket kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public EnmIslemKodu getIslemKodu() {
        return islemKodu;
    }

    public HopFinansalHareket islemKodu(EnmIslemKodu islemKodu) {
        this.islemKodu = islemKodu;
        return this;
    }

    public void setIslemKodu(EnmIslemKodu islemKodu) {
        this.islemKodu = islemKodu;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public HopFinansalHareket tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public HopFinansalHareket tutar(BigDecimal tutar) {
        this.tutar = tutar;
        return this;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public HopDosya getDosya() {
        return dosya;
    }

    public HopFinansalHareket dosya(HopDosya hopDosya) {
        this.dosya = hopDosya;
        return this;
    }

    public void setDosya(HopDosya hopDosya) {
        this.dosya = hopDosya;
    }

    public HopFinansalHareket getIlgi() {
        return ilgi;
    }

    public HopFinansalHareket ilgi(HopFinansalHareket hopFinansalHareket) {
        this.ilgi = hopFinansalHareket;
        return this;
    }

    public void setIlgi(HopFinansalHareket hopFinansalHareket) {
        this.ilgi = hopFinansalHareket;
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
        HopFinansalHareket hopFinansalHareket = (HopFinansalHareket) o;
        if (hopFinansalHareket.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hopFinansalHareket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HopFinansalHareket{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", islemKodu='" + getIslemKodu() + "'" +
            ", tarih='" + getTarih() + "'" +
            ", tutar=" + getTutar() +
            "}";
    }
}
