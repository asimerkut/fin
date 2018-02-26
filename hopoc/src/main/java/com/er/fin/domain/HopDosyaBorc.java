package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmBorcGrubu;

/**
 * A HopDosyaBorc.
 */
@Entity
@Table(name = "hop_dosya_borc")
@Document(indexName = "hopdosyaborc")
public class HopDosyaBorc extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "borc_grubu")
    private EnmBorcGrubu borcGrubu;

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

    public HopDosyaBorc kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public HopDosyaBorc tutar(BigDecimal tutar) {
        this.tutar = tutar;
        return this;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public EnmBorcGrubu getBorcGrubu() {
        return borcGrubu;
    }

    public HopDosyaBorc borcGrubu(EnmBorcGrubu borcGrubu) {
        this.borcGrubu = borcGrubu;
        return this;
    }

    public void setBorcGrubu(EnmBorcGrubu borcGrubu) {
        this.borcGrubu = borcGrubu;
    }

    public HopDosya getDosya() {
        return dosya;
    }

    public HopDosyaBorc dosya(HopDosya hopDosya) {
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
        HopDosyaBorc hopDosyaBorc = (HopDosyaBorc) o;
        if (hopDosyaBorc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hopDosyaBorc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HopDosyaBorc{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", tutar=" + getTutar() +
            ", borcGrubu='" + getBorcGrubu() + "'" +
            "}";
    }
}
