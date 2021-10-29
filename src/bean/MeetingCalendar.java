package bean;

import java.util.Calendar;

public class MeetingCalendar {
	// カレンダーインスタンスを生成するメソッド
	public CalendarBean createCalendar(int... args) {
		// カレンダーモデルをインスタンス生成
		CalendarBean cb = new CalendarBean();
		// 現在の日時でカレンダーを生成
		Calendar cal = Calendar.getInstance();
		if(args.length == 2) {
			// 最初の引数で年を指定
			cal.set(Calendar.YEAR, args[0]);
			// 次の引数で月を指定
			cal.set(Calendar.MONTH, args[1]-1);
		}

		// カレンダーに年を設定
		cb.setYear(cal.get(Calendar.YEAR));
		// カレンダーに月を設定
		cb.setMonth(cal.get(Calendar.MONTH) + 1);

	}


}
