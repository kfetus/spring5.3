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
			left: 0;
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
						yyyymm = Number(result.YYYYMMDD.replace('-',''));
/*
						for ( let i = 0 ; i < result.RESULT_DATA.length ; i++ ) {
							console.log(result.RESULT_DATA[i])
						}
						for ( let rr of result.RESULT_DATA ) {
							console.log(rr);
						}
*/

						for ( row in result.RESULT_DATA) {
							$("#DAY-SUN-"+row).text(result.RESULT_DATA[row].SUN.split('|')[0]);
							$("#T-SUN-"+row).text(result.RESULT_DATA[row].SUN.split('|')[1]);
							$("#ADD-SUN-"+row).text(result.RESULT_DATA[row].SUN.split('|')[1]);
							$("#DAY-MON-"+row).text(result.RESULT_DATA[row].MON.split('|')[0]);
							$("#T-MON-"+row).text(result.RESULT_DATA[row].MON.split('|')[1]);
							$("#DAY-TUE-"+row).text(result.RESULT_DATA[row].TUE.split('|')[0]);
							$("#T-TUE-"+row).text(result.RESULT_DATA[row].TUE.split('|')[1]);
							$("#DAY-WED-"+row).text(result.RESULT_DATA[row].WED.split('|')[0]);
							$("#T-WED-"+row).text(result.RESULT_DATA[row].WED.split('|')[1]);
							$("#DAY-THU-"+row).text(result.RESULT_DATA[row].THU.split('|')[0]);
							$("#T-THU-"+row).text(result.RESULT_DATA[row].THU.split('|')[1]);
							$("#DAY-FRI-"+row).text(result.RESULT_DATA[row].FRI.split('|')[0]);
							$("#T-FRI-"+row).text(result.RESULT_DATA[row].FRI.split('|')[1]);
							$("#DAY-SAT-"+row).text(result.RESULT_DATA[row].SAT.split('|')[0]);
							$("#T-SAT-"+row).text(result.RESULT_DATA[row].SAT.split('|')[1]);
						}
						
					},
					error : function(request, status, error) {        
						console.log(error);
					}
				});
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
		<div id="schedule">
			<div>
				<button type="button" id="s1" onclick="javascript:schedule();"><span><strong>조회</strong></span></button>
			</div>
			<div>
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
		
			<div class="div-table-row">
				<div class="div-table-cell">
				<p id="DAY-SUN-0"></p>
				<span id="T-SUN-0"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-MON-0"></p>
				<span id="T-MON-0"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-TUE-0"></p>
				<span id="T-TUE-0"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-WED-0"></p>
				<span id="T-WED-0"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-THU-0"></p>
				<span id="T-THU-0"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-FRI-0"></p>
				<span id="T-FRI-0"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-SAT-0"></p>
				<span id="T-SAT-0"></span>
				</div>
			</div>
			
			<div class="div-table-row">
				<div class="div-table-cell">
				<p id="DAY-SUN-1"></p>
				<span id="T-SUN-1"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-MON-1"></p>
				<span id="T-MON-1"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-TUE-1"></p>
				<span id="T-TUE-1"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-WED-1"></p>
				<span id="T-WED-1"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-THU-1"></p>
				<span id="T-THU-1"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-FRI-1"></p>
				<span id="T-FRI-1"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-SAT-1"></p>
				<span id="T-SAT-1"></span>
				</div>
			</div>

			<div class="div-table-row">
				<div class="div-table-cell">
				<p id="DAY-SUN-2"></p>
				<span id="T-SUN-2"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-MON-2"></p>
				<span id="T-MON-2"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-TUE-2"></p>
				<span id="T-TUE-2"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-WED-2"></p>
				<span id="T-WED-2"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-THU-2"></p>
				<span id="T-THU-2"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-FRI-2"></p>
				<span id="T-FRI-2"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-SAT-2"></p>
				<span id="T-SAT-2"></span>
				</div>
			</div>

			<div class="div-table-row">
				<div class="div-table-cell">
				<p id="DAY-SUN-3"></p>
				<span id="T-SUN-3"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-MON-3"></p>
				<span id="T-MON-3"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-TUE-3"></p>
				<span id="T-TUE-3"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-WED-3"></p>
				<span id="T-WED-3"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-THU-3"></p>
				<span id="T-THU-3"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-FRI-3"></p>
				<span id="T-FRI-3"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-SAT-3"></p>
				<span id="T-SAT-3"></span>
				</div>
			</div>

			<div class="div-table-row">
				<div class="div-table-cell">
				<p id="DAY-SUN-4"></p>
				<span id="T-SUN-4"></span>
				<p id="ADD-SUN-4" class="arrow_box">말풍선 등장!</p>
				</div>
				<div class="div-table-cell">
				<p id="DAY-MON-4"></p>
				<span id="T-MON-4"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-TUE-4"></p>
				<span id="T-TUE-4"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-WED-4"></p>
				<span id="T-WED-4"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-THU-4"></p>
				<span id="T-THU-4"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-FRI-4"></p>
				<span id="T-FRI-4"></span>
				</div>
				<div class="div-table-cell">
				<p id="DAY-SAT-4"></p>
				<span id="T-SAT-4"></span>
				</div>
			</div>
			
		</div>


	</body>
</html>