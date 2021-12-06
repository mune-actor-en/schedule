package bean;
import java.io.Serializable;

public class CalendarBean implements Serializable {
	// カレンダーの年
	private int year;
	// カレンダーの月
	private int month;
	// カレンダーのデータを保持する配列
	private String[][] data;
	
	// 予約済みの会議室名
	private String room_name;
	
	// 予約しているユーザー名
	private String user_name;
	
	// 利用開始時間
	private String use_start_time;
	
	// 利用終了時間
	private String use_end_time;
	

	// セッター・ゲッターを定義
	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public void setData(String [][] data) {
		this.data = data;
	}

	public String[][] getData(){
		return data;
	}

	public void setRoomName(String room_name) {
		this.room_name = room_name;
	}
	
	public String getRoomName() {
		return room_name;
	}
	
	public void setUserName(String user_name) {
		this.user_name = user_name;
	}
	
	public String getUserName() {
		return user_name;
	}
	
	public void setUseStartTime(String use_start_time) {
		this.use_start_time = use_start_time;
	}
	
	public String getUseStartTime() {
		return use_start_time;
	}
	
	public void setUseEndTime(String use_end_time) {
		this.use_end_time = use_end_time;
	}
	
	public String getUseEndTime() {
		return use_end_time;
	}

}
