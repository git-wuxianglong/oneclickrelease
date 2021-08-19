package com.wupao.oneclickrelease.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * <br>创建时间：2021/4/6
 *
 * @author wuxianglong
 */
public class DateUtil {

    /**
     * Base ISO 8601 Date format yyyyMMdd i.e., 20021225 for the 25th day of December in the year 2002
     */
    public static final String ISO_DATE_FORMAT = "yyyyMMdd";

    /**
     * Expanded ISO 8601 Date format yyyy-MM-dd i.e., 2002-12-25 for the 25th day of December in the year 2002
     */
    public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd hh:mm:ss
     */
    public static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_PATTERN = "yyyyMMddHHmmss";
    public static String DATE_PATTERN_MILLISECONDS = "yyyyMMddHHmmssSSSS";

    private static final boolean LENIENT_DATE = false;

    private static final Random RANDOM = new Random();
    private static final int ID_BYTES = 10;

    private static final int NUM_TWO = 2;
    private static final int NUM_TWELVE = 12;

    public synchronized static String generateId() {
        StringBuilder result = new StringBuilder();
        result.append(System.currentTimeMillis());
        for (int i = 0; i < ID_BYTES; i++) {
            result.append(RANDOM.nextInt(10));
        }
        return result.toString();
    }

    protected static float normalizedJulian(float jd) {
        return Math.round(jd + 0.5f) - 0.5f;
    }

    /**
     * Returns the Date from a julian. The Julian date will be converted to noon GMT,
     * such that it matches the nearest half-integer (i.e., a julian date of 1.4 gets
     * changed to 1.5, and 0.9 gets changed to 0.5.)
     *
     * @param jd the Julian date
     * @return the Gregorian date
     */
    public static Date toDate(float jd) {
        /* To convert a Julian Day Number to a Gregorian date, assume that it is for 0 hours, Greenwich time (so
         * that it ends in 0.5). Do the following calculations, again dropping the fractional part of all
         * multiplications and divisions. Note: This method will not give dates accurately on the
         * Gregorian Problematic Calendar, i.e., the calendar you get by extending the Gregorian
         * calendar backwards to years earlier than 1582. using the Gregorian leap year
         * rules. In particular, the method fails if Y<400.
         * */
        float z = (normalizedJulian(jd)) + 0.5f;
        float w = (int) ((z - 1867216.25f) / 36524.25f);
        float x = (int) (w / 4f);
        float a = z + 1 + w - x;
        float b = a + 1524;
        float c = (int) ((b - 122.1) / 365.25);
        float d = (int) (365.25f * c);
        float e = (int) ((b - d) / 30.6001);
        float f = (int) (30.6001f * e);
        int day = (int) (b - d - f);
        int month = (int) (e - 1);

        if (month > NUM_TWELVE) {
            month = month - 12;
        }

        // (if Month is January or February) or C-4716 (otherwise)
        int year = (int) (c - 4715);

        if (month > NUM_TWO) {
            year--;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        // damn 0 offsets
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);

        return calendar.getTime();
    }

    /**
     * Returns the days between two dates. Positive values indicate that
     * the second date is after the first, and negative values indicate, well,
     * the opposite. Relying on specific times is problematic.
     *
     * @param early the "first date"
     * @param late  the "second date"
     * @return the days between the two dates
     */
    public static int daysBetween(Date early, Date late) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(early);
        c2.setTime(late);
        return daysBetween(c1, c2);
    }

    /**
     * Returns the days between two dates. Positive values indicate that
     * the second date is after the first, and negative values indicate, well,
     * the opposite.
     *
     * @param early the "first date"
     * @param late  the "second date"
     * @return the days between two dates.
     */
    public static int daysBetween(Calendar early, Calendar late) {
        return (int) (toJulian(late) - toJulian(early));
    }

    /**
     * Return a Julian date based on the input parameter.
     *
     * @param c a calendar instance
     * @return the julian day number
     */
    public static float toJulian(Calendar c) {
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DATE);
        int a = y / 100;
        int b = a / 4;
        int g = 2 - a + b;
        float e = (int) (365.25f * (y + 4716));
        float f = (int) (30.6001f * (m + 1));
        return g + d + e + f - 1524.5f;
    }

    /**
     * Return a Julian date based on the input parameter.
     *
     * @param date Date
     * @return the julian day number
     */
    public static float toJulian(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return toJulian(c);
    }

    /**
     * @param isoString 字符串日期
     * @param fmt       format
     * @param field     Calendar.YEAR/Calendar.MONTH/Calendar.DATE
     * @param amount    the amount of date or time to be added to the field.
     * @return 格式化增加后的日期字符串
     */
    public static String dateIncrease(String isoString, String fmt, int field, int amount) {
        try {
            Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.setTime(stringToDate(isoString, fmt, true));
            cal.add(field, amount);
            return dateToString(cal.getTime(), fmt);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Time Field Rolling function.
     * Rolls (up/down) a single unit of time on the given time field.
     *
     * @param isoString 字符串日期
     * @param field     the time field.
     * @param up        Indicates if rolling up or rolling down the field value.
     * @throws ParseException if an unknown field value is given.
     */
    public static String roll(String isoString, String fmt, int field,
                              boolean up) throws ParseException {
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(stringToDate(isoString, fmt));
        cal.roll(field, up);
        return dateToString(cal.getTime(), fmt);
    }

    /**
     * Time Field Rolling function.
     * Rolls (up/down) a single unit of time on the given time field.
     *
     * @param isoString 字符串日期
     * @param field     the time field.
     * @param up        Indicates if rolling up or rolling down the field value.
     * @throws ParseException if an unknown field value is given.
     */
    public static String roll(String isoString, int field, boolean up) throws ParseException {
        return roll(isoString, DATETIME_PATTERN, field, up);
    }

    /**
     * 字符串转date
     *
     * @param dateText 字符串日期
     * @param format   格式化格式
     * @param lenient  是否严格解析字符串
     * @return 字符串转date
     */
    public static Date stringToDate(String dateText, String format, boolean lenient) {
        if (dateText == null) {
            return null;
        }
        DateFormat df = null;
        try {
            if (format == null) {
                df = new SimpleDateFormat();
            } else {
                df = new SimpleDateFormat(format);
            }
            // setLenient avoids allowing dates like 9/32/2001
            // which would otherwise parse to 10/2/2001
            df.setLenient(false);
            return df.parse(dateText);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @return Timestamp
     */
    public static java.sql.Timestamp getCurrentTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * 字符串转date
     *
     * @param dateString 字符串日期
     * @param format     格式化格式
     * @return 字符串转date
     */
    public static Date stringToDate(String dateString, String format) {
        return stringToDate(dateString, format, LENIENT_DATE);
    }

    /**
     * 字符串转date
     *
     * @param dateString 字符串日期
     * @return 字符串转date
     */
    public static Date stringToDate(String dateString) {
        return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
    }

    /**
     * date日期转字符串
     *
     * @param pattern 格式化格式
     * @param date    Date日期
     * @return date日期转字符串
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
            sfDate.setLenient(false);
            return sfDate.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * date日期转字符串，返回格式：yyyy-MM-dd
     *
     * @param date Date日期
     * @return 返回格式为yyyy-MM-dd的字符串日期
     */
    public static String dateToString(Date date) {
        return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
    }

    /**
     * 获取当前日期
     *
     * @return Date
     */
    public static Date getCurrentDateTime() {
        Calendar calNow = Calendar.getInstance();
        return calNow.getTime();
    }

    /**
     * 返回指定格式的当前日期字符串
     *
     * @param pattern 格式化日期格式
     * @return 返回指定格式的当前日期字符串
     */
    public static String getCurrentDateString(String pattern) {
        return dateToString(getCurrentDateTime(), pattern);
    }

    /**
     * 返回格式为yyyy-MM-dd的当前日期字符串
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentDateString() {
        return dateToString(getCurrentDateTime(), ISO_EXPANDED_DATE_FORMAT);
    }

    /**
     * 返回固定格式的当前时间 yyyy-MM-dd hh:mm:ss
     *
     * @return yyyy-MM-dd hh:mm:ss 当前时间字符串
     */
    public static String dateToStringWithTime() {
        return dateToString(new Date(), DATETIME_PATTERN);
    }


    /**
     * 将Date日期转换为 yyyy-MM-dd hh:mm:ss 字符串格式
     *
     * @param date Date
     * @return yyyy-MM-dd hh:mm:ss 字符串
     */
    public static String dateToStringWithTime(Date date) {
        return dateToString(date, DATETIME_PATTERN);
    }

    /**
     * 返回增加指定天数的日期
     *
     * @param date Date
     * @param days 要增加的天数
     * @return java.util.Date
     */
    public static Date dateIncreaseByDay(Date date, int days) {
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 返回增加指定月数的日期
     *
     * @param date Date
     * @param mnt  要增加的月数
     * @return java.util.Date
     */
    public static Date dateIncreaseByMonth(Date date, int mnt) {
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(date);
        cal.add(Calendar.MONTH, mnt);
        return cal.getTime();
    }

    /**
     * 返回增加指定年数的日期
     *
     * @param date Date
     * @param mnt  要增加的年数
     * @return java.util.Date
     */
    public static Date dateIncreaseByYear(Date date, int mnt) {
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(date);
        cal.add(Calendar.YEAR, mnt);
        return cal.getTime();
    }

    /**
     * 返回增加指定天数的日期字符串
     *
     * @param date yyyy-MM-dd
     * @param days 要增加的天数
     * @return yyyy-MM-dd 字符串
     */
    public static String dateIncreaseByDay(String date, int days) {
        return dateIncreaseByDay(date, ISO_DATE_FORMAT, days);
    }

    /**
     * 返回增加指定天数后的指定格式的日期字符串
     *
     * @param date fmt格式的日期
     * @param fmt  日期格式
     * @param days 要增加的天数
     * @return 增加指定天数后的、fmt格式的日期
     */
    public static String dateIncreaseByDay(String date, String fmt, int days) {
        return dateIncrease(date, fmt, Calendar.DATE, days);
    }

    /**
     * 字符串转字符串
     *
     * @param src    源日期字符串
     * @param srcFmt 源日期格式
     * @param desFmt 需要转换的格式
     * @return 将一种字符串日期格式转为另一种格式
     */
    public static String stringToString(String src, String srcFmt, String desFmt) {
        return dateToString(stringToDate(src, srcFmt), desFmt);
    }

    /**
     * 获取年 yyyy
     *
     * @param date Date
     * @return string
     */
    public static String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    /**
     * 获取月
     *
     * @param date Date
     * @return string
     */
    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    /**
     * 获取天  dd
     *
     * @param date Date
     * @return string
     */
    public static String getDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(date);
    }

    /**
     * 获取int类型天  dd
     *
     * @param date Date
     * @return int类型天
     */
    public static int getDayInt(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String curDay = format.format(date);
        return Integer.parseInt(curDay);
    }

    /**
     * 获取小时 HH
     *
     * @param date Date
     * @return string
     */
    public static String getHour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return format.format(date);
    }

    /**
     * 获取分钟 int
     *
     * @param dt Date
     * @return int
     */
    public static int getMinuteFromDate(Date dt) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dt);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        return ((hour * 60) + min);
    }

    /**
     * @param str      String input in YYYY-MM-DD HH:MM[:SS] format.
     * @param isExpiry boolean if set and input string is invalid then next day date is returned
     * @return Date
     */
    public static Date convertToDate(String str, boolean isExpiry) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt;
        try {
            dt = fmt.parse(str);
        } catch (ParseException ex) {
            Calendar cal = Calendar.getInstance();
            if (isExpiry) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
            } else {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
            }
            dt = cal.getTime();
        }
        return dt;
    }

    public static Date convertToDate(String str) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date dt;
        try {
            dt = fmt.parse(str);
        } catch (ParseException ex) {
            dt = new Date();
        }
        return dt;
    }

    public static String dateFormat(Date date, int minute) {
        String dateFormat;
        int year = Integer.parseInt(getYear(date));
        int month = Integer.parseInt(getMonth(date));
        int day = Integer.parseInt(getDay(date));
        int hour = minute / 60;
        int min = minute % 60;
        dateFormat = year
                +
                (month > 9 ? String.valueOf(month) :
                        "0" + month)
                +
                (day > 9 ? String.valueOf(day) : "0" + day)
                + " "
                +
                (hour > 9 ? String.valueOf(hour) : "0" + hour)
                +
                (min > 9 ? String.valueOf(min) : "0" + min)
                + "00";
        return dateFormat;
    }

    public static String sDateFormat() {
        return new SimpleDateFormat(DATE_PATTERN).format(Calendar.getInstance().getTime());
    }

    /**
     * 获得本月的第一天日期
     *
     * @return 本月的第一天日期字符串
     */
    public static String getFirstDateOfThisMonth() {
        SimpleDateFormat format = new SimpleDateFormat(ISO_EXPANDED_DATE_FORMAT);
        Calendar calendarFirst = Calendar.getInstance();
        calendarFirst = Calendar.getInstance();
        calendarFirst.add(Calendar.MONTH, 0);
        calendarFirst.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(calendarFirst.getTime());
    }

    /**
     * 获得本月的最后一天日期
     *
     * @return 本月的最后一天日期字符串
     */
    public static String getLastDateOfThisMonth() {
        SimpleDateFormat format = new SimpleDateFormat(ISO_EXPANDED_DATE_FORMAT);
        Calendar calendarLast = Calendar.getInstance();
        calendarLast.setTime(new Date());
        calendarLast.getActualMaximum(Calendar.DAY_OF_MONTH);
        return format.format(calendarLast.getTime());
    }

    /**
     * 判断字符串日期是否匹配指定的格式化日期
     *
     * @param strDate   日期字符串
     * @param formatter format
     * @return 匹配true, 不匹配false
     */
    public static boolean isValidDate(String strDate, String formatter) {
        SimpleDateFormat sdf;
        ParsePosition pos = new ParsePosition(0);
        if (StringUtils.isBlank(strDate) || StringUtils.isBlank(formatter)) {
            return false;
        }
        try {
            sdf = new SimpleDateFormat(formatter);
            sdf.setLenient(false);
            Date date = sdf.parse(strDate, pos);
            if (date == null) {
                return false;
            } else {
                return pos.getIndex() <= sdf.format(date).length();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
