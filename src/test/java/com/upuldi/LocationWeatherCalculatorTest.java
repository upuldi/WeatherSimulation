package com.upuldi;

import com.upuldi.app.LocationWeatherCalculator;
import com.upuldi.domain.Condition;
import com.upuldi.domain.Hemisphere;
import com.upuldi.domain.Location;
import com.upuldi.domain.Season;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by udoluweera on 8/19/17.
 */
public class LocationWeatherCalculatorTest {


    private Location locationInSouthernHemisphere = new Location();
    private Location locationInNorthernHemisphere = new Location();

    private ZonedDateTime dateInDecember = ZonedDateTime.of(2017, 12, 01, 12, 0, 0, 0, ZoneId.of("UTC+10:00"));


    @Before
    public void setUp() throws Exception {

        locationInSouthernHemisphere.setCity("Sydney");
        locationInSouthernHemisphere.setLongitude("10.10");
        locationInSouthernHemisphere.setLatitude("11.11");
        locationInSouthernHemisphere.setTimezone("UTC+10:00");
        locationInSouthernHemisphere.setHemisphere(Hemisphere.Southern);
        locationInSouthernHemisphere.setZonedDateTime(dateInDecember);

        locationInSouthernHemisphere.setMaxRecordedTemp(40.00);
        locationInSouthernHemisphere.setMinRecordedTemp(-10.00);
        locationInSouthernHemisphere.setMaxRecordedPressure(1120.4);
        locationInSouthernHemisphere.setMinRecordedPressure(854.7);
        locationInSouthernHemisphere.setMaxRecordedHumidity(120);
        locationInSouthernHemisphere.setMinRecordedHumidity(45);

        locationInNorthernHemisphere.setCity("New York");
        locationInNorthernHemisphere.setLongitude("15.10");
        locationInNorthernHemisphere.setLatitude("08.11");
        locationInNorthernHemisphere.setTimezone("UTC-05:00");
        locationInNorthernHemisphere.setHemisphere(Hemisphere.Northern);
        locationInNorthernHemisphere.setZonedDateTime(dateInDecember);
    }

    @Test
    public void seasonShouldBeBasedOnMonthAndHemisphere() throws Exception {

        Location summerInDecember = LocationWeatherCalculator.calculateSeason().apply(locationInSouthernHemisphere);
        MatcherAssert.assertThat(summerInDecember.getSeason(), Is.is(Season.SUMMER));

        Location winterInDecember = LocationWeatherCalculator.calculateSeason().apply(locationInNorthernHemisphere);
        MatcherAssert.assertThat(winterInDecember.getSeason(), Is.is(Season.WINTER));
    }

    @Test
    public void conditionCanNotBeSnowyInSummer(){

        locationInSouthernHemisphere.setSeason(Season.SUMMER);
        Location summerDay = LocationWeatherCalculator.calculateCondition().apply(locationInSouthernHemisphere);

        MatcherAssert.assertThat(summerDay.getCondition(), IsNot.not(Condition.Snow));
    }

    @Test
    public void conditionCanNotBeAnythingInWinter(){

        locationInSouthernHemisphere.setSeason(Season.WINTER);
        Location summerDay = LocationWeatherCalculator.calculateCondition().apply(locationInSouthernHemisphere);

        MatcherAssert.assertThat(summerDay.getCondition(), AnyOf.anyOf(Is.is(Condition.Snow),Is.is(Condition.Rain),Is.is(Condition.Sunny)));
    }

    @Test
    public void conditionCanNotBeSnowyInSpring(){

        locationInSouthernHemisphere.setSeason(Season.SPRING);
        Location summerDay = LocationWeatherCalculator.calculateCondition().apply(locationInSouthernHemisphere);

        MatcherAssert.assertThat(summerDay.getCondition(), IsNot.not(Condition.Snow));
    }

    @Test
    public void conditionCanNotBeAnythingInAutumn(){

        locationInSouthernHemisphere.setSeason(Season.AUTUMN);
        Location summerDay = LocationWeatherCalculator.calculateCondition().apply(locationInSouthernHemisphere);

        MatcherAssert.assertThat(summerDay.getCondition(), AnyOf.anyOf(Is.is(Condition.Snow),Is.is(Condition.Rain),Is.is(Condition.Sunny)));
    }

    @Test
    public void locationAndConditionShouldBeCalculated(){

        Location summerDay = LocationWeatherCalculator.calculateLocationCondition().apply(locationInSouthernHemisphere);

        MatcherAssert.assertThat(summerDay.getSeason(), Is.is(Season.SUMMER));
        MatcherAssert.assertThat(summerDay.getCondition(), AnyOf.anyOf(Is.is(Condition.Snow),Is.is(Condition.Rain),Is.is(Condition.Sunny)));
    }


    @Test
    public void temperatureShouldBeHighForASunnyDay(){

        locationInSouthernHemisphere.setSeason(Season.SPRING);
        locationInSouthernHemisphere.setCondition(Condition.Sunny);

        Location hotDay = LocationWeatherCalculator.calculateTemperature().apply(locationInSouthernHemisphere);

        Assert.assertTrue(hotDay.getCurrentTemperature()> hotDay.getMinRecordedTemp() &&
                hotDay.getCurrentTemperature() <= hotDay.getMaxRecordedTemp());
    }

    @Test
    public void temperatureShouldBetweenMidLowerAndUpperForRainyDay(){

        double max = locationInSouthernHemisphere.getMaxRecordedTemp();
        double min = locationInSouthernHemisphere.getMinRecordedTemp();
        double avg = (max+min)/2;
        double lowToAvg = (min+avg)/2;
        double avgToUpper = (avg+max)/2;

        locationInSouthernHemisphere.setSeason(Season.SPRING);
        locationInSouthernHemisphere.setCondition(Condition.Rain);

        Location testLocation = LocationWeatherCalculator.calculateTemperature().apply(locationInSouthernHemisphere);

        Assert.assertTrue(testLocation.getCurrentTemperature()> lowToAvg &&
                testLocation.getCurrentTemperature() <= avgToUpper);
    }

    @Test
    public void temperatureShouldBetweenLowerInSnowyDays(){

        double avg = (locationInSouthernHemisphere.getMaxRecordedTemp() +
                locationInSouthernHemisphere.getMinRecordedTemp())/2;

        double lowToAvg = (locationInSouthernHemisphere.getMinRecordedTemp() + avg)/2;

        locationInSouthernHemisphere.setSeason(Season.SPRING);
        locationInSouthernHemisphere.setCondition(Condition.Snow);

        Location testLocation = LocationWeatherCalculator.calculateTemperature().apply(locationInSouthernHemisphere);

        Assert.assertTrue(testLocation.getCurrentTemperature()> locationInSouthernHemisphere.getMinRecordedTemp() &&
                testLocation.getCurrentTemperature() <= lowToAvg);
    }

    @Test
    public void pressureShouldBeHighForSunnyDay() {

        locationInSouthernHemisphere.setSeason(Season.SPRING);
        locationInSouthernHemisphere.setCondition(Condition.Sunny);

        Location testLocation = LocationWeatherCalculator.calculatePressure().apply(locationInSouthernHemisphere);

        Assert.assertTrue(testLocation.getCurrentPressure() > locationInSouthernHemisphere.getMinRecordedPressure() &&
                testLocation.getCurrentPressure() <= locationInSouthernHemisphere.getMaxRecordedPressure());
    }

    @Test
    public void pressureShouldBeCalculatedForRainyDay() {

        locationInSouthernHemisphere.setSeason(Season.SPRING);
        locationInSouthernHemisphere.setCondition(Condition.Rain);

        Location testLocation = LocationWeatherCalculator.calculatePressure().apply(locationInSouthernHemisphere);

        Assert.assertTrue(testLocation.getCurrentPressure() > locationInSouthernHemisphere.getMinRecordedPressure() &&
                testLocation.getCurrentPressure() <= locationInSouthernHemisphere.getMaxRecordedPressure());
    }

    @Test
    public void pressureShouldBeCalculatedForSnowyDay() {

        locationInSouthernHemisphere.setSeason(Season.SUMMER);
        locationInSouthernHemisphere.setCondition(Condition.Snow);

        Location testLocation = LocationWeatherCalculator.calculatePressure().apply(locationInSouthernHemisphere);

        Assert.assertTrue(testLocation.getCurrentPressure() > locationInSouthernHemisphere.getMinRecordedPressure() &&
                testLocation.getCurrentPressure() <= locationInSouthernHemisphere.getMaxRecordedPressure());
    }

    @Test
    public void humidityShouldBeHighForSunnyDay() {

        locationInSouthernHemisphere.setSeason(Season.SUMMER);
        locationInSouthernHemisphere.setCondition(Condition.Sunny);

        Location testLocation = LocationWeatherCalculator.calculateHumidity().apply(locationInSouthernHemisphere);

        Assert.assertTrue(testLocation.getCurrentHumidity() > locationInSouthernHemisphere.getMinRecordedHumidity() &&
                testLocation.getCurrentHumidity() <= locationInSouthernHemisphere.getMaxRecordedHumidity());
    }

    @Test
    public void humidityShouldBeHighForRainyDay() {

        locationInSouthernHemisphere.setSeason(Season.WINTER);
        locationInSouthernHemisphere.setCondition(Condition.Rain);

        Location testLocation = LocationWeatherCalculator.calculateHumidity().apply(locationInSouthernHemisphere);

        Assert.assertTrue(testLocation.getCurrentHumidity() > locationInSouthernHemisphere.getMinRecordedHumidity() &&
                testLocation.getCurrentHumidity() <= locationInSouthernHemisphere.getMaxRecordedHumidity());
    }

    @Test
    public void humidityShouldBeHighForSnowyDay() {

        locationInSouthernHemisphere.setSeason(Season.WINTER);
        locationInSouthernHemisphere.setCondition(Condition.Snow);

        Location testLocation = LocationWeatherCalculator.calculateHumidity().apply(locationInSouthernHemisphere);

        Assert.assertTrue(testLocation.getCurrentHumidity() > locationInSouthernHemisphere.getMinRecordedHumidity() &&
                testLocation.getCurrentHumidity() <= locationInSouthernHemisphere.getMaxRecordedHumidity());
    }

    @Test
    public void shouldCalculateWeatherForALocationForBasicInfo() {
        Location testLocation = LocationWeatherCalculator.calculateLocationWeather(locationInSouthernHemisphere);

        Assert.assertThat(testLocation.getSeason(),Is.is(Season.SUMMER));
        Assert.assertThat(testLocation.getCondition(),Is.is(Condition.Sunny));
        Assert.assertNotNull(testLocation.getCurrentTemperature());
        Assert.assertNotNull(testLocation.getCurrentPressure());
        Assert.assertNotNull(testLocation.getCurrentHumidity());

    }


}