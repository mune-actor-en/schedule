package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.CalendarBean;

public class ScheduleService {

	// ドライバーのクラス名
	private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
	// JDMC接続先情報
	private static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5433/schedule";
	// ユーザー
	private static final String USER = "postgres";
	// パスワード
	private static final String PASS = "postgres";
	// タイムフォーマット
	private static final String TIME_FORMAT = "HH:mm";
	// 予約状況を取得するSQL
	private static final String SELECT_SQL = "SELECT mr.room_name, rs.user_name, rs.use_start_time, rs.use_end_time FROM meeting_rooms mr INNER JOIN reservation_status rs ON mr.room_id = rs.room_id;";

	// 予約状況クラスのインスタンスを生成
	// ReservationStatusBean reservationStatus = new ReservationStatusBean();
	CalendarBean calendarbean = null;
	
	// public String roomName = calendarbean.getRoomName();

	public static void main(String[] args) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			// データベースに接続
			Class.forName(POSTGRES_DRIVER);
			// 接続先の情報
			connection = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS);
			statement = connection.createStatement();

			// SimpleDateFormat sdFormat = new SimpleDateFormat(TIME_FORMAT);
			// String use_start_time = sdFormat.format(calendarbean.getUseStartTime());
			
			preparedStatement = connection.prepareStatement(SELECT_SQL);
			// preparedStatement.setString(1, roomName);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String tmpRoomName = resultSet.getString("room_name");
				String tmpUserName = resultSet.getString("user_name");
				String tmpUseStartTime = resultSet.getString("user_start_time");
				String tmpUserEndTime = resultSet.getString("use_end_time");
				
				// calendarBean = new CalendarBean();
			}
			
			// forName()で例外発生
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

			// getConnection()で例外発生
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				
				if(resultSet != null) {
					resultSet.close();
				}
				
				if(statement != null) {
					statement.close();
				}
				
				if (connection != null) {
					// データベースを切断
					connection.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}