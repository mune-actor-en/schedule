package bean;

import java.util.List;
import java.util.Map;


public class ScheduleBean {

	private String nowYear;
	
	private String nowMonth;
	
	private String nowDay;
	
	private List<DayDataBean> dateList;
	
	private Map<String, ScheduleDetailBean> schMap;

	public String getNowYear() {
		return nowYear;
	}

	public void setNowYear(String nowYear) {
		this.nowYear = nowYear;
	}

	public String getNowMonth() {
		return nowMonth;
	}

	public void setNowMonth(String nowMonth) {
		this.nowMonth = nowMonth;
	}

	public String getNowDay() {
		return nowDay;
	}

	public void setNowDay(String nowDay) {
		this.nowDay = nowDay;
	}

	public List<DayDataBean> getDateList() {
		return dateList;
	}

	public void setDateList(List<DayDataBean> dateList) {
		this.dateList = dateList;
	}

	public Map<String, ScheduleDetailBean> getSchMap() {
		return schMap;
	}

	public void setSchMap(Map<String, ScheduleDetailBean> schMap) {
		this.schMap = schMap;
	}
}
