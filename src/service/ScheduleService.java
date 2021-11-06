package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ScheduleService {

	// ドライバーのクラス名
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    // JDMC接続先情報
    private static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5433/schedule";
    // ユーザー
    private static final String USER = "postgres";
    // パスワード
    private static final String PASS = "postgres";

    public static void main(String[] args) {
        Connection connection = null;
        try {
        	// データベースに接続
            Class.forName(POSTGRES_DRIVER);
            // 接続先の情報
            connection = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS);

        } catch(ClassNotFoundException e) {
        	e.printStackTrace();

        } catch(SQLException e) {
        	e.printStackTrace();

        } finally {
        	try {
        		if (connection != null) {
        			connection.close();
        		}

        	} catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }

}