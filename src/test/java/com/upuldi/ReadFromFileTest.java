package com.upuldi;

import com.upuldi.domain.Hemisphere;
import com.upuldi.domain.Location;
import com.upuldi.util.FileUtils;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Created by udoluweera on 8/18/17.
 */
public class ReadFromFileTest {

    private static final String TEST_CILTY_NAME = "Test Cilty Name";
    private static final String LATTITUDE = "11.11";
    private static final String LONGITUDE = "22.22";
    private static final String TIME_ZONE = "UTC+10:00";


    public static final String LOCATIONS_FILE = "test-locations.txt";
    public static final String HEMISPHERE = "SH";
    public static final double RECORDED_MAX_TEMP = 35.25;
    public static final double RECORDED_MIN_TEMP = -5.25;
    public static final double RECORDED_MAX_PRESSURE = 1245.8;
    public static final double RECORDED_MIN_PRESSURE = 873.6;
    public static final int RECORDED_MAX_HUMIDITY = 110;
    public static final int RECORDED_MIN_HUMIDITY = 9;
    public static final int ELEVATION = 53;

    @Before
    public void setUp() throws Exception {

        Path testFileLocation = Paths.get(ClassLoader.getSystemResource(LOCATIONS_FILE).toURI());

        StringBuffer locationEntry = new StringBuffer();
        locationEntry.append(TEST_CILTY_NAME).append(",");
        locationEntry.append(LATTITUDE).append(",");
        locationEntry.append(LONGITUDE).append(",");
        locationEntry.append(ELEVATION).append(",");
        locationEntry.append(TIME_ZONE).append(",");
        locationEntry.append(HEMISPHERE).append(",");
        locationEntry.append(RECORDED_MAX_TEMP).append(",");
        locationEntry.append(RECORDED_MIN_TEMP).append(",");
        locationEntry.append(RECORDED_MAX_PRESSURE).append(",");
        locationEntry.append(RECORDED_MIN_PRESSURE).append(",");
        locationEntry.append(RECORDED_MAX_HUMIDITY).append(",");
        locationEntry.append(RECORDED_MIN_HUMIDITY).append(",");

        Files.write(testFileLocation, locationEntry.toString().getBytes());
    }

    @After
    public void tearDown() throws Exception {

        Path testFileLocation = Paths.get(ClassLoader.getSystemResource(LOCATIONS_FILE).toURI());
        Files.write(testFileLocation, "".getBytes());
    }

    @Test
    public void shouldReadLocationFile() throws Exception {

        List<Location> locationList = FileUtils.readLocations(LOCATIONS_FILE);
        assertThat(locationList.size(), Is.is(1));

        Location testLocation = locationList.get(0);
        assertThat(testLocation.getCity(),Is.is(TEST_CILTY_NAME));
        assertThat(testLocation.getLatitude(), Is.is(LATTITUDE));
        assertThat(testLocation.getLongitude(), Is.is(LONGITUDE));
        assertThat(testLocation.getElevation(), Is.is(ELEVATION));
        assertThat(testLocation.getTimezone(), Is.is(TIME_ZONE));
        assertThat(testLocation.getHemisphere(), Is.is(Hemisphere.Southern));
        assertThat(testLocation.getMaxRecordedTemp(), Is.is(RECORDED_MAX_TEMP));
        assertThat(testLocation.getMinRecordedTemp(), Is.is(RECORDED_MIN_TEMP));
        assertThat(testLocation.getMaxRecordedPressure(), Is.is(RECORDED_MAX_PRESSURE));
        assertThat(testLocation.getMinRecordedPressure(), Is.is(RECORDED_MIN_PRESSURE));
        assertThat(testLocation.getMaxRecordedHumidity(), Is.is(RECORDED_MAX_HUMIDITY));
        assertThat(testLocation.getMinRecordedHumidity(), Is.is(RECORDED_MIN_HUMIDITY));
    }

}