package com.er.fin.service.dto;

import com.er.fin.domain.enumeration.EnmMedeni;
import com.er.fin.domain.enumeration.EnmSozlesme;

import javax.validation.constraints.NotNull;

public class PersonInfo extends  Credential{

    private String personName;
    private String personGivenName;
    private String personFamilyName;
    private String personEmail;
    @NotNull
    private EnmSozlesme enmSozlesme;
    @NotNull
    private EnmMedeni medeni;
    private String hizmtCode;
    private String brans;
    private String unvan;
    private String kadro;
    private String karyr;
    private String konum;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonGivenName() {
        return personGivenName;
    }

    public void setPersonGivenName(String personGivenName) {
        this.personGivenName = personGivenName;
    }

    public String getPersonFamilyName() {
        return personFamilyName;
    }

    public void setPersonFamilyName(String personFamilyName) {
        this.personFamilyName = personFamilyName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public EnmSozlesme getEnmSozlesme() {
        return enmSozlesme;
    }

    public void setEnmSozlesme(EnmSozlesme enmSozlesme) {
        this.enmSozlesme = enmSozlesme;
    }

    public EnmMedeni getMedeni() {
        return medeni;
    }

    public void setMedeni(EnmMedeni medeni) {
        this.medeni = medeni;
    }

    public String getHizmtCode() {
        return hizmtCode;
    }

    public void setHizmtCode(String hizmtCode) {
        this.hizmtCode = hizmtCode;
    }

    public String getBrans() {
        return brans;
    }

    public void setBrans(String brans) {
        this.brans = brans;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getKadro() {
        return kadro;
    }

    public void setKadro(String kadro) {
        this.kadro = kadro;
    }

    public String getKaryr() {
        return karyr;
    }

    public void setKaryr(String karyr) {
        this.karyr = karyr;
    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }
}
