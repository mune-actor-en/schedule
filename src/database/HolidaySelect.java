package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class HolidaySelect {

	public static Map<String, String> getHolidayData(Connection conn, int year, int month) throws SQLException {
		String sql = "SELECT DATE, HOLIDAY_NAME FROM HOLIDAY WHERE YEAR = ? AND MONTH = ? AND DELETE_FLAG = 0";
		Map<String, String> holidayMap = new HashMap<>();
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			DecimalFormat df = new DecimalFormat("00");
			
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				holidayMap.put(df.format(month) + df.format(rs.getInt("DATE")), rs.getString("HOLIDAY_NAME"));
			}
			
			return holidayMap;
		}
	}
}
