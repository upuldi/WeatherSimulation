package com.upuldi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class WatherSimulator {

    public List<Location> readLocationData(String path) {

        Function<String, Location> mapToLocation = s -> {

            String[] columns = s.split("[,]");
            Location location = new Location();
            location.setCity(columns[0]);
            location.setLatitude(columns[1]);
            location.setLongitude(columns[2]);
            location.setTimezone(columns[3]);
            location.setHemisphere(Hemisphere.getByCode(columns[4]));

            //Keep the local date time for the location
            ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of(columns[3]));
            location.setZonedDateTime(zonedDateTime);

            return location;
        };

        List<Location> locationList = null;
        try (Stream<String> stream = Files.lines(getPath(path))) {
            locationList = stream.filter(line -> !line.startsWith("#")).map(mapToLocation).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return locationList;
    }

    private Path getPath(String path) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(path).toURI());
    }

    public static void main(String[] args) throws InterruptedException {

        //Read from the locations property file
        WatherSimulator simulator = new WatherSimulator();
        List<Location> locationList = simulator.readLocationData("locations.txt");

        //Perform simulation
        simulator.performSimulation(locationList);

    }

    private Consumer<Location> publishWeatherForLocation(int count) {

        return location -> {

            //adjest date
            setDateFromToday(location);

            //calculate condition
            calculateCondition(location);




        };
    }

    private void setDateFromToday(Location location) {
        location.setZonedDateTime(location.getZonedDateTime().plusDays(1));
    }

    private Location calculateCondition(Location location) {

        Season currentSeason = Season.of(location.getZonedDateTime().getMonth(), location.getHemisphere());
        location.setSeason(currentSeason);

        return null;
    }

    public void performSimulation(List<Location> locationList) throws InterruptedException {

        int step =0;
        while (true) {

            Thread.sleep(5000);
            System.out.println("Running simulation .... ");

            locationList.stream().forEach(publishWeatherForLocation(step));
            step =+1;
        }

    }


}
