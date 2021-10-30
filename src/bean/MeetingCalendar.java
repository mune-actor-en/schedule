package bean;

import java.util.Calendar;

public class MeetingCalendar {
	// カレンダーインスタンスを生成するメソッド
	public CalendarBean createCalendar(int... args) {
		// カレンダーモデルをインスタンス生成
		CalendarBean cb = new CalendarBean();
		// 現在の日時でカレンダーを生成
		Calendar cal = Calendar.getInstance();
		if (args.length == 2) {
			// 最初の引数で年を指定
			cal.set(Calendar.YEAR, args[0]);
			// 次の引数で月を指定
			cal.set(Calendar.MONTH, args[1] - 1);
		}

		// カレンダーに年を設定
		cb.setYear(cal.get(Calendar.YEAR));
		// カレンダーに月を設定
		cb.setMonth(cal.get(Calendar.MONTH) + 1);
		// 当月の1日が何曜日か判定するため、日付を1日に設定
		cal.set(Calendar.DATE, 1);
		// カレンダーの最初の空白の数
		int before = cal.get(Calendar.DAY_OF_WEEK) -1;
		// カレンダーの日付の数
		int daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 当月の最後の日が何曜日か判定するために日付を最終日に設定
		cal.set(Calendar.DATE, daysCount);
		// 最終日あとの空白の数
		int after = 7 - cal.get(Calendar.DAY_OF_WEEK);
		// すべての要素数
		int total = before + daysCount + after;
		// 全要素数を7個の配列に格納した場合、何行になるか判定
		int rows = total / 7;
		// 計算した行数で2次元配列を生成
		String [][] data = new String[rows][7];
		// 現時点のカレンダーが今月のカレンダーかどうかを判定するために現時点の日付情報を持つインスタンスを別途生成
		Calendar now =  Calendar.getInstance();
		for (int i = 0; i < rows; i++) {
			for(int j = 0; j < 7; j++) {
				if (i == 0 && j < before || i == rows -1 && j >= (7 - after)) {
					// カレンダーの前後に入る空白の部分を空にする
					data[i][j] = "";
				} else {
					// カウンター変数と実際の日付の変換
					int date = i * 7 + j + 1 - before;
					// 配列に日付を格納
					data[i][j] = String.valueOf(date);
					// 現時点のカレンダーが今月のカレンダーの場合、今日の日付の先頭に「*」を付与
					if(now.get(Calendar.DATE) == date && now.get(Calendar.MONTH) == cb.getMonth() -1 && now.get(Calendar.YEAR) == cb.getYear()) {
						data[i][j] = "*" + data[i][j];
					}
				}
			}
		}

		// 作成した2次元配列をカレンダーに格納
		cb.setData(data);
		return cb;
	}

}
