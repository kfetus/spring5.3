<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>게시판</title>
	<meta name="viewport"
		content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
	<%-- 엑셀 다운로드시 필요 js . 현재 csv 는 상관 없음
	<script lang="javascript" src="https://cdn.sheetjs.com/xlsx-0.20.2/package/dist/xlsx.full.min.js"></script>
	--%>
	<script src="<c:url value="/static/js/toastgrid/tui-grid.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-grid.css" />" />

	<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>

	<script>
	
		function baseSearch(wantPageNo) {
			let pagePerCnt = 9;
			$.ajax({
				type : 'post',
				url : '/boardList.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'nowPage':wantPageNo, 'pageListCnt':pagePerCnt}),    
				success : function(result) {
					console.log(result);
					pageGrid.resetData(result.RESULT_LIST);
					fn_makePaging(wantPageNo,result.RESULT_TOTAL_CNT,pagePerCnt,'paging','baseSearch');
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
	
		let gridData = [
			{
				SEQ : 549731,
				TITLE : 'Birdy',
				REG_DT : '2016.03.26',
				CODE_NAME : 'Deluxe',
				CATEGORY : '1',
			}, 
			{
				SEQ : 436461,
				TITLE : 'Ed Sheeran',
				REG_DT : '2014.06.24',
				CODE_NAME : 'Deluxe',
				CATEGORY : '1',
			},
		];
		
		$(function() {
			pageGrid = new tui.Grid({
	<%--			el : document.getElementById('toastGrid'),--%>
				el : $("#toastGrid")[0],
	//			data : gridData,
				scrollX : false,
				scrollY : false,
				rowHeaders: [
					{
		                type: 'rowNum',
		                header: "순번",
		                width: 50,
	            	}
				],
				columns : [ 
					{
						header : '글 번호',
						name : 'SEQ',
						sortable: true,			<%-- 정렬 여부 --%>
					},
					{
						header : '카테고리',
						name : 'CATEGORY',
						sortable: true,			<%-- 정렬 여부 --%>
					},
					{
						header : '카테고리명',
						name : 'CODE_NAME',
						sortable: true,			<%-- 정렬 여부 --%>
					},
					{
						header : '게시글 제목',		<%-- 헤더 명 --%>
						name : 'TITLE',			<%-- data 키값 --%>
						sortable: true,			<%-- 정렬 여부 --%>
						resizable: true,		<%-- 컬럼 크기 조절 여부 --%>
						editor: 'text',			<%-- edit 여부 --%> 					
					}, 
					{
						header : '작성 날짜',
						name : 'REG_DT',
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
			<button type="button" id="s1" onclick="javascript:fn_makePaging(3,76,10,'paging','baseSearch');">
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