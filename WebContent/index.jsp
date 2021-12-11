<%@page import="bean.MeetingCalendar"%>
<%@page import="service.ScheduleService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="bean.CalendarBean"%>
<%
// CalendarBean calendarBean = (CalendarBean)request.getAttribute("CalendarBean");
%>

<% ScheduleService scheduleService = new ScheduleService(); %>
<% MeetingCalendar meetingCalendar = new MeetingCalendar(); %>
<% CalendarBean calendarBean = meetingCalendar.createCalendar(); %>
<% // calendarBean = scheduleService.getCalendarBean(); %>


<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css2?family=M+PLUS+1p:wght@300&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="./css/index.css">
<meta charset="UTF-8">
<title>会議室予約</title>
</head>
<body>
  <div id="container" align="center">
    <h1>会議室予約</h1>
    <div id="schedule_area">
      <div class="year_month"></div>
      <a href="javascript:void(0);" onClick="false">前月</a> <span><%=calendarBean.getYear()%>年<%=calendarBean.getMonth()%>月</span> <a href="javascript:void(0);" onClick="false">翌月</a>
    </div>
    <table border="1">
      <tr>
        <th>日</th>
        <th>月</th>
        <th>火</th>
        <th>水</th>
        <th>木</th>
        <th>金</th>
        <th>土</th>
      </tr>
      <%
      for (String[] row : calendarBean.getData()) {
      %>
      <tr>
        <%
        for (String col : row) {
        %>
        <%
        if (col.startsWith("*")) {
        %>
        <td class="today"><%=col.substring(1)%><br><%=calendarBean.getRoomName() %><br></td>
        <%
        } else {
        %>
        <td><%=col%><br><></td>
        <%
        }
        %>
        <%
        }
        %>
      </tr>
      <%
      }
      %>
    </table>
  </div>
</body>
</html>