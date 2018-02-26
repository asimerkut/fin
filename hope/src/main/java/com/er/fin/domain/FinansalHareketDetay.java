package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.er.fin.domain.enumeration.HesapEnum;

/**
 * A FinansalHareketDetay.
 */
@Entity
@Table(name = "finansal_hareket_detay")
@Document(indexName = "finansalhareketdetay")
public class FinansalHareketDetay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id", insertable=false, updatable = false)
    private String kod;

    @Column(name = "islem_tutari", precision=10, scale=2)
    private BigDecimal islemTutari;

    @Column(name = "finans_hesap_yonu")
    private Long hesapYonu;

    @Enumerated(EnumType.STRING)
    @Column(name = "finans_hesap_id")
    private HesapEnum hesapId;

    @Enumerated(EnumType.STRING)
    @Column(name = "karsi_finans_hesap_id")
    private HesapEnum karsiHesapId;

    @ManyToOne
    private FinansalHareket finansalHareket;

    @ManyToOne
    private DosyaBorc dosyaBorc;

    @ManyToOne
    private DosyaBorcKalem dosyaBorcKalem;

    @ManyToOne
    private FinansalHareketDetay karsiFhd;

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

    public FinansalHareketDetay kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public BigDecimal getIslemTutari() {
        return islemTutari;
    }

    public FinansalHareketDetay islemTutari(BigDecimal islemTutari) {
        this.islemTutari = islemTutari;
        return this;
    }

    public void setIslemTutari(BigDecimal islemTutari) {
        this.islemTutari = islemTutari;
    }

    public Long getHesapYonu() {
        return hesapYonu;
    }

    public FinansalHareketDetay hesapYonu(Long hesapYonu) {
        this.hesapYonu = hesapYonu;
        return this;
    }

    public void setHesapYonu(Long hesapYonu) {
        this.hesapYonu = hesapYonu;
    }

    public HesapEnum getHesapId() {
        return hesapId;
    }

    public FinansalHareketDetay hesapId(HesapEnum hesapId) {
        this.hesapId = hesapId;
        return this;
    }

    public void setHesapId(HesapEnum hesapId) {
        this.hesapId = hesapId;
    }

    public HesapEnum getKarsiHesapId() {
        return karsiHesapId;
    }

    public FinansalHareketDetay karsiHesapId(HesapEnum karsiHesapId) {
        this.karsiHesapId = karsiHesapId;
        return this;
    }

    public void setKarsiHesapId(HesapEnum karsiHesapId) {
        this.karsiHesapId = karsiHesapId;
    }

    public FinansalHareket getFinansalHareket() {
        return finansalHareket;
    }

    public FinansalHareketDetay finansalHareket(FinansalHareket finansalHareket) {
        this.finansalHareket = finansalHareket;
        return this;
    }

    public void setFinansalHareket(FinansalHareket finansalHareket) {
        this.finansalHareket = finansalHareket;
    }

    public DosyaBorc getDosyaBorc() {
        return dosyaBorc;
    }

    public FinansalHareketDetay dosyaBorc(DosyaBorc dosyaBorc) {
        this.dosyaBorc = dosyaBorc;
        return this;
    }

    public void setDosyaBorc(DosyaBorc dosyaBorc) {
        this.dosyaBorc = dosyaBorc;
    }

    public DosyaBorcKalem getDosyaBorcKalem() {
        return dosyaBorcKalem;
    }

    public FinansalHareketDetay dosyaBorcKalem(DosyaBorcKalem dosyaBorcKalem) {
        this.dosyaBorcKalem = dosyaBorcKalem;
        return this;
    }

    public void setDosyaBorcKalem(DosyaBorcKalem dosyaBorcKalem) {
        this.dosyaBorcKalem = dosyaBorcKalem;
    }

    public FinansalHareketDetay getKarsiFhd() {
        return karsiFhd;
    }

    public FinansalHareketDetay karsiFhd(FinansalHareketDetay finansalHareketDetay) {
        this.karsiFhd = finansalHareketDetay;
        return this;
    }

    public void setKarsiFhd(FinansalHareketDetay finansalHareketDetay) {
        this.karsiFhd = finansalHareketDetay;
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
        FinansalHareketDetay finansalHareketDetay = (FinansalHareketDetay) o;
        if (finansalHareketDetay.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), finansalHareketDetay.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinansalHareketDetay{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", islemTutari=" + getIslemTutari() +
            ", hesapYonu=" + getHesapYonu() +
            ", hesapId='" + getHesapId() + "'" +
            ", karsiHesapId='" + getKarsiHesapId() + "'" +
            "}";
    }
}
