package com.upuldi.domain;


public enum Hemisphere {

    Northern("NH"),Southern("SH");

    private String code;

    Hemisphere(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Hemisphere getByCode(String code) {

        for (Hemisphere hemisphere :  Hemisphere.values()) {
            if(hemisphere.getCode().equalsIgnoreCase(code)) {
                return hemisphere;
            }
        }
        return null;
    }
}
