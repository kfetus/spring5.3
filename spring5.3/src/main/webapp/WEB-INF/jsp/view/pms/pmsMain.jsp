<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>일정관리</title>
	<meta name="viewport"
		content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />


	<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-date-picker.css" />" />
	<script src="<c:url value="/static/js/toastgrid/tui-date-picker.js" />"></script>

	<%-- 엑셀 다운로드시 필요 js . 현재 csv 는 상관 없음
	<script lang="javascript" src="https://cdn.sheetjs.com/xlsx-0.20.2/package/dist/xlsx.full.min.js"></script>
	--%>
	<script src="<c:url value="/static/js/toastgrid/tui-grid.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-grid.css" />" />

	<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>

	<script>
		var DatePicker = tui.DatePicker;
		
		DatePicker.localeTexts['ko'] = {
			titles: {
				DD: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
				D: ['일', '월', '화', '수', '목', '금', '토'],
				MMM: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
				MMMM: ['1월', '2월', '3월', '4월', '5월', '6월','7월', '8월', '9월', '10월', '11월', '12월']
			},
			titleFormat: 'yyyy년 MMM',
			todayFormat: 'yyyy년 MMM d일 (DD)'
		};
		
		function saveTable() {
			alert('저장');
			if(pageGrid.getModifiedRows().updatedRows.length > 0) {
				for(var i = 0 ; i < pageGrid.getModifiedRows().updatedRows.length ; i++) {
					console.log(pageGrid.getModifiedRows().updatedRows[i]);
				}
			}
		}



		function addTableRow() {
			pageGrid.appendRow({CODE: "WEB",DUE_DT: fn_getTodayYYYYMMDD(),FIN_DT: null, MASTER_NAME: "${userNm}",MENU_DEPTH_1: "",MENU_NAME: "",PROG_FILE_NM: "",START_DT: fn_getTodayYYYYMMDD(), STATE: "R"});
//			pageGrid.appendRow({CODE: "WEB",DUE_DT: fn_getTodayYYYYMMDD(),FIN_DT: null, MASTER_NAME: "홍길동",MENU_DEPTH_1: "",MENU_NAME: "",PROG_FILE_NM: "",START_DT: fn_getTodayYYYYMMDD(), STATE: "R"},{at:7});
		}


		let pageGrid;
		function pmsSearch(wantPageNo) {
			let pagePerCnt = 9;
			$.ajax({
				type : 'post',
				url : '/pmsList.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'nowPage':wantPageNo, 'pageListCnt':pagePerCnt, 'menuName' : $("#menuName").val() ,'masterName': $("#masterName").val()}),    
				success : function(result) {
					console.log(result);
					if(result.RESULT_SIZE == 0) {
						pageGrid.clear();
					} else {
						pageGrid.resetData(result.RESULT_LIST);
					}
					fn_makePaging(wantPageNo,result.RESULT_TOTAL_CNT,pagePerCnt,'paging','pmsSearch');
				},
				error : function(request, status, error) {        
					console.log(error);
				}
			});
		}
		
		$(function() {
			pageGrid = new tui.Grid({
				el : $("#toastGrid")[0],
				scrollX : false,
				scrollY : false,
				columns : [ 
/*					{
						header : '순번',
						name : 'SEQ',
						width : 50,
						sortable: true,			<%-- 정렬 여부 --%>
					},
*/					{
						header : '유형',
						name : 'CODE',
						width : 100,
						sortable: true,
						formatter: 'listItemText',
						editor: {
							type: 'select',
							options: {
								listItems: [
									{ text: '웹프로그램', value: 'WEB' },
									{ text: '배치프로그램', value: 'BATCH' },
									{ text: '앱프로그램', value: 'APP' }
								]
							}
						}
					},
					{
						header : '메뉴1뎁스',
						name : 'MENU_DEPTH_1',
						width : 80,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '메뉴2뎁스',
						name : 'MENU_DEPTH_2',
						width : 130,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '메뉴3뎁스',
						name : 'MENU_DEPTH_3',
						width : 100,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '프로그램파일명',
						name : 'PROG_FILE_NM',
						sortable: true,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '프로그램명',
						name : 'MENU_NAME',
						width : 150,
						sortable: true,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '상태',			<%-- 헤더 명 --%>
						name : 'STATE',		<%-- data 키값 --%>
						width : 100,				<%-- 컬럼 width --%>
						sortable: true,			<%-- 정렬 여부 --%>
						resizable: true,		<%-- 컬럼 크기 조절 여부 --%>
						formatter: 'listItemText',
						editor: {
							type: 'select',
							options: {
								listItems: [
									{ text: '준비', value: 'R' },
									{ text: '진행중', value: 'I' },
									{ text: '완료', value: 'E' },
									{ text: '지연', value: 'D' }
								]
							}
						}
					}, 
					{
						header : '담당자',
						width : 70,
						name : 'MASTER_NAME',
						editor: 'text',			<%-- edit 여부 --%>
					}, 
					{
						header : '시작일',
						name : 'START_DT',
						width : 80,
						editor: {
							type: 'datePicker',
							options: {
								format: 'yyyy-MM-dd',
								language: 'ko'
							}
						}						
					}, 
					{
						header : '목표예정일',
						name : 'DUE_DT',
						width : 80,
						editor: {
							type: 'datePicker',
							options: {
								format: 'yyyy-MM-dd',
								language: 'ko'
							}
						}						
					}, 
					{
						header : '완료일',
						name : 'FIN_DT',
						width : 80,
						editor: {
							type: 'datePicker',
							options: {
								format: 'yyyy-MM-dd',
								language: 'ko'
							}
						}						
					}, 
				],
			});
	
			console.log('document.onload()');
			pmsSearch(1);
		})
	</script>

</head>
<body>
	<div>
		<div>
			<input name="menuName" id="menuName" value="" placeholder="프로그램명" onkeyup="if(window.event.keyCode==13){pmsSearch('1')}">
			<input name="masterName" id="masterName" value="" placeholder="담당자" onkeyup="if(window.event.keyCode==13){pmsSearch('1')}">
			<button type="button" onclick="javascript:pmsSearch(1);"><span><strong>조회</strong></span></button>
			<button type="button" onclick="javascript:saveTable();"><span><strong>저장</strong></span></button>
			<button type="button" onclick="javascript:addTableRow();"><span><strong>추가</strong></span></button>
		</div>

		<div>
			<div id="toastGrid"></div>
			<div id="paging" ></div>

		</div>
	</div>
</body>
</html>