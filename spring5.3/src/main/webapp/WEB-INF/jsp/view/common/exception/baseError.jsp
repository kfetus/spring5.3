<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.Enumeration" %>

<%
/**
 * ExceptionHandler로 구현할 수도 있다. 귀찬다. 똑같은 결과가 전송된다.
 */
	//개발시 참고용 로그. 운영시 삭제
	System.out.println("baseError.jsp=>"+request.getContentType());
	System.out.println("¿¿¿¿¿¿¿¿¿¿¿¿ Attribute ¿¿¿¿¿¿¿¿¿¿¿¿");
	Enumeration<?> attrNames = request.getAttributeNames();
	while (attrNames.hasMoreElements()) {
		String attrName = (String) attrNames.nextElement();
		System.out.println("key : " + attrName +";value="+request.getAttribute(attrName));

	}

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
        <title>System Error</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
    </head>
    
    <body>
    
        <div data-role="page" data-theme="d">
            <div data-role="header" data-position="inline" data-theme="g">
            	<h1>Error Page</h1>
                <a href="<c:url value="/index.jsp" />" data-ajax="false" data-icon="back" class="ui-btn-right">Back</a>
            </div>  
            <div data-role="content" >
                <h1>시스템 에러(BASE)</h1>
                <p>관리자에게 문의해주세요.</p>
                <a href="<c:url value="/index.jsp" />"  data-ajax="false" data-role="button" data-inline="true">돌아가기</a>
                <br>
                <p><c:out value="${exception.message}"/></p>
                <br><br><br>
                <br><br><br>
            </div>
            <div>
                 <h4>풋터</h4>
            </div>
        </div>
  	</body>
</html>
<%
	}
%>