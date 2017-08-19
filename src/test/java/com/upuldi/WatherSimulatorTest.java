package com.upuldi;

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
public class WatherSimulatorTest {

    private static final String TEST_CILTY_NAME = "Test Cilty Name";
    private static final String LATTITUDE = "11.11";
    private static final String LONGITUDE = "22.22";
    private static final String TIME_ZONE = "UTC+10:00";


    public static final String LOCATIONS_FILE = "test-locations.txt";

    private WatherSimulator application = new WatherSimulator();

    @Before
    public void setUp() throws Exception {

        Path testFileLocation = Paths.get(ClassLoader.getSystemResource(LOCATIONS_FILE).toURI());

        StringBuffer locationEntry = new StringBuffer();
        locationEntry.append(TEST_CILTY_NAME).append(",");
        locationEntry.append(LATTITUDE).append(",");
        locationEntry.append(LONGITUDE).append(",");
        locationEntry.append(TIME_ZONE).append(",");

        Files.write(testFileLocation, locationEntry.toString().getBytes());

    }

    @After
    public void tearDown() throws Exception {

        Path testFileLocation = Paths.get(ClassLoader.getSystemResource(LOCATIONS_FILE).toURI());
        Files.write(testFileLocation, "".getBytes());
    }

    @Test
    public void shouldReadLocationFile() throws Exception {

        List<Location> locationList = application.readLocationData(LOCATIONS_FILE);
        assertThat(locationList.size(), Is.is(1));

        Location dummyLocation = locationList.get(0);
        assertThat(dummyLocation.getCity(),Is.is(TEST_CILTY_NAME));
        assertThat(dummyLocation.getLatitude(), Is.is(LATTITUDE));
        assertThat(dummyLocation.getLongitude(), Is.is(LONGITUDE));
        assertThat(dummyLocation.getTimezone(), Is.is(TIME_ZONE));
    }

}