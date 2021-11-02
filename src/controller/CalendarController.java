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
		String year = request.getParameter("year");
		String month = request.getParameter("month");

		CalendarBean cb = null;
		MeetingCalendar mc = new MeetingCalendar();

		if(year != null && month != null) {
			int i_year = Integer.parseInt(year);
			int i_month = Integer.parseInt(month);
			if(i_month == 0) {
				i_month = 12;
				i_year --;
			}
			if(i_month ==13) {
				i_month = 1;
				i_year ++;
			}
			// クエリパラメータがきている場合、当年月でカレンダーを作成
			cb = mc.createCalendar(i_year, i_month);
		} else {
			// クエリパラメータがきていない場合、実行日時のカレンダーを生成
			cb = mc.createCalendar();
		}
		request.setAttribute("cb", cb);
		RequestDispatcher rd = request.getRequestDispatcher("/WebContent/index.jsp");
		rd.forward(request, response);
	}


}
