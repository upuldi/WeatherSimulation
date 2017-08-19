package com.upuldi;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by L078878 on 18/08/2017.
 */
public class Test {

    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of("UTC+05:30"));
        System.out.println(zonedDateTime.toLocalDateTime());
    }
}
