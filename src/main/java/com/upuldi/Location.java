package com.upuldi;

import java.time.ZonedDateTime;

/**
 * Created by udoluweera on 8/18/17.
 */
public class Location {

    private String city;
    private String latitude;
    private String longitude;
    private String timezone;
    private ZonedDateTime zonedDateTime;
    private Hemisphere hemisphere;
    private Season season;




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
}
