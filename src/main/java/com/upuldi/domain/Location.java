package com.upuldi.domain;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Location {

    private String city;
    private String latitude;
    private String longitude;
    private String timezone;
    private ZonedDateTime zonedDateTime;
    private Hemisphere hemisphere;
    private Season season;
    private Condition condition;
    private Double maxRecordedTemp;
    private Double minRecordedTemp;
    private Double currentTemperature;
    private Double maxRecordedPressure;
    private Double minRecordedPressure;
    private Double currentPressure;
    private Integer maxRecordedHumidity;
    private Integer minRecordedHumidity;
    private Double currentHumidity;
    private Integer elevation;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public Hemisphere getHemisphere() {
        return hemisphere;
    }

    public void setHemisphere(Hemisphere hemisphere) {
        this.hemisphere = hemisphere;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Double getMaxRecordedTemp() {
        return maxRecordedTemp;
    }

    public void setMaxRecordedTemp(Double maxRecordedTemp) {
        this.maxRecordedTemp = maxRecordedTemp;
    }

    public Double getMinRecordedTemp() {
        return minRecordedTemp;
    }

    public void setMinRecordedTemp(Double minRecordedTemp) {
        this.minRecordedTemp = minRecordedTemp;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getMaxRecordedPressure() {
        return maxRecordedPressure;
    }

    public void setMaxRecordedPressure(Double maxRecordedPressure) {
        this.maxRecordedPressure = maxRecordedPressure;
    }

    public Double getMinRecordedPressure() {
        return minRecordedPressure;
    }

    public void setMinRecordedPressure(Double minRecordedPressure) {
        this.minRecordedPressure = minRecordedPressure;
    }

    public Integer getMaxRecordedHumidity() {
        return maxRecordedHumidity;
    }

    public void setMaxRecordedHumidity(Integer maxRecordedHumidity) {
        this.maxRecordedHumidity = maxRecordedHumidity;
    }

    public Integer getMinRecordedHumidity() {
        return minRecordedHumidity;
    }

    public void setMinRecordedHumidity(Integer minRecordedHumidity) {
        this.minRecordedHumidity = minRecordedHumidity;
    }

    public Double getCurrentPressure() {
        return currentPressure;
    }

    public void setCurrentPressure(Double currentPressure) {
        this.currentPressure = currentPressure;
    }

    public Double getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(Double currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    @Override
    public String toString() {

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        DecimalFormat singleDigit = new DecimalFormat("#");

        StringBuffer location = new StringBuffer();
        location.append(city).append("|");
        location.append(latitude).append(",");
        location.append(longitude).append(",");
        location.append(elevation).append("|");
        location.append(zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("|");
        location.append(condition.name()).append("|");
        location.append(decimalFormat.format(currentTemperature)).append("|");
        location.append(decimalFormat.format(currentPressure)).append("|");
        location.append(singleDigit.format(currentHumidity));

        return location.toString();
    }

}
