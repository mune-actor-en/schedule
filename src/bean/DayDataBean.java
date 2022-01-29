package bean;

public class DayDataBean {
	
	int year;
	
	int month;
	
	int day;
	
	boolean isHoliday;
	
	boolean isGray;
	
	String holidayName;
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isHoliday() {
		return isHoliday;
	}

	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}

	public boolean isGray() {
		return isGray;
	}

	public void setGray(boolean isGray) {
		this.isGray = isGray;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
}
