

package com.phoenix.fitness.common.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理
 *
 * @author Mark sm516116978@outlook.com
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String formatLong(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     */
    public static Date stringToDate(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_PATTERN);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDatePattern(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    public static Date getEndOfOneDay(Date date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strEndDate = simpleDateFormat.format(date);
        strEndDate += " 23:59:59";
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat2.parse(strEndDate);
    }

    public static List<Date> getWeekFirstEndDay(Date date) {
        List<Date> listDate = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        listDate.add(cal.getTime());
        cal.add(Calendar.DATE, 6);
        listDate.add(cal.getTime());
        return listDate;
    }

    public static List<Date> getMonthFirstEndDay(Date date) {
        List<Date> listDate = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        listDate.add(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        listDate.add(cal.getTime());
        return listDate;
    }

    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        //tempStart.add(Calendar.DAY_OF_YEAR, 1);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    public static long daysBetween(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }

    public static long daysBetweenSpecial(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference) + 1;
    }

    public static Date showMaxDate(List<Date> dateList) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateArrayStr = new ArrayList<>();
        for (Date date : dateList) {
            dateArrayStr.add(simpleDateFormat.format(date));
        }
        Map<String, Integer> dateMap = new TreeMap<String, Integer>();
        int i, arrayLen;
        arrayLen = dateArrayStr.size();
        for (i = 0; i < arrayLen; i++) {
            String dateKey = dateArrayStr.get(i);
            if (dateMap.containsKey(dateKey)) {
                int value = dateMap.get(dateKey) + 1;
                dateMap.put(dateKey, value);
            } else {
                dateMap.put(dateKey, 1);
            }
        }
        Set<String> keySet = dateMap.keySet();
        String[] sorttedArray = new String[keySet.size()];
        Iterator<String> iter = keySet.iterator();
        int index = 0;
        while (iter.hasNext()) {
            String key = iter.next();
            //    System.out.println(key + ":" + dateMap.get(key));
            sorttedArray[index++] = key;
        }
        int sorttedArrayLen = sorttedArray.length;
        System.out.println("最小日期是：" + sorttedArray[0] + "," +
                " 天数为" + dateMap.get(sorttedArray[0]));
        System.out.println("最大日期是：" + sorttedArray[sorttedArrayLen - 1] + "," +
                " 天数为" + dateMap.get(sorttedArray[sorttedArrayLen - 1]));
        String maxDateStr = sorttedArray[sorttedArrayLen - 1];
        return simpleDateFormat.parse(maxDateStr);
    }

    public static Date showMinDate(List<Date> dateList) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateArrayStr = new ArrayList<>();
        for (Date date : dateList) {
            dateArrayStr.add(simpleDateFormat.format(date));
        }
        Map<String, Integer> dateMap = new TreeMap<String, Integer>();
        int i, arrayLen;
        arrayLen = dateArrayStr.size();
        for (i = 0; i < arrayLen; i++) {
            String dateKey = dateArrayStr.get(i);
            if (dateMap.containsKey(dateKey)) {
                int value = dateMap.get(dateKey) + 1;
                dateMap.put(dateKey, value);
            } else {
                dateMap.put(dateKey, 1);
            }
        }
        Set<String> keySet = dateMap.keySet();
        String[] sorttedArray = new String[keySet.size()];
        Iterator<String> iter = keySet.iterator();
        int index = 0;
        while (iter.hasNext()) {
            String key = iter.next();
            //    System.out.println(key + ":" + dateMap.get(key));
            sorttedArray[index++] = key;
        }
        int sorttedArrayLen = sorttedArray.length;
        System.out.println("最小日期是：" + sorttedArray[0] + "," +
                " 天数为" + dateMap.get(sorttedArray[0]));
        System.out.println("最大日期是：" + sorttedArray[sorttedArrayLen - 1] + "," +
                " 天数为" + dateMap.get(sorttedArray[sorttedArrayLen - 1]));
        String minDateStr = sorttedArray[0];
        return simpleDateFormat.parse(minDateStr);
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式

        List<Date> monthFirstEndDay = getMonthFirstEndDay(new Date());
        System.out.println(sdf.format(monthFirstEndDay.get(0)));
        System.out.println(monthFirstEndDay.get(0));


        String sEndDate = "2021-06-01";
        Date date1 = new Date();
        Date date2 = sdf.parse(sEndDate);
        long l = daysBetweenSpecial(date1, date2);
        System.out.println(l);

        String[] dateArray = {"2013-04-01", "2013-04-08", "2013-04-28", "2013-04-08", "2013-11-11"};
        // showMaxDate(dateArray);
    }


}
