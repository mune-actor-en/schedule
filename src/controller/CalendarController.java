package controller;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.CalendarBean;
import bean.MeetingCalendar;

public class CalendarController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s_year = request.getParameter("year");
		String s_month = request.getParameter("month");

		MeetingCalendar meetingCalendar = new MeetingCalendar();
		CalendarBean calendarBean = null;

		if(s_year != null && s_month != null) {
			int year = Integer.parseInt(s_year);
			int month = Integer.parseInt(s_month);
			if(month == 0) {
				month = 12;
				year --;
			}
			if(month ==13) {
				month = 1;
				year ++;
			}
			// クエリパラメータがきている場合、当年月でカレンダーを作成
			calendarBean = meetingCalendar.createCalendar(year, month);
		} else {
			// クエリパラメータがきていない場合、実行日時のカレンダーを生成
			calendarBean = meetingCalendar.createCalendar();
		}
		request.setAttribute("CalendarBean", calendarBean);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

}
