package com.upuldi;

import com.upuldi.app.LocationWeatherCalculator;
import com.upuldi.domain.Location;
import com.upuldi.util.FileUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * This is the main class where the simulation is taking place.
 *
 * It uses {@link com.upuldi.app.LocationWeatherCalculator} class to generate various weather conditions
 * for a given day.
 *
 * Simulation starts with the current day and with every execution date will be increased randomly with in a range.
 *
 * Simulation will run for 100 days and between every simulation it will sleep for 2 seconds.
 */
public class WeatherSimulator {

    private Function<String, List<Location>> readLocationData() {
        return FileUtils::readLocations;
    }

    /**
     * This method is used to display weather information.
     *
     * @return
     */
    private Consumer<Location> displayInformation() {

        Consumer<Location> printWeatherInfo = location -> {
            System.out.println(location.toString());
        };
        return printWeatherInfo;
    }

    /**
     * This method returns a function which will set a date to a given location based on a random date
     * within the given step. for ex if the step value is 5, it will pick a date between 1 and 5 and will
     * increment the current date of the location by that value.
     *
     * @return
     */
    public BiFunction<Location, Integer, Location> setRandomDate() {

        BiFunction<Location, Integer, Location> setDateForLocation = (location, step) -> {
            int randomNum = ThreadLocalRandom.current().nextInt(1, step + 1);
            location.setZonedDateTime(location.getZonedDateTime().plusDays(randomNum));
            return location;
        };
        return setDateForLocation;
    }

    /**
     * This method will simulate the weather for a given set of locations for the given no of steps.
     *
     * @param step
     * @param locations
     */
    public void simulateWeather(int step, List<Location> locations) {

        Function<Location, Location> setSimulatedDate = l -> setRandomDate().apply(l, step);
        locations.stream().map(setSimulatedDate).map(LocationWeatherCalculator::calculateLocationWeather).forEach(displayInformation());

        sleep();
    }

    private static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        List<Location> locationList = readLocationData().apply("locations.txt");
        IntStream.rangeClosed(1, 100).forEach(i -> simulateWeather(i, locationList));
    }

    public static void main(String[] args) {
        new WeatherSimulator().run();
    }
}
