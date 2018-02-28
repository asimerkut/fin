package com.er.fin.service.dto;

import com.er.fin.domain.enumeration.EnmDay;
import com.er.fin.domain.enumeration.EnmDersGrup;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Plan extends  Credential {

    @NotNull
    private LocalDate startDate;
    @NotNull
    private EnmDay dayNo;
    //Ders (1 -15) --> dersAdet = 1, Sabit Gorev -> n, Degisken Gorev -> n
    @NotNull
    private EnmDersGrup dersGrup;
    @NotNull
    private String dersCode;
    @NotNull
    private Integer dersSira;
    //adet dedigi saat
    @NotNull
    private Integer dersAdet;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public EnmDay getDayNo() {
        return dayNo;
    }

    public void setDayNo(EnmDay dayNo) {
        this.dayNo = dayNo;
    }

    public EnmDersGrup getDersGrup() {
        return dersGrup;
    }

    public void setDersGrup(EnmDersGrup dersGrup) {
        this.dersGrup = dersGrup;
    }

    public String getDersCode() {
        return dersCode;
    }

    public void setDersCode(String dersCode) {
        this.dersCode = dersCode;
    }

    public Integer getDersSira() {
        return dersSira;
    }

    public void setDersSira(Integer dersSira) {
        this.dersSira = dersSira;
    }

    public Integer getDersAdet() {
        return dersAdet;
    }

    public void setDersAdet(Integer dersAdet) {
        this.dersAdet = dersAdet;
    }
}
