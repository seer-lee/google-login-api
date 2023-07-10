package com.googleLoginApi.auth.dto;

import lombok.Getter;

@Getter
public class GoogleAuthDto {
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    private String emailVerified;
    private String atHash;
    private String name;
    private String picture;
    private String givenName;
    private String familyName;
    private String locale;
    private String iat;
    private String exp;
    private String alg;
    private String kid;
    private String typ;
    private String hd;

    public void setIss(String iss) {
        this.iss = iss;
    }

    public void setAzp(String azp) {
        this.azp = azp;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setAtHash(String atHash) {
        this.atHash = atHash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }
}
