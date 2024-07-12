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
/*
			function getBoardOne(seq) {
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
*/

			const ajaxUpload = () => {
				var formData = new FormData();
				var inputFile = $("#srcAddFile");
				var files = inputFile[0].files;
				
				console.log(files[0]);
				
				var reg = /(.*?)\/(jpg|jpeg|png|bmp)$/;
				if (!files[0].type.match(reg)) {
	                alert("확장자는 이미지 확장자만 가능합니다.");
	                return;
	            }

		        formData.append('category',$("#srcCategory").val());
		        formData.append('title',$("#srcTitle").val());
		        formData.append('bodyText',$("#srcContents").val());
				formData.append("multiFiles", files[0]);
				
				console.log(formData);
				$.ajax({
					type : 'post',
					url: '/insertBoardOne.do',
					processData : false,
					contentType : false,
					data : formData,
					success : function(result){
						alert(result);
					},
					error : function(request, status, error) {
						console.log(error);
					}
				});
			}

			function getCodeList() {
				$.ajax({
					type : 'post',
					url : '/codeList.do',
					async : true,
					dataType : 'json',
					headers : {"Content-Type" : "application/json"},
					data : JSON.stringify({'codeType':'BOARD' }),
					success : function(result) {
						console.log(result);

						for(let i = 0; i < result.RESULT_SIZE ; i++) {
							var option = $("<option value='"+result.RESULT_LIST[i].CODE+"'>"+result.RESULT_LIST[i].CODE_NAME+"</option>");
							$('#srcCategory').append(option);
						}
					},
					error : function(request, status, error) {
						console.log(error);
					}
				});
			}


			$(function() {
				getCodeList();
				console.log('document.onload()');
			})

			function boardList() {
				location.href="/urlToView/board/boardList.do";
			}			
		</script>
	
	</head>
	<body>
        <div>
            <div>
                <label for="srcCategory">분류</label>
                <select name="srcCategory" id="srcCategory">
                    <option value="">선택하세요</option>
                </select>
                <label for="srcTitle">제목</label>
                <input name="srcTitle" id="srcTitle" placeholder="제목입력" />
            </div>
            <div style="padding: 10px;"></div>
            <div>
                <label for="srcContents" style="vertical-align: top;">내용</label>
                <textarea name="srcContents" id="srcContents" cols="54" rows="10" placeholder="내용입력" ></textarea>
            </div>
            <div>
                <label for="srcAddFile">첨부파일</label>
                <input type="file" id="srcAddFile"  />
            </div>
        </div>
		<div style="padding: 10px;"></div>
        <div>
			<button type="button" id="s1" onclick="javascript:ajaxUpload();"><span>ajax저장</span></button>
			<button type="button" id="s1" onclick="javascript:submitUpload();"><span>submit저장</span></button>
			<button type="button" id="s1" onclick="javascript:boardList();"><span><strong>목록</strong></span></button>
		</div>
	</body>
</html>