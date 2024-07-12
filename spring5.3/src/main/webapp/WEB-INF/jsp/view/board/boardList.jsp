<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>게시판</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

		<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>
		
		<script>
		
			const boardList = (nowPage) => {
				console.log('ajax start');

				$("#boardList").show();
				$("#boardDetail").hide();

				let pageListCnt = 8;
				console.log($("#listTable tr").length);
				if ( $("#listTable tr").length != 1) {
					while($("#listTable tr").length != 1) {
						$("#listTable > tbody:last > tr:last").remove();
					}
				}
				
				$.ajax({
					type : 'post',
					url : '/boardList.do',
					async : true,
					dataType : 'json',
	 				headers : {"Content-Type" : "application/json"},
					data : JSON.stringify({ 'nowPage':nowPage, 'pageListCnt':pageListCnt, 'category':$("#category").val(), 'title':$("#title").val() }),
					success : function(result) {
						console.log(result);
//						let listData = JSON.parse(result);//dataType : 'text'로 선언했을 경우 json 파싱 
						let appendStr;

						$(result.RESULT_LIST).each(function(index){
							appendStr += "<TR>";
							appendStr += '<TD>'+result.RESULT_LIST[index].SEQ + '</TD>';
							appendStr += '<TD>'+result.RESULT_LIST[index].CATEGORY + '</TD>';
							appendStr += '<TD>'+result.RESULT_LIST[index].CODE_NAME + '</TD>';
							appendStr += '<TD><a href="javascript:showDetail('+result.RESULT_LIST[index].SEQ+')">'+result.RESULT_LIST[index].TITLE + '</a></TD>';
							appendStr += '<TD>'+result.RESULT_LIST[index].BODY_TEXT + '</TD>';
							appendStr += '<TD>'+result.RESULT_LIST[index].USER_NO + '</TD>';
							appendStr += '<TD>'+result.RESULT_LIST[index].REG_DT + '</TD>';
							appendStr += '<TD>'+result.RESULT_LIST[index].CNG_DT + '</TD>';
							appendStr += '</TR>';
						});
						
	                    $("#listTable").append(appendStr);
	                    fn_makePaging(nowPage,result.RESULT_TOTAL_CNT,pageListCnt,'paging','boardList');
						
					},
					error : function(request, status, error) {        
						console.log(error);
					}
				});
			}
			
			function showDetail(seq) {
				$("#boardList").hide();
				$("#boardDetail").show();
				
				$.ajax({
					type : 'post',
					url : '/boardOne.do',
					async : true,
					dataType : 'json',
	 				headers : {"Content-Type" : "application/json"},
					data : JSON.stringify({ 'SEQ':seq }),
					success : function(result) {
						console.log(result);
						$("#detailTitle").text(result.RESULT_DATA.TITLE);
						$("#detailContent").text(result.RESULT_DATA.BODY_TEXT);
						$("#detailCategory").text(result.RESULT_DATA.CATEGORY);
						$("#detailFileName").text(result.RESULT_DATA.FILE_NAME);
						$("#detailTitle").data("seq",result.RESULT_DATA.SEQ);
						
						$("#detailHref").attr("href", "http://localhost:8080/blobFiledown.do?seq="+seq+"&fileSeq="+result.RESULT_DATA.FILE_SEQ)
 					},
					error : function(request, status, error) {        
						console.log(error);
					}
				});
			}
			
			$(function() {
				boardList(1);
				console.log('document.onload()');
			})
			
			function registBoard() {
				location.href="/urlToView/board/boardEdit.do";
			}
		
		</script>
	
	</head>
	<body>
		<div id="boardList">
			<div>
				<select name="category" id="category">
					<option value="" selected="selected">게시판종류</option>
					<option value="BASE">기본</option>
					<option value="NOTICE">공지</option>
				</select>
				<input name="title" id="title" value="" placeholder="제목" onkeyup="if(window.event.keyCode==13){boardList('1')}">
				<button type="button" id="s1" onclick="javascript:boardList('1');"><span><strong>조회</strong></span></button>
				<button type="button" id="s1" onclick="javascript:registBoard();"><span><strong>등록</strong></span></button>
			</div>
		
	       	<div>
	       		<table id="listTable">
	       			<colgroup>
	       				<col width="10%"/>
	       				<col width="10%"/>
	       				<col width="15%"/>
	       				<col width="15%"/>
	       				<col width="20%"/>
	       				<col width="10%"/>
	       				<col width="10%"/>
	       				<col width="10%"/>
	       			</colgroup>
	       			<tr>
	       				<th align="center">순번</th>
	       				<th align="center">카테고리</th>
	       				<th align="center">코드명</th>
	       				<th align="center">제목</th>
	       				<th align="center">내용</th>
	       				<th align="center">등록자</th>
	       				<th align="center">등록일</th>
	       				<th align="center">변경일</th>
	       			</tr>
	       			<c:forEach var="result" items="${list}" varStatus="status">
	           			<tr>
	           				<td><c:out value="${result.CODE_TYPE}"/></td>
	           				<td><a href="#"><c:out value="${result.CODE_TYPE_NAME}"/></a></td>
	           				<td><c:out value="${result.ORDER_NUM}"/></td>
	           				<td><c:out value="${result.USE_YN}"/></td>
	           				<td><c:out value="${result.CODE}"/></td>
	           				<td><c:out value="${result.CODE_NAME}"/></td>
	           				<td><c:out value="${result.CODE_NAME}"/></td>
	           				<td><c:out value="${result.CODE_NAME}"/></td>
	           			</tr>
	       			</c:forEach>
	       		</table>
	       	</div>
	       	<div id="paging" ></div>
		</div>
		<div id="boardDetail" style="display:none">
			<div>
				상세
				<button type="button" id="s1" onclick="javascript:boardList('1');"><span><strong>목록</strong></span></button>
			</div>
			<div>제목<span id="detailTitle" data-seq=""></span></div>
			<div>내용<span id="detailContent"></span></div>
			<div>카테고리<span id="detailCategory"></span></div> 
			<div>파일명<a id="detailHref" href=""><span id="detailFileName"></span></a></div> 
		</div>
	</body>
</html>