<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>admin</title>
</head>
<body>
	hello Admin <br/>
	<b>접속 상태 : </b><span id="socketStatus">연결 중</span><br/>
	<input type="text" id="sendMessage"/>
	<button onclick="send()">send</button>
	
	<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
	<script>
	var socket = null;
	
	document.addEventListener("DOMContentLoaded", function(){
	    // 웹소켓 연결
	    var sock = new SockJS("/echo-ws");
	    socket = sock;

		sock.onopen = onOpen;
	    sock.onclose = onClose;
	});

    function send(){ 
    	var msg = document.getElementById("sendMessage").value;
        socket.send(msg);
    }

	function onOpen() {
		document.getElementById("socketStatus").innerHTML = "접속 중";
	}
	
	function onClose() {
		document.getElementById("socketStatus").innerHTML = "접속 종료";
	}
	</script>
</body>
</html>