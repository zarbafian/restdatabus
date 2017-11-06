package com.restdatabus.common;

import com.restdatabus.web.api.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    public static Date parseDatetime(String value) {

        LOG.debug("parseDate: {}", value);

        Date date = null;

        try {

            int year = Integer.parseInt(value.substring(0, 4));
            int month = Integer.parseInt(value.substring(5, 7));
            int day = Integer.parseInt(value.substring(8, 10));

            int hours = Integer.parseInt(value.substring(11, 13));
            int minutes = Integer.parseInt(value.substring(14, 16));
            int seconds = Integer.parseInt(value.substring(17, 19));


            LocalDateTime dateTime = LocalDateTime.of(year, month, day, hours, minutes, seconds);

            // TODO: FIXME
            long time = dateTime.atZone(Constants.TIME_ZONE).toInstant().toEpochMilli();

            System.out.print("time: " + time);

            date = new Date(time);

        } catch (Exception e) {
            throw new IllegalArgumentException("error parsing date: " + value, e);
        }

        LOG.debug("parseDate: {} -> {}", value, date);

        return date;
    }

    public static String formatDatetime (OffsetDateTime dateTime) {

        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
