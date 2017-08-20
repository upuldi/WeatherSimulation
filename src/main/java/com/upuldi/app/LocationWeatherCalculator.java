package com.upuldi.app;

import com.upuldi.domain.Condition;
import com.upuldi.domain.Location;
import com.upuldi.domain.Season;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static com.upuldi.util.ProbabilityConstants.*;

/**
 * This class contains the logic for calculating weather data based on the probabilities
 * defined in the {@link  com.upuldi.util.ProbabilityConstants  ProbabilityConstants} class.
 *
 */
public class LocationWeatherCalculator {

    /**
     * This method returns a function which can calculate both condition and season of the day
     *
     * @see com.upuldi.domain.Season
     * @see com.upuldi.domain.Condition
     *
     * @return
     */
    public static Function<Location,Location> calculateLocationCondition() {
        return calculateCondition().compose(calculateSeason());
    }

    /**
     * This method returns a function which can calculate current season of the location based on current day.
     * @see  com.upuldi.domain.Season  Seasons
     *
     * @return
     */
    public static Function<Location,Location> calculateSeason() {

        Function<Location, Location> calculateSeason = location -> {
            Season currentSeason = Season.of(location.getZonedDateTime().getMonth(), location.getHemisphere());
            location.setSeason(currentSeason);
            return location;
        };
        return calculateSeason;
    }

    /**
     * This method returns a function which can calculate condition for the day.
     * Here the probabilities defined in the {@link  com.upuldi.util.ProbabilityConstants  ProbabilityConstants} class
     * is used to calculate the condition of day. Probabilities are defined based on the season, for ex: there is a
     * high probability of next day being sunny in Summer, on the other hand in winter there is a high probability of
     * day being snowy.
     *
     * @see  com.upuldi.domain.Season  Seasons
     * @see  com.upuldi.domain.Condition  Conditions
     * @return
     */
    public static Function<Location,Location> calculateCondition() {

        Function<Location, Location> calculateSeason = location -> {

            Random random = new Random();
            float randomFloat = random.nextFloat();

            switch (location.getSeason()) {

                case SUMMER: {

                    if (randomFloat < PROBABILITY_OF_DAY_BEING_SUNNY_IN_SUMMER) {
                        location.setCondition(Condition.Sunny);
                    } else {
                        location.setCondition(Condition.Rain);
                    }
                    break;
                }
                case WINTER: {

                    if (randomFloat < PROBABILITY_OF_DAY_BEING_RAINY_IN_WINTER) {
                        location.setCondition(Condition.Rain);
                    } else if (randomFloat > PROBABILITY_OF_DAY_BEING_RAINY_IN_WINTER &&
                            randomFloat < PROBABILITY_OF_DAY_BEING_SNOWY_IN_WINTER) {
                        location.setCondition(Condition.Snow);
                    } else {
                        location.setCondition(Condition.Sunny);
                    }
                    break;
                }
                case SPRING: {

                    if (randomFloat < PROBABILITY_OF_DAY_BEING_SUNNY_IN_SPRING) {
                        location.setCondition(Condition.Sunny);
                    } else {
                        location.setCondition(Condition.Rain);
                    }
                    break;
                }
                case AUTUMN: {

                    if (randomFloat < PROBABILITY_OF_DAY_BEING_SUNNY_IN_AUTUMN) {
                        location.setCondition(Condition.Sunny);
                    } else if (randomFloat > PROBABILITY_OF_DAY_BEING_SUNNY_IN_AUTUMN &&
                            randomFloat < PROBABILITY_OF_DAY_BEING_RAINY_IN_AUTUMN) {
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

    /**
     * This method generates the temperature of the location for a given day based on the
     * {@link com.upuldi.domain.Condition Condition} of the day. Probabilities are defined in the
     * {@link  com.upuldi.util.ProbabilityConstants  ProbabilityConstants} class.
     *
     * @see com.upuldi.domain.Condition
     *
     * @return
     */
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

    /**
     * This method is used to calculate the Pressure for a given location.
     * It uses probabilities defined in the {@link  com.upuldi.util.ProbabilityConstants  ProbabilityConstants} class,
     * to calculate pressure for a given date.
     *
     * @return
     */
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

    /**
     * This method returns a function which can be used to calculate humidity for a given location.
     * It uses probabilities defined in the {@link  com.upuldi.util.ProbabilityConstants  ProbabilityConstants} class,
     * to calculate humidity for a given date.
     *
     * @return
     */
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

    /**
     * This method calculates the humidity for a given location based on a probability figure.
     * A random number is generated and if that falls in the range of high probability category
     * we pick a range between average recorded humidity for the location and recorded max humidity.
     * If not a value will be selected between recorded min humidity and average humidity.
     *
     * @param highProbability
     * @param location
     */
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

    /**
     * This method is used to calculate weather information for a given location.
     * It wil calculate Season,Condition,Temperature,Pressure and Humidity values.
     *
     * @param l {@link com.upuldi.domain.Location Location} instance.
     * @return {@link com.upuldi.domain.Location Location} instance with weather information.
     */
    public static Location calculateLocationWeather(Location l) {

        Function<Location, Location> calculateAll = calculateLocationCondition().andThen(calculateTemperature()).
                andThen(calculatePressure()).andThen(calculateHumidity());

        return calculateAll.apply(l);
    }
}
