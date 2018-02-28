package com.er.fin.service.dto;

import javax.validation.constraints.NotNull;

public class Credential {

    @NotNull
    private String ssoId;
    @NotNull
    private String ssoType;

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getSsoType() {
        return ssoType;
    }

    public void setSsoType(String ssoType) {
        this.ssoType = ssoType;
    }
}
