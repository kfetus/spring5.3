<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>일정관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />


	<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-date-picker.css" />" />
	<script src="<c:url value="/static/js/toastgrid/tui-date-picker.js" />"></script>

	<%-- 엑셀 다운로드시 필요 js . 현재 csv 는 상관 없음
	<script lang="javascript" src="https://cdn.sheetjs.com/xlsx-0.20.2/package/dist/xlsx.full.min.js"></script>
	--%>
	<script src="<c:url value="/static/js/toastgrid/tui-grid.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-grid.css" />" />

	<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>

	<script>
		var DatePicker = tui.DatePicker;
		
		DatePicker.localeTexts['ko'] = {
			titles: {
				DD: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
				D: ['일', '월', '화', '수', '목', '금', '토'],
				MMM: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
				MMMM: ['1월', '2월', '3월', '4월', '5월', '6월','7월', '8월', '9월', '10월', '11월', '12월']
			},
			titleFormat: 'yyyy년 MMM',
			todayFormat: 'yyyy년 MMM d일 (DD)'
		};
		
		function saveTable() {
			pageGrid.finishEditing();//페이지 에디팅 종료
			let sendData = [];
			
			if(pageGrid.getModifiedRows().updatedRows.length > 0) {
				for(var i = 0 ; i < pageGrid.getModifiedRows().updatedRows.length ; i++) {
					console.log(pageGrid.getModifiedRows().updatedRows[i]);
/*					
					let rowData= {};
					rowData.MODE = 'U';
					rowData.CNG_DT = pageGrid.getModifiedRows().updatedRows[i].CNG_DT;
					rowData.CODE = pageGrid.getModifiedRows().updatedRows[i].CODE;
					rowData.DUE_DT = pageGrid.getModifiedRows().updatedRows[i].DUE_DT;
					rowData.FIN_DT = pageGrid.getModifiedRows().updatedRows[i].FIN_DT;
					rowData.MASTER_NAME = pageGrid.getModifiedRows().updatedRows[i].MASTER_NAME;
					rowData.MENU_DEPTH_1 = pageGrid.getModifiedRows().updatedRows[i].MENU_DEPTH_1;
					rowData.MENU_DEPTH_2 = pageGrid.getModifiedRows().updatedRows[i].MENU_DEPTH_2;
					rowData.MENU_DEPTH_3 = pageGrid.getModifiedRows().updatedRows[i].MENU_DEPTH_3;
					rowData.MENU_DEPTH_4 = pageGrid.getModifiedRows().updatedRows[i].MENU_DEPTH_4;
					rowData.MENU_NAME = pageGrid.getModifiedRows().updatedRows[i].MENU_NAME;
					rowData.PROG_FILE_NM = pageGrid.getModifiedRows().updatedRows[i].PROG_FILE_NM;
					rowData.REG_DT = pageGrid.getModifiedRows().updatedRows[i].REG_DT;
					rowData.SEQ = pageGrid.getModifiedRows().updatedRows[i].SEQ;
					rowData.START_DT = pageGrid.getModifiedRows().updatedRows[i].START_DT;
					rowData.STATE = pageGrid.getModifiedRows().updatedRows[i].STATE;
					
					sendData.push(rowData);
*/					
					let { _attributes,CNG_DT,REG_DT,rowKey, ...rest } = pageGrid.getModifiedRows().updatedRows[i];//rest parameter 방식.
					rest.MODE = 'U';
					rest.START_DT = pageGrid.getModifiedRows().updatedRows[i].START_DT.replace(/-/g, '').trim();
					rest.DUE_DT = pageGrid.getModifiedRows().updatedRows[i].DUE_DT.replace(/-/g, '').trim();
					if( !!pageGrid.getModifiedRows().updatedRows[i].FIN_DT) {
						rest.FIN_DT = pageGrid.getModifiedRows().updatedRows[i].FIN_DT.replace(/-/g, '').trim();
					}
					sendData.push(rest);
				}
			}
			console.log(sendData);
			if(pageGrid.getModifiedRows().createdRows.length > 0) {
				for(var i = 0 ; i < pageGrid.getModifiedRows().createdRows.length ; i++) {
					console.log(pageGrid.getModifiedRows().createdRows[i]);
					let { _attributes,rowKey, ...rest } = pageGrid.getModifiedRows().createdRows[i];
					rest.MODE = 'I';
					rest.START_DT = pageGrid.getModifiedRows().createdRows[i].START_DT.replace(/-/g, '').trim();
					rest.DUE_DT = pageGrid.getModifiedRows().createdRows[i].DUE_DT.replace(/-/g, '').trim();
					if( !!pageGrid.getModifiedRows().createdRows[i].FIN_DT) {
						rest.FIN_DT = pageGrid.getModifiedRows().createdRows[i].FIN_DT.replace(/-/g, '').trim();
					}
					sendData.push(rest);
				}
			}
			console.log(sendData);
			$(".spinner-container").show();
			$.ajax({
				type : 'post',
				url : 'changePmsList.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( sendData ),    
				success : function(result) {
					console.log(result);
					if(result.RESCODE != '0000') {
						alert('데이터 수정에 실패하였습니다.');
						return;
					}
					pmsSearch(pageGrid.nowPage);
				},
				error : function(error) {        
					console.log(error);
				}
			});
			$(".spinner-container").hide();
		}

		function addTableRow() {
			pageGrid.finishEditing();//페이지 에디팅 종료
			pageGrid.appendRow({CODE: "WEB",DUE_DT: fn_getTodayYYYY_MM_DD(),FIN_DT: null, MASTER_NAME: "${userNm}",MENU_DEPTH_1: "",MENU_NAME: "",PROG_FILE_NM: "",START_DT: fn_getTodayYYYY_MM_DD(), STATE: "R"});
//			pageGrid.appendRow({CODE: "WEB",DUE_DT: fn_getTodayYYYY_MM_DD(),FIN_DT: null, MASTER_NAME: "홍길동",MENU_DEPTH_1: "",MENU_NAME: "",PROG_FILE_NM: "",START_DT: fn_getTodayYYYY_MM_DD(), STATE: "R"},{at:7});
		}

		
		function deleteRows() {
			pageGrid.finishEditing();//페이지 에디팅 종료
			if( pageGrid.getCheckedRows().length == 0) {
				alert('삭제하려는 행을 선택하세요');
				return;
			}
			console.log(pageGrid.getCheckedRows());

			let delList = [];
			for(var i = 0 ; i < pageGrid.getCheckedRows().length ; i++) {
				if (!!pageGrid.getCheckedRows()[i].SEQ) {
					console.log(pageGrid.getCheckedRows()[i]);
					delList.push(pageGrid.getCheckedRows()[i].SEQ);
//					delList[i] = pageGrid.getCheckedRows()[i].SEQ;
				}
			}

			if( delList.length == 0) {
				pageGrid.removeCheckedRows();
				return;
			}
			$(".spinner-container").show();
			$.ajax({
				type : 'post',
				url : 'deletePmsList.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( delList ),    
				success : function(result) {
					console.log(result);
					pmsSearch(pageGrid.nowPage);
				},
				error : function(request, status, error) {        
					console.log(error);
				}
			});
			$(".spinner-container").hide();
		}
		

		let pageGrid;
		function pmsSearch(wantPageNo) {
			pageGrid.nowPage = wantPageNo;<%-- 현재페이지 셋팅 --%>
			let pagePerCnt = $('#pagePerCnt').val();
			$(".spinner-container").show();
			$.ajax({
				type : 'post',
				url : '/pmsList.do',
				async : true,
				dataType : 'json',
				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'nowPage':wantPageNo, 'pageListCnt':pagePerCnt, 'menuName' : $("#menuName").val() ,'masterName': $("#masterName").val() ,'menu1Depth': $("#menu1Depth").val(), } ),    
				success : function(result) {
					console.log(result);
					if(result.RESULT_SIZE == 0) {
						pageGrid.clear();
					} else {
						pageGrid.resetData(result.RESULT_LIST);
						$("#totalCnt").text(result.RESULT_TOTAL_CNT);
					}
					fn_makePaging(wantPageNo,result.RESULT_TOTAL_CNT,pagePerCnt,'paging','pmsSearch');
				},
				error : function(request, error) {        
					console.log(request);
					console.log(error);
					alert(request.responseJSON.RESMSG);
				}
			});
			$(".spinner-container").hide();
		}
		
		$(function() {
			pageGrid = new tui.Grid({
				el : $("#toastGrid")[0],
				scrollX : false,
				scrollY : false,
				rowHeaders: [
					{ type: 'checkbox', header: '선택'}
				],
				columns : [ 
/*					{
						header : '순번',
						name : 'SEQ',
						width : 50,
						sortable: true,			<%-- 정렬 여부 --%>
					},
*/					{
						header : '유형',
						name : 'CODE',
						width : 100,
						sortable: true,
						formatter: 'listItemText',
						editor: {
							type: 'select',
							options: {
								listItems: [
									{ text: '웹프로그램', value: 'WEB' },
									{ text: '배치프로그램', value: 'BATCH' },
									{ text: '앱프로그램', value: 'APP' }
								]
							}
						}
					},
					{
						header : '메뉴1뎁스',
						name : 'MENU_DEPTH_1',
						width : 80,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '메뉴2뎁스',
						name : 'MENU_DEPTH_2',
						width : 130,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '메뉴3뎁스',
						name : 'MENU_DEPTH_3',
						width : 100,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '프로그램파일명',
						name : 'PROG_FILE_NM',
						sortable: true,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '프로그램명',
						name : 'MENU_NAME',
						width : 150,
						sortable: true,
						editor: 'text',			<%-- edit 여부 --%>
					},
					{
						header : '상태',			<%-- 헤더 명 --%>
						name : 'STATE',		<%-- data 키값 --%>
						width : 100,				<%-- 컬럼 width --%>
						sortable: true,			<%-- 정렬 여부 --%>
						resizable: true,		<%-- 컬럼 크기 조절 여부 --%>
						formatter: 'listItemText',
						editor: {
							type: 'select',
							options: {
								listItems: [
									{ text: '준비', value: 'R' },
									{ text: '진행중', value: 'I' },
									{ text: '완료', value: 'E' },
									{ text: '지연', value: 'D' }
								]
							}
						}
					}, 
					{
						header : '담당자',
						width : 70,
						name : 'MASTER_NAME',
						editor: 'text',			<%-- edit 여부 --%>
					}, 
					{
						header : '시작일',
						name : 'START_DT',
						width : 80,
						editor: {
							type: 'datePicker',
							options: {
								format: 'yyyy-MM-dd',
								language: 'ko'
							}
						}						
					}, 
					{
						header : '목표예정일',
						name : 'DUE_DT',
						width : 80,
						editor: {
							type: 'datePicker',
							options: {
								format: 'yyyy-MM-dd',
								language: 'ko'
							}
						}						
					}, 
					{
						header : '완료일',
						name : 'FIN_DT',
						width : 80,
						editor: {
							type: 'datePicker',
							options: {
								format: 'yyyy-MM-dd',
								language: 'ko'
							}
						}						
					}, 
				],
			});
	
			console.log('document.onload()');
			pmsSearch(1);
		})
		
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
						window.location.href = "/pmsMain.do";
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

</head>
<body>
	<div>
		<div>
			<input name="menu1Depth" id="menu1Depth" value="" placeholder="메뉴1뎁스" onkeyup="if(window.event.keyCode==13){pmsSearch('1')}">
			<input name="menuName" id="menuName" value="" placeholder="프로그램명" onkeyup="if(window.event.keyCode==13){pmsSearch('1')}">
			<input name="masterName" id="masterName" value="" placeholder="담당자" onkeyup="if(window.event.keyCode==13){pmsSearch('1')}">
<select name="pagePerCnt" id="pagePerCnt">
	<option value="5">5</option>
	<option value="10" selected="selected">10</option>
	<option value="20">20</option>
	<option value="30">30</option>
</select>
			<button type="button" onclick="javascript:pmsSearch(1);"><span><strong>조회</strong></span></button>
			<button type="button" onclick="javascript:saveTable();"><span><strong>저장</strong></span></button>
			<button type="button" onclick="javascript:addTableRow();"><span><strong>추가</strong></span></button>
			<button type="button" onclick="javascript:deleteRows();"><span><strong>삭제</strong></span></button>
			<button type="button" onclick="javascript:fn_Login();"><span><strong>로그인</strong></span></button>
			<span><span id="totalCnt"></span>건</span>
		</div>

		<div>
			<div id="toastGrid"></div>
			<div id="paging" ></div>

		</div>
	</div>
<jsp:include page="/WEB-INF/jsp/view/common/include/commonHtml.jsp"></jsp:include>
</body>
</html>