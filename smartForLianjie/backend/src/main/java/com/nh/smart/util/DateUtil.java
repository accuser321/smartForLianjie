package com.nh.smart.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil {

	public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(calendar.getTime());
    }

    public static String getNow(){
    	 Date date = new Date();
         String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
         return dateStr;
    }

	public static String getNextMonth(String dateStr,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

    /**
      * <h2>把字符串转换成日期</h2>
      * @Title: parseStringToDate
      * @param dateString
      * @param formatString
      * @return Date
     */
    public static Date parseStringToDate(String dateString,String formatString){
    	SimpleDateFormat sdf;
    	if(StringUtils.isBlank(formatString)){
    		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	}else{
    		sdf = new SimpleDateFormat(formatString);
    	}

    	Date date = null;
    	if(StringUtils.isNotBlank(dateString)){
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	return date;
    }

	/**
	 * 获取当前月的第一天
	 * @return
	 */
   public static String getFirstDay(){
	   Calendar cale = null;
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   String firstday="";
	   // 获取前月的第一天
	   cale = Calendar.getInstance();
	   cale.add(Calendar.MONTH, 0);
	   cale.set(Calendar.DAY_OF_MONTH, 1);
	   firstday = format.format(cale.getTime());
	   return  firstday;
   }


    /**
     * <h2>把日期转换成字符串</h2>
     * @Title: parseDateToString
     * @param date
     * @param formatString
     * @return String
    */
   public static String parseDateToString(Date date,String formatString){
   		SimpleDateFormat sdf;
   		if(StringUtils.isBlank(formatString)){
   			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		}else{
   			sdf = new SimpleDateFormat(formatString);
   		}

   		String dateString = null;
   		if(date!=null){
   			dateString = sdf.format(date);
   		}

   		return dateString;
   }

  @SuppressWarnings("static-access")
  public static String getBeforeDateByMonth(int n){
	  	Date dNow = new Date();   //当前时间
	  	Date dBefore = new Date();
	  	Calendar calendar = Calendar.getInstance(); //得到日历
	  	calendar.setTime(dNow);//把当前时间赋给日历
	  	calendar.add(calendar.MONTH, -n);  //设置为前n月
	  	dBefore = calendar.getTime();   //得到前n月的时间
	  	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
	  	String defaultStartDate = sdf.format(dBefore);    //格式化前n月的时间
	  	return defaultStartDate;
  }

  @SuppressWarnings("static-access")
  public static String getAfterDateByMonth(int n){
	  	Date dNow = new Date();   //当前时间
	  	Date dBefore = new Date();
	  	Calendar calendar = Calendar.getInstance(); //得到日历
	  	calendar.setTime(dNow);//把当前时间赋给日历
	  	calendar.add(calendar.MONTH, +n);  //设置为后n月
	  	dBefore = calendar.getTime();   //得到后n月的时间
	  	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
	  	String defaultStartDate = sdf.format(dBefore);    //格式化前n月的时间
	  	return defaultStartDate;
  }

	public static String getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
		date = calendar.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		return sdf.format(date);
	}

	public static void main(String[] args) {
		System.out.println(getNextDay(new Date()));
		System.out.println(getNextMonth("2018-12","yyyy-MM"));
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException calendar 对日期进行时间操作
	 *                        getTimeInMillis() 获取日期的毫秒显示形式
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

	/**
	 * 返回一定时间后的日期
	 *
	 * @param date
	 *            开始计时的时间
	 * @param year
	 *            增加的年
	 * @param month
	 *            增加的月
	 * @param day
	 *            增加的日
	 * @param hour
	 *            增加的小时
	 * @param minute
	 *            增加的分钟
	 * @param second
	 *            增加的秒
	 * @return
	 */
	public static Date getAfterDate(Date date, int year, int month, int day,
									int hour, int minute, int second) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = new GregorianCalendar();

		cal.setTime(date);
		if (year != 0) {
			cal.add(Calendar.YEAR, year);
		}
		if (month != 0) {
			cal.add(Calendar.MONTH, month);
		}
		if (day != 0) {
			cal.add(Calendar.DATE, day);
		}
		if (hour != 0) {
			cal.add(Calendar.HOUR_OF_DAY, hour);
		}
		if (minute != 0) {
			cal.add(Calendar.MINUTE, minute);
		}
		if (second != 0) {
			cal.add(Calendar.SECOND, second);
		}
		return cal.getTime();
	}
}


