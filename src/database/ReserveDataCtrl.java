package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.DayDataBean;
import bean.ReserveDataBean;

public class ReserveDataCtrl {

	private static final String SELECT_SQL = 
			"SELECT RD.ID, RD.START_DATE, RD.END_DATE, MR.NAME, RD.RESERVER_NAME " +
			"FROM RESERVE_DATA RD, MEETING_ROOM MR " +
			"WHERE RD.START_DATE >= ? AND END_DATE < ? AND RD.MEETING_ROOM_ID = MR.ID " +
			"ORDER BY RD.START_DATE, MR.ID";
	
	private static final String INSERT_SQL =
			"INSERT INTO RESERVE_DATA VALUES (NEXTVAL('RESERVE_ID'),?,?,?,?,NOW(),NOW())";
	
	private static final String DELETE_SQL =
			"DELETE FROM RESERVE_DATA WHERE ID = ?";
	
	private static final String CHECK_SQL =
			"SELECT COUNT(*) CNT FROM RESERVE_DATA " +
			"WHERE MEETING_ROOM_ID = ? AND START_DATE <= ? AND END_DATE >= ?";

	public static Map<String, List<ReserveDataBean>> getReserveList(Connection conn, List<DayDataBean> dateList) throws SQLException {
		DayDataBean firstBean = dateList.get(0);
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, firstBean.getYear());
		startCal.set(Calendar.MONTH, firstBean.getMonth() - 1);
		startCal.set(Calendar.DAY_OF_MONTH, firstBean.getDay());
		
		DayDataBean lastBean = dateList.get(dateList.size() - 1);
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.YEAR, lastBean.getYear());
		endCal.set(Calendar.MONTH, lastBean.getMonth() - 1);
		endCal.set(Calendar.DAY_OF_MONTH, lastBean.getDay());
		endCal.add(Calendar.DAY_OF_MONTH, 1);
		
		SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat ymdFmt = new SimpleDateFormat("yyyyMMdd");
		
		Map<String, List<ReserveDataBean>> result = new HashMap<>();
		
		try (PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL)) {
			pstmt.setDate(1, new java.sql.Date(startCal.getTimeInMillis()));
			pstmt.setDate(2, new java.sql.Date(endCal.getTimeInMillis()));
			
			String tempYmd = null;
			List<ReserveDataBean> BeanList = new ArrayList<>();
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("ID");
				Date startDate = rs.getTimestamp("START_DATE");
				Date endDate   = rs.getTimestamp("END_DATE");
				String roomName = rs.getString("NAME");
				String reserverName = rs.getString("RESERVER_NAME");
				
				String startTime = hhmm.format(startDate);
				String endTime   = hhmm.format(endDate);
				String ymd = ymdFmt.format(startDate);
				
				if (tempYmd != null && !ymd.equals(tempYmd)) {
					result.put(tempYmd, BeanList);
					BeanList = new ArrayList<>();
				}
				
				tempYmd = ymd;
				
				ReserveDataBean Bean = new ReserveDataBean();
				Bean.setId(id);
				Bean.setStartTime(startTime);
				Bean.setEndTime(endTime);
				Bean.setRoomName(roomName);
				Bean.setReserverName(reserverName);
				
				BeanList.add(Bean);
			}
			
			if (tempYmd != null) {
				result.put(tempYmd, BeanList);
			}
		}
		
		return result;
	}
	
	public static List<ReserveDataBean> getReserveData(Connection conn, Date ymd) throws SQLException {
		
		List<ReserveDataBean> result = new ArrayList<>();
		
		SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");
		
		try (PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(ymd);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			Date endParam = cal.getTime();
			
			pstmt.setDate(1, new java.sql.Date(ymd.getTime()));
			pstmt.setDate(2, new java.sql.Date(endParam.getTime()));
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("ID");
				Date startDate = rs.getTime("START_DATE");
				Date endDate   = rs.getTime("END_DATE");
				String roomName = rs.getString("NAME");
				String reserverName = rs.getString("RESERVER_NAME");
				
				String startTime = hhmm.format(startDate);
				String endTime   = hhmm.format(endDate);
				
				ReserveDataBean Bean = new ReserveDataBean();
				Bean.setId(id);
				Bean.setStartTime(startTime);
				Bean.setEndTime(endTime);
				Bean.setRoomName(roomName);
				Bean.setReserverName(reserverName);
				
				result.add(Bean);
			}
		}
		
		return result;
	}
	
	public static int insertReserveData(Connection conn, int roomId, Date startDate, Date endDate, String reserverName) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {
			pstmt.setInt(1, roomId);
			pstmt.setTimestamp(2, new Timestamp(startDate.getTime()));
			pstmt.setTimestamp(3, new Timestamp(endDate.getTime()));
			pstmt.setString(4, reserverName);
			
			int result = pstmt.executeUpdate();
			
			return result;
		}
	}
	
	public static int deleteReserveData(Connection conn, int id) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {
			pstmt.setInt(1, id);
			
			int result = pstmt.executeUpdate();
			
			return result;
		}
	}
	
	public static int checkReserveDuplex(Connection conn, int roomId, Date startDate, Date endDate) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(CHECK_SQL)) {
			pstmt.setInt(1, roomId);
			pstmt.setTimestamp(2, new Timestamp(startDate.getTime()));
			pstmt.setTimestamp(3, new Timestamp(endDate.getTime()));
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				return rs.getInt("CNT");
			}
			
			return 0;
		}
	}
}
