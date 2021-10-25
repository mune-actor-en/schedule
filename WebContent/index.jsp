<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css2?family=M+PLUS+1p:wght@300&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="./css/index.css">
<meta charset="UTF-8">
<title>会議室予約</title>
</head>
<body>
  <h1>会議室予約</h1>
  <div id="container" align="center">
    <div id="schedule_area">
      <div class="year_month"></div>
      <a href="javascript:void(0);" onClick="false">前月</a>
      <span>2021年10月</span>
      <a href="javascript:void(0);" onClick="false">翌月</a>
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
    </table>
  </div>
</body>
</html>