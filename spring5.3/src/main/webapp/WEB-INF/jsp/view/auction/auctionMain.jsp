<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>경매 메인</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />

<style>
button {
top:50%;
background-color:#22bbca;
color: #fff;
border:none;
border-radius:10px;
padding:5px;
min-height:30px;
min-width: 120px;
font-size: 30px;
}
.circle_div {
display: flex;
}
.circle {
width: 40px;
height: 40px;
border-radius: 50%;
background-color: #bebebe;
}

</style>

<script>

const websocketTest = () => {

// 웹소켓 연결. 브라우저에서 도메인으로 접속하면 도메인으로. ip로 접속했으면 ip로 해야 함. 도메인(localhost)으로 연결하고 ip로 WebSocket를 연결하면 Session이 null로 처리됨.
// 현재 도메인이 없으므로 핸폰 테스트를 위해서 아이피로 접속하게 함
//const socket = new WebSocket('ws://localhost:8080/auctionWebsocket.do');
//핸드폰이나 기타 다른 피씨에서 테스트 활 경우에는 여기 IP를 서버수행 아이피로 해야 한다. session 유지를 위해서 브라우저 주소도 아이피로 연결
const socket = new WebSocket('ws://192.168.0.16:8080/auctionWebsocket.do');
let entryNumber = '';
let nowAuctionState = 'R';

socket.onopen = (Event) => {
$("#connStatus").html("접속 중");
console.log(Event);
};

const auctionSate = {
R:'준비',
S:'시작',
O:'낙찰',
F:'유찰',
E:'종료'
}

socket.onmessage = (getData) => {
console.log(getData.data);
var message = JSON.parse(getData.data);
entryNumber = message.entryNumber;
$("#userId").html(message.bidId);
$("#userName").html(message.userName);
$("#entryNumber").html(message.entryNumber);
$("#auctionState").html(auctionSate[message.auctionState]);
nowAuctionState = message.auctionState;
$("#competePeopleNum").html(message.competePeopleNum);

if(0 === message.competePeopleNum) {
$('#circleOne').css('backgroundColor', '#bebebe');
$('#circleTwo').css('backgroundColor', '#bebebe');
$('#circleThree').css('backgroundColor', '#bebebe');
} else if(1 === message.competePeopleNum) {
$('#circleOne').css('backgroundColor', '#99cc00');
$('#circleTwo').css('backgroundColor', '#bebebe');
$('#circleThree').css('backgroundColor', '#bebebe');
} else if(2 === message.competePeopleNum) {
$('#circleOne').css('backgroundColor', '#bebebe');
$('#circleTwo').css('backgroundColor', '#DB631F');
$('#circleThree').css('backgroundColor', '#bebebe');
} else {
$('#circleOne').css('backgroundColor', '#bebebe');
$('#circleTwo').css('backgroundColor', '#bebebe');
$('#circleThree').css('backgroundColor', '#FF0000');
}

$("#hasRight").html(message.hasRight+'');
$("#minAuctionMoney").html(message.minAuctionMoney);
$("#hopeAuctionMoney").html(message.hopeAuctionMoney);
$("#nowAuctionMoney").html(message.nowAuctionMoney);
$("#auctionCarInfo").html(message.auctionCarInfo);
$("#imgSrc").html(message.imgSrc);
$("#successfulBidYN").html(message.successfulBidYN);//Y => 이미지 주위가 번쩍임. 낙찰, 유찰 결과 보여주기
$("#performanceCheckList").html(message.performanceCheckList);
$("#evaluationGrade").html(message.evaluationGrade);
$("#message").html(message.message);

if('S' === message.auctionState) {
$('#bidBtn').attr("disabled", false);

$('#activeBidBtnSpan').show();
$('#noActiveBidBtnSpan').hide();

<c:if test="${'M' eq grade}">
$('#startBtnSpan').hide();
$('#stopBtnSpan').show();
</c:if>
//R, O, E, F
} else {
$('#bidBtn').attr("disabled", true);
$('#activeBidBtnSpan').hide();
$('#noActiveBidBtnSpan').show();

<c:if test="${'M' eq grade}">
$('#startBtnSpan').show();
$('#stopBtnSpan').hide();
</c:if>

}
};

socket.onclose = (getData) =>{
$("#connStatus").html("접속 종료");
console.log(getData);
};

socket.onerror = function(getData) {
$("#message").html(getData);
};

//$(function() {} 이렇게 선언해서 해당 ready 함수안에 $('#bidBtn').click(function...) 같이 선언하면 되나 객체방식 socket 핸들링을 위해 이벤트 핸들링을 아래와 같이 동적 이벤트로 할당 함
$(document).on('click','#bidBtn',function() {
var bidMessage = {};
bidMessage.entryNumber = entryNumber;
socket.send(JSON.stringify(bidMessage));
});
$(document).on('click','#noBidBtn',function() {
alert('경매 '+auctionSate[nowAuctionState]+' 상태');
});

<c:if test="${'M' eq grade}">
$(document).on('click','#startBtn',function() {
var bidMessage = {};
bidMessage.entryNumber = entryNumber;
bidMessage.message = 'START_MASTER';
socket.send(JSON.stringify(bidMessage));
});
$(document).on('click','#stopBtn',function() {
var bidMessage = {};
bidMessage.entryNumber = entryNumber;
bidMessage.message = 'STOP_MASTER';
socket.send(JSON.stringify(bidMessage));
});
</c:if>
}

websocketTest();



</script>

</head>
<body>
<div>
<div>
경매 메인
<c:if test="${'M' eq grade}">
<span id="startBtnSpan"><button id="startBtn">시작</button></span>
<span id="stopBtnSpan" style="display: none;"><button id="stopBtn">중지</button></span>
</c:if><br>
사용자 ID:<span id="userId"></span>사용자 이름:<span id="userName"></span><br>
접속상태:<span id="connStatus"></span>
</div>
<div>
<ul>
<li>출품번호:<span style="color: red;" id="entryNumber"></span></li>
<li>경매상태(준비,시작,낙찰,종료):<span style="color: red;font-size: 30px;" id="auctionState"></span></li>
<li>경쟁상태 신호등:<span style="color: red;"  id="competePeopleNum"></span>
<div class="circle_div">
<div id="circleOne" class="circle" ></div>
<div id="circleTwo" class="circle" ></div>
<div id="circleThree" class="circle" ></div>
</div>
</li>
<li>권리여부:<span style="color: red;"  id="hasRight"></span></li>
<li>입찰시작가:<span style="color: red;"  id="minAuctionMoney"></span></li>
<li>희망낙찰가:<span style="color: red;"  id="hopeAuctionMoney"></span></li>
<li>현재 입찰가:<span style="color: red;"  id="nowAuctionMoney"></span></li>
<li>응찰버튼:
<div>
<span id="activeBidBtnSpan"><button id="bidBtn" style="font-size: 50px;">응찰</button></span>
<span id="noActiveBidBtnSpan" style="display: none;"><button id="noBidBtn" style="font-size: 50px;">응찰</button></span>
</div>
</li>
<li>차량간단정보(차량번호,년식,기어,연료,주행거리,자가또는법인):<span style="color: red;"  id="auctionCarInfo"></span></li>
<li>차량이미지:<span style="color: red;"  id="imgSrc"></span></li>
<li>응찰가격이 희망가격을 넘어섯다. 낙찰이 되는 물건이 되었다:<span style="color: red;"  id="successfulBidYN"></span></li>
<li>차량수리내역(성능점검) 이미지:<span style="color: red;"  id="performanceCheckList"></span></li>
<li>차량 평가 급수:<span style="color: red;"  id="evaluationGrade"></span></li>
<li>서버 메세지:<span style="color: red;"  id="message"></span></li>
</ul>
</div>
</div>

</body>
</html>