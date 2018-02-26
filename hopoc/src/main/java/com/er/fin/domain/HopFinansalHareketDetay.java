package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmHesap;

/**
 * A HopFinansalHareketDetay.
 */
@Entity
@Table(name = "hop_finansal_hareket_detay")
@Document(indexName = "hopfinansalhareketdetay")
public class HopFinansalHareketDetay extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Column(name = "hesap_yonu")
    private Long hesapYonu;

    @Enumerated(EnumType.STRING)
    @Column(name = "hesap")
    private EnmHesap hesap;

    @Enumerated(EnumType.STRING)
    @Column(name = "karsi_hesap")
    private EnmHesap karsiHesap;

    @Column(name = "tutar", precision=10, scale=2)
    private BigDecimal tutar;

    @ManyToOne
    private HopFinansalHareket finansalHareket;

    @ManyToOne
    private HopDosyaBorc dosyaBorc;

    @ManyToOne
    private HopDosyaBorcKalem dosyaBorcKalem;

    @ManyToOne
    private HopFinansalHareketDetay ilgi;

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

    public HopFinansalHareketDetay kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public Long getHesapYonu() {
        return hesapYonu;
    }

    public HopFinansalHareketDetay hesapYonu(Long hesapYonu) {
        this.hesapYonu = hesapYonu;
        return this;
    }

    public void setHesapYonu(Long hesapYonu) {
        this.hesapYonu = hesapYonu;
    }

    public EnmHesap getHesap() {
        return hesap;
    }

    public HopFinansalHareketDetay hesap(EnmHesap hesap) {
        this.hesap = hesap;
        return this;
    }

    public void setHesap(EnmHesap hesap) {
        this.hesap = hesap;
    }

    public EnmHesap getKarsiHesap() {
        return karsiHesap;
    }

    public HopFinansalHareketDetay karsiHesap(EnmHesap karsiHesap) {
        this.karsiHesap = karsiHesap;
        return this;
    }

    public void setKarsiHesap(EnmHesap karsiHesap) {
        this.karsiHesap = karsiHesap;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public HopFinansalHareketDetay tutar(BigDecimal tutar) {
        this.tutar = tutar;
        return this;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public HopFinansalHareket getFinansalHareket() {
        return finansalHareket;
    }

    public HopFinansalHareketDetay finansalHareket(HopFinansalHareket hopFinansalHareket) {
        this.finansalHareket = hopFinansalHareket;
        return this;
    }

    public void setFinansalHareket(HopFinansalHareket hopFinansalHareket) {
        this.finansalHareket = hopFinansalHareket;
    }

    public HopDosyaBorc getDosyaBorc() {
        return dosyaBorc;
    }

    public HopFinansalHareketDetay dosyaBorc(HopDosyaBorc hopDosyaBorc) {
        this.dosyaBorc = hopDosyaBorc;
        return this;
    }

    public void setDosyaBorc(HopDosyaBorc hopDosyaBorc) {
        this.dosyaBorc = hopDosyaBorc;
    }

    public HopDosyaBorcKalem getDosyaBorcKalem() {
        return dosyaBorcKalem;
    }

    public HopFinansalHareketDetay dosyaBorcKalem(HopDosyaBorcKalem hopDosyaBorcKalem) {
        this.dosyaBorcKalem = hopDosyaBorcKalem;
        return this;
    }

    public void setDosyaBorcKalem(HopDosyaBorcKalem hopDosyaBorcKalem) {
        this.dosyaBorcKalem = hopDosyaBorcKalem;
    }

    public HopFinansalHareketDetay getIlgi() {
        return ilgi;
    }

    public HopFinansalHareketDetay ilgi(HopFinansalHareketDetay hopFinansalHareketDetay) {
        this.ilgi = hopFinansalHareketDetay;
        return this;
    }

    public void setIlgi(HopFinansalHareketDetay hopFinansalHareketDetay) {
        this.ilgi = hopFinansalHareketDetay;
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
        HopFinansalHareketDetay hopFinansalHareketDetay = (HopFinansalHareketDetay) o;
        if (hopFinansalHareketDetay.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hopFinansalHareketDetay.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HopFinansalHareketDetay{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", hesapYonu=" + getHesapYonu() +
            ", hesap='" + getHesap() + "'" +
            ", karsiHesap='" + getKarsiHesap() + "'" +
            ", tutar=" + getTutar() +
            "}";
    }
}
