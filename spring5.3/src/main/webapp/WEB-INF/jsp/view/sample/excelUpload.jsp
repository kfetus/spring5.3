<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>엑셀 업로드</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

		<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>
		
		<script>

			const ajaxUpload = () => {

				if ($("#srcSheetNm").val() == '') {
	                alert("Sheet명이 없어.");
	                $("#srcSheetNm").focus();
	                return;
	            }
				
				var formData = new FormData();
				var inputFile = $("#srcAddFile");
				var files = inputFile[0].files;
				
				console.log(files[0]);
				
				if( !files[0] ) {
					alert('file이 없어');
					return false;
				} else {
					console.log(files[0].name);
					var reg = /(.*?)\.(csv|xls|xlsx|png)$/;
					if (!files[0].name.match(reg)) {
		                alert("확장자는 엑셀 확장자만 가능합니다.");
		                return;
		            }
				}

				formData.append("multiFiles", files[0]);
				formData.append('srcSheetNm',$("#srcSheetNm").val());
				
				console.log(formData);
				$.ajax({
					type : 'post',
					url: '/upload/excelUploadSample.do',
					processData : false,
					contentType : false,
					data : formData,
					success : function(res){
						alert(res.resMsg);
					},
					error : function(request, status, error) {
						console.log(error);
					}
				});
			}

			$(function() {
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
                <label for="srcSheetNm" style="vertical-align: top;">업로드 sheet명</label>
                <input name="srcSheetNm" id="srcSheetNm" placeholder="Sheet명입력" value="sheet1"/>
            </div>
            <div style="padding: 10px;"></div>
            <div>
                <label for="srcAddFile">엑셀</label>
                <input type="file" id="srcAddFile" /><!-- accept=".xls,.xlsx" -->
            </div>
        </div>
		<div style="padding: 10px;"></div>
        <div>
			<button type="button" id="s1" onclick="javascript:ajaxUpload();"><span>ajax업로드</span></button>
			<button type="button" id="s1" onclick="location.href='http://192.168.0.7:8080/welcome.jsp';">메인</button>
		</div>
	</body>
</html>