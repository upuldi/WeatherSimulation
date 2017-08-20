package com.upuldi.util;

import com.upuldi.domain.Hemisphere;
import com.upuldi.domain.Location;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a util class which is used to read text files.
 *
 */
public class FileUtils {

    private static Function<String, Location> readLocationRow = s -> {

        String[] columns = s.split("[,]");
        Location location = new Location();
        location.setCity(columns[0]);
        location.setLatitude(columns[1]);
        location.setLongitude(columns[2]);
        location.setElevation(Integer.parseInt(columns[3]));
        location.setTimezone(columns[4]);
        location.setHemisphere(Hemisphere.getByCode(columns[5]));
        location.setMaxRecordedTemp(Double.parseDouble(columns[6]));
        location.setMinRecordedTemp(Double.parseDouble(columns[7]));
        location.setMaxRecordedPressure(Double.parseDouble(columns[8]));
        location.setMinRecordedPressure(Double.parseDouble(columns[9]));
        location.setMaxRecordedHumidity(Integer.parseInt(columns[10]));
        location.setMinRecordedHumidity(Integer.parseInt(columns[11]));

        //Keep the local date time for the location
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of(columns[4]));
        location.setZonedDateTime(zonedDateTime);

        return location;
    };

    public static List<Location> readLocations(String path) {

            List<Location> locationList = null;
            try (Stream<String> stream = Files.lines(getPath(path))) {
                locationList = stream.filter(line -> !line.startsWith("#")).map(readLocationRow).collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return locationList;
    }

    private static Path getPath(String path) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(path).toURI());
    }
}
