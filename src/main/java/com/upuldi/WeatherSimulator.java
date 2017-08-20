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

public class WeatherSimulator {

    private Function<String, List<Location>> readLocationData() {
        return FileUtils::readLocations;
    }

    private Consumer<Location> displayInformation() {

        Consumer<Location> printWeatherInfo = location -> {
            System.out.println(location.toString());
        };
        return printWeatherInfo;
    }

    public BiFunction<Location, Integer, Location> setRandomDate() {

        BiFunction<Location, Integer, Location> setDateForLocation = (location, step) -> {
            int randomNum = ThreadLocalRandom.current().nextInt(1, step + 1);
            location.setZonedDateTime(location.getZonedDateTime().plusDays(randomNum));
            return location;
        };
        return setDateForLocation;
    }

    public void simulateWeather(int step, List<Location> locations) {

        Function<Location, Location> setSimulatedDate = l -> setRandomDate().apply(l, step);
        locations.stream().map(setSimulatedDate).map(LocationWeatherCalculator::calculateLocationWeather).forEach(displayInformation());
        sleep();
    }

    private static void sleep() {
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        List<Location> locationList = readLocationData().apply("locations.txt");
        IntStream.rangeClosed(1, 10).forEach(i -> simulateWeather(i, locationList));
    }

    public static void main(String[] args) {
        new WeatherSimulator().run();
    }
}
