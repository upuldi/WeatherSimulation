package com.upuldi.domain;

/**
 * Is used to represent the Hemisphere where the location is belongs to.
 * This will be used to calculate the season for the current day.
 *
 * @see com.upuldi.domain.Season
 */
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
