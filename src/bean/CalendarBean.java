package bean;
import java.io.Serializable;

public class CalendarBean implements Serializable {
	// カレンダーの年
	private int year;
	// カレンダーの月
	private int month;
	// カレンダーのデータを保持する配列
	private String[][] data;

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

}
