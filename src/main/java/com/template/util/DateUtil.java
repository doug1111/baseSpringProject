package com.template.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期处理工具类
 *
 * @author Doug Liu
 * @since 2022-06-10
 *
 */
public class DateUtil {

    private static final long ONE_MINUTE = 60;

    private static final long ONE_HOUR = 3600;

    private static final long ONE_DAY = 86400;

    private static final long ONE_MONTH = 2592000;

    private static final long ONE_YEAR = 31104000;

    /**
     * 获取前几天的时间
     * @return String
     */
    public static Date getDate(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }


    /**
     * 获取某个时间某天的时间
     * @return String
     */
    public static Date getDate(int num, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }

    /**
     * 年月日字符串格式化成日期
     * @param date 日期字符串
     * @return Date
     */
    public static Date stringToDateShort(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 年月日时分秒字符串格式化成日期
     * @param date 日期字符串
     * @return Date
     */
    public static Date stringToDateLong(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 年月日时分秒日期格式化成字符串
     * @param date 日期
     * @return String
     */
    public static String dateToStringLong(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 年月日日期格式化成字符串
     * @param date 日期
     * @return String
     */
    public static String dateToStringShort(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 月日日期格式化字符串
     * @param date 日期
     * @return String
     */
    public static String dateToStringSmall(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(date);
    }

    /**
     * 距离当前时间之前多久
     * @param date 日期
     * @return String
     */
    public static String fromToShortDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long time = date.getTime() / 1000;
        long now = System.currentTimeMillis() / 1000;
        long ago = now - time;
        if (ago <= ONE_MINUTE) {
            return ago + "秒前";
        }
        else if (ago <= ONE_HOUR) {
            return ago / ONE_MINUTE + "分钟前";
        }
        else if (ago <= ONE_DAY) {
            return ago / ONE_HOUR + "小时前";
        }
        else {
            return dateToStringShort(date);
        }
    }

    /**
     * 距离当前时间之后多久
     * @param date 日期
     * @return String
     */
    public static String afterToDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long time = date.getTime() / 1000;
        long now = System.currentTimeMillis() / 1000;
        long ago = time - now;
        if (ago <= ONE_MINUTE) {
            return ago + "秒后";
        }
        else if (ago <= ONE_HOUR) {
            return ago / ONE_MINUTE + "分钟后";
        }
        else if (ago <= ONE_DAY) {
            return ago / ONE_HOUR + "小时后";
        }
        else {
            return ago / ONE_DAY + "天后";
        }
    }


    /**
     * 距离日期多久结束
     * @param date 日期
     * @return String
     */
    public static String fromToSmallDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long time = date.getTime() / 1000;
        long now = System.currentTimeMillis() / 1000;
        long ago = now - time;
        if (ago <= ONE_MINUTE) {
            return ago + "秒前";
        }
        else if (ago <= ONE_HOUR) {
            return ago / ONE_MINUTE + "分钟前";
        }
        else if (ago <= ONE_DAY) {
            return ago / ONE_HOUR + "小时前";
        }
        else {
            return dateToStringSmall(date);
        }
    }


    /**
     * 通过时间区间计算开始日期和结束日期
     * @param dateInterval 时间区间，过去的天数为负，将来的时间为正
     * @return Map<String, String>
     */
    public static Map<String, String> getDefaultDate(Integer dateInterval) {
        Date beginDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(beginDate);
        c.add(Calendar.DAY_OF_MONTH, dateInterval);
        Calendar n = Calendar.getInstance();
        n.setTime(beginDate);
        Map<String, String> dateMap = new HashMap<>(8);
        dateMap.put("beginDate", dateToStringShort(c.getTime()));
        dateMap.put("endDate", dateToStringShort(n.getTime()));
        return dateMap;
    }

    /**
     * 获取两个时间段每天的日期列表
     * @param begin 开始日期
     * @param end 结束日期
     * @return List<String>
     */
    public static List<String> getBetweenDate(String begin, String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> betweenList = new ArrayList<String>();

        try {
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(format.parse(begin));
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(format.parse(end));
            while (calBegin.getTime().before(calEnd.getTime()) || calBegin.getTime().compareTo(calEnd.getTime()) == 0) {
                betweenList.add(format.format(calBegin.getTime()));
                calBegin.add(Calendar.DATE, 1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return betweenList;
    }

    /**
     * 获取某个日期的周一
     * @param date 日期
     * @return Date
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }


    /**
     * 获取一定天数之前或之后的日期
     * @param date 当前日期
     * @param days 正数之后的天数，负数为之前的天数
     * @return Date
     */
    public static Date getIntervalDate(Date date, Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Integer getAge(Date date) {
        Calendar cal = Calendar.getInstance();
        //出生日期晚于当前时间，无法计算
        if (cal.before(date)) {
            return null;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(date);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //计算整岁数
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //当前日期在生日之前，年龄减一
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            }
            else {
                age--;
            }
        }
        return age;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() > getDate(1, stringToDateLong("2021-6-24 10:09:02")).getTime());
    }
}