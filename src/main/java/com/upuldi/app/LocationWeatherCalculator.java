package com.upuldi.app;

import com.upuldi.domain.Condition;
import com.upuldi.domain.Location;
import com.upuldi.domain.Season;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static com.upuldi.util.ProbabilityConstants.*;
import static com.upuldi.util.ProbabilityConstants.PROBABILITY_OF_HIGH_HUMIDITY_IN_RAINY_DAY;
import static com.upuldi.util.ProbabilityConstants.PROBABILITY_OF_HIGH_HUMIDITY_IN_SNOWY_DAY;

/**
 * Created by udoluweera on 8/19/17.
 */
public class LocationWeatherCalculator {


    public static Function<Location,Location> calculateLocationCondition() {
        return calculateCondition().compose(calculateSeason());
    }

    public static Function<Location,Location> calculateSeason() {

        Function<Location, Location> calculateSeason = location -> {
            Season currentSeason = Season.of(location.getZonedDateTime().getMonth(), location.getHemisphere());
            location.setSeason(currentSeason);
            return location;
        };

        return calculateSeason;
    }

    public static Function<Location,Location> calculateCondition() {

        Function<Location, Location> calculateSeason = location -> {

            Random random = new Random();
            float f = random.nextFloat();

            switch (location.getSeason()) {

                case SUMMER: {

                    if (f < PROBABILITY_OF_DAY_BEING_SUNNY_IN_SUMMER) {
                        location.setCondition(Condition.Sunny);
                    } else {
                        location.setCondition(Condition.Rain);
                    }
                    break;
                }

                case WINTER: {

                    if (f < PROBABILITY_OF_DAY_BEING_RAINY_IN_WINTER) {
                        location.setCondition(Condition.Rain);
                    } else if (f > PROBABILITY_OF_DAY_BEING_RAINY_IN_WINTER &&
                            f < PROBABILITY_OF_DAY_BEING_SNOWY_IN_WINTER) {
                        location.setCondition(Condition.Snow);
                    } else {
                        location.setCondition(Condition.Sunny);
                    }
                    break;
                }

                case SPRING: {

                    if (f < PROBABILITY_OF_DAY_BEING_SUNNY_IN_SPRING) {
                        location.setCondition(Condition.Sunny);
                    } else {
                        location.setCondition(Condition.Rain);
                    }
                    break;
                }

                case AUTUMN: {

                    if (f < PROBABILITY_OF_DAY_BEING_SUNNY_IN_AUTUMN) {
                        location.setCondition(Condition.Sunny);
                    } else if (f > PROBABILITY_OF_DAY_BEING_SUNNY_IN_AUTUMN &&
                            f < PROBABILITY_OF_DAY_BEING_RAINY_IN_AUTUMN) {
                        location.setCondition(Condition.Rain);
                    } else {
                        location.setCondition(Condition.Snow);
                    }
                    break;
                }
            }
            return location;
        };
        return calculateSeason;
    }

    public static Function<Location, Location> calculateTemperature(){

        Function<Location, Location> calculateTemperatureForLocation = location -> {

            Double averageTemp = (location.getMaxRecordedTemp() + location.getMinRecordedTemp())/2;
            Double loweToAvgTemp = (location.getMinRecordedTemp() + averageTemp)/2;
            Double avgToUpper = (averageTemp+location.getMaxRecordedTemp()) /2;

            switch (location.getCondition()) {

                case Sunny: {

                    //Temperature should be above average in a sunny day
                    Double currentTemp = ThreadLocalRandom.current().nextDouble(averageTemp, location.getMaxRecordedTemp());
                    location.setCurrentTemperature(currentTemp);
                    break;
                }
                case Rain: {

                    //Rainy day could have a temp range from mid lower temp to mid upper temp
                    Double currentTemp = ThreadLocalRandom.current().nextDouble(loweToAvgTemp, avgToUpper);
                    location.setCurrentTemperature(currentTemp);
                    break;
                }
                case Snow: {

                    //Snowy day temperature sets at lower end
                    Double currentTemp = ThreadLocalRandom.current().nextDouble(location.getMinRecordedTemp(), loweToAvgTemp);
                    location.setCurrentTemperature(currentTemp);
                    break;
                }
            }
            return location;
        };

        return calculateTemperatureForLocation;
    }

    public static Function<Location, Location> calculatePressure() {

        Function<Location, Location> calculatePressureForLocation = location -> {

            switch (location.getCondition()) {

                case Sunny: {
                    pressureByProbability(PROBABILITY_OF_HIGH_PRESSURE_IN_SUNNY_DAY,location);
                    break;
                }
                case Rain: {
                    pressureByProbability(PROBABILITY_OF_HIGH_PRESSURE_IN_RAINY_DAY,location);
                    break;

                }
                case Snow: {
                    pressureByProbability(PROBABILITY_OF_HIGH_PRESSURE_IN_SNOWY_DAY,location);
                    break;
                }
            }
            return location;
        };
        return calculatePressureForLocation;
    }

    /**
     * Temperature and Pressure inter related,So high temperature is usually high pressure,but in order to make it
     * unpredictable(as actual weather) we are generating the pressure based on a probability
     *
     * @param highProbability
     * @param location
     */
    private static void pressureByProbability(Double highProbability, Location location) {

        Random random = new Random();
        float probVal = random.nextFloat();

        Double avgPressure = (location.getMinRecordedPressure()+location.getMaxRecordedPressure())/2;

        if (probVal < highProbability) {
            Double pressure = ThreadLocalRandom.current().nextDouble(avgPressure,
                    location.getMaxRecordedPressure());
            location.setCurrentPressure(pressure);
        } else {
            Double pressure = ThreadLocalRandom.current().nextDouble(location.getMinRecordedPressure(),
                    avgPressure);
            location.setCurrentPressure(pressure);
        }
    }

    public static Function<Location, Location> calculateHumidity() {
        Function<Location, Location> calculateHumiditForLocation = location -> {

            switch (location.getCondition()) {

                case Sunny: {
                    humidityByProbability(PROBABILITY_OF_HIGH_HUMIDITY_IN_SUNNY_DAY,location);
                    break;
                }
                case Rain: {
                    humidityByProbability(PROBABILITY_OF_HIGH_HUMIDITY_IN_RAINY_DAY,location);
                    break;
                }
                case Snow: {
                    humidityByProbability(PROBABILITY_OF_HIGH_HUMIDITY_IN_SNOWY_DAY,location);
                    break;
                }
            }
            return location;
        };
        return calculateHumiditForLocation;
    }

    private static void humidityByProbability(Double highProbability, Location location) {

        Random random = new Random();
        float probVal = random.nextFloat();

        Double avgHumidity = (location.getMinRecordedHumidity() + location.getMaxRecordedHumidity()) / 2d;

        if (probVal < highProbability) {
            Double humidity = ThreadLocalRandom.current().nextDouble(avgHumidity,
                    location.getMaxRecordedHumidity());
            location.setCurrentHumidity(humidity);
        } else {
            Double humidity = ThreadLocalRandom.current().nextDouble(location.getMinRecordedHumidity(),
                    avgHumidity);
            location.setCurrentHumidity(humidity);
        }
    }

    public static Location calculateLocationWeather(Location l) {

        Function<Location, Location> calculateAll = calculateLocationCondition().andThen(calculateTemperature()).
                andThen(calculatePressure()).andThen(calculateHumidity());

        return calculateAll.apply(l);
    }
}
