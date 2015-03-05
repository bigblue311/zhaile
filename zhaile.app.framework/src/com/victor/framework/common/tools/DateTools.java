package com.victor.framework.common.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class DateTools {
	private static DateFormat simpleFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date today(){
		return new Date();
	}
	
	public static int weekDay(){
		Date today = today();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		if(weekDay == 1) {
			weekDay = weekDay + 7;
		}
		return weekDay-1;
	}
	
	public static int weekDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		if(weekDay == 1) {
			weekDay = weekDay + 7;
		}
		return weekDay-1;
	}
	
	public static String getWeekDayName(int week){
		if(week == 1){
			return "周一";
		}
		if(week == 2){
			return "周二";
		}
		if(week == 3){
			return "周三";
		}
		if(week == 4){
			return "周四";
		}
		if(week == 5){
			return "周五";
		}
		if(week == 6){
			return "周六";
		}
		if(week == 7){
			return "周日";
		}
		return "不知道";
	}
	
	public static Date forever(){
		try {
			return StringToDate("9999-12-31 23:59:59");
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 计算date+hourLength小时后是否还未到
	 * @param hourLength
	 * @param date
	 * @return
	 */
	public static Boolean isValid(int hourLength,Date date){
		Date now = today();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+hourLength);
		return now.before(cal.getTime());
	}
	
	public static Date getDate(int dayLength){
		Date now = today();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)+dayLength);
		return cal.getTime();
	}
	
	public static Date getDate(Date date, int dayLength){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)+dayLength);
		return cal.getTime();
	}
	
	public static Date getDateTime(long duration){
		Date now = today();
		long time = now.getTime()+duration;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.getTime();
	}
	
	public static Date getDateTime(Date date, long duration){
		long time = date.getTime()+duration;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return cal.getTime();
	}
	
	public static String DateToString(Date date){
		if(date == null){
			return "";
		}
		return simpleFull.format(date);
	}
	
	public static Date StringToDate(String date) throws ParseException{
		if(StringTools.isEmpty(date)) {
			return null;
		}
		if(date.contains(":")){
			return simpleFull.parse(date);
		} else {
			return simpleDate.parse(date);
		}
	}
	
	public static Date getDayBegin(Date date){
		if(date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	public static Date getDayEnd(Date date){
		if(date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public static boolean inRange(Date fromDate, Date endDate){
		if(fromDate == null || endDate == null) {
			return false;
		}
		Date today = new Date();
		if(fromDate.before(endDate)){
			return fromDate.before(today) && endDate.after(today);
		}
		return false;
	}
	
	public static boolean inTime(String open, String close) throws ParseException{
		Date openDate = timeToDate(open);
		Date closeDate = timeToDate(close);
		return inRange(openDate,closeDate);
	}
	
	public static long getRandomId(){
		Date today = today();
		return today.getTime();
	}
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        smdate=getDayBegin(smdate);  
        bdate=getDayEnd(bdate);  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24)+1;  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }
	
	private static Date timeToDate(String time) throws ParseException{
		Date today = today();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		String Date = cal.get(Calendar.YEAR) + "-" + trunk(cal.get(Calendar.MONTH)+1) + "-" + trunk(cal.get(Calendar.DATE)) + " "+ time;
		return StringToDate(Date);
	}
	
	private static String trunk(int val){
		if(val<10) {
			return "0"+val;
		}else{
			return ""+val;
		}
	}
	
	public static String getTodayPath(){
		Date today = today();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		return cal.get(Calendar.YEAR)+trunk(cal.get(Calendar.MONTH)+1) + trunk(cal.get(Calendar.DATE));
	}
	
	/**
	 * 周一到周日
	 * @return
	 */
	public static List<Date> getThisWeek(){
		Date today = today();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		if(weekDay == 1){
			weekDay = weekDay + 7;
		}
		Date monday = getDayBegin(getDate(2-weekDay));
		List<Date> result = Lists.newArrayList();
		for(int i=0;i<7;i++){
			Date nextDate = getDate(monday,i);
			result.add(nextDate);
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(getDateTime(30*24*60*60*1000l));
	}
}
