<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>경매 메인</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />

<style>

</style>
	<script>
		const moveLocation =() => {
			window.location.href = "/javaxWebsocketSample.do";
		}
	</script>

	</head>
<body>
	<div>
		<div>
			<p>경매 메인.</p>
			<p>사용자:</p>
		</div>
		<div>
			<ul>
				<li>출품번호:</li>
				<li>경매상태(준비,시작,경쟁,낙찰):</li>
				<li>신호등:</li>
				<li>권리여부:</li>
				<li>입찰시작가 => 현재 입찰가:</li>
				<li>응찰버튼:
					<div class="position-relative">
						<button onClick="javascript:moveLocation()">socket이동</button>
					</div>
				</li>
				<li>차량간단정보(차량번호,년식,기어,연료,주행거리,자가또는법인):</li>
				<li>차량이미지 => 응찰가격이 희망가격에 도달하게 되면 이미지 주위가 번쩍임. 낙찰, 유찰 결과 보여주기:</li>
				<li>차량수리내역(성능점검) 이미지:</li>
				<li>차량 평가 급수:</li>
				<li>낙찰여부 메세지:</li>
			</ul>
		</div>
	</div>
	
</body>
</html>