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

						for ( let row = 0 ; row < result.RESULT_DATA.length ; row++ ) {
							console.log(result.RESULT_DATA[row]);

							makeDiv(result.RESULT_DATA,row,'SUN');
							makeDiv(result.RESULT_DATA,row,'MON');
							makeDiv(result.RESULT_DATA,row,'TUE');
							makeDiv(result.RESULT_DATA,row,'WED');
							makeDiv(result.RESULT_DATA,row,'THU');
							makeDiv(result.RESULT_DATA,row,'FRI');
							makeDiv(result.RESULT_DATA,row,'SAT');
/*							
							let SUNDay = result.RESULT_DATA[row].SUN.split('|')[0];
							if ( SUNDay != '' ) {
								$("#DAY-SUN-"+row).html(SUNDay);	
								$("#divCell_"+row+"_SUN").click(function () {
									divCellClick(row+'_SUN');
								});
							}							
							let SUNtext = result.RESULT_DATA[row].SUN.split('|')[1];
							if (!!SUNtext) {
								if(SUNtext.indexOf('@') == -1 ) {
									$("#T-SUN-"+row).html(SUNtext);
									$("#ADD-SUN-"+row).html(SUNtext);
								} else {
									$("#T-SUN-"+row).html(SUNtext.replace('@','<BR>'));
									$("#ADD-SUN-"+row).html(SUNtext.replace('@','<BR>'));
								}
							}
							$("#DAY-MON-"+row).html(result.RESULT_DATA[row].MON.split('|')[0]);
							$("#T-MON-"+row).html(result.RESULT_DATA[row].MON.split('|')[1]);
							$("#ADD-MON-"+row).html(result.RESULT_DATA[row].MON.split('|')[1]);
							$("#DAY-TUE-"+row).html(result.RESULT_DATA[row].TUE.split('|')[0]);
							$("#T-TUE-"+row).html(result.RESULT_DATA[row].TUE.split('|')[1]);
							$("#ADD-TUE-"+row).html(result.RESULT_DATA[row].TUE.split('|')[1]);
							$("#DAY-WED-"+row).html(result.RESULT_DATA[row].WED.split('|')[0]);
							$("#T-WED-"+row).html(result.RESULT_DATA[row].WED.split('|')[1]);
							$("#ADD-WED-"+row).html(result.RESULT_DATA[row].WED.split('|')[1]);
							$("#DAY-THU-"+row).html(result.RESULT_DATA[row].THU.split('|')[0]);
							$("#T-THU-"+row).html(result.RESULT_DATA[row].THU.split('|')[1]);
							$("#ADD-THU-"+row).html(result.RESULT_DATA[row].THU.split('|')[1]);
							$("#DAY-FRI-"+row).html(result.RESULT_DATA[row].FRI.split('|')[0]);
							$("#T-FRI-"+row).html(result.RESULT_DATA[row].FRI.split('|')[1]);
							$("#ADD-FRI-"+row).html(result.RESULT_DATA[row].FRI.split('|')[1]);
							$("#DAY-SAT-"+row).html(result.RESULT_DATA[row].SAT.split('|')[0]);
							$("#T-SAT-"+row).html(result.RESULT_DATA[row].SAT.split('|')[1]);
							$("#ADD-SAT-"+row).html(result.RESULT_DATA[row].SAT.split('|')[1]);
							//인서트 업데이트
*/							
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
			
			function makeDiv(data,rowIndex,dayName) {
				let day = data[rowIndex][dayName].split('|')[0];
				if ( day != '' ) {
					$("#DAY-"+dayName+"-"+rowIndex).html(day);	
					$("#divCell_"+rowIndex+"_"+dayName).click(function () {
						divCellClick(rowIndex+'_'+dayName,data[rowIndex][dayName].split('|'));
					});
				}							
				let text = data[rowIndex][dayName].split('|')[1];
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

			function divCellClick(cell,data) {
				console.log(cell);
				alert(cell+""+data);
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
			
			<div class="div-table-row">
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

			<div class="div-table-row">
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

			<div class="div-table-row">
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

			<div class="div-table-row">
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
			
		</div>


	</body>
</html>