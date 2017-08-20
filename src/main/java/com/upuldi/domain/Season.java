package com.upuldi.domain;

import java.time.Month;

public enum Season {

    SPRING, SUMMER, AUTUMN, WINTER;

    static public Season of(Month month, Hemisphere hemisphere) {

        switch (month) {

            case MARCH:
                return Hemisphere.Northern.equals(hemisphere) ? SPRING : AUTUMN;

            case APRIL:
                return Hemisphere.Northern.equals(hemisphere) ? SPRING : AUTUMN;

            case MAY:
                return Hemisphere.Northern.equals(hemisphere) ? SPRING : AUTUMN;

            case JUNE:
                return Hemisphere.Northern.equals(hemisphere) ? SUMMER : WINTER;

            case JULY:
                return Hemisphere.Northern.equals(hemisphere) ? SUMMER : WINTER;

            case AUGUST:
                return Hemisphere.Northern.equals(hemisphere) ? SUMMER : WINTER;

            case SEPTEMBER:
                return Hemisphere.Northern.equals(hemisphere) ? AUTUMN : SPRING;

            case OCTOBER:
                return Hemisphere.Northern.equals(hemisphere) ? AUTUMN : SPRING;

            case NOVEMBER:
                return Hemisphere.Northern.equals(hemisphere) ? AUTUMN : SPRING;

            case DECEMBER:
                return Hemisphere.Northern.equals(hemisphere) ? WINTER : SUMMER;

            case JANUARY:
                return Hemisphere.Northern.equals(hemisphere) ? WINTER : SUMMER;

            case FEBRUARY:
                return Hemisphere.Northern.equals(hemisphere) ? WINTER : SUMMER;

            default:
                throw new IllegalArgumentException("Invalid month");
        }
    }

}
