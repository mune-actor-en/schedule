package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.DayDataBean;
import bean.ReserveDataBean;
import database.HolidaySelect;
import database.MeetingRoomSelect;
import database.ReserveDataCtrl;
import utils.DBConnection;

/**
 * Servlet implementation class ScheduleService
 */
@WebServlet("/ScheduleService")
public class ScheduleService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 表示要求年月(yyyymm形式)
		String yearMonth = request.getParameter("ym");
		
		try (Connection conn = DBConnection.getConnection()){
			// カレンダー情報の設定
			List<DayDataBean> dayList = createCalendar(conn, yearMonth);
			request.setAttribute("calendar", dayList);
			// 表示年月を設定
			Calendar cal = Calendar.getInstance();
			if (yearMonth == null || yearMonth.length() == 0) {
				// 年月が指定されていない場合はシステム日付
				cal.setTimeInMillis(System.currentTimeMillis());
			} else {
				// 年月が設定されている場合
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				try {
					Date date = sdf.parse(yearMonth);
					cal.setTime(date);
				} catch (ParseException e) {
					// 年月の書式が不正な場合
					System.out.println("年月の書式が不正です: " + yearMonth);
					throw new ServletException(e);
				}
			}
			
			// 会議室予約状況の取得
			Map<String, List<ReserveDataBean>> reserveMap = ReserveDataCtrl.getReserveList(conn, dayList);
			request.setAttribute("reserveMap", reserveMap);
			
			Integer year = cal.get(Calendar.YEAR);
			Integer month = cal.get(Calendar.MONTH) + 1;
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("ym", yearMonth == null ? "" : yearMonth);
			
			request.setAttribute("meeting_room", MeetingRoomSelect.getMeetingRoomData(conn));
		
			RequestDispatcher dispatcher = request.getRequestDispatcher("/schedule.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private List<DayDataBean> createCalendar(Connection conn, String yearMonth) throws SQLException {
		// 現在日次の取得
		Calendar cal = Calendar.getInstance();
		if (yearMonth == null) {
			// 年月が指定されていない場合はシステム日付
			cal.setTimeInMillis(System.currentTimeMillis());
		} else {
			// 年月が設定されている場合
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			try {
				Date date = sdf.parse(yearMonth);
				cal.setTime(date);
			} catch (ParseException e) {
				// 年月の書式が不正な場合
				cal.setTimeInMillis(System.currentTimeMillis());
			}
		}
		
		// 祝日の取得
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		Map<String, String> holidayMap = HolidaySelect.getHolidayData(conn, year, month) ;
				
		// 当月の月初を設定
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// 1日が何曜日か取得
		int startDay = cal.get(Calendar.DAY_OF_WEEK);
		// 当月の最終日
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// 表示カレンダー用リスト
		List<DayDataBean> dateList = new ArrayList<>();
		
		if (startDay != 1) {
			cal.add(Calendar.DAY_OF_MONTH, -(startDay - 1));
		}
		
		DecimalFormat df = new DecimalFormat("00");
		int loopCnt = lastDay + (startDay - 1);
		for (int cnt = 0; cnt < loopCnt; cnt++) {
			DayDataBean bean = new DayDataBean();
			
			bean.setYear(cal.get(Calendar.YEAR));
			bean.setMonth(cal.get(Calendar.MONTH) + 1);
			int date = cal.get(Calendar.DAY_OF_MONTH);
			bean.setDay(date);
			// 祝日の設定
			String mmdd = df.format(bean.getMonth()) + df.format(date);
			String holidayName = holidayMap.get(mmdd);
			if (holidayName != null && holidayName.length() != 0) {
				bean.setHoliday(true);
				bean.setHolidayName(holidayName);
			}
			
			// 表示月以外の日付の場合
			if (bean.getMonth() != month) {
				bean.setGray(true);
			}
			
			dateList.add(bean);
			// 日付を1加算
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		// 翌月の設定
		// カレンダーの日付は翌月の1日になっている
		// 日曜以外であれば土曜までの日付を表示する
		int nowDay = cal.get(Calendar.DAY_OF_WEEK);
		if (nowDay != 1) {
			for (int n = 7; nowDay <= n; n--) {
				DayDataBean bean = new DayDataBean();
				
				bean.setYear(cal.get(Calendar.YEAR));
				bean.setMonth(cal.get(Calendar.MONTH) + 1);
				bean.setDay(cal.get(Calendar.DAY_OF_MONTH));
				bean.setGray(true);
				
				dateList.add(bean);
				// 日付を1加算
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		return dateList;
	}
}
