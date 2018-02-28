package com.er.fin.service.dto;

import com.er.fin.domain.enumeration.EnmDersGrup;

public class Ders {

    private EnmDersGrup dersGrup;
    private String code;
    private String name;

    public EnmDersGrup getDersGrup() {
        return dersGrup;
    }

    public void setDersGrup(EnmDersGrup dersGrup) {
        this.dersGrup = dersGrup;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
