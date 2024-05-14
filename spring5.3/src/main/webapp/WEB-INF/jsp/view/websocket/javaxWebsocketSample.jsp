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
	    socket = new WebSocket('ws://localhost:8080/auctionWebsocket.do');
	    //핸드폰이나 기타 다른 피씨에서 테스트 활 경우에는 여기 IP를 서버수행 아이피로 해야 한다. localhost 안됨
//	    socket = new WebSocket('ws://192.168.0.7:8080/auctionWebsocket.do');
	    socket.onopen = onOpen;
	    socket.onmessage = onMessage;
	    socket.onclose = onClose;
	    socket.onerror = function(message) {
	    	document.getElementById("msg").innerHTML = message;
	    };
	    		    
	});

    function send(){ 
    	
    	var bidMessage = {};
    	bidMessage.entryNumber = '1233';
    	bidMessage.message = document.getElementById("sendMessage").value;
    	socket.send(JSON.stringify(bidMessage));
    	
//    	var msg = document.getElementById("sendMessage").value;
//      socket.send(msg);
    }

	function onOpen() {
		document.getElementById("socketStatus").innerHTML = "접속 중";
	}
	function onMessage(evt){
		console.log(evt);
		var message = JSON.parse(evt.data);
		
	    document.getElementById("msg").innerHTML = message.message;
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
	function onClose(e) {
		document.getElementById("socketStatus").innerHTML = "접속 종료";
		console.log(e);
	}
	</script>
</body>
</html>