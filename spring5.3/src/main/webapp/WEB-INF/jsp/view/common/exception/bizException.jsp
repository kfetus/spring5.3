<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%
	System.out.println("bizException.jsp=>"+request.getContentType());
	String contentType = request.getContentType();
	if ("application/json".equals(contentType)) {
%>
{"RESMSG":"${exception.message}","RESCODE":"9999"}
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
                 <h4 class="main">메인</h4>
            </div>
            
        </div>
        		
  	</body>
</html>
<%
	}
%>



               