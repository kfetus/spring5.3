<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>게시판</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
		
		<script>
		
			const boardList = () => {
				console.log('ajax start');
				
				console.log($("#listTable tr").length);
				if ( $("#listTable tr").length != 1) {
					
					while($("#listTable tr").length != 1) {
						$("#listTable > tbody:last > tr:last").remove();
					}
				}
				
				$.ajax({
					type : 'post',
					url : '/boardList.do',
					async : true,
					dataType : 'text',
	 				headers : {"Content-Type" : "application/json"},
					data : JSON.stringify( {'type1':'name', 'type2':'identity'}),    
					success : function(result) {
						console.log(result);
						let listData = JSON.parse(result); 
						console.log(listData.RESULT_LIST);

						let appendStr;

						$(listData.RESULT_LIST).each(function(index){
							appendStr += "<TR>";
							appendStr += '<TD>'+listData.RESULT_LIST[index].SEQ + '</TD>';
							appendStr += '<TD>'+listData.RESULT_LIST[index].CATEGORY + '</TD>';
							appendStr += '<TD>'+listData.RESULT_LIST[index].CODE_NAME + '</TD>';
							appendStr += '<TD>'+listData.RESULT_LIST[index].TITLE + '</TD>';
							appendStr += '<TD>'+listData.RESULT_LIST[index].BODY_TEXT + '</TD>';
							appendStr += '<TD>'+listData.RESULT_LIST[index].USER_NO + '</TD>';
							appendStr += '<TD>'+listData.RESULT_LIST[index].REG_DT + '</TD>';
							appendStr += '<TD>'+listData.RESULT_LIST[index].CNG_DT + '</TD>';
							appendStr += '</TR>';
						});
						
	                    $("#listTable").append(appendStr);						
						
					},
					error : function(request, status, error) {        
						console.log(error);
					}
				});
			}
			
			$(function() {
				boardList();
				console.log('document.onload()');
			})
		
		</script>
	
	</head>
	<body>
		<div>
			<div>
				본문
				<button type="button" id="s1" onclick="javascript:boardList();"><span><strong>조회</strong></span></button>
			</div>
		
	       	<div>
	       		<table id="listTable">
	       			<colgroup>
	       				<col width="10%"/>
	       				<col width="10%"/>
	       				<col width="15%"/>
	       				<col width="15%"/>
	       				<col width="20%"/>
	       				<col width="10%"/>
	       				<col width="10%"/>
	       				<col width="10%"/>
	       			</colgroup>
	       			<tr>
	       				<th align="center">순번</th>
	       				<th align="center">카테고리</th>
	       				<th align="center">코드명</th>
	       				<th align="center">제목</th>
	       				<th align="center">내용</th>
	       				<th align="center">등록자</th>
	       				<th align="center">등록일</th>
	       				<th align="center">변경일</th>
	       			</tr>
	       			<c:forEach var="result" items="${list}" varStatus="status">
	           			<tr>
	           				<td><c:out value="${result.CODE_TYPE}"/></td>
	           				<td><a href="#"><c:out value="${result.CODE_TYPE_NAME}"/></a></td>
	           				<td><c:out value="${result.ORDER_NUM}"/></td>
	           				<td><c:out value="${result.USE_YN}"/></td>
	           				<td><c:out value="${result.CODE}"/></td>
	           				<td><c:out value="${result.CODE_NAME}"/></td>
	           				<td><c:out value="${result.CODE_NAME}"/></td>
	           				<td><c:out value="${result.CODE_NAME}"/></td>
	           			</tr>
	       			</c:forEach>
	       		</table>
	       	</div>
		</div>
	</body>
</html>