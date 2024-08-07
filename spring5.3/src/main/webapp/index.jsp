<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Welcom 테스트</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<script src="/static/js/comm/jquery-3.7.1.js"></script>
		<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>
	</head>


	<style>
		* {
			box-sizing: border-box;
			margin: 0;
			padding: 0;
		}
		:root {
			--padding: 60px;
		}
		body{
		    background: #62546b;
		}
		
		.box {
			position: relative;
			margin: 50px auto;
			width: 400px;
			display: flex;
			flex-direction: column;
			justify-content: center;
			padding: var(--padding);
			background-color: #8092a7;;
			border-radius: 7px;
		}
		
		.box input,
		.box button {
			padding: 15px;
			font-size: 1.2em;
			border: none;
		}
		.box input {
			margin-bottom: 25px;
		}
		.box button {
			background-color: #534c44;
			color: #547fb2;
			border-radius: 4px;
			margin-bottom: 25px;
		}
<%--	
		button {
		  margin: 0;
		  padding: 0.5rem 1rem;
		  font-size: 1rem;
		  font-weight: 400;
		  text-align: center;
		  text-decoration: none;
		  display: inline-block;
		  width: auto;
		  border: none;
		  border-radius: 4px;
		  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
		  cursor: pointer;
		  transition: 0.5s;
		  background: #0d6efd;
		  color: #ffffff;
		}
		
		button:focus {
		  background: var(--button-hover-bg-color);
		  outline: 0;
		}
--%>

	</style>	

	<script>
		function fn_Login(){
			if( !$('#userId').val() ) {
				alert('ID 입력');
				$('#userId').focus();
				return;
			}
			if( !$('#userPass').val() ) {
				alert('Password 입력');
				$('#userPass').focus();
				return;
			}
						
			$.ajax({
				type : 'post',
				url : '<c:url value="/restLogin.do" />',
				async : true,
				dataType : 'text',
					headers : {"Content-Type" : "application/json"},
					//jwt를 사용하려면 아래 헤더도 추가 token은 아래 sessionStorage에서 가져온다
					//headers:{'token':token}, 
				data : JSON.stringify( {'userId':$('#userId').val(), 'userPass':$('#userPass').val()}),
				success : function(result) {
					console.log(result);
					let retData = JSON.parse(result);
					if(retData.RESCODE === '0000') {
						//jwt 관련 access Token ,refresh token 저장이 필요하면 사용. 구현하지 않음
						fn_SetSessionStorage(G_TOKEN_KEY,retData.token);
	
						console.log(fn_GetSessionStorage(G_TOKEN_KEY));
												
						//utl to view 컨트롤러 호출
						window.location.href = "/welcome.jsp";
					} else {
						alert('로그인 실패');
					}
				},
				error : function(request, status, error) {        
					console.log(error);
					console.log(status);
				
				}
			});
		}
		
		function moveJoin() {
			location.href="/user/joinForm.do";
		}
		
		function enterCheck(event) {
			if(event.keyCode == 13){
				fn_Login();
			}
		}
	</script>


<body>
<%-- 
	<div>
		<form action="" method="post" onsubmit="return false;">
			<label for="userId">ID</label>
			<input type="text" name="userId" id="userId" autofocus="autofocus" required="required">
			<div class="position-relative">
				<label for="userPass">Password</label>
				<input type="password" name="userPass" id="userPass" autocomplete="current-password" required="required">
				<button onClick="javascript:fn_Login()">Login</button>
			</div>
		</form>
			<div>
				<a href="/user/joinForm.do">회원가입</a>
			</div>
	</div>
  --%>
	<div class="box">
		<input type="text" placeholder="ID" name="userId" id="userId" autofocus="autofocus" required="required" />
		<input type="password" placeholder="Password" name="userPass" id="userPass" autocomplete="current-password" required="required" onkeypress="enterCheck(event)"/>
		<button onClick="javascript:fn_Login()">Sign in</button>
		<button onClick="javascript:moveJoin()">join</button>
	</div>


</body>
</html>