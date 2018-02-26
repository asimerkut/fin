package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.er.fin.domain.enumeration.EnmDosyaTipi;

/**
 * A HopDosya.
 */
@Entity
@Table(name = "hop_dosya")
@Document(indexName = "hopdosya")
public class HopDosya extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Enumerated(EnumType.STRING)
    @Column(name = "dosya_tipi")
    private EnmDosyaTipi dosyaTipi;

    @ManyToOne
    private HopDosya klasor;

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

    public HopDosya kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public EnmDosyaTipi getDosyaTipi() {
        return dosyaTipi;
    }

    public HopDosya dosyaTipi(EnmDosyaTipi dosyaTipi) {
        this.dosyaTipi = dosyaTipi;
        return this;
    }

    public void setDosyaTipi(EnmDosyaTipi dosyaTipi) {
        this.dosyaTipi = dosyaTipi;
    }

    public HopDosya getKlasor() {
        return klasor;
    }

    public HopDosya klasor(HopDosya hopDosya) {
        this.klasor = hopDosya;
        return this;
    }

    public void setKlasor(HopDosya hopDosya) {
        this.klasor = hopDosya;
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
        HopDosya hopDosya = (HopDosya) o;
        if (hopDosya.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hopDosya.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HopDosya{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", dosyaTipi='" + getDosyaTipi() + "'" +
            "}";
    }
}
