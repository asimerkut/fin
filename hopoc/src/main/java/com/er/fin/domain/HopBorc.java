package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmBorcTipi;

/**
 * A HopBorc.
 */
@Entity
@Table(name = "hop_borc")
@Document(indexName = "hopborc")
public class HopBorc extends AbstractAuditingEntity implements Serializable {

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
    @Column(name = "borc_tipi")
    private EnmBorcTipi borcTipi;

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

    public HopBorc kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public HopBorc tutar(BigDecimal tutar) {
        this.tutar = tutar;
        return this;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public EnmBorcTipi getBorcTipi() {
        return borcTipi;
    }

    public HopBorc borcTipi(EnmBorcTipi borcTipi) {
        this.borcTipi = borcTipi;
        return this;
    }

    public void setBorcTipi(EnmBorcTipi borcTipi) {
        this.borcTipi = borcTipi;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public HopBorc tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public HopDosya getDosya() {
        return dosya;
    }

    public HopBorc dosya(HopDosya hopDosya) {
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
        HopBorc hopBorc = (HopBorc) o;
        if (hopBorc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hopBorc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HopBorc{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", tutar=" + getTutar() +
            ", borcTipi='" + getBorcTipi() + "'" +
            ", tarih='" + getTarih() + "'" +
            "}";
    }
}
