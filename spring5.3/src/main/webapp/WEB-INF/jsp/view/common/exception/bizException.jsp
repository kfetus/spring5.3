<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String contentType = request.getContentType();
	System.out.println("bizException.jsp=>"+contentType);
	if ("application/json".equals(contentType)) {
		response.setContentType("application/json");
		
//		{"RESMSG":"${exception.message}","RESCODE":"9999"} 이렇게 처리할 때 exception.message에서 줄바꿈(newline)문자가 있을 경우 제대로 된 값이 나가지 않는다.
%>
{"RESMSG":"비지니스 에러 처리 중 에러가 발생했습니다.","RESCODE":"9999"}
<%
	} else {
%>
<!DOCTYPE html> 
<html> 
    <head>
        <title>bizException</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
    </head>
    
    <body>
    
        <div data-role="page" data-theme="d">
            <div data-role="header" data-position="inline" data-theme="g">
            	<h1>Error Page</h1>
                <a href="/index.jsp" data-icon="back" class="ui-btn-right">Back</a>
            </div>  
            <div data-role="content" >
                <h1>시스템 에러(biz exception)</h1>
                <p>관리자에게 문의해주세요.</p>
               	<a href="/index.jsp"  data-role="button" data-inline="true">돌아가기</a>
                <br>
                <p><c:out value="${exception.message}"/></p>
            </div>
            <div data-role="footer" data-theme="g">
                 <h4>풋터</h4>
            </div>
        </div>
  	</body>
</html>
<%
	}
%>



               