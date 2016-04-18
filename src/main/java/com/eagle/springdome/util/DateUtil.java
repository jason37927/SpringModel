/**
 * 
 */
package com.eagle.springdome.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期工具类
 * 
 * @author Wang Yong
 *
 */
public class DateUtil {
	/**
     * 时间格式map。
     */
    private static Map<String, DateFormat> mapDateFormat;
    
    private synchronized static void initDateFormat()
    {
        if(mapDateFormat == null)
        {
            mapDateFormat = new ConcurrentHashMap<String, DateFormat>();
        }
    }
    
	/**
	 * 将long类型时间戳转换Date类型时间。
	 * 
	 * @param dateTime long类型时间
	 * @return Date类型时间
	 */
	public final static Date long2Date(long dateTime)
	{
		Date date = new Date(dateTime*1000);
		return date;
	}
	
	/**
	 * 将Date类型时间转换成为long类型时间戳（以秒为单位）。
	 * 
	 * @param date Date类型时间
	 * @return long类型时间戳
	 */
	public final static long date2Long(Date date)
	{
	    return date.getTime() / 1000;
    }

    /**
     * 获取指定的时间格式化器。
     * @param formatKey 时间格式key
     * @return 格式化器
     */
    public static DateFormat getDateFormat(String formatKey)
    {
        initDateFormat();
        DateFormat format = mapDateFormat.get(formatKey);
        if(format == null)
        {
            format = new SimpleDateFormat(formatKey);
            mapDateFormat.put(formatKey, format);
        }
        return format;
    }
    
    /**
     * 获取格式化的当前日期时间。
     * 
     * @return 格式化后的当前日期时间，日期时间格式为：YYYY-MM-DD HH:MM:SS
     */
    public final static String getCurrentDatetime()
    {
        return formatDateTime(Calendar.getInstance().getTime());
    }
	
	/**
     * 获取格式化的当前日期。
     * 
     * @param formatKey 格式化key
     * @return 格式化后的当前日期
     */
    public static String getCurrentDate(String formatKey)
    {
        return formatDate(Calendar.getInstance().getTime(), formatKey);
    }

    /**
     * 获取日期中指定字段的数值，如获取月份。
     * @param date 日期
     * @param field 指定字段
     * @return 数值
     */
    private static int getCalendarField(Date date, int field)
    {
        if(date != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(field);
        }
        return 0;
    }

    /**
     * 获取日期字符串的日期风格，失败返回null。
     * 
     * @param date 日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) 
    {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
        List<Long> timestamps = new ArrayList<Long>();
        for(DateStyle style : DateStyle.values())
        {
            Date dateTmp = parseDate(date, style.getValue());
            if(dateTmp != null)
            {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        dateStyle = map.get(getAccurateDate(timestamps).getTime());
        return dateStyle;
    }

    /**
     * 获取下一个时间。
     * @param date 原始时间对象
     * @param field 增量字段
     * @param amount 增量
     * @return 应用增量后的时间
     */
    private static String getNextDate(String date, int field, int amount)
    {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if(dateStyle != null)
        {
            Date myDate = parseDate(date, dateStyle.getValue());
            myDate = getNextDate(myDate, field, amount);
            dateString = formatDate(myDate, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 获取下一个时间。
     * @param date 原始时间对象
     * @param field 增量字段
     * @param amount 增量
     * @return 应用增量后的时间
     */
    private static Date getNextDate(Date date, int field, int amount)
    {
        if(date != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(field, amount);
            
            return calendar.getTime();
        }
        return null;
    }
    
    /**
     * 获取以当前时间为基准的下一个时间。
     * @param field 增量字段
     * @param amount 增量
     * @return 应用增量后的时间
     */
    public static Date getNextDate(int field, int amount)
    {
        return getNextDate(new Date(), field, amount);
    }

    /**
     * 获取精确的日期。
     * @param timestamps 时间戳集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps)
    {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if(timestamps != null && timestamps.size() > 0)
        {
            if(timestamps.size() > 1)
            {
                for(int i = 0; i < timestamps.size(); i++)
                {
                    for(int j = i + 1; j < timestamps.size(); j++)
                    {
                        long absoluteValue = Math.abs(timestamps.get(i)
                                - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = { timestamps.get(i),
                                timestamps.get(j) };
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
                long minAbsoluteValue = -1;
                if(!absoluteValues.isEmpty())
                {
                    // 如果timestamps的size为2，这是差值只有一个，因此要给默认值
                    minAbsoluteValue = absoluteValues.get(0);
                }
                
                for(int i = 0; i < absoluteValues.size(); i++)
                {
                    for(int j = i + 1; j < absoluteValues.size(); j++)
                    {
                        if(absoluteValues.get(i) > absoluteValues.get(j))
                        {
                            minAbsoluteValue = absoluteValues.get(j);
                        }
                        else
                        {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if(minAbsoluteValue != -1)
                {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);
                    if(absoluteValues.size() > 1)
                    {
                        timestamp = Math.max(timestampsLastTmp[0],
                                timestampsLastTmp[1]);
                    }
                    else if(absoluteValues.size() == 1)
                    {
                        // 当timestamps的size为2，需要与当前时间作为参照
                        long dateOne = timestampsLastTmp[0];
                        long dateTwo = timestampsLastTmp[1];
                        if((Math.abs(dateOne - dateTwo)) < 100000000000L)
                        {
                            timestamp = Math.max(timestampsLastTmp[0],
                                    timestampsLastTmp[1]);
                        }
                        else
                        {
                            long now = new Date().getTime();
                            if(Math.abs(dateOne - now) <= Math.abs(dateTwo - now))
                            {
                                timestamp = dateOne;
                            }
                            else
                            {
                                timestamp = dateTwo;
                            }
                        }
                    }
                }
            }
            else
            {
                timestamp = timestamps.get(0);
            }
        }

        if(timestamp != 0)
        {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断指定的文本是否为日期格式。
     * @param date 文本内容
     * @return true:是日期格式 false:非日期格式
     */
    public static boolean isDate(String date)
    {
        boolean isDate = false;
        if(date != null)
        {
            if(parseDate(date) != null)
            {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 解析指定的文本为日期对象。解析异常则返回null。
     * @param date 文本内容
     * @return 日期对象
     */
    public static Date parseDate(String date)
    {
        try
        {
            return getDateFormat(DateStyle.YYYY_MM_DD.getValue()).parse(date);
        }
        catch(ParseException exp)
        {
            return null;
        }
    }

    /**
     * 解析指定的文本为日期对象。解析异常则返回null。
     * @param date 文本内容
     * @param formatKey 时间格式key
     * @return 日期对象
     */
    public static Date parseDate(String date, String formatKey)
    {
        try
        {
            return getDateFormat(formatKey).parse(date);
        }
        catch(Exception exp)
        {
            return null;
        }
    }

    /**
     * 格式化指定的日期时间。
     * @param date 待格式化的日期时间
     * @return 格式化后的日期时间，日期时间格式为：YYYY-MM-DD HH:MM:SS
     */
    public static String formatDateTime(Date date)
    {
        if(date != null)
        {
            return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(date);
        }
        return "";
    }
    
    /**
     * 格式化指定的日期时间。
     * @param date 待格式化的日期时间
     * @return 格式化后的日期时间，日期时间格式为：YYYY-MM-DD HH:MM:SS:SSS
     */
    public static String formatMilliDateTime(Date date)
    {
        if(date != null)
        {
            return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS_SSS.getValue()).format(date);
        }
        return "";
    }

    /**
     * 格式化指定的日期。
     * @param date 待格式化的日期
     * @return 格式化后的日期，日期格式为：YYYY-MM-DD
     */
    public static String formatDate(Date date)
    {
        if(date != null)
        {
            return getDateFormat(DateStyle.YYYY_MM_DD.getValue()).format(date);
        }
        return "";
    }

    /**
     * 格式化指定的时间。
     * @param date 待格式化的时间
     * @return 格式化后的时间，时间格式为：HH:MM:SS
     */
    public static String formatTime(Date date)
    {
        if(date != null)
        {
            return getDateFormat(DateStyle.HH_MM_SS.getValue()).format(date);
        }
        return "";
    }
    
    /**
     * 格式化指定的时间。
     * @param date 待格式化的时间
     * @param formatKey 时间格式key
     * @return 格式化后的时间
     */
    public static String formatDate(Date date, String formatKey)
    {
        if(date != null)
        {
            return getDateFormat(formatKey).format(date);
        }
        return "";
    }

    /**
     * 格式化指定的时间。
     * @param date 待格式化的时间
     * @param formatKey 时间格式key
     * @return 格式化后的时间
     */
    public static String formatDateWithNull(Date date, String formatKey)
    {
        if(date != null)
        {
            return getDateFormat(formatKey).format(date);
        }
        return "空";
    }

    /**
     * 将指定的日期文本转换成另一格式的日期文本，失败返回null。
     * @param date 文本内容
     * @param formatKey 日期格式key
     * @return 新格式的日期文本
     */
    public static String changeFormatDate(String date, String formatKey)
    {
        return changeFormatDate(date, null, formatKey);
    }

    /**
     * 将指定的日期文本转换成另一格式的日期文本，失败返回null。
     * @param date 文本内容
     * @param oldFormatKey 旧日期格式key
     * @param newFormatKey 新日期格式key
     * @return 新格式的日期文本
     */
    public static String changeFormatDate(String date, String oldFormatKey,
            String newFormatKey)
    {
        String dateString = null;
        if(oldFormatKey == null)
        {
            DateStyle style = getDateStyle(date);
            if(style != null)
            {
                Date myDate = parseDate(date, style.getValue());
                dateString = formatDate(myDate, newFormatKey);
            }
        }
        else
        {
            Date myDate = parseDate(date, oldFormatKey);
            dateString = formatDate(myDate, newFormatKey);
        }
        return dateString;
    }

    /**
     * 增加日期的年份，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加年份后的日期
     */
    public static String addYear(String date, int amount)
    {
        return getNextDate(date, Calendar.YEAR, amount);
    }

    /**
     * 增加日期的年份，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int amount)
    {
        return getNextDate(date, Calendar.YEAR, amount);
    }

    /**
     * 增加日期的月份，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加月份后的日期
     */
    public static String addMonth(String date, int amount)
    {
        return getNextDate(date, Calendar.MONTH, amount);
    }

    /**
     * 增加日期的月份，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int amount)
    {
        return getNextDate(date, Calendar.MONTH, amount);
    }

    /**
     * 增加日期的天数，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加天数后的日期
     */
    public static String addDay(String date, int amount)
    {
        return getNextDate(date, Calendar.DATE, amount);
    }

    /**
     * 增加日期的天数，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int amount)
    {
        return getNextDate(date, Calendar.DATE, amount);
    }

    /**
     * 增加日期的小时，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加小时后的日期
     */
    public static String addHour(String date, int amount)
    {
        return getNextDate(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 增加日期的小时，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int amount)
    {
        return getNextDate(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 增加日期的分钟，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加分钟后的日期
     */
    public static String addMinute(String date, int amount)
    {
        return getNextDate(date, Calendar.MINUTE, amount);
    }

    /**
     * 增加日期的分钟，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int amount)
    {
        return getNextDate(date, Calendar.MINUTE, amount);
    }

    /**
     * 增加日期的秒数，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加秒数后的日期
     */
    public static String addSecond(String date, int amount)
    {
        return getNextDate(date, Calendar.SECOND, amount);
    }

    /**
     * 增加日期的秒数，失败返回null。
     * @param date 基准日期
     * @param amount 增量
     * @return 增加秒数后的日期
     */
    public static Date addSecond(Date date, int amount)
    {
        return getNextDate(date, Calendar.SECOND, amount);
    }

    /**
     * 获取日期的年份，失败返回0。
     * @param date 日期对象
     * @return 年份
     */
    public static int getYear(String date)
    {
        return getYear(parseDate(date));
    }

    /**
     * 获取日期的年份，失败返回0。
     * @param date 日期对象
     * @return 年份
     */
    public static int getYear(Date date)
    {
        return getCalendarField(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份，失败返回0。
     * @param date 日期对象
     * @return 月份
     */
    public static int getMonth(String date)
    {
        return getMonth(parseDate(date));
    }

    /**
     * 获取日期的月份，失败返回0。
     * @param date 日期对象
     * @return 月份
     */
    public static int getMonth(Date date)
    {
        return getCalendarField(date, Calendar.MONTH);
    }

    /**
     * 获取日期的天数，失败返回0。
     * @param date 日期对象
     * @return 天
     */
    public static int getDay(String date)
    {
        return getDay(parseDate(date));
    }

    /**
     * 获取日期的天数，失败返回0。
     * @param date 日期对象
     * @return 天
     */
    public static int getDay(Date date)
    {
        return getCalendarField(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时，失败返回0。
     * @param date 日期对象
     * @return 小时
     */
    public static int getHour(String date)
    {
        return getHour(parseDate(date));
    }

    /**
     * 获取日期的小时，失败返回0。
     * @param date 日期对象
     * @return 小时，24小时模式
     */
    public static int getHour(Date date)
    {
        return getCalendarField(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟，失败返回0。
     * @param date 日期对象
     * @return 分钟
     */
    public static int getMinute(String date)
    {
        return getMinute(parseDate(date));
    }

    /**
     * 获取日期的分钟，失败返回0。
     * @param date 日期对象
     * @return 分钟
     */
    public static int getMinute(Date date)
    {
        return getCalendarField(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的second字段，失败返回0。
     * @param date 日期对象
     * @return second字段
     */
    public static int getSecond(String date)
    {
        return getSecond(parseDate(date));
    }

    /**
     * 获取日期的second字段，失败返回0。
     * @param date 日期对象
     * @return second字段
     */
    public static int getSecond(Date date)
    {
        return getCalendarField(date, Calendar.SECOND);
    }

    /**
     * 获取日期的星期。失败返回null。
     * @param date 日期对象
     * @return 星期
     */
    public static Week getWeek(String date)
    {
        Week week = null;
        DateStyle dateStyle = getDateStyle(date);
        if(dateStyle != null)
        {
            Date myDate = parseDate(date, dateStyle.getValue());
            week = getWeek(myDate);
        }
        return week;
    }

    /**
     * 获取日期的星期。失败返回null。
     * @param date 日期对象
     * @return 星期
     */
    public static Week getWeek(Date date)
    {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
        case 0:
            week = Week.SUNDAY;
            break;
        case 1:
            week = Week.MONDAY;
            break;
        case 2:
            week = Week.TUESDAY;
            break;
        case 3:
            week = Week.WEDNESDAY;
            break;
        case 4:
            week = Week.THURSDAY;
            break;
        case 5:
            week = Week.FRIDAY;
            break;
        case 6:
            week = Week.SATURDAY;
            break;
        }
        return week;
    }

    /**
     * 获取两个日期相差的天数。
     * @param date1 日期对象1
     * @param date2 日期对象2
     * @return 相差天数
     */
    public static int getIntervalDays(String date1, String date2)
    {
        return getIntervalDays(parseDate(date1), parseDate(date2));
    }

    /**
     * 获取两个日期相差的天数。
     * @param date1 日期对象1
     * @param date2 日期对象2
     * @return 相差天数
     */
    public static int getIntervalDays(Date date1, Date date2)
    {
        long time = Math.abs(date1.getTime() - date2.getTime());
        return (int) time / (24 * 60 * 60 * 1000);
    }
    
    /**
     * 获得预定义开始时间。
     * @return 开始时间，默认使用格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getBeginTime(Date date)
    {
        return String.format("%s 00:00:00", formatDate(date != null ? date : new Date()));
    }

    /**
     * 获得预定义结束时间。
     * @return 结束时间，默认使用格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getEndTime(Date date)
    {
        return String.format("%s 23:59:59", formatDate(date != null ? date : new Date()));
    }

    /**
     * 得到某日期的最后一刻TimeStamp，比如：2011-08-17 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndTimeOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        Calendar ret = Calendar.getInstance();
        cal.setTime(date);
        ret.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                .get(Calendar.DATE), 23, 59, 59);
        return ret.getTime();
    }

    /**
     * 得到某日期的开始一刻TimeStamp，比如：2011-08-17 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getBeginTimeOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        Calendar ret = Calendar.getInstance();
        cal.setTime(date);
        ret.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                .get(Calendar.DATE), 0, 0, 0);
        return ret.getTime();
    }
}
