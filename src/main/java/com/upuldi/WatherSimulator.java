package com.upuldi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {

    public List<Location> readLocationData(String path) {

        Function<String, Location> mapToLocation = s -> {
            String[] columns = s.split("[,]");
            Location location = new Location();
            location.setCity(columns[0]);
            location.setLatitude(columns[1]);
            location.setLongitude(columns[2]);
            return location;
        };

        List<Location> locationList = null;
        try (Stream<String> stream = Files.lines(getPath(path))) {
            locationList = stream.filter(line -> !line.startsWith("#")).map(mapToLocation)..collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return locationList;
    }

    private static Location setRandomTimeStamp(Location location) {
        long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));

        return null;
    }

    private Path getPath(String path) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(path).toURI());
    }

    public static void main(String[] args) {

        String fileName = "locations.txt";
        System.out.println(new App().readLocationData(fileName).size());

/*        ZoneId america = ZoneId.of("UTC+05:30");
        LocalDateTime localtDateAndTime = LocalDateTime.now();
        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localtDateAndTime, america );

        System.out.println(Instant.now().atZone(ZoneId.of("UTC+05:30")));*/

    }

}
