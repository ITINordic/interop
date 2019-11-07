package com.itinordic.interop.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class YearFactory {

    public static List<Integer> getYears(int startYear, int numberOfYears) {
        List<Integer> years = new ArrayList<>();
        for (int year = startYear; year >= (startYear - numberOfYears); year--) {
            years.add(year);
        }
        return years;
    }

}
