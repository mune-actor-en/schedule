package bean;

import java.util.Date;

public class ScheduleDetailBean {

	private Date startTime;
	
	private Date endTime;
	
	private String userName;
	
	private String placeName;
	
	private int placeCode;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public int getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(int placeCode) {
		this.placeCode = placeCode;
	}
}
