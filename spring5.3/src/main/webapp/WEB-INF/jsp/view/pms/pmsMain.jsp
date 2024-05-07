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

	
	
		function baseSearch(wantPageNo) {
			let pagePerCnt = 9;
			$.ajax({
				type : 'post',
				url : '/pmsList.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'nowPage':wantPageNo, 'pageListCnt':pagePerCnt}),    
				success : function(result) {
					console.log(result);
					pageGrid.resetData(result.RESULT_LIST);
					makePaging(wantPageNo,result.RESULT_TOTAL_CNT,pagePerCnt,'paging');
				},
				error : function(request, status, error) {        
					console.log(error);
				}
			});
		}
		

		function saveExcel() {
			pageGrid.export('csv');
		}
		
		let pageGrid;

		$(function() {
			pageGrid = new tui.Grid({
				el : $("#toastGrid")[0],
	//			data : gridData,
				scrollX : false,
				scrollY : false,
/*				rowHeaders: [
					{
		                type: 'rowNum',
		                header: "순번",
		                width: 50,
	            	}
				],
*/				columns : [ 
					{
						header : '유형',
						name : 'TYPE',
						sortable: true,			<%-- 정렬 여부 --%>
					},
					{
						header : '프로그램파일명',
						name : 'PROG_FILE_NM',
						sortable: true,			<%-- 정렬 여부 --%>
					},
					{
						header : '프로그램명',
						name : 'MENU_NAME',
						sortable: true,			<%-- 정렬 여부 --%>
					},
					{
						header : '상태',		<%-- 헤더 명 --%>
						name : 'STATE_NM',			<%-- data 키값 --%>
						sortable: true,			<%-- 정렬 여부 --%>
						resizable: true,		<%-- 컬럼 크기 조절 여부 --%>
						editor: 'text',			<%-- edit 여부 --%> 					
					}, 
/*개발 후 통테용					{
						header : '우선순위',
						name : 'REG_DT',
					}, 
					{
						header : '발견자',
						name : 'REG_DT',
					}, 
*/					{
						header : '담당자',
						name : 'MASTER_NAME',
					}, 
					{
						header : '시작일',
						name : 'START_DT',
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
	//		pageGrid.resetData(gridData);
			baseSearch(1);
		})
	</script>

</head>
<body>
	<div>
		<div>
			본문
			<button type="button" id="s1" onclick="javascript:baseSearch(1);">
				<span><strong>조회</strong></span>
			</button>
			<button type="button" id="s1" onclick="javascript:saveExcel();">
				<span><strong>엑셀저장</strong></span>
			</button>
			<button type="button" id="s1" onclick="javascript:makePaging(3,76,10,'paging');">
				<span><strong>페이징생성</strong></span>
			</button>
		</div>

		<div>
			<div id="toastGrid"></div>
			<div id="paging" ></div>
<%--
<div>
<span><span>first</span></span>
<span><span>prev</span></span>
<strong>1</strong>
<a href="#" >2</a>
<a href="#" >3</a>
<a href="#" >4</a>
<a href="#" ><span >next</span></a>
<a href="#" ><span >last</span></a>
</div>
--%>

		</div>
	</div>
</body>
</html>