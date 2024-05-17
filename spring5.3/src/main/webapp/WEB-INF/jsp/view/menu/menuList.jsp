<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>메뉴관리</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
		<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
	
	
		<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-date-picker.css" />" />
		<script src="<c:url value="/static/js/toastgrid/tui-date-picker.js" />"></script>
		<script src="<c:url value="/static/js/toastgrid/tui-grid.js" />"></script>
		<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-grid.css" />" />
	
		<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>	</head>

		<style>
#layer_bg {
    display:none;
    position:absolute;
    top:0;
    left:0;
    width:100%;
    height:100%;
    background:rgba(0,0,0,0.5);
	z-index: 30;
}

#layer_bg > #popup {
    position:absolute;
    padding:15px;
    border-radius:15px; 
    top:50%;
    left:50%;
    transform:translate(-50%, -50%);
    width:80%;
    height:80%;
    background:#fff;
    box-shadow: 7px 7px 5px rgba(0,0,0,0.2); 
}

#layer_bg > #popup > h2 {
    margin-bottom:25px;
}

#layer_bg > #popup > h2 > button {
    float: right;
}
#layer_bg > #popup > button {
    float: right;
}

		</style>	

	<script>
		let mainGrid;
		
		function fn_makeGrid() {
<%-- 일반적인 foreach 문으로 돌릴때 받아서 처리하는 값
			let initData = "<c:out value="${RESULT_LIST}"/>";
			console.log(initData);
--%>

			<c:choose>
				<c:when test="${'0' eq RESULT_TOTAL_CNT }">
				let jsonListMapData = JSON.parse('[]');
				</c:when>
				<c:otherwise>
				let jsonListMapData = JSON.parse('${RESULT_JSON_LIST}');
				</c:otherwise>
			</c:choose>
			
			mainGrid = new tui.Grid({
				el : $("#mainGrid")[0],
				scrollX : false,
				scrollY : false,
				data: jsonListMapData,
				rowHeaders: [
					{ type: 'checkbox', header: '선택'}
				],
				columns : [ 
					{
						header : '메뉴  URL',
						name : 'URL',
						width : 80,
					},
					{
						header : '시스템 그룹',
						name : 'SYS_GROUP',
						width : 130,
					},
					{
						header : '부모 URL',
						name : 'PARENT_URL',
						width : 100,
						editor: 'text',
					},
					{
						header : '메뉴명',
						name : 'MENU_NAME',
						sortable: true,
						editor: 'text',
					},
					{
						header : '레벨',
						name : 'MENU_LEVEL',
						width : 150,
						sortable: true,
						editor: 'text',
						validation: {
							required: true,
							dataType: 'number',
						},
					},
					{
						header : '정렬순서',
						name : 'ORDER_NUM',
						width : 100,
						editor: 'text',
						validation: {
							required: true,
							dataType: 'number',
//							regExp: /^[a-z|A-Z|ㄱ-ㅎ|가-힣|0-9 ]*$/, // [정규식] 특수문자를 제외한 공백포함 사용 가능
						},
					}, 
					{
						header : '사용',
						width : 50,
						name : 'USE_YN',
						editor: 'text',
						formatter: 'listItemText',
						editor: {
							type: 'select',
							options: {
								listItems: [
									{ text: '사용', value: 'Y' },
									{ text: '미사용', value: 'N' },
								]
							}
						}
					}, 
					{
						header : '링크',
						name : 'LINK_YN',
						width : 50,
						formatter: 'listItemText',
						editor: {
							type: 'select',
							options: {
								listItems: [
									{ text: '있음', value: 'Y' },
									{ text: '없음', value: 'N' },
								]
							}
						}
					}, 
				],
			});
			
//이벤트 잡기 샘플			mainGrid.on('keydown', (ev) => {
//				console.log(ev);
//			});
			
		}

		$(function() {
			fn_makeGrid();
			let searchCnt = ${RESULT_TOTAL_CNT};
			if(searchCnt > 0) {
				let nowPage = ${nowPage};
				if(nowPage == 0) {
					nowPage += 1;
				}
				fn_makePaging(nowPage,searchCnt,${pageListCnt},'paging','pagingMove');
			}
			console.log('document.onload()');
		})
		
		function pagingMove(wantPageNo) {
			$("#nowPage").val(wantPageNo);
			$("#mainForm").submit();
		}
		
		function formSubmit() {
//			$("#mainForm").attr("action", "/menu/menuList.do");
//			$("#mainForm").attr("method", "post");
			$("#nowPage").val(0);
			$("#mainForm").submit();
			return false;
		}
		
		function saveTable() {
			mainGrid.finishEditing();//페이지 에디팅 종료
			let sendData = [];
			
			if(mainGrid.getModifiedRows().updatedRows.length > 0) {
				for(var i = 0 ; i < mainGrid.getModifiedRows().updatedRows.length ; i++) {
					console.log(mainGrid.getModifiedRows().updatedRows[i]);
					let { _attributes,CNG_DT,REG_DT,rowKey, ...rest } = mainGrid.getModifiedRows().updatedRows[i];//rest parameter 방식.
					rest.MODE = 'U';
					sendData.push(rest);
				}
			} else {
				alert('변경된 데이터가 없습니다.');
				return false;
			}
			console.log(sendData);
<%--	추가는 따로 구현		
			if(mainGrid.getModifiedRows().createdRows.length > 0) {
				for(var i = 0 ; i < mainGrid.getModifiedRows().createdRows.length ; i++) {
					console.log(mainGrid.getModifiedRows().createdRows[i]);
					let { _attributes,rowKey, ...rest } = mainGrid.getModifiedRows().createdRows[i];
					rest.MODE = 'I';
					sendData.push(rest);
				}
			}
			console.log(sendData);
--%>
			
			$.ajax({
				type : 'post',
				url : 'updateMenuList.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( sendData ),    
				success : function(result) {
					console.log(result);
					if(result.RESCODE != '0000') {
						alert('데이터 수정에 실패하였습니다.');
						return false;
					}
					$("#nowPage").val(0);
					$("#mainForm").submit();
				},
				error : function(error) {        
					console.log(error);
				}
			});
			
		}

		<%-- 페이지 로딩시 그리면 그려지지 않음.... --%>
		let addGrid;
		function addTableRow() {
			if(!addGrid) {
				addGrid = new tui.Grid({
					el : $("#addGrid")[0],
					scrollX : false,
					scrollY : false,
//					data: [{URL: "",SYS_GROUP: "",PARENT_URL: "", MENU_NAME: "",MENU_LEVEL: "1",ORDER_NUM: "1",USE_YN: "Y", LINK_YN: "Y"}],
					columns : [ 
						{
							header : '메뉴  URL',
							name : 'URL',
							width : 80,
							editor: 'text',
							validation: {
								required: true,
								dataType: 'string',
							},
						},
						{
							header : '시스템 그룹',
							name : 'SYS_GROUP',
							width : 130,
							editor: 'text',
							validation: {
								required: true,
								dataType: 'string',
							},
							editor: {
								type: 'select',
								options: {
									listItems: [
										{ text: '모바일웹', value: 'MOW' },
										{ text: '자동차', value: 'CAR' },
									]
								}
							},
						},
						{
							header : '부모 URL',
							name : 'PARENT_URL',
							width : 100,
							editor: 'text',
							validation: {
								required: true,
								dataType: 'string',
//								validatorFn : fn_test,
							},
						},
						{
							header : '메뉴명',
							name : 'MENU_NAME',
							sortable: true,
							editor: 'text',
							validation: {
								required: true,
								dataType: 'string',
							},
						},
						{
							header : '레벨',
							name : 'MENU_LEVEL',
							width : 150,
							sortable: true,
							editor: 'text',
							validation: {
								required: true,
								dataType: 'number',
							},
						},
						{
							header : '정렬순서',
							name : 'ORDER_NUM',
							width : 100,
							editor: 'text',
							validation: {
								required: true,
								dataType: 'number',
							},
						}, 
						{
							header : '사용',
							width : 50,
							name : 'USE_YN',
							validation: {
								required: true,
								dataType: 'string',
							},
							formatter: 'listItemText',
							editor: {
								type: 'select',
								options: {
									listItems: [
										{ text: '사용', value: 'Y' },
										{ text: '미사용', value: 'N' },
									]
								}
							}
						}, 
						{
							header : '링크',
							name : 'LINK_YN',
							width : 50,
							validation: {
								required: true,
								dataType: 'string',
							},
							formatter: 'listItemText',
							editor: {
								type: 'select',
								options: {
									listItems: [
										{ text: '있음', value: 'Y' },
										{ text: '없음', value: 'N' },
									]
								}
							}
						}, 
					],
				});
			} else {
				addGrid.clear();
			}
			addGrid.appendRow({URL: "",SYS_GROUP: "",PARENT_URL: "", MENU_NAME: "",MENU_LEVEL: "1",ORDER_NUM: "1",USE_YN: "Y", LINK_YN: "Y"});			
			$("#layer_bg").show();
		}

		<%-- 그리드 해당 셀 추가벨리데이션 리턴 함수 false 일 경우 빨간색으로 표시됨--%>
		function fn_test(a) {
			console.log(a);
			return false;
		}
		
		function saveAddData() {
			addGrid.finishEditing();//페이지 에디팅 종료
			//몇가지 방식 중 그리드가 제공하는 체크로 validation을 할수도 있고, 일반적으로하는 각 값마다 체크하는 방법도 있다. 
			if (addGrid.validate().length) {
				for(let i =0 ; i < addGrid.validate()[0].errors.length;i++) {
					if ( addGrid.validate()[0].errors[0].errorInfo[0].code == 'REQUIRED') {
						alert('필수 입력을 하지 않았다. 머 대충 이런식');
						return false;
					}
				}
			}
/*			
			if(addGrid.getModifiedRows().createdRows.length > 0) {
				for(var i = 0 ; i < mainGrid.getModifiedRows().createdRows.length ; i++) {
					if( addGrid.getModifiedRows().createdRows[i].URL == '') {
						alert('URL is Empty');
						return false;
					}
				}
			}			
*/
			
			let { _attributes,rowKey, ...rest } = addGrid.getModifiedRows().createdRows[0];
			$.ajax({
				type : 'post',
				url : 'saveMenuOne.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( rest ),    
				success : function(result) {
					console.log(result);
					if(result.RESCODE != '0000') {
						alert(result.RESMSG);
						return false;
					}
					closeDim();
					$("#nowPage").val(0);
					$("#mainForm").submit();
				},
				error : function(error) {        
					console.log(error);
				}
			});

		}
		
		
		function closeDim() {
			$("#layer_bg").hide();
		}
		
		function deleteRows() {
			mainGrid.finishEditing();//페이지 에디팅 종료
			if( mainGrid.getCheckedRows().length == 0) {
				alert('삭제하려는 행을 선택하세요');
				return;
			}
			console.log(mainGrid.getCheckedRows());

			let delList = [];
			for(var i = 0 ; i < mainGrid.getCheckedRows().length ; i++) {
				<%-- 새로 추가한 행일경우 서버까지 던지면 안됨 --%>
				if (!!mainGrid.getCheckedRows()[i].REG_DT) {
					let delRow = {};
					delRow.URL = mainGrid.getCheckedRows()[i].URL;
					delRow.SYS_GROUP = mainGrid.getCheckedRows()[i].SYS_GROUP;
					delList.push(delRow);
				}
			}
			console.log(delList);
			if( delList.length == 0) {
				mainGrid.removeCheckedRows();
				return;
			}

			$.ajax({
				type : 'post',
				url : '/menu/deleteMenuList.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( delList ),    
				success : function(result) {
					console.log(result);
					if(result.RESCODE == "0000") {
						window.location.href = "/menu/menuList.do";
					} else {
						alert("삭제에 실패하였습니다.");
					}
				},
				error : function(request, status, error) {        
					console.log(error);
				}
			});
			
		}
		
		function fn_Login(){
			$.ajax({
				type : 'post',
				url : '<c:url value="/restLogin.do" />',
				async : true,
				dataType : 'text',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'userId':'auto', 'userPass':'auto1!'}),
				success : function(result) {
					let retData = JSON.parse(result);
					if(retData.RESCODE === '0000') {
						window.location.href = "/menu/menuList.do";
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
<form action="/menu/menuList.do" method="post" id="mainForm" name="mainForm">
<input type="hidden" name="nowPage" id="nowPage" value="">
	<div>
		<div style="border: 1px solid #bcbcbc;">
			<c:out value="${userNm}"/>
		</div>
		<div>
			<select name="sysGroup" id="sysGroup">
				<option value="" <c:if test="${'' eq sysGroup}">selected="selected"</c:if>>시스템그룹</option>
				<option value="MOW" <c:if test="${'MOW' eq sysGroup}">selected="selected"</c:if>>MOW</option>
				<option value="PCW" <c:if test="${'PCW' eq sysGroup}">selected="selected"</c:if>>PCW</option>
				<option value="APP" <c:if test="${'APP' eq sysGroup}">selected="selected"</c:if>>APP</option>
				<option value="CAR" <c:if test="${'CAR' eq sysGroup}">selected="selected"</c:if>>CAR</option>
			</select>
			<select name="useYn" id="useYn">
				<option value="" <c:if test="${'' eq useYn}">selected="selected"</c:if>>사용여부</option>
				<option value="Y" <c:if test="${'Y' eq useYn}">selected="selected"</c:if>>사용</option>
				<option value="N" <c:if test="${'N' eq useYn}">selected="selected"</c:if>>미사용</option>
			</select>
			<input name="menuName" id="menuName" value="<c:out value="${menuName}"/>" placeholder="메뉴 명" onkeyup="if(window.event.keyCode==13){formSubmit()}">
			 
			<button type="button" onclick="javascript:formSubmit();"><span><strong>조회</strong></span></button>
			<button type="button" onclick="javascript:saveTable();"><span><strong>수정</strong></span></button>
			<button type="button" onclick="javascript:deleteRows();"><span><strong>삭제</strong></span></button>			
			<button type="button" onclick="javascript:addTableRow();"><span><strong>추가</strong></span></button>
			<button type="button" onclick="javascript:fn_Login();"><span><strong>로그인</strong></span></button>
		</div>
		<div>
			<div id="mainGrid"></div>
			<div id="paging"></div>
		</div>

	</div>
</form>

<div id="layer_bg">
	<div id="popup">
		<h2>추가
		<button type="button" onclick="closeDim();">닫기</button>
		</h2>
		<div id="addGrid"></div>
		<button type="button" onclick="javascript:saveAddData();">저장</button>
	</div>
</div>

</body>
</html>