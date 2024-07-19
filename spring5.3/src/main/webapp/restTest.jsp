<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<title>REST 테스트</title>
	<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />

	<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
	<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>
	
	<link type="text/css" rel="stylesheet" href="<c:url value="/static/js/jsgrid-1.5.3/jsgrid.min.css" />" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/static/js/jsgrid-1.5.3/jsgrid-theme.min.css" />" />
	<script src="<c:url value="/static/js/jsgrid-1.5.3/jsgrid.min.js" />"></script>
	 
	<script>
		function fn_queryServer(url){
			console.log('ddd');
			$.ajax({
				type : 'post',
				url : url,
				async : true,
				dataType : 'text',
 				headers : {"Content-Type" : "application/json"},
				data : JSON.stringify( {'type1':'name', 'type2':'identity'}),    
				success : function(result) {
					console.log(result);
				},
				error : function(request, status, error) { // 결과 에러 콜백함수        
					console.log(error);
				}
			});
		}
	
		function fn_scriptTest() {
			const initialState = [{count: 0},'dddd'];
			const [a,b] = initialState;
			console.log(a);
			console.log(a.count);
			console.log(b);
		}

		$(function() {
		    
		    var CATEGORY = [
		        { Name: "", VALUE: 0 },
		        { Name: "BASE", VALUE: "BASE" },
		        { Name: "NOTICE", VALUE: "NOTICE" }
		    ];
		    $("#jsGrid").jsGrid({
		        width: "100%",
		        height: "400px",

//		        inserting: true,// type:control 필드와 엮여서 로우 추가가 된다 
		        editing: true,
		        sorting: true,//ajax 조회 일 경우 헤더 클릭해서 소팅을 하고자 하면 서버사이드에서 소팅하게 파리커가 추가된다.추가필드=>sortField:"CODE_NAME" sortOrder:"asc"
				autoload: true,//초기 ajax 조회 여부
		        
		        paging: true,
		        pageLoading: true,
		        pageSize: 6,
		        pageIndex: 1,
		        pageButtonCount: 7,
		        pagerFormat: "Pages: {first} {prev} {pages} {next} {last}    {pageIndex} of {pageCount}",
		        pagePrevText: "Prev",
		        pageNextText: "Next",
		        pageFirstText: "First",
		        pageLastText: "Last",
		        pageNavigatorNextText: "...",
		        pageNavigatorPrevText: "...",		        
		        
		        deleteConfirm: "지울거여?",
//		        data: clients,
				
				controller: {
					loadData: function(pagingParam) {
						console.log(pagingParam);
						var d = $.Deferred();
						$.ajax({
							type : 'post',
							url : '/boardList.do',
							async : true,
							dataType : 'json',
			 				headers : {"Content-Type" : "application/json"},
							data : JSON.stringify( {'nowPage':pagingParam.pageIndex, 'pageListCnt':6}), 
						}).done(function(res) {
							console.log(res);
							d.resolve({data: res.RESULT_LIST, itemsCount: res.RESULT_TOTAL_CNT});
						}).fail(function(res) {
							console.log('error='+res);
						});
						
						return d.promise();
					}
				},

		        fields: [
/*		            { name: "Name", type: "text", width: 150, validate: "required" },
		            { name: "Age", type: "number", width: 50 },
		            { name: "Address", type: "text", width: 200 },
		            { name: "Country", type: "select", items: countries, valueField: "VALUE", textField: "Name" },
		            { name: "Married", type: "checkbox", title: "Is Married", sorting: false },
		            { type: "control" }
*/
		            { title:'순번', name: "SEQ", type: "number", width: 20},
		            { title:'카테고리', name: "CATEGORY", type: "select", items: CATEGORY, valueField: "VALUE", width: 30, textField: "Name"},
		            { title:'코드명', name: "CODE_NAME", type: "text", width: 50, validate: "required" },
		            { title:'제목', name: "TITLE", type: "text", width: 100, validate: "required" },
//		            { title:'내용', name: "BODY_TEXT", type: "text", width: 50, validate: "required" },
		            { title:'등록일', name: "REG_DT", type: "text", width: 50, validate: "required" }
		        ]
		    });

		})
	    
	</script>
</head>

<body>
	<div>
		<header>
			<div>
				<h1>rest테스트</h1>
			</div>
		</header>
		<main>
			<div>
				본문
				<button type="button" onclick="javascript:fn_queryServer('/sampleRest.do');"><span><strong>샘플</strong></span></button>
				<button type="button" onclick="javascript:fn_queryServer('/restBaseModel.do');">기본</button>
				<button type="button" onclick="javascript:fn_queryServer('/restBaseResVo.do');">응답기본</button>
				<button type="button" onclick="javascript:fn_scriptTest();">스크립트테스트</button>
			</div>

	       	<div>
	       		<div id="jsGrid"></div>
	       	</div>
		</main>

		<footer>
			바닥
		</footer>
	</div>
</body>
</html>