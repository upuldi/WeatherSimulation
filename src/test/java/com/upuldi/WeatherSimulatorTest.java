package com.upuldi;

import com.upuldi.domain.Condition;
import com.upuldi.domain.Hemisphere;
import com.upuldi.domain.Location;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * Created by udoluweera on 8/20/17.
 */
public class WeatherSimulatorTest {

    private WeatherSimulator weatherSimulator = new WeatherSimulator();
    private Location location = new Location();
    private ZonedDateTime dateInDecember = ZonedDateTime.of(2017, 12, 01, 12, 0, 0, 0, ZoneId.of("UTC+10:00"));

    @Before
    public void setUp() throws Exception {
        location.setCity("Sydney");
        location.setLongitude("10.10");
        location.setLatitude("11.11");
        location.setTimezone("UTC+10:00");
        location.setHemisphere(Hemisphere.Southern);
        location.setZonedDateTime(dateInDecember);
        location.setMaxRecordedTemp(40.00);
        location.setMinRecordedTemp(-10.00);
        location.setMaxRecordedPressure(1120.4);
        location.setMinRecordedPressure(854.7);
        location.setMaxRecordedHumidity(120);
        location.setMinRecordedHumidity(45);
    }

    @Test
    public void dateShouldBeIncreasedInEveryStep() throws Exception {
        Location result = weatherSimulator.setRandomDate().apply(location,5);
        Assert.assertTrue(result.getZonedDateTime().isAfter(dateInDecember));
    }

    @Test
    public void dateRangeShouldBeBetweenCurrentDateAndStep() throws Exception {

        int step = 5;
        int maxPossibleDate = dateInDecember.getDayOfMonth() + step;
        Location result = weatherSimulator.setRandomDate().apply(location,step);
        int increasedDate = result.getZonedDateTime().getDayOfMonth();
        Assert.assertTrue(increasedDate > dateInDecember.getDayOfMonth() &&
                (increasedDate <= maxPossibleDate));
    }

    @Test
    public void simulateWeatherShouldGenerateLocationData() throws Exception {

        weatherSimulator.simulateWeather(1, Arrays.asList(location));

        Assert.assertNotNull(location.getCondition());
        Assert.assertNotNull(location.getSeason());
        Assert.assertNotNull(location.getCurrentTemperature());
        Assert.assertNotNull(location.getCurrentPressure());
        Assert.assertNotNull(location.getCurrentHumidity());
    }

}