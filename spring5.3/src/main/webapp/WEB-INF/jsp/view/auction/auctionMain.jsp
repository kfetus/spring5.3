<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
		
		const moveTest =() => {
			window.location.href = "/javaxWebsocketSample.do";
		}
		
		const websocketTest = () => {

			// 웹소켓 연결. 브라우저에서 도메인으로 접속하면 도메인으로. ip로 접속했으면 ip로 해야 함. 도메인(localhost)으로 연결하고 ip로 WebSocket를 연결하면 Session이 null로 처리됨.
			// 현재 도메인이 없으므로 핸폰 테스트를 위해서 아이피로 접속하게 함
			//const socket = new WebSocket('ws://localhost:8080/auctionWebsocket.do');
			//핸드폰이나 기타 다른 피씨에서 테스트 활 경우에는 여기 IP를 서버수행 아이피로 해야 한다. session 유지를 위해서 브라우저 주소도 아이피로 연결
			const socket = new WebSocket('ws://192.168.0.16:8080/auctionWebsocket.do');
			let entryNumber = '';
			
			socket.onopen = (Event) => {
				$("#connStatus").html("접속 중");
				console.log(Event);
			};
			
			socket.onmessage = (getData) => {
				console.log(getData.data);
				var message = JSON.parse(getData.data);
				entryNumber = message.entryNumber;
				$("#entryNumber").html(message.entryNumber);
				$("#auctionState").html(message.auctionState);
				$("#competePeopleNum").html(message.competePeopleNum);
				$("#hasRight").html(message.hasRight+'');
				$("#minAuctionMoney").html(message.minAuctionMoney);
				$("#hopeAuctionMoney").html(message.hopeAuctionMoney);
				$("#nowAuctionMoney").html(message.nowAuctionMoney);
				$("#auctionCarInfo").html(message.auctionCarInfo);
				$("#imgSrc").html(message.imgSrc);
				$("#performanceCheckList").html(message.performanceCheckList);
				$("#evaluationGrade").html(message.evaluationGrade);
				$("#successfulBidYN").html(message.successfulBidYN);
				$("#message").html(message.message);
			};
			
			socket.onclose = (getData) =>{
				$("#connStatus").html("접속 종료");
				console.log(getData);
			};
			
			socket.onerror = function(getData) {
				$("#message").html(getData);
			};
			
			$(document).on('click','#btn1',function() {
				var bidMessage = {};
				bidMessage.entryNumber = entryNumber;
//				bidMessage.message = $("#message").html();
				socket.send(JSON.stringify(bidMessage));
			});
		}
		
		websocketTest();
		
	</script>

	</head>
<body>
	<div>
		<div>
			경매 메인.<br>
			사용자:<span id="userId">asdfasfsdf</span><br>
			접속상태:<span id="connStatus"></span>
		</div>
		<div>
			<ul>
				<li>출품번호:<span style="color: red;" id="entryNumber"></span></li>
				<li>경매상태(준비,시작,경쟁,낙찰):<span style="color: red;"  id="auctionState"></span></li>
				<li>신호등:<span style="color: red;"  id="competePeopleNum"></span></li>
				<li>권리여부:<span style="color: red;"  id="hasRight"></span></li>
				<li>입찰시작가:<span style="color: red;"  id="minAuctionMoney"></span></li>
				<li>희망낙찰가:<span style="color: red;"  id="hopeAuctionMoney"></span></li>
				<li>현재 입찰가:<span style="color: red;"  id="nowAuctionMoney"></span></li>
				<li>응찰버튼:
					<div>
						<button id="btn1">응찰</button>
						<button onClick="javascript:moveTest()">moveTest</button>
					</div>
				</li>
				<li>차량간단정보(차량번호,년식,기어,연료,주행거리,자가또는법인):<span style="color: red;"  id="auctionCarInfo"></span></li>
				<li>차량이미지 => 응찰가격이 희망가격에 도달하게 되면 이미지 주위가 번쩍임. 낙찰, 유찰 결과 보여주기:<span style="color: red;"  id="imgSrc"></span></li>
				<li>차량수리내역(성능점검) 이미지:<span style="color: red;"  id="performanceCheckList"></span></li>
				<li>차량 평가 급수:<span style="color: red;"  id="evaluationGrade"></span></li>
				<li>낙찰여부:<span style="color: red;"  id="successfulBidYN"></span></li>
				<li>서버 메세지:<span style="color: red;"  id="message"></span></li>
			</ul>
		</div>
	</div>
	
</body>
</html>