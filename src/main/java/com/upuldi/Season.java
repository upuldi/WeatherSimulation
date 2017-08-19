package com.upuldi;

import java.time.Month;

import static com.upuldi.Hemisphere.*;

/**
 * Created by L078878 on 18/08/2017.
 */
public enum Season {

    SPRING, SUMMER, AUTUMN, WINTER;

    static public Season of(Month month, Hemisphere hemisphere) {

        switch (month) {

            case MARCH:
                return Northern.equals(hemisphere) ? SPRING : AUTUMN;

            case APRIL:
                return Northern.equals(hemisphere) ? SPRING : AUTUMN;

            case MAY:
                return Northern.equals(hemisphere) ? SPRING : AUTUMN;

            case JUNE:
                return Northern.equals(hemisphere) ? SUMMER : WINTER;

            case JULY:
                return Northern.equals(hemisphere) ? SUMMER : WINTER;

            case AUGUST:
                return Northern.equals(hemisphere) ? SUMMER : WINTER;

            case SEPTEMBER:
                return Northern.equals(hemisphere) ? AUTUMN : SPRING;

            case OCTOBER:
                return Northern.equals(hemisphere) ? AUTUMN : SPRING;

            case NOVEMBER:
                return Northern.equals(hemisphere) ? AUTUMN : SPRING;

            case DECEMBER:
                return Northern.equals(hemisphere) ? WINTER : SUMMER;

            case JANUARY:
                return Northern.equals(hemisphere) ? WINTER : SUMMER;

            case FEBRUARY:
                return Northern.equals(hemisphere) ? WINTER : SUMMER;

            default:
                throw new IllegalArgumentException("Invalid month");
        }
    }

}
