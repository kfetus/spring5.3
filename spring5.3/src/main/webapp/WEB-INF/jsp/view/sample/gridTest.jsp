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
<link rel="stylesheet"
	href="<c:url value="/static/js/toastgrid/tui-grid.css" />" />


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
				makePaging(wantPageNo,result.RESULT_TOTAL_CNT,pagePerCnt);
			},
			error : function(request, status, error) {        
				console.log(error);
			}
		});
	}
	
	//nowPage가 시작. 최대 5개까지만 보여주기.
	function makePaging(nowPage,totalCnt,pagePerCnt) {
		$("#paging").empty();
		
		let showPagingCnt = 5;//페이징은 5개까지만
		let maxPagingCnt = 0;//
		
		let totalPageCnt = Math.floor(totalCnt / pagePerCnt) ;
		let remainder = totalCnt % pagePerCnt;
		if ( remainder != 0) {
			totalPageCnt += 1; 
		}
		if(nowPage > totalPageCnt) {
			nowPage = totalPageCnt;
		}
		
		if( (nowPage + showPagingCnt - 1) < totalPageCnt ) {
			maxPagingCnt = nowPage + showPagingCnt -1;
		} else {
			maxPagingCnt = totalPageCnt;
		}
		
		console.log(totalPageCnt);
		
		let pagingHtml =  '';
		if( nowPage == 1 ) {
			pagingHtml = pagingHtml + '';
		} else if ( nowPage > 1) {
			pagingHtml = pagingHtml + '<span><span onclick="baseSearch(1);"> 처음 </span></span>';
			if ( nowPage >= 2) {
				pagingHtml = pagingHtml + '<span onclick="baseSearch('+(nowPage-1)+');"><span> 이전 </span></span>';
			}
		}
		
		for(var i = nowPage ; i <= maxPagingCnt ; i++) {
			if( i == nowPage) {
				pagingHtml = pagingHtml + '<strong> '+nowPage+' </strong>';
			} else {
				pagingHtml = pagingHtml + '<a href="javascript:baseSearch('+i+');" > '+i+' </a>';
			}
		}
		if ( maxPagingCnt < totalPageCnt) {
			pagingHtml = pagingHtml + '<a href="javascript:baseSearch('+(nowPage+1)+');" ><span > 다음 </span></a>';
		}
		if ( nowPage < totalPageCnt) {
			pagingHtml = pagingHtml + '<a href="javascript:baseSearch('+totalPageCnt+');" ><span > 마지막 </span></a>';
		}
		
		$("#paging").append(pagingHtml);
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
		pageGrid.resetData(gridData);
	})
</script>

</head>
<body>
	<div>
		<div>
			본문
			<button type="button" id="s1" onclick="javascript:baseSearch('1');">
				<span><strong>조회</strong></span>
			</button>
			<button type="button" id="s1" onclick="javascript:saveExcel();">
				<span><strong>엑셀저장</strong></span>
			</button>
			<button type="button" id="s1" onclick="javascript:makePaging(3,76,10);">
				<span><strong>페이징생성</strong></span>
			</button>
		</div>

		<div>
			<div id="toastGrid"></div>
			<div id="paging"></div>
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