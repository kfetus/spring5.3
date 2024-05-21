<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>스케쥴</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

		<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>

	<style>
		div {
			border: 1px solid #bcbcbc;
		}
		.div-table {
			display: table;
			table-layout: fixed;
			width: 100%;
		}
		.div-table-row {
			display: table-row;
			vertical-align: top;			
		}
		.div-table-header {
			display: table-cell;
			text-align: center;
			height: 30px;
			background-color: orchid;
		}
		.div-table-cell {
			display: table-cell;
			padding: 0px 20px;
			height: 130px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		
		.arrow_box {
			display: none;
			position: absolute;
			width: 200px;
			height: 110px;
			padding: 8px;
			border-radius: 8px;
			background: #07708b;
			color: #fff;
			font-size: 14px;
			white-space: pre-wrap;
		}
		.arrow_box:after {
			position: absolute;
			bottom: 100%;
			left: 50%;
			width: 0;
			height: 0;
			margin-left: -10px;
			border: solid transparent;
			border-color: rgba(51, 51, 51, 0);
			border-bottom-color: #07708b;
			border-width: 10px;
			pointer-events: none;
			content: ' ';
		}
		
		span:hover + p.arrow_box {
			display: block;
		}

		#dimLayerPopUp {
		    display:none;
		    position:absolute;
		    top:0;
		    left:0;
		    width:100%;
		    height:100%;
		    background:rgba(0,0,0,0.5);
			z-index: 30;
		}
		
		#dimLayerPopUp > #dimContent {
		    position:absolute;
		    padding:15px;
		    border-radius:15px; 
		    top:50%;
		    left:50%;
		    transform:translate(-50%, -50%);
		    width:80%;
		    height:80%;
		    background:#fff;
		    box-shadow: 7px 7px 5px rgba(0,0,0,0.2); 
		}
		
		#dimLayerPopUp > #dimContent > h2 {
		    margin-bottom:25px;
		}
		
		#dimLayerPopUp > #dimContent > h2 > button {
		    float: right;
		}
		#dimLayerPopUp > #dimContent > button {
		    float: right;
		}
		
		
	</style>	

		
		<script>
			let yyyymm;
			const schedule = () => {
				console.log('ajax start');
				$.ajax({
					type : 'post',
					url : '/scheduleCalender.do',
					async : true,
					dataType : 'json',
	 				headers : {"Content-Type" : "application/json"},
					data : JSON.stringify({ 'yyyymmdd':yyyymm }),
					success : function(result) {
						console.log(result);
						if(result.RESCODE != '0000') {
							alert(result.RESMSG);
							return false;
						}
						yyyymm = Number(result.YYYYMMDD.replace('-',''));
						$("#spanMonthTitle").text(result.YYYYMMDD);

						if( result.RESULT_DATA.length < 6) {
							$("#weekly_5").hide();
						} else {
							$("#weekly_5").show();
						}
						
						for ( let row = 0 ; row < result.RESULT_DATA.length ; row++ ) {
//							console.log(result.RESULT_DATA[row]);
							makeDiv(result.RESULT_DATA, row, 'SUN');
							makeDiv(result.RESULT_DATA, row, 'MON');
							makeDiv(result.RESULT_DATA, row, 'TUE');
							makeDiv(result.RESULT_DATA, row, 'WED');
							makeDiv(result.RESULT_DATA, row, 'THU');
							makeDiv(result.RESULT_DATA, row, 'FRI');
							makeDiv(result.RESULT_DATA ,row, 'SAT');
						}
/*
						for ( let rr of result.RESULT_DATA ) {
							console.log(rr);
						}

						//이건 row로 loop문 안에서 처리를 하면 예로 위에서 div에 이벤트 할당할때 처럼 하면 row 변수가 공유가 되어서 모두 마지막 값으로 셋팅된다. 즉 loop는 정상적으로 수행 되나 loop 종료 후 데이턴s row 최종값만 셋팅된다.
						for ( row in result.RESULT_DATA) {
							console.log(result.RESULT_DATA[i])
						}
*/
					},
					error : function(error, status) {        
						console.log(error);
						alert(error.responseJSON.RESMSG);
					}
				});
			}
			
			function makeDiv(data, rowIndex, dayName) {
				let day = data[rowIndex][dayName].split('|')[0];
				let text = data[rowIndex][dayName].split('|')[1];

				$("#DAY-"+dayName+"-"+rowIndex).html('');
				$("#divCell_"+rowIndex+"_"+dayName).off("click");
				$("#T-"+dayName+"-"+rowIndex).html('');
				$("#ADD-"+dayName+"-"+rowIndex).html('');
				
				if ( day != '' ) {
					$("#DAY-"+dayName+"-"+rowIndex).html(day);	
					$("#divCell_"+rowIndex+"_"+dayName).click(function () {
						divCellClick(day,text);
					});
				}							
				if (!!text) {
					if(text.indexOf('@') == -1 ) {
						$("#T-"+dayName+"-"+rowIndex).html(text);
						$("#ADD-"+dayName+"-"+rowIndex).html(text);
					} else {
						$("#T-"+dayName+"-"+rowIndex).html(text.replace('@','<BR>'));
						$("#ADD-"+dayName+"-"+rowIndex).html(text.replace('@','<BR>'));
					}
				}
			}
			
			function resetCalendar() {

				for ( let row = 0 ; row < 5; row++ ) {
					$("#DAY-SUN-"+row).html('');
					$("#divCell_"+row+"_SUN").off("click");
					$("#T-SUN-"+row).html('');
					$("#ADD-SUN-"+row).html('');
				}
				
			}

			function divCellClick(day,data) {
				let yyyymmdd = String(yyyymm);
				if(Number(day) < 10 ) {
					yyyymmdd = yyyymmdd + '0' + day;
				} else {
					yyyymmdd = yyyymmdd + day;
				}

				$("#changeTitle").text(yyyymmdd);
				$("#addSchedule").find("tr:gt(0)").remove();

				
				let rowTimeList = [];
				let rowMinuteList = [];
				let html = '';

				if(data == '') {
					html = makeTableTr(0);
				} else {
					
					let dataList = data.split('@');
					console.log(dataList);
					for( let i = 0 ; i < dataList.length ; i++) {
						let rowData = dataList[i];
						let rowTime = Number(rowData.substr(0,2));
						let rowMinute = rowData.substr(3,2);
						let rowText = rowData.substr(6);
						console.log(rowData);
						rowTimeList.push(rowTime);
						rowMinuteList.push(rowMinute);
						
						html += '<tr>';
						html += '<td><select name="time'+i+'" id="time'+i+'">';
		   			<c:forEach begin="1" end="23" varStatus="status">
						html += '<option value="<c:out value="${status.index}"/>"><c:out value="${status.index}"/></option>';
		   			</c:forEach>
						html += '</select></td>';
						html += '<td><select name="minute'+i+'" id="minute'+i+'">';
						html += '<option value="00">00</option><option value="10">10</option><option value="20">20</option>';
						html += '<option value="30">30</option><option value="40">40</option><option value="50">50</option>';
						html += '</select></td>';
						html += '<td><input type="text" style="width: 500px; name="schduleText'+i+'" id="schduleText'+i+'" value="'+rowText+'" /></td>';				
						html += '</tr>';
					}
				}
				
				$("#addSchedule").append(html);
				if(rowTimeList.length > 0) {
					for(var i = 0 ; i < rowTimeList.length ; i++) {
						$("#time"+i).val(rowTimeList[i]);
						$("#minute"+i).val(rowMinuteList[i]);
					}
				}
				$("#dimLayerPopUp").show();
			}

			
			function addRow() {
				console.log($("#addSchedule > tbody tr").length);
				let trStr = makeTableTr($("#addSchedule > tbody tr").length-1);
				$("#addSchedule").append(trStr);
			}

			function makeTableTr(rowCnt) {
				let html = "";
				html += '<tr>';
				html += '<td><select name="time'+rowCnt+'" id="time'+rowCnt+'">';
   			<c:forEach begin="1" end="23" varStatus="status">
				html += '<option value="<c:out value="${status.index}"/>"><c:out value="${status.index}"/></option>';
   			</c:forEach>
				html += '</select></td>';
				html += '<td><select name="minute'+rowCnt+'" id="minute'+rowCnt+'">';
				html += '<option value="00">00</option><option value="10">10</option><option value="20">20</option>';
				html += '<option value="30">30</option><option value="40">40</option><option value="50">50</option>';
				html += '</select></td>';
				html += '<td><input type="text" style="width: 500px; name="schduleText'+rowCnt+'" id="schduleText'+rowCnt+'" value="" /></td>';				
				html += '</tr>';
				return html;
			}
			
			function saveSchedule() {

				let paramList = [];
				$("select[id^='time']").each(function(index, el) {
					let paramRow = {YYYYMMDD:"",HH:"",MM:"",SCHEDULE:""};
					paramRow["YYYYMMDD"] = $("#changeTitle").text();
					paramRow["HH"] = $("#time"+index).val();
					paramRow["MM"] = $("#minute"+index).val();
					paramRow["SCHEDULE"] = $("#schduleText"+index).val();
					paramList.push(paramRow);
				});
				
				console.log(paramList);
				
				let checkState = false;
				let tempStr = "";
				//중복값 체크.
				for(let i = 0 ; i < paramList.length; i++) {
					let tempStr = paramList[i]["HH"]+paramList[i]["MM"];
					for(let j = i+1 ; j < paramList.length; j++ ) {
						if( tempStr == paramList[j]["HH"]+paramList[j]["MM"]) {
							checkState = true;		
							break;
						}
					}
				}
				if(checkState) {
					alert('중복값이 있습니다.' + tempStr);
					return false;
				}

				console.log(JSON.stringify({ "yyyymmdd":$("#changeTitle").text(),"scheduleList":paramList }));
				
				$.ajax({
					type : 'post',
					url : '/scheduleUpdate.do',
					async : true,
					dataType : 'json',
	 				headers : {"Content-Type" : "application/json"},
					data : JSON.stringify({ "yyyymmdd":$("#changeTitle").text(),"scheduleList":JSON.stringify(paramList)}),
					success : function(result) {
						console.log(result);
						if(result.RESCODE != '0000') {
							alert(result.RESMSG);
							return false;
						}
					}, 
					error : function(error, status) {        
						console.log(error);
						alert(error.responseJSON.RESMSG);
					}
				});				
				
				$("#dimLayerPopUp").hide();
				schedule();
			}

			function closeDim() {
				$("#dimLayerPopUp").hide();
			}
			
			function nextSchedule() {
				let strYyyymm = String(yyyymm);
				let yyyy = strYyyymm.substr(0,4);
				let mm = strYyyymm.substr(4,2);
				if(Number(mm) == 12 ) {
					yyyymm = Number((Number(yyyy)+1)+'01');
				} else {
					yyyymm += 1;
				}
				schedule();
			}
			
			$(function() {
				console.log('document.onload()');
				schedule();
			})
		
		</script>
	
	</head>
	<body>
	<div>
		<div id="schedule">
			<div style="float: left;">
				<span id="spanMonthTitle"></span> 월
			</div>
			<div style="float: right;">
				<button type="button" id="s1" onclick="javascript:nextSchedule();"><span><strong>다음달</strong></span></button>
			</div>
		</div>

		<div class="div-table">
			<div class="div-table-row">
				<div class="div-table-header">
				<p>일</p>
				</div>
				<div class="div-table-header">
				<p>월</p>
				</div>
				<div class="div-table-header">
				<p>화</p>
				</div>
				<div class="div-table-header">
				<p>수</p>
				</div>
				<div class="div-table-header">
				<p>목</p>
				</div>
				<div class="div-table-header">
				<p>금</p>
				</div>
				<div class="div-table-header">
				<p>토</p>
				</div>
			</div>
		
			<div class="div-table-row" id="weekly_0">
				<div class="div-table-cell" id="divCell_0_SUN">
					<p id="DAY-SUN-0"></p>
					<span id="T-SUN-0"></span>
					<p id="ADD-SUN-0" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_0_MON">
					<p id="DAY-MON-0"></p>
					<span id="T-MON-0"></span>
					<p id="ADD-MON-0" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_0_TUE">
					<p id="DAY-TUE-0"></p>
					<span id="T-TUE-0"></span>
					<p id="ADD-TUE-0" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_0_WED">
					<p id="DAY-WED-0"></p>
					<span id="T-WED-0"></span>
					<p id="ADD-WED-0" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_0_THU">
					<p id="DAY-THU-0"></p>
					<span id="T-THU-0"></span>
					<p id="ADD-THU-0" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_0_FRI">
					<p id="DAY-FRI-0"></p>
					<span id="T-FRI-0"></span>
					<p id="ADD-FRI-0" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_0_SAT">
					<p id="DAY-SAT-0"></p>
					<span id="T-SAT-0"></span>
					<p id="ADD-SAT-0" class="arrow_box"></p>
				</div>
			</div>
			
			<div class="div-table-row" id="weekly_1">
				<div class="div-table-cell" id="divCell_1_SUN">
				<p id="DAY-SUN-1"></p>
				<span id="T-SUN-1"></span>
				<p id="ADD-SUN-1" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_1_MON">
				<p id="DAY-MON-1"></p>
				<span id="T-MON-1"></span>
				<p id="ADD-MON-1" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_1_TUE">
				<p id="DAY-TUE-1"></p>
				<span id="T-TUE-1"></span>
				<p id="ADD-TUE-1" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_1_WED">
				<p id="DAY-WED-1"></p>
				<span id="T-WED-1"></span>
				<p id="ADD-WED-1" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_1_THU">
				<p id="DAY-THU-1"></p>
				<span id="T-THU-1"></span>
				<p id="ADD-THU-1" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_1_FRI">
				<p id="DAY-FRI-1"></p>
				<span id="T-FRI-1"></span>
				<p id="ADD-FRI-1" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_1_SAT">
				<p id="DAY-SAT-1"></p>
				<span id="T-SAT-1"></span>
				<p id="ADD-SAT-1" class="arrow_box"></p>
				</div>
			</div>

			<div class="div-table-row" id="weekly_2">
				<div class="div-table-cell" id="divCell_2_SUN">
				<p id="DAY-SUN-2"></p>
				<span id="T-SUN-2"></span>
				<p id="ADD-SUN-2" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_2_MON">
				<p id="DAY-MON-2"></p>
				<span id="T-MON-2"></span>
				<p id="ADD-MON-2" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_2_TUE">
				<p id="DAY-TUE-2"></p>
				<span id="T-TUE-2"></span>
				<p id="ADD-TUE-2" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_2_WED">
				<p id="DAY-WED-2"></p>
				<span id="T-WED-2"></span>
				<p id="ADD-WED-2" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_2_THU">
				<p id="DAY-THU-2"></p>
				<span id="T-THU-2"></span>
				<p id="ADD-THU-2" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_2_FRI">
				<p id="DAY-FRI-2"></p>
				<span id="T-FRI-2"></span>
				<p id="ADD-FRI-2" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_2_SAT">
				<p id="DAY-SAT-2"></p>
				<span id="T-SAT-2"></span>
				<p id="ADD-SAT-2" class="arrow_box"></p>
				</div>
			</div>

			<div class="div-table-row" id="weekly_3">
				<div class="div-table-cell" id="divCell_3_SUN">
				<p id="DAY-SUN-3"></p>
				<span id="T-SUN-3"></span>
				<p id="ADD-SUN-3" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_3_MON">
				<p id="DAY-MON-3"></p>
				<span id="T-MON-3"></span>
				<p id="ADD-MON-3" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_3_TUE">
				<p id="DAY-TUE-3"></p>
				<span id="T-TUE-3"></span>
				<p id="ADD-TUE-3" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_3_WED">
				<p id="DAY-WED-3"></p>
				<span id="T-WED-3"></span>
				<p id="ADD-WED-3" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_3_THU">
				<p id="DAY-THU-3"></p>
				<span id="T-THU-3"></span>
				<p id="ADD-THU-3" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_3_FRI">
				<p id="DAY-FRI-3"></p>
				<span id="T-FRI-3"></span>
				<p id="ADD-FRI-3" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_3_SAT">
				<p id="DAY-SAT-3"></p>
				<span id="T-SAT-3"></span>
				<p id="ADD-SAT-3" class="arrow_box"></p>
				</div>
			</div>

			<div class="div-table-row" id="weekly_4">
				<div class="div-table-cell" id="divCell_4_SUN">
				<p id="DAY-SUN-4"></p>
				<span id="T-SUN-4"></span>
				<p id="ADD-SUN-4" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_4_MON">
				<p id="DAY-MON-4"></p>
				<span id="T-MON-4"></span>
				<p id="ADD-MON-4" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_4_TUE">
				<p id="DAY-TUE-4"></p>
				<span id="T-TUE-4"></span>
				<p id="ADD-TUE-4" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_4_WED">
				<p id="DAY-WED-4"></p>
				<span id="T-WED-4"></span>
				<p id="ADD-WED-4" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_4_THU">
				<p id="DAY-THU-4"></p>
				<span id="T-THU-4"></span>
				<p id="ADD-THU-4" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_4_FRI">
				<p id="DAY-FRI-4"></p>
				<span id="T-FRI-4"></span>
				<p id="ADD-FRI-4" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_4_SAT">
				<p id="DAY-SAT-4"></p>
				<span id="T-SAT-4"></span>
				<p id="ADD-SAT-4" class="arrow_box"></p>
				</div>
			</div>

			<div class="div-table-row" id="weekly_5" style="display: none;">
				<div class="div-table-cell" id="divCell_5_SUN">
				<p id="DAY-SUN-5"></p>
				<span id="T-SUN-5"></span>
				<p id="ADD-SUN-5" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_5_MON">
				<p id="DAY-MON-5"></p>
				<span id="T-MON-5"></span>
				<p id="ADD-MON-5" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_5_TUE">
				<p id="DAY-TUE-5"></p>
				<span id="T-TUE-5"></span>
				<p id="ADD-TUE-5" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_5_WED">
				<p id="DAY-WED-5"></p>
				<span id="T-WED-5"></span>
				<p id="ADD-WED-5" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_5_THU">
				<p id="DAY-THU-5"></p>
				<span id="T-THU-5"></span>
				<p id="ADD-THU-5" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_5_FRI">
				<p id="DAY-FRI-5"></p>
				<span id="T-FRI-5"></span>
				<p id="ADD-FRI-5" class="arrow_box"></p>
				</div>
				<div class="div-table-cell" id="divCell_5_SAT">
				<p id="DAY-SAT-5"></p>
				<span id="T-SAT-5"></span>
				<p id="ADD-SAT-5" class="arrow_box"></p>
				</div>
			</div>

		</div>

		<div id="dimLayerPopUp">
			<div id="dimContent">
				<h2><span id="changeTitle"></span> 일
				<button type="button" onclick="$('#dimLayerPopUp').hide();">닫기</button>
				</h2>
				<button type="button" onclick="addRow();">추가</button> 
				<div id="addGrid">
		       		<table id="addSchedule">
		       			<colgroup>
		       				<col width="10%"/>
		       				<col width="10%"/>
		       				<col width=""/>
		       			</colgroup>
		       			<tr>
		       				<th align="center">시간</th>
		       				<th align="center">분</th>
		       				<th align="center">내용</th>
		       			</tr>
	           			<tr>
	           				<td>
								<select name="time">
			       			<c:forEach begin="1" end="23" varStatus="status">
			           				<option value="<c:out value="${status.index}"/>"><c:out value="${status.index}"/></option>
			       			</c:forEach>
								</select>
	           				</td>
	           				<td>
								<select name="minute">
									<option value="00">00</option>
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="30">30</option>
									<option value="40">40</option>
									<option value="50">50</option>
								</select>
	           				</td>
	           				<td>
	           					<input type="text" style="width: 500px; name="schduleText" value="" />
	           				</td>
	           			</tr>
		       		</table>
				</div>
				<button type="button" onclick="javascript:saveSchedule();">저장</button>
			</div>
		</div>
	</div>
	</body>
</html>