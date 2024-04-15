<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%
	System.out.println(request.getContentType());
	String contentType = request.getContentType();
	if ("application/json".equals(contentType)) {
%>
{"RESMSG":"${exception.message}","RESCODE":"${exception.errorCode}"}
<%
	} else {
%>
<!DOCTYPE html> 
<html> 
    <head>
        <title>DB Access Error</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="/js/comm/jquery-3.7.1.min.js"></script>
    </head>
    
    <body>
    
        <!-- page start -->
        <div data-role="page" data-theme="d">
        
            <!-- header start -->
            <div data-role="header" data-position="inline" data-theme="g">
            	<h1>Error Page</h1>
                <a href="/index.jsp" data-icon="back" class="ui-btn-right">Back</a>
            </div>  
            <!-- header end -->
            
            <!-- content start -->      
            <div data-role="content" >
                <h1>시스템 에러(biz exception)</h1>
                <p>관리자에게 문의해주세요.</p>
               	<a href="/index.jsp"  data-role="button" data-inline="true">돌아가기</a>
                <br>
                <p><c:out value="${exception.message}"/></p>
                
                <br><br><br>
                <br><br><br>
            </div>
            <!-- content end -->
            
            <!-- footer start -->
            <div data-role="footer" data-theme="g">
                 <h4 class="main">메인</h4>
            </div>
            <!-- footer end --> 
            
        </div>
        <!-- page end -->   
        		
  	</body>
</html>
<%
	}
%>



               