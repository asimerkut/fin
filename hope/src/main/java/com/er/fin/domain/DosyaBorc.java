package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DosyaBorc.
 */
@Entity
@Table(name = "dosya_borc")
@Document(indexName = "dosyaborc")
public class DosyaBorc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id", insertable=false, updatable = false)
    private String kod;

    @ManyToOne
    private Dosya dosya;

    @ManyToOne
    private BorcGrubu borcGrubu;

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

    public DosyaBorc kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public Dosya getDosya() {
        return dosya;
    }

    public DosyaBorc dosya(Dosya dosya) {
        this.dosya = dosya;
        return this;
    }

    public void setDosya(Dosya dosya) {
        this.dosya = dosya;
    }

    public BorcGrubu getBorcGrubu() {
        return borcGrubu;
    }

    public DosyaBorc borcGrubu(BorcGrubu borcGrubu) {
        this.borcGrubu = borcGrubu;
        return this;
    }

    public void setBorcGrubu(BorcGrubu borcGrubu) {
        this.borcGrubu = borcGrubu;
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
        DosyaBorc dosyaBorc = (DosyaBorc) o;
        if (dosyaBorc.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dosyaBorc.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DosyaBorc{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            "}";
    }
}
