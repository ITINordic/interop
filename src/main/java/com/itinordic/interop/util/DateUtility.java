package com.itinordic.interop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;

/**
 *
 * @author Charles Chigoriwa
 */
public class DateUtility {

    public static final String DHIS_LONG_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static String getStartEventDate(int month, int year) {
        return year + "-" + formatMonth(month) + "-01";
    }

    public static String formatMonth(int month) {
        if (month < 10) {
            return "0" + month;
        }
        return "" + month;
    }

    public static String getEndEventDate(int month, int year) {
        DateTime dateTime = DateTime.now().withTimeAtStartOfDay();
        dateTime = dateTime.withDate(year, month, 1).dayOfMonth().withMaximumValue();
        return year + "-" + formatMonth(month) + "-" + dateTime.getDayOfMonth();
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String dateString = format.format(date);
        return dateString;
    }

    public static String toISOShortDateFormat(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static Date toDate(String stringDate, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = format.parse(stringDate);
        return date;
    }

    public static Date parseLongDhisDate(String stringDate){
        if (GeneralUtility.isEmpty(stringDate)) {
            return null;
        } else {
            try {
                return toDate(stringDate,DHIS_LONG_DATE_FORMAT);
            } catch (ParseException ex) {
               throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(toDate("2019-10-24T18:16:03.138", DHIS_LONG_DATE_FORMAT));
    }

}
