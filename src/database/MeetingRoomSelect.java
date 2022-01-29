package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MeetingRoomSelect {

	public static final String SQL = "SELECT ID, NAME FROM MEETING_ROOM WHERE DELETE_FLAG = 0 ORDER BY ID";
	
	public static Map<Integer, String> getMeetingRoomData(Connection conn) throws SQLException {
		Map<Integer, String> roomMap = new HashMap<>();
		
		try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				roomMap.put(rs.getInt("ID"), rs.getString("NAME"));
			}
			
			return roomMap;
		}
	}
}
