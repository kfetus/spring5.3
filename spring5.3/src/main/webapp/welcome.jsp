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
</style>	
	<script>
		function testRest(){
			console.log(fnGetSessionStorage(G_TOKEN_KEY));
			$.ajax({
				type : 'post',
				url : '<c:url value="/restBaseError.do" />',
				async : true,
				dataType : 'text',
 				headers : {"Content-Type" : "application/json","Authorization":fnGetSessionStorage(G_TOKEN_KEY)},
 				//jwt를 사용하려면 아래 헤더도 추가 token은 아래 sessionStorage에서 가져온다
 				//headers:{'token':token}, 
				data : JSON.stringify( {'param':'test'}),
				success : function(result) {
					console.log(result);
					let retData = JSON.parse(result);
					if(retData.RESCODE === '0000') {
						console.log(fnGetSessionStorage('token'));
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
	</script>


<body>

<div data-role="header" data-position="inline" data-theme="g">
	<h1>Welcom 테스트 페이지</h1>
	<img src="/static/images/cmmn/menu-hambuger.png">
	<ul>
		<li>
			<a href="<c:url value="/index.jsp" />">index</a>
		</li>
		<li>
			<a href="<c:url value="/baseList.do" />">기본리스트</a>
		</li>
		<li>
			<a href="<c:url value="/urlToView/board/boardList.do" />">urltoView</a>
		</li>
		<li>
			<button onClick="javascript:testRest()">restError테스트</button>
		</li>
	</ul>
</div>  

</body>
</html>