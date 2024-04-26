<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcom 테스트</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<script src="/static/js/comm/jquery-3.7.1.js"></script>
	</head>
<body>

<div data-role="header" data-position="inline" data-theme="g">
	<h1>Welcom 테스트 페이지</h1>
	<img src="/static/images/cmmn/menu-hambuger.png">
	<ul>
		<li>
			<a href="<c:url value="/index.jsp" />" data-ajax="false" data-icon="back" class="ui-btn-right">index</a>
		</li>
		<li>
			<a href="<c:url value="/baseList.do" />" data-ajax="false" data-icon="back" class="ui-btn-right">기본리스트</a>
		</li>
		<li>
			<a href="<c:url value="/urlToView/board/boardList.do" />" data-ajax="false" data-icon="back" class="ui-btn-right">urltoView</a>
		</li>
	</ul>
</div>  

</body>
</html>