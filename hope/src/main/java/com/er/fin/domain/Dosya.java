package com.er.fin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Dosya.
 */
@Entity
@Table(name = "dosya")
@Document(indexName = "dosya")
public class Dosya implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id", insertable=false, updatable = false)
    private String kod;

    @Column(name = "dosya_no")
    private String dosyaNo;

    @ManyToOne
    @JoinColumn(name="dosya_id")
    private Dosya klasor;

    @ManyToOne
    private DosyaTipi dosyaTipi;

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

    public Dosya kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getDosyaNo() {
        return dosyaNo;
    }

    public Dosya dosyaNo(String dosyaNo) {
        this.dosyaNo = dosyaNo;
        return this;
    }

    public void setDosyaNo(String dosyaNo) {
        this.dosyaNo = dosyaNo;
    }

    public Dosya getKlasor() {
        return klasor;
    }

    public Dosya klasor(Dosya dosya) {
        this.klasor = dosya;
        return this;
    }

    public void setKlasor(Dosya dosya) {
        this.klasor = dosya;
    }

    public DosyaTipi getDosyaTipi() {
        return dosyaTipi;
    }

    public Dosya dosyaTipi(DosyaTipi dosyaTipi) {
        this.dosyaTipi = dosyaTipi;
        return this;
    }

    public void setDosyaTipi(DosyaTipi dosyaTipi) {
        this.dosyaTipi = dosyaTipi;
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
        Dosya dosya = (Dosya) o;
        if (dosya.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dosya.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dosya{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", dosyaNo='" + getDosyaNo() + "'" +
            "}";
    }
}
