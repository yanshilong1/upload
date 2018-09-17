package cn.cloud.core.common;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jvc.util.DateUtils;

public class DateTimeUtil {
	
	String dateFormat = "yyyy-MM-dd";
    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
	
	public static int getDayNumPerMonth(String yearMonth) {
		int days = 31;
		if (yearMonth == null || yearMonth.equals("")) {
			return days;
		}
		
		String year;
		String month;
		if (yearMonth.contains("-")) {
			year = yearMonth.substring(0, 4);
			month = yearMonth.substring(5, 7);
		} else {
			year = yearMonth.substring(0, 4);
			month = yearMonth.substring(5, 6);
		}
		days = getDayCount(Integer.parseInt(year),
				Integer.parseInt(month));
		return days;
	} 

	public static int getDayCount(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		return cal.getActualMaximum(Calendar.DATE);
	}

	public static String getOneHoursAgoTime(String dateTime)
			throws ParseException {
		Date date = changeStingToDate(dateTime, "yyyy-MM-dd HH:mm:ss");
		long dateHM = date.getTime();
		long beforeOnHourHM = dateHM - 3600000;
		Date date1 = new Date(beforeOnHourHM);
		return DateUtils.formatDate(date1, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getOneHoursLaterTime(String dateTime)
			throws ParseException {
		Date date = changeStingToDate(dateTime, "yyyy-MM-dd HH:mm:ss");
		long dateHM = date.getTime();
		long beforeOnHourHM = dateHM + 3600000;
		Date date1 = new Date(beforeOnHourHM);
		return DateUtils.formatDate(date1, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date changeStingToDate(String date, String parse)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(parse);
		return sdf.parse(date);
	}

	public static String getYearMonthFormat(String yearMonth) {
		if (yearMonth.contains("-")) {
			return yearMonth;
		} else {
			String year = yearMonth.substring(0, 4);
			String month = yearMonth.substring(4);
			return year + "-" + month;
		}
	}

	/**
	 * @date1与date2的先后比较 1:代表date1大于date2; 0:代表date1等于date2; -1:代表date1小于date2;
	 * */
	public static int beforeOrAfter(String date1, String date2, String parse)
			throws ParseException {
		Date now = changeStingToDate(date1, parse);
		Date ymd = changeStingToDate(date2, parse);
		if (now.getTime() - ymd.getTime() > 0) {
			return 1;
		}
		if (now.getTime() - ymd.getTime() == 0) {
			return 0;
		}
		if (now.getTime() - ymd.getTime() < 0) {
			return -1;
		}
		return 0;
	}

	/**
	 * 判断是否润年
	 */
	public static boolean isLeapYear(String ddate) {
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24
					* 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 获取一个月的最后一天
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM
		String str = dat.substring(0, 7);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8
				|| mon == 10 || mon == 12) {
			str += "-31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "-30";
		} else {
			if (isLeapYear(dat)) {
				str += "-29";
			} else {
				str += "-28";
			}
		}
		return str;
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getWeekStr(String sdate) {
		String str = "";
		str = getWeek(sdate);
		if ("1".equals(str)) {
			str = "星期日";
		} else if ("2".equals(str)) {
			str = "星期一";
		} else if ("3".equals(str)) {
			str = "星期二";
		} else if ("4".equals(str)) {
			str = "星期三";
		} else if ("5".equals(str)) {
			str = "星期四";
		} else if ("6".equals(str)) {
			str = "星期五";
		} else if ("7".equals(str)) {
			str = "星期六";
		}
		return str;
	}

	public static String getNowMonth(String sdate) {
		// 取该时间所在月的一号
		// sdate = sdate.substring(0, 8) + "01";
		sdate = sdate + "-01";
		// 得到这个月的1号是星期几
		Calendar c = Calendar.getInstance();
		Date date = strToDate(sdate);
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	public static int getSundayNum(String sdate) {
		sdate = sdate + "-01";
		Calendar c = Calendar.getInstance();
		Date date = strToDate(sdate);
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String edate = getEndDateOfMonth(sdate);
		String newday;
		newday = getNextDay(sdate, (1 - u) + "");
		String week;
		int i = 0, j = 1;
		do {
			newday = getNextDay(sdate, "" + i++);
			week = getWeek(newday);
			if (week.equals("星期日")){
				j++;
			}
		} while (!newday.equals(edate));
		return j - 1;
	}
	
	// 得到这个月的1号是星期几
	public static String getFirstDayWeek(String sdate){
		sdate = sdate + "-01";
		return getWeek(sdate);
	}
	
	// 得到这个月的最后一天是星期几
	public static String getLastDayWeek(String sdate) {
		return getWeek(getEndDateOfMonth(sdate));
	}
	
	public static int getWeekNum(String sdate){
		if(getLastDayWeek(sdate).equals("1")) {
			return getSundayNum(sdate);
		} else {
			return getSundayNum(sdate) + 1;
		}
	}
	
	public static int getFirstSunday(String sdate) {
		for (int i = 1; i <= 8; i++) {
			String str = getWeekStr(sdate + "-0" + i);
			if (str.equals("星期日")) {
				return i;
			} else {
				continue;
			}
		}
		return 0;
	}
	
	
	public static Integer[] getWeekThDays(String sdate, String weekTh) {
		Integer[] inArray = new Integer[2];
		inArray[0] = 1;
		inArray[1] = 31;
		int date = getFirstSunday(sdate);
		if(weekTh.equals("1")) {
			inArray[0] = 1;
			inArray[1] = date;
			return inArray;
		}
		if(weekTh.equals("2")) {
			inArray[0] = date + 1;
			inArray[1] = date + 7;
			return inArray;
		}
		if(weekTh.equals("3")) {
			inArray[0] = date + 8;
			inArray[1] = date + 14;
			return inArray;
		}
		if(weekTh.equals("4")) {
			inArray[0] = date + 15;
			inArray[1] = date + 21;
			return inArray;
		}
		if(weekTh.equals("5")) {
			inArray[0] = date + 22;
			inArray[1] = date + 28;
			return inArray;
		}
		if(weekTh.equals("6")) {
			inArray[0] = date + 29;
			inArray[1] = date + 35;
			return inArray;
		}
		return inArray;
	}
	
	public static String getYearOfYearMonth(String yearMonth){
		if (yearMonth == null || yearMonth.equals("")) {
			String now = DateUtils.getDate("yyyy-MM-dd");
			return now.substring(0, 4);
		}
		return yearMonth.substring(0, 4);
	}
	
	public static String getMonthOfYearMonth(String yearMonth){
		if (yearMonth == null || yearMonth.equals("")) {
			String now = DateUtils.getDate("yyyy-MM-dd");
			return now.substring(5, 7);
		}
		return yearMonth.substring(4, 6);
	}
	
	public static String timeLong(String startTime, String endTime, String parse) {
		try {
			Date start = changeStingToDate(startTime, parse);
			Date end = changeStingToDate(endTime, parse);
			long startTimeL = start.getTime();
			long endTimeL = end.getTime();
			if (startTimeL >= endTimeL) {
				return "";
			}
			long betweenL = (endTimeL - startTimeL) / 1000;
			int hour = (int)betweenL / 3600;
			int level1 = (int)betweenL % 3600;
			int min = level1 / 60;
			int sec = level1 % 60;
			String hourS = hour < 10 ? ("0" + hour) : (hour + "");
			String minS = min < 10 ? ("0" + min) : (min + "");
			String secS = sec < 10 ? ("0" + sec) : (sec + "");
			return hourS + ":" + minS + ":" + secS;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getNowDateTime(){
		return DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
	}
	
	public static long getDiffTimes(String bigTime, String smallTime, String parse) throws ParseException {
		Date bigTimes = changeStingToDate(bigTime, parse);
		Date smallTimes = changeStingToDate(smallTime, parse);
		long diffValue = bigTimes.getTime() - smallTimes.getTime();
		return diffValue;
	}
	
	public static String removeEndZero(String date){
		if (date.indexOf(".0") == -1) {
			return date;
		}
		return date.replace(".0", "");
	}
	
	public List<String> process(String date1, String date2){
		List<String> list = new ArrayList<String>();
		
		list.add(date1);
		
        String tmp;
        if(date1.compareTo(date2) > 0){  //确保 date1的日期不晚于date2
            tmp = date1; date1 = date2; date2 = tmp;
        }
 
        tmp = format.format(str2Date(date1).getTime() + 3600*24*1000);
 
        while(tmp.compareTo(date2) < 0){                   
            list.add(tmp);
            tmp = format.format(str2Date(tmp).getTime() + 3600*24*1000);
        }
        
        if(!date1.equals(date2)) list.add(date2);
        
        return list;
    }
 
	public Date str2Date(String str) {
        if (str == null) return null;
 
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public String getDayBeforeNow(int day){
		Date today = new Date();
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(today);
		theCa.add(theCa.DATE, -day);
		Date start = theCa.getTime();
		String startDate = format.format(start);
		
		return startDate;
	}
	
	public static String timeLongMinute(String startTime, String endTime, String parse) {
		try {
			Date start = changeStingToDate(startTime, parse);
			Date end = changeStingToDate(endTime, parse);
			long startTimeL = start.getTime();
			long endTimeL = end.getTime();
			if (startTimeL >= endTimeL) {
				return "0";
			}
			long betweenL = (endTimeL - startTimeL) / 1000;
			int sec = (int)betweenL / 60;
			return sec+"";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		String start = "2018-05-11 12:02:00";
		String end = "2018-05-11 12:11:00";
		System.out.println(timeLongMinute(start, end, "yyyy-MM-dd HH:mm:ss"));
	}
}
