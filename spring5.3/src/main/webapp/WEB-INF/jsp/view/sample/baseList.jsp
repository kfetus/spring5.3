<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcom 테스트</title> 
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
		 
		<script>
		
			function fn_selectList(url){
				console.log('ddd');
							
				$.ajax({
					type : 'post',
					url : url,
					async : true,
					dataType : 'text',
	 				headers : {"Content-Type" : "application/json"},
					data : JSON.stringify( {'type1':'name', 'type2':'identity'}),    
					success : function(result) {
						console.log(result);
					},
					error : function(request, status, error) { // 결과 에러 콜백함수        
						console.log(error);
					}
				});
	
			}
			
			$(function() {
				console.log('document.onload()');
			})
			
		</script>
	</head>
	<body>
		<div>
			<div>
				본문
				<button type="button" id="s1" onclick="javascript:fn_selectList('/sampleRest.do');"><span><strong>샘플</strong></span></button>
				<button type="button" id="s2" onclick="javascript:fn_selectList('/restBaseModel.do');">기본</button>
				<button type="button" id="s2" onclick="javascript:fn_scriptTest();">스크립트테스트</button>
			</div>
		
	       	<div>
	       		<table>
	       			<colgroup>
	       				<col width="15%"/>
	       				<col width="20%"/>
	       				<col width="15%"/>
	       				<col width="15%"/>
	       				<col width="15%"/>
	       				<col width="20%"/>
	       			</colgroup>
	       			<tr>
	       				<th align="center">코드타입</th>
	       				<th align="center">코드타입명</th>
	       				<th align="center">정렬순서</th>
	       				<th align="center">사용여부</th>
	       				<th align="center">코드</th>
	       				<th align="center">코드명</th>
	       			</tr>
	       			<c:forEach var="result" items="${list}" varStatus="status">
	           			<tr>
	           				<td><c:out value="${result.CODE_TYPE}"/></td>
	           				<td><a href="#"><c:out value="${result.CODE_TYPE_NAME}"/></a></td>
	           				<td><c:out value="${result.ORDER_NUM}"/></td>
	           				<td><c:out value="${result.USE_YN}"/></td>
	           				<td><c:out value="${result.CODE}"/></td>
	           				<td><c:out value="${result.CODE_NAME}"/></td>
	           			</tr>
	       			</c:forEach>
	       		</table>
	       	</div>
		</div>
	</body>
</html>
