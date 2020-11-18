/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static boolean getDistanceOfTwoDate(Date before, Date after) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(before);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(after);
		return isSameDay(cal1, cal2);

//		long beforeTime = before.getTime();
//		long afterTime = after.getTime();
//		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if(cal1 != null && cal2 != null) {
			return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
		} else {
			throw new IllegalArgumentException("The date must not be null");
		}
	}

	/**
	 * 判断日期是否介乎于连续两天的某个时刻
	 * @param createDate
	 * @return
	 */
	public static boolean dateJudge(Date createDate){
		int MORNING = 11;
		int AFTERNOON = 17;
		//根据当前的时间，确定允许修改的录入时间范围
		Calendar calendar = Calendar.getInstance();
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String[] strNow1 = new SimpleDateFormat("yyyy/MM/dd").format(now).split("/");
		String[] strNow2 = new SimpleDateFormat("HH:mm:ss").format(now).toString().split(":");
		String year = strNow1[0];
		String month = strNow1[1];
		String day = strNow1[2];
//		System.out.println(year+"/"+month+"/"+day);
		String hour = strNow2[0];
		String minute = strNow2[1];
		String mills = strNow2[2];
//		System.out.println(hour+":"+minute+":"+mills);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date=null;
		try {
			date = sdf.parse(year+"/"+month+"/"+day+" "+hour+":"+minute+":"+mills);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setTime(date);
		int prevDay=calendar.get(Calendar.DATE);
		//此处修改为+1则是获取后一天
		calendar.set(Calendar.DATE,prevDay-1);
		calendar.set(Calendar.HOUR_OF_DAY,AFTERNOON);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		String lastDay = sdf.format(calendar.getTime());

		calendar.set(Calendar.DATE,prevDay);
		calendar.set(Calendar.HOUR_OF_DAY,MORNING);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		String today1 = sdf.format(calendar.getTime());

		calendar.set(Calendar.DATE,prevDay);
		calendar.set(Calendar.HOUR_OF_DAY,AFTERNOON);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		String today2 = sdf.format(calendar.getTime());

		calendar.set(Calendar.DATE,prevDay+1);
		calendar.set(Calendar.HOUR_OF_DAY,MORNING);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		String nextDay = sdf.format(calendar.getTime());
		//生成4个时间点
		System.out.println(lastDay);
		System.out.println(today1);
		System.out.println(today2);
		System.out.println(nextDay);

		try{
			Date prevDate = df.parse(lastDay);
			Date todayDate1 = df.parse(today1);
			Date todayDate2 = df.parse(today2);
			Date nextDate = df.parse(nextDay);

			if(compareTime(createDate,prevDate) && compareTime(todayDate1,createDate) && compareTime(now,prevDate) && compareTime(todayDate1,now)){
				//录入时间在昨天17点到今天11点之间
				return true;
			}else if(compareTime(createDate,todayDate1) && compareTime(todayDate2,createDate) && compareTime(now,todayDate1) && compareTime(todayDate2,now)){
				//录入时间在今天11点到今天17点之间
				return true;
			}else if(compareTime(createDate,todayDate2) && compareTime(nextDate,createDate)  && compareTime(now,todayDate2) && compareTime(nextDate,now)){
				//录入时间在今天17点到明天11点之间
				return true;
			}else{
				return false;
			}


		}catch (Exception e){
			e.printStackTrace();
			return false;
		}

	}
	/*计算两个日期相差的天数，按照自然日计算*/
	public static int differentDays(Date oldDate,Date nowDate)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(oldDate);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(nowDate);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2) //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0) //闰年
				{
					timeDistance += 366;
				}
				else //不是闰年
				{
					timeDistance += 365;
				}
			}

			return (timeDistance + (day2-day1)) ;
		}
		else //不同年
		{
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return ((day2-day1));
		}
	}


	public static boolean compareTime(Date date1,Date date2){
		return date1.getTime()>=date2.getTime();
	}
}
