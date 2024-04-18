<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>javax.websocket</title>
</head>
<body>
	hello javax.websocket <br/>
	<b>접속 상태 : </b><span id="socketStatus">연결 중</span><br/>
	<input type="text" id="sendMessage"/>
	<button onclick="send()">send</button><br >

	<b>전송 받은 메시지 : </b><span id="msg"></span><br >
	<b>최근 전송 받은 시간 : </b><span id="msgTime"></span>
	
	<script>
	var socket;
	
	document.addEventListener("DOMContentLoaded", function(){
	    // 웹소켓 연결
	    socket = new WebSocket('ws://localhost:8080/websocket.do' + name);
	    socket.onopen = onOpen;
	    socket.onmessage = onMessage;
	    socket.onclose = onClose;
	    socket.onerror = function(message) {
	    	document.getElementById("msg").innerHTML = message;
	    };
	    		    
	});

    function send(){ 
    	var msg = document.getElementById("sendMessage").value;
        socket.send(msg);
    }

	function onOpen() {
		document.getElementById("socketStatus").innerHTML = "접속 중";
	}
	function onMessage(evt){
	    document.getElementById("msg").innerHTML = evt.data;
	    document.getElementById("msgTime").innerHTML = getNowString();
	}
	function getNowString() {
		var today = new Date();   
		var year = today.getFullYear(); 
		var month = today.getMonth() + 1;
		var date = today.getDate();
		var hours = today.getHours(); 
		var minutes = today.getMinutes(); 
		var seconds = today.getSeconds();
		
		return year + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds;
	}	
	function onClose() {
		document.getElementById("socketStatus").innerHTML = "접속 종료";
	}
	</script>
</body>
</html>