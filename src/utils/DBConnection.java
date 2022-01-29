package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	// ドライバーのクラス名
	private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
	// JDMC接続先情報
	private static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5433/schedule";
	// ユーザー
	private static final String USER = "postgres";
	// パスワード
	private static final String PASS = "postgres";
	
	public static Connection getConnection() throws Exception {
		
		Class.forName(POSTGRES_DRIVER);
		Connection conn = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS);
		
		return conn;
	}
}
