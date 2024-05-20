<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.Enumeration" %>
<%
/**
 * ExceptionHandler로 구현할 수도 있다. 귀찬다. 똑같은 결과가 전송된다.
 */
	//개발시 참고용 로그. 운영시 삭제
	System.out.println("error.jsp=>"+request.getContentType());
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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>500 Error</title>
</head>

<body>
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="100%" height="100%" align="center" valign="middle" style="padding-top: 150px;"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="<spring:message code='image.errorBg' />">
                            <span style="font-family: Tahoma; font-weight: bold; color: #000000; line-height: 150%; width: 440px; height: 70px;">
                            <c:out value="${exception.message}"/>
                            </span>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>
<%
	}
%>