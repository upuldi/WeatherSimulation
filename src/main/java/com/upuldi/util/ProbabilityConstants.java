package com.upuldi.util;

/**
 * This class contains probabilities for various weather conditions.
 *
 */
public class ProbabilityConstants {

    //In Summer most of the days are sunny, but can not have any snowy days
    public static final double PROBABILITY_OF_DAY_BEING_SUNNY_IN_SUMMER =  0.7;
    public static final double PROBABILITY_OF_DAY_BEING_RAINY_IN_SUMMER =  0.3;

    //In Winter most of the days are snowy
    public static final double PROBABILITY_OF_DAY_BEING_SUNNY_IN_WINTER =  0.2;
    public static final double PROBABILITY_OF_DAY_BEING_RAINY_IN_WINTER =  0.2;
    public static final double PROBABILITY_OF_DAY_BEING_SNOWY_IN_WINTER =  0.6;

    //In Spring most of the days are still sunny, and can not have any snowy days
    public static final double PROBABILITY_OF_DAY_BEING_SUNNY_IN_SPRING =  0.6;
    public static final double PROBABILITY_OF_DAY_BEING_RAINY_IN_SPRING =  0.4;

    //In Autumn most of the days are rainy, can have some sunny days and snowy days
    public static final double PROBABILITY_OF_DAY_BEING_SUNNY_IN_AUTUMN =  0.2;
    public static final double PROBABILITY_OF_DAY_BEING_RAINY_IN_AUTUMN =  0.6;
    public static final double PROBABILITY_OF_DAY_BEING_SNOWY_IN_AUTUMN =  0.2;

    //Sunny days have high atmospheric temperature, temperature and pressure is interrelates
    //so it is safe to assume that the pressure of the atmosphere is also high
    public static final double PROBABILITY_OF_HIGH_PRESSURE_IN_SUNNY_DAY =  0.8;
    public static final double PROBABILITY_OF_LOW_PRESSURE_IN_SUNNY_DAY =   0.2;

    //Some assumptions, rain caused by drop of atmospheric pressure
    public static final double PROBABILITY_OF_HIGH_PRESSURE_IN_RAINY_DAY =  0.6;
    public static final double PROBABILITY_OF_LOW_PRESSURE_IN_RAINY_DAY =   0.4;

    //I am assuming snow is formed due to high pressure
    public static final double PROBABILITY_OF_HIGH_PRESSURE_IN_SNOWY_DAY =  0.2;
    public static final double PROBABILITY_OF_LOW_PRESSURE_IN_SNOWY_DAY =   0.8;

    //Sunny days have high temperature,so humidity should be low
    public static final double PROBABILITY_OF_HIGH_HUMIDITY_IN_SUNNY_DAY =  0.3;
    public static final double PROBABILITY_OF_LOW_HUMIDITY_IN_SUNNY_DAY =   0.7;

    //Rain is caused of water vapors, so humidity should be higher in a rainy day
    public static final double PROBABILITY_OF_HIGH_HUMIDITY_IN_RAINY_DAY =  0.8;
    public static final double PROBABILITY_OF_LOW_HUMIDITY_IN_RAINY_DAY =   0.2;

    //Usually in winter humidity is low.
    public static final double PROBABILITY_OF_HIGH_HUMIDITY_IN_SNOWY_DAY =  0.1;
    public static final double PROBABILITY_OF_LOW_HUMIDITY_IN_SNOWY_DAY =   0.9;
}
