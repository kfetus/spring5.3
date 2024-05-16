<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>메뉴관리</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>
		
		<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-date-picker.css" />" />
		<script src="<c:url value="/static/js/toastgrid/tui-date-picker.js" />"></script>
		<script src="<c:url value="/static/js/toastgrid/tui-grid.js" />"></script>
		<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-grid.css" />" />
	</head>

	<style>
		div {

		}

	</style>	

	<script>
		let mainGrid;
		
		function fn_makeGrid() {
<%-- 일반적인 foreach 문으로 돌릴때 받아서 처리하는 값
			let initData = "<c:out value="${RESULT_LIST}"/>";
			console.log(initData);
--%>

			<c:choose>
				<c:when test="${'0' eq RESULT_TOTAL_CNT }">
				let jsonListMapData = JSON.parse('[]');
				</c:when>
				<c:otherwise>
				let jsonListMapData = JSON.parse('${RESULT_JSON_LIST}');
				</c:otherwise>
			</c:choose>
			
			mainGrid = new tui.Grid({
				el : $("#mainGrid")[0],
				scrollX : true,
				scrollY : false,
				data: jsonListMapData,
				rowHeaders: [
					{ type: 'checkbox', header: '선택'}
				],
				columns : [ 
					{
						header : '메뉴  URL',
						name : 'URL',
						width : 80,
						editor: 'text',
					},
					{
						header : '시스템 그룹',
						name : 'SYS_GROUP',
						width : 130,
						editor: 'text',
					},
					{
						header : '부모 URL',
						name : 'PARENT_URL',
						width : 100,
						editor: 'text',
					},
					{
						header : '메뉴명',
						name : 'MENU_NAME',
						sortable: true,
						editor: 'text',
					},
					{
						header : '레벨',
						name : 'MENU_LEVEL',
						width : 150,
						sortable: true,
						editor: 'text',
					},
					{
						header : '정렬순서',
						name : 'ORDER_NUM',
						width : 100,
					}, 
					{
						header : '사용여부',
						width : 70,
						name : 'USE_YN',
						editor: 'text',			<%-- edit 여부 --%>
					}, 
					{
						header : '링크여부(일반중간메뉴)',
						name : 'LINK_YN',
						width : 80,
					}, 
				],
			});
		}

		$(function() {
			fn_makeGrid();
			let searchCnt = ${RESULT_TOTAL_CNT};
			if(searchCnt > 0) {
				fn_makePaging(${nowPage}+1,searchCnt,${pageListCnt},'paging','pagingMove');
			}
			console.log('document.onload()');
		})
		
		function pagingMove(wantPageNo) {
			$("#nowPage").val(wantPageNo);
			$("#mainForm").submit();
		}
		
		function formSubmit() {
//			$("#mainForm").attr("action", "/menu/menuList.do");
//			$("#mainForm").attr("method", "post");
			$("#nowPage").val(0);
			$("#mainForm").submit();
			return false;
		}
		
		function fn_Login(){
			$.ajax({
				type : 'post',
				url : '<c:url value="/restLogin.do" />',
				async : true,
				dataType : 'text',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'userId':'auto', 'userPass':'auto1!'}),
				success : function(result) {
					let retData = JSON.parse(result);
					if(retData.RESCODE === '0000') {
						window.location.href = "/menu/menuList.do";
					} else {
						alert('로그인 실패');
					}
				},
				error : function(request, status, error) {        
					console.log(error);
					console.log(status);
				}
			});
		}
	</script>


<body>
<form action="/menu/menuList.do" method="post" id="mainForm" name="mainForm">
<input type="hidden" name="nowPage" id="nowPage" value="">
	<div>
		<div style="border: 1px solid #bcbcbc;">
			<c:out value="${userNm}"/>
		</div>
		<div>
			<select name="sysGroup" id="sysGroup">
				<option value="" <c:if test="${'' eq sysGroup}">selected="selected"</c:if>>선택</option>
				<option value="MOW" <c:if test="${'MOW' eq sysGroup}">selected="selected"</c:if>>MOW</option>
				<option value="PCW" <c:if test="${'PCW' eq sysGroup}">selected="selected"</c:if>>PCW</option>
				<option value="APP" <c:if test="${'APP' eq sysGroup}">selected="selected"</c:if>>APP</option>
				<option value="CAR" <c:if test="${'CAR' eq sysGroup}">selected="selected"</c:if>>CAR</option>
			</select>
			<input name="menu1Depth" id="menu1Depth" value="" placeholder="메뉴1뎁스" onkeyup="if(window.event.keyCode==13){pmsSearch('1')}">
			<input name="masterName" id="masterName" value="" placeholder="담당자" onkeyup="if(window.event.keyCode==13){pmsSearch('1')}">
			 
			<button type="button" onclick="javascript:formSubmit();"><span><strong>조회</strong></span></button>
			<button type="button" onclick="javascript:fn_Login();"><span><strong>로그인</strong></span></button>
		</div>
		<div>
			<div id="mainGrid"></div>
			<div id="paging"></div>
		</div>

	</div>
</form>
</body>
</html>