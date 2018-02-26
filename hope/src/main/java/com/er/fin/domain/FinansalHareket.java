package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FinansalHareket.
 */
@Entity
@Table(name = "finansal_hareket")
@Document(indexName = "finansalhareket")
public class FinansalHareket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id", insertable=false, updatable = false)
    private String kod;

    @Column(name = "islem_kabul_tarihi")
    private LocalDate islemKabulTarihi;

    @Column(name = "islem_tutari", precision=10, scale=2)
    private BigDecimal islemTutari;

    @Column(name = "aciklama")
    private String aciklama;

    @ManyToOne
    private Dosya dosya;

    @ManyToOne
    private IslemKodu islemKodu;

    @ManyToOne
    @JoinColumn(name="dosya_fh_id")
    private FinansalHareket klasorFh;

    @ManyToOne
    private FinansalHareket finansalHareket;

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

    public FinansalHareket kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public LocalDate getIslemKabulTarihi() {
        return islemKabulTarihi;
    }

    public FinansalHareket islemKabulTarihi(LocalDate islemKabulTarihi) {
        this.islemKabulTarihi = islemKabulTarihi;
        return this;
    }

    public void setIslemKabulTarihi(LocalDate islemKabulTarihi) {
        this.islemKabulTarihi = islemKabulTarihi;
    }

    public BigDecimal getIslemTutari() {
        return islemTutari;
    }

    public FinansalHareket islemTutari(BigDecimal islemTutari) {
        this.islemTutari = islemTutari;
        return this;
    }

    public void setIslemTutari(BigDecimal islemTutari) {
        this.islemTutari = islemTutari;
    }

    public String getAciklama() {
        return aciklama;
    }

    public FinansalHareket aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Dosya getDosya() {
        return dosya;
    }

    public FinansalHareket dosya(Dosya dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(Dosya dosya) {
        this.dosya = dosya;
    }

    public IslemKodu getIslemKodu() {
        return islemKodu;
    }

    public FinansalHareket islemKodu(IslemKodu islemKodu) {
        this.islemKodu = islemKodu;
        return this;
    }

    public void setIslemKodu(IslemKodu islemKodu) {
        this.islemKodu = islemKodu;
    }

    public FinansalHareket getKlasorFh() {
        return klasorFh;
    }

    public FinansalHareket klasorFh(FinansalHareket finansalHareket) {
        this.klasorFh = finansalHareket;
        return this;
    }

    public void setKlasorFh(FinansalHareket finansalHareket) {
        this.klasorFh = finansalHareket;
    }

    public FinansalHareket getFinansalHareket() {
        return finansalHareket;
    }

    public FinansalHareket finansalHareket(FinansalHareket finansalHareket) {
        this.finansalHareket = finansalHareket;
        return this;
    }

    public void setFinansalHareket(FinansalHareket finansalHareket) {
        this.finansalHareket = finansalHareket;
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
        FinansalHareket finansalHareket = (FinansalHareket) o;
        if (finansalHareket.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), finansalHareket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinansalHareket{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", islemKabulTarihi='" + getIslemKabulTarihi() + "'" +
            ", islemTutari=" + getIslemTutari() +
            ", aciklama='" + getAciklama() + "'" +
            "}";
    }
}
