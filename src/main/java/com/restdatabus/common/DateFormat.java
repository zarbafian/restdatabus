package com.restdatabus.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

public class DateFormat {

    private static Logger LOG = LoggerFactory.getLogger(DateFormat.class);

    private DateFormat() {}

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Date parseDate(String value) {

        LOG.debug("parseDate: {}", value);

        Date date = null;

        try {

            int year = Integer.parseInt(value.substring(0, 4));
            int month = Integer.parseInt(value.substring(5, 7));
            int day = Integer.parseInt(value.substring(8, 10));

            LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);

            // TODO: FIXME
            long time = dateTime.atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli();

            System.out.print("time: " + time);

            date = new Date(time);

        } catch (Exception e) {
            throw new IllegalArgumentException("error parsing date: " + value, e);
        }

        LOG.debug("parseDate: {} -> {}", value, date);

        return date;
    }
}
