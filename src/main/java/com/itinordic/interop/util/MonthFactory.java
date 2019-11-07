package com.itinordic.interop.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class MonthFactory {

    public static List<Named> getMonths() {
        List<Named> months = new ArrayList<>();
        months.add(new Named("January", 1));
        months.add(new Named("February", 2));
        months.add(new Named("March", 3));
        months.add(new Named("April", 4));
        months.add(new Named("May", 5));
        months.add(new Named("June", 6));
        months.add(new Named("July", 7));
        months.add(new Named("August", 8));
        months.add(new Named("September", 9));
        months.add(new Named("October", 10));
        months.add(new Named("November", 11));
        months.add(new Named("December", 12));
        return months;
    }

    public static List<Integer> getMonths(int numberOfMonths) {
        List<Integer> months = new ArrayList<>();
        for (int month = 1; month <= numberOfMonths; month++) {
            months.add(month);
        }
        return months;
    }

}
