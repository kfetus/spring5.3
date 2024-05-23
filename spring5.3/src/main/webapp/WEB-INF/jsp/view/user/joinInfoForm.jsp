<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>회원가입</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	</head>

	<style>
		button {
			top:50%;
			background-color:#a0345d;
			color: #fff;
			border:none;
			border-radius:10px;
			padding:5px;
			min-width: 50px;
			font-size: 14px;
		}
	</style>	

	<script>
		function dupCheck(){
			if( !$('#userId').val() ) {
				alert('ID 입력');
				$('#userId').focus();
				return;
			}
			if( !$('#userPass').val() ) {
				alert('Pass 입력');
				$('#userPass').focus();
				return;
			}
			$.ajax({
				type : 'post',
				url : '<c:url value="/checkDupId.do" />',
				async : true,
				dataType : 'text',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'userId':$('#userId').val(), 'userPass':$('#userPass').val()}),
				success : function(result) {
					console.log(result);
					let retData = JSON.parse(result);
					
					if( retData.RESCODE === '0000' ) {
						if( retData.RESULT_STATE === 'FALSE' ) {
							alert('사용가능해');
							$('#checkStatus').val('Y');
						} else {
							alert('중복된 값이 있다');
							$('#checkStatus').val('N');
						}
					} else {
						alert(retData.RESMSG);
						$('#checkStatus').val('N');
					}
				},
				error : function(request, status, error) {        
					console.log(error);
					console.log(status);
				
				}
			});
		}
		
		function joinUs(){
			if( !$('#userId').val() ) {
				alert('ID 입력');
				$('#userId').focus();
				return;
			}
			if( !$('#userPass').val() ) {
				alert('Pass 입력');
				$('#userPass').focus();
				return;
			}

			if( !$('#userName').val() ) {
				alert('이름 입력');
				$('#userName').focus();
				return;
			}

			if( !$('#hpNo').val() ) {
				alert('핸드폰번호 입력');
				$('#hpNo').focus();
				return;
			}

			if( !$('#grade').val() ) {
				alert('역활 선택');
				$('#grade').focus();
				return;
			}
			
			if( 'Y' != $('#checkStatus').val() ) {
				alert('중복체크부터 하시요');
				$('#userId').focus();
				return;
			}
			
			$.ajax({
				type : 'post',
				url : '<c:url value="/joinUserOne.do" />',
				async : true,
				dataType : 'text',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'userId':$('#userId').val(), 'userPass':$('#userPass').val(), 'userName':$('#userName').val() 
										,'grade':$('#grade').val(), 'hpNo':$('#hpNo').val(), 'email':$('#email').val()
										, 'zoneCode':$('#zoneCode').val(), 'roadAddr':$('#roadAddr').val(), 'detailAddr':$('#detailAddr').val()}),
				success : function(result) {
					console.log(result);
					let retData = JSON.parse(result);
					if(retData.RESCODE === '0000') {
						alert('회원가입 완료');
						window.location.href = "/welcome.jsp";
					} else {
						alert('회원가입 실패');
					}
				},
				error : function(error, status) {        
					console.log(error);
					console.log(status);
					alert('회원가입 실패');
				}
			});
		}
		
		function daumPostCode() {
	        let daumPost = new daum.Postcode({
	            oncomplete: function(data) {
	            	console.log(data);
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var roadAddr = data.roadAddress; // 도로명 주소 변수
	                var extraRoadAddr = ''; // 참고 항목 변수

	                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                    extraRoadAddr += data.bname;
	                }
	                // 건물명이 있고, 공동주택일 경우 추가한다.
	                if(data.buildingName !== '' && data.apartment === 'Y'){
	                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                if(extraRoadAddr !== ''){
	                    extraRoadAddr = ' (' + extraRoadAddr + ')';
	                }

	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('zoneCode').value = data.zonecode;
	                document.getElementById("roadAddr").value = roadAddr;
/*	                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
	                
	                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
	                if(roadAddr !== ''){
	                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
	                } else {
	                    document.getElementById("sample4_extraAddress").value = '';
	                }

	                var guideTextBox = document.getElementById("guide");
	                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
	                if(data.autoRoadAddress) {
	                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
	                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
	                    guideTextBox.style.display = 'block';

	                } else if(data.autoJibunAddress) {
	                    var expJibunAddr = data.autoJibunAddress;
	                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
	                    guideTextBox.style.display = 'block';
	                } else {
	                    guideTextBox.innerHTML = '';
	                    guideTextBox.style.display = 'none';
	                }
*/
	            }
	        }).open();
	        
	    }		
	</script>


<body>
		<header>
			<div>
				<h1>사이트 가입</h1>
			</div>
		</header>
		<main>
	       	<div>
				<fieldset>
					<legend>기본 필수 정보</legend>
					<ul>
						<li>
							<label for="userId">ID</label>
							<input type="text" name="userId" id="userId" placeholder="ID">
							<input type="hidden" name="checkStatus" id="checkStatus" value="">
							<button type="button" id="checkBtn" onclick="javascript:dupCheck();"><strong>중복체크</strong></button>
						</li>
						<li>
							<label for="userPass">password</label>
							<input type="password" name="userPass" id="userPass">
						</li>
						<li>
							<label for="userName">이름</label>
							<input type="text" name="userName" id="userName" placeholder="이름">
						</li>
						<li>
							<label for="hpNo">휴대폰번호</label>
							<input type="text" name="hpNo" id="hpNo" placeholder="-없이 입력">
						</li>
						<li>
							<label for="grade">역활</label>
							<select name="grade" id="grade">
								<option value="">선택</option>
								<option value="COMPA">입점업체</option>
								<option value="PHARM">약국</option>
								<option value="WHOLE">도매자</option>
								<option value="ADMIN">관리자</option>
							</select>
						</li>
					</ul>
				</fieldset>
				<fieldset>
					<legend>추가 정보</legend>
					<ul>
						<li>
							<label for="email">이메일주소</label>
							<input type="text" name="email" id="email">
						</li>
						<li>
							<label for="zoneCode">우편번호</label>
							<input type="text" name="zoneCode" id="zoneCode">
							<button type="button" onclick="javascript:daumPostCode();">검색</button>
						</li>
						<li>
							<label for="roadAddr">도로명주소</label>
							<input type="text" name="roadAddr" id="roadAddr">
						</li>
						<li>
							<label for="detailAddr">상세주소</label>
							<input type="text" name="detailAddr" id="detailAddr">
						</li>
					</ul>
				</fieldset>
	       	</div>
			<div style="float:right">
				<button type="button" onclick="javascript:joinUs();"><strong>가입</strong></button>
			</div>
		</main>

		<footer>
			바닥
		</footer>
</body>
</html>