package com.itinordic.interop.util;

import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.springframework.web.util.HtmlUtils;

/**
 *
 * @author Charles Chigoriwa
 */
public class GeneralUtility {

    public static boolean isEmpty(String value) {
        return (value == null || value.trim().isEmpty());
    }

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Object object) {
        return object == null;
    }

    public static UUID parseIdUuid(String string) {
        return UUID.fromString(string);
    }

    public static Long parseIdLong2(String string) {
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException ex) {
            return -1l;
        }
    }

    public static Integer parseInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static String htmlString(String string) {
        if (!isEmpty(string)) {
            return HtmlUtils.htmlEscape(string).replace("\n\r\n", " <br />").replace("\r\n\n", " <br/>").replace("\n\r", " <br/>").replace("\r\n", " <br/>").replace("\n", " <br />")
                    .replace("\r", " <br/>");
        } else {
            return string;
        }
    }

    public static String removeSpaces(String string) {
        return string.replaceAll("\\s+", "");
    }

    public static boolean isTrue(Boolean bool) {
        return bool != null && bool.equals(Boolean.TRUE);
    }

    public static Integer getCurrentYear() {
        return DateTime.now().getYear();
    }

    public static boolean isFutureDate(Date date) {
        DateTime todayDateTime = DateTime.now().withTimeAtStartOfDay();
        DateTime dateTime = new DateTime(date).withTimeAtStartOfDay();
        return dateTime.isAfter(todayDateTime);
    }

    public static String getEmailDomain(String emailAddress) {
        return emailAddress.split("@")[1];
    }

    public static File getGeoLiteCityFile() {
        String fileName = "GeoLiteCity.dat";
        File fundoLinkerDir = new File(System.getProperty("user.home") + File.separator + ".flnkr");
        File file = new File(fundoLinkerDir, fileName);
        return file;
    }

    public static Integer parsePositiveInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static String getBasicAuthorization(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public static String displayMoney(BigDecimal money) {
        DecimalFormat f = new DecimalFormat("###,###.00");
        if (money.compareTo(BigDecimal.ZERO) == 0) {
            f = new DecimalFormat("#0.00");
        }
        return f.format(money);
    }

    public static String displayMoney(double money) {
        DecimalFormat f = new DecimalFormat("###,###.00");
        if (money == 0) {
            f = new DecimalFormat("#0.00");
        }
        return f.format(money);
    }

    public static boolean isValidPhoneNumber(String value) {
        if (value.startsWith("+27")) {
            return isValidSouthAfricaPhoneNumber(value);
        } else if (value.startsWith("+263")) {
            return isValidZimbabwePhoneNumber(value);
        } else {
            return isValidGenericPhoneNumber(value);
        }
    }

    public static boolean isValidSouthAfricaPhoneNumber(String value) {
        String regex = "(\\+27)((60[3-9]|64[0-5])\\d{6}|(7[1-4689]|6[1-3]|8[1-4])\\d{7})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidZimbabwePhoneNumber(String value) {
        String regex = "(\\+263)((77||73|71|78)\\d{7})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidGenericPhoneNumber(String value) {
        String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidEmailAddress(String value) {
        return new EmailValidator().isValid(value, null);
    }

    public static boolean auditEquals(String string1, String string2) {
        if (string1 == null && string2 == null) {
            return true;
        } else {
            return string1 != null && string2 != null && string1.trim().equalsIgnoreCase(string2.trim());
        }
    }

    public static boolean auditEquals(Object object1, Object object2) {
        if (object1 == null && object2 == null) {
            return true;
        } else {
            return object1 != null && object2 != null && object1.equals(object2);
        }
    }

    public static boolean auditEquals(BigDecimal object1, BigDecimal object2) {
        if (object1 == null && object2 == null) {
            return true;
        } else {
            return object1 != null && object2 != null && object1.compareTo(object2) == 0;
        }
    }

    public static String toBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] fromBase64String(String base64String) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return decodedBytes;

    }

    public static void testPhonesMain(String[] args) {
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("+11234567890123");
        phoneNumbers.add("+12123456789");
        phoneNumbers.add("+123123456");
        phoneNumbers.add("+263773831847");
        String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(regex);

        phoneNumbers.forEach((phoneNumber) -> {
            Matcher matcher = pattern.matcher(phoneNumber);
            System.out.println(phoneNumber + " : " + matcher.matches());
        });
    }

    public static String getSubstring(String string, int length) {
        if (length >= string.length()) {
            return string;
        } else {
            return string.substring(0, length);
        }
    }

    public static String wrapAnchors(String string) {
        String[] parts = string.split("\\s+");
        if (parts.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String item : parts) {
                if (isUrl(item)) {
                    sb.append(hrefWrap(item));
                } else {
                    sb.append(item).append(" ");
                }
            }
            return sb.toString();
        } else if (isUrl(string)) {
            return hrefWrap(string);
        } else {
            return string;
        }
    }

    public static String displayStandSize(BigDecimal size) {
        if (isIntegerValue(size)) {
            DecimalFormat f = new DecimalFormat("###,###");
            return f.format(size);
        } else {
            DecimalFormat f = new DecimalFormat("###,###.00");
            return f.format(size);
        }

    }

    public static boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    private static boolean isUrl(String string) {
        try {
            URL url = new URL(string);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    private static String hrefWrap(String string) {
        String href = formatAsUrl(string);
        return "<a target=\"_blank\" href=\"" + href + "\">" + string + "</a> ";
    }

    private static String formatAsUrl(String string) {
        String[] endArgs = {" ", ",", ".", ":", ";"};
        do {
            if (strEndsWith(string, endArgs)) {
                string = string.substring(0, string.length() - 1);
            }
        } while (strEndsWith(string, endArgs));
        return string;
    }

    private static boolean strEndsWith(String string, String... args) {
        if (args == null || args.length == 0) {
            return false;
        } else {
            for (String arg : args) {
                if (string.endsWith(arg)) {
                    return true;
                }
            }

        }
        return false;
    }

    public static double getLoanAmount(BigDecimal standPrice, BigDecimal depositRatePercentage) {
        double depositRate = depositRatePercentage.doubleValue();
        double loanAmount = standPrice.doubleValue() * (100.0 - depositRate) / 100.0;
        return loanAmount;
    }

    public static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static String getTimeString(Date date) {
        String unitName;
        Integer unit;
        DateTime creationDateTime = new DateTime(date);
        DateTime todayDateTime = DateTime.now();
        unit = Years.yearsBetween(creationDateTime, todayDateTime).getYears();

        if (unit >= 1) {
            unitName = "year";
        } else {
            unit = Months.monthsBetween(creationDateTime, todayDateTime).getMonths();
            if (unit >= 1) {
                unitName = "month";
            } else {
                unit = Weeks.weeksBetween(creationDateTime, todayDateTime).getWeeks();
                if (unit >= 1) {
                    unitName = "week";
                } else {
                    unit = Days.daysBetween(creationDateTime, todayDateTime).getDays();
                    if (unit >= 1) {
                        unitName = "day";
                    } else {
                        unit = Hours.hoursBetween(creationDateTime, todayDateTime).getHours();
                        if (unit >= 1) {
                            unitName = "hour";
                        } else {
                            unit = Minutes.minutesBetween(creationDateTime, todayDateTime).getMinutes();
                            if (unit >= 1) {
                                unitName = "minute";
                            } else {
                                unit = Seconds.secondsBetween(creationDateTime, todayDateTime).getSeconds();
                                unitName = "second";
                            }
                        }
                    }
                }
            }
        }

        if (unit > 1) {
            unitName += "s";
        }
        return unit + " " + unitName + " ago";

    }

    public static void main1(String[] args) {
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("+11234567890123");
        phoneNumbers.add("+12123456789");
        phoneNumbers.add("+123123456");
        phoneNumbers.add("+27715711701");
        phoneNumbers.forEach((phoneNumber) -> {
            System.out.println(phoneNumber + " : " + GeneralUtility.isValidSouthAfricaPhoneNumber(phoneNumber));
        });
    }

    public static String getNumericEndedUuid() {
        return UUID.randomUUID().toString() + "-" + (int) (Math.random() * 10);
    }

    public static boolean isMoney(String value) {
        try {
            BigDecimal money = new BigDecimal(value);
            return money.compareTo(BigDecimal.ZERO) == 1;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isMoney(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 1;
    }

    public static Date getNextAmortizationStartDate(Date date) {
        DateTime dateTime = new DateTime(date);
        int dayOfMonth = dateTime.dayOfMonth().get();
        int month = dateTime.getMonthOfYear();
        int year = dateTime.getYear();
        if (dayOfMonth >= 20) {
            month = month + 1;
            if (month > 12) {
                year = year + 1;
                month = 1;
            }
        }
        return new DateTime(year, month, 25, 12, 12).toDate();

    }

    public static boolean in(String string, String... otherStrings) {
        if (otherStrings == null || otherStrings.length < 1) {
            return false;
        }

        for (String otherString : otherStrings) {
            if (otherString.equalsIgnoreCase(string)) {
                return true;
            }
        }

        return false;
    }

    public static String encloseCsvString(String string) {
        return "" + string + "";
    }

}
