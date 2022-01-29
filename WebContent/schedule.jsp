<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bean.ReserveDataBean"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.util.List"%>
<%@ page import="bean.DayDataBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<DayDataBean> schList = (List<DayDataBean>) request.getAttribute("calendar");
String ym = (String) request.getAttribute("ym");
Integer year = (Integer) request.getAttribute("year");
Integer month = (Integer) request.getAttribute("month");
Calendar cal = Calendar.getInstance();
cal.setTimeInMillis(System.currentTimeMillis());
int nowYear = cal.get(Calendar.YEAR);
int nowMonth = cal.get(Calendar.MONTH) + 1;
int nowDate = cal.get(Calendar.DAY_OF_MONTH);

Calendar viewCal = Calendar.getInstance();
viewCal.set(Calendar.YEAR, year);
viewCal.set(Calendar.MONTH, month - 1);
DecimalFormat df = new DecimalFormat("00");
// 翌月を取得
viewCal.add(Calendar.MONTH, 1);
String nextYm = viewCal.get(Calendar.YEAR) + df.format(viewCal.get(Calendar.MONTH) + 1);
// 前月を取得
viewCal.add(Calendar.MONTH, -2);
String beforeYm = viewCal.get(Calendar.YEAR) + df.format(viewCal.get(Calendar.MONTH) + 1);
// 会議室取得
Map<Integer, String> roomMap = (Map<Integer, String>) request.getAttribute("meeting_room");
// 予約状況一覧の取得
Map<String, List<ReserveDataBean>> reserveMap = (Map<String, List<ReserveDataBean>>) request.getAttribute("reserveMap");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会議室予約</title>
<link rel="stylesheet" type="text/css" href="./css/style.css">
<script src="./js/jquery-3.6.0.min.js"></script>
<script>
	$(document).on('click', 'tr.cal > td', function() {
		var ymd = $(this).find('input.ymd').val();
		var url = "./sch_detail";
		var param = {
			ymd : ymd
		};

		// 予約状況の詳細をAjax通信で取得
		$.ajax({
			type : 'post',
			url : url,
			data : param,
			dataType : 'json'
		}).done(function(data) {
			printDetailData(data, ymd);
		});

		scroll_position = $(window).scrollTop();
		$('body').addClass('fixed').css({
			'top' : -scroll_position
		});
		$('.post_process').fadeIn();
		$('.modal').fadeIn();
	});

	$(document).on('click', 'button.modal_close', function() {
		$('form[name="reload"]').submit();
		//        $('.post_process').fadeOut();
		//        $('.modal').fadeOut();
		// 表示項目の初期化
		//        $('input.ins_date').val('');
		//      $('span.ins_date_view').text('');
		//      $('select.start_h').val('9');
		//      $('select.start_m').val('0');
		//      $('select.end_h').val('10');
		//      $('select.end_m').val('0');
		//      $('select.room').val('m01');
	});

	$(document).on('click', 'button.regist', function() {
		var reserverName = $('input[name="reserver_name"]').val();
		if (reserverName == null || reserverName.length == 0) {
			alert('利用者名が未設定です。');
			return;
		}
		var startHour = $('select[name="start_h"]').val();
		var startMin = $('select[name="start_m"]').val();
		var endHour = $('select[name="end_h"]').val();
		var endMin = $('select[name="end_m"]').val();
		var start = '' + startHour + startMin;
		var end = '' + endHour + endMin;
		if (end < start) {
			alert('終了時刻は開始時刻以降を設定してください。');
			return;
		}

		var url = "./sch_detail";
		var room = $('select[name="room"]').val();
		var ymd = $('input.ins_date').val();
		var param = {
			start : start,
			end : end,
			reserver : reserverName,
			room : room,
			ymd : ymd,
			mode : 'insert'
		};
		$.ajax({
			type : 'post',
			url : url,
			data : param,
			dataType : 'json'
		}).done(function(data) {
			printDetailData(data, ymd);
		});
	});

	$(document).on('click', 'button.delete', function() {
		var ymd = $('input.ins_date').val();
		var url = "./sch_detail";
		var delIds = '';
		$('input[name="meeting_del"]:checked').each(function(index, elem) {
			delIds += ($(elem).val() + ',');
		});
		var param = {
			del_id : delIds,
			ymd : ymd,
			mode : 'delete'
		};
		$.ajax({
			type : 'post',
			url : url,
			data : param,
			dataType : 'json'
		}).done(function(data) {
			printDetailData(data, ymd);
		});
	});

	function printDetailData(jsonData, ymd) {
		var returnCode = jsonData.return_code;
		if (returnCode != 0) {
			var errMsg = jsonData.err_msg;
			alert(errMsg);
			return;
		}

		var detailDataList = jsonData.reserve_data;
		var detail = '';
		$
				.each(
						detailDataList,
						function(index, value) {
							var data = '<tr class="sch_info detail">'
									+ '<input type=\"hidden\" name=\"sch_id\" value=\"'+value.id+'\">'
									+ '<td class="check"><input type=\"checkbox\" name=\"meeting_del\" value=\"'+value.id+'\"></td>'
									+ '<td>' + value.start + '〜' + value.end
									+ '</td>' + '<td>' + value.room_name
									+ '</td>' + '<td>' + value.reserver_name
									+ '</td></tr>';
							detail += data;
						});

		var ymdView = ymd.substring(0, 4) + '/' + ymd.substring(4, 6) + '/'
				+ ymd.substring(6);

		$('tr.detail').remove();
		$('tr#detail_head').after(detail);
		$('span.ins_date_view').text(ymdView);
		$('input.ins_date').val(ymd);
	}
</script>
</head>
<body>
  <div id="title_area">
    <h1>会議室予約</h1>
  </div>
  <div id="schedule_area">
    <form name="reload">
      <input type="hidden" name="ym" value="<%=ym%>">
    </form>
    <div class="year_month" style="margin-bottom: 20px; text-align: center;">
      <a href="./schedule?ym=<%=beforeYm%>" style="display: inline;">前月</a> <span style="font-size: 150%; font: bold; margin: 0 20px;"><%=year%>年<%=month%>月</span> <a
        href="./schedule?ym=<%=nextYm%>" onClick="false">翌月</a>
    </div>
    <table border="1" align="center">
      <tr class="day">
        <th class="cal holiday">日</th>
        <th class="cal">月</th>
        <th class="cal">火</th>
        <th class="cal">水</th>
        <th class="cal">木</th>
        <th class="cal">金</th>
        <th class="cal saturday">土</th>
      </tr>
      <%
      int index = 1;
      for (DayDataBean ddb : schList) {
      	String ymd = ddb.getYear() + df.format(ddb.getMonth()) + df.format(ddb.getDay());
      	String cls = (ddb.isHoliday() ? "holiday" : "");
      	if ((index - 1) % 7 == 0) {
      		cls = "holiday";
      %>
      <tr class="cal">
        <%
        } else if (index % 7 == 0) {
        if (ddb.isHoliday()) {
        	cls = "holiday";
        } else {
        	cls = "saturday";
        }
        }

        if (ddb.getYear() == nowYear && ddb.getMonth() == nowMonth && ddb.getDay() == nowDate) {
        cls = cls + " now";
        }
        %>
        <td class="<%=cls%>"><input type="hidden" class="ymd" value="<%=ymd%>">
          <div class="date <%=ddb.isGray() ? "gray" : ""%>"><%=ddb.getDay()%>
            <%
            if (ddb.isHoliday()) {
            %>
            <span style="font-size: 70%; vertical-align: middle;"><%="　" + ddb.getHolidayName()%></span>
            <%
            }
            %>
          </div>
          <div class="reserve">
            <%
            int count = 0;
            int other = 0;
            List<ReserveDataBean> reserveList = reserveMap.get(ymd);
            if (reserveList != null) {
            	for (ReserveDataBean rsv : reserveList) {
            		if (count >= 5) {
            	other = reserveList.size() - 5;
            		}
            		count++;
            %>
            <div><%=rsv.getStartTime()%>
              -
              <%=rsv.getEndTime()%>
              <%=rsv.getRoomName()%></div>
            <%
            }
            }
            %>
            <%
            if (0 < other) {
            %>
            <div>
              他<%=other%>
              件
            </div>
            <%
            }
            %>
          </div></td>
        <%
        if (index % 7 == 0) {
        %>
      </tr>
      <%
      }
      index++;
      }
      %>
    </table>
  </div>
  <div class="modal"></div>
  <div class="post_process">
    <h3 class="post_title">詳細情報</h3>
    <div class="modal_upper" style="max-height: 210px; overflow-y: auto;">
      <table border="1" align="center" style="width: 500px; max-height: 200px; table-layout: fixed; font-size: 80%">
        <tr id="detail_head" class="sch_info">
          <th class="sch_info" width="20"></th>
          <th class="sch_info" width="150">時間</th>
          <th class="sch_info" width="130">会議室</th>
          <th class="sch_info" width="200">予約者</th>
        </tr>
      </table>
    </div>
    <div class="post_btn" style="margin-top: 5px;">
      <button class="btn btn-outline-danger delete" type="button">削除</button>
      <button class="btn btn-outline-primary modal_close" type="button">キャンセル</button>
    </div>
    <h3 class="post_title">予約登録</h3>
    <form name="regist" method="post" action="/fusionia/sch_detail?mode=regist" enctype="multipart/form-data">
      <div>
        日付：<span class="ins_date_view"></span>
      </div>
      <input type="hidden" class="ins_date" value="">
      <div style="font-size: 80%;">
        <span>開始時間： <select name="start_h">
            <%
            for (int n = 0; n < 24; n++) {
            %>
            <option value="<%=df.format(n)%>" <%=n == 9 ? "selected" : ""%>><%=df.format(n)%></option>
            <%
            }
            %>
        </select> ： <select name="start_m">
            <%
            for (int n = 0; n < 60; n += 15) {
            %>
            <option value="<%=df.format(n)%>" <%=n == 0 ? "selected" : ""%>><%=df.format(n)%></option>
            <%
            }
            %>
        </select>
        </span> <span style="margin-left: 30px;">終了時間： <select name="end_h">
            <%
            for (int n = 0; n < 24; n++) {
            %>
            <option value="<%=df.format(n)%>" <%=n == 10 ? "selected" : ""%>><%=df.format(n)%></option>
            <%
            }
            %>
        </select> ： <select name="end_m">
            <%
            for (int n = 0; n < 60; n += 15) {
            %>
            <option value="<%=df.format(n)%>" <%=n == 0 ? "selected" : ""%>><%=df.format(n)%></option>
            <%
            }
            %>
        </select>
        </span>
      </div>
      <div>
        <span>会議室：</span> <select name="room">
          <%
          for (Map.Entry<Integer, String> entry : roomMap.entrySet()) {
          %>
          <option value="<%=entry.getKey()%>"><%=entry.getValue()%></option>
          <%
          }
          %>
        </select>
      </div>
      <div>
        <span>利用者名：</span><input name="reserver_name" type="text" size="20">
      </div>
      <div class="post_btn">
        <button class="btn btn-outline-danger regist" type="button">登録</button>
        <button class="btn btn-outline-primary modal_close" type="button">キャンセル</button>
      </div>
    </form>
  </div>
</body>
</html>