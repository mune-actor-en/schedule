package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.ReservationStatusBean;

public class ScheduleService {

	// ドライバーのクラス名
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    // JDMC接続先情報
    private static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5433/schedule";
    // ユーザー
    private static final String USER = "postgres";
    // パスワード
    private static final String PASS = "postgres";
    
    // 予約状況クラスのインスタンスを生成
    ReservationStatusBean reservationStatus = new ReservationStatusBean();

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

        // forName()で例外発生
        } catch(ClassNotFoundException e) {
        	e.printStackTrace();

        // getConnection()で例外発生
        } catch(SQLException e) {
        	e.printStackTrace();

        } finally {
        	try {
        		if (connection != null) {
        			// データベースを切断
        			connection.close();
        		}

        	} catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }

}