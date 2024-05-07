<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>게시판</title>
	<meta name="viewport"
		content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<script src="<c:url value="/static/js/comm/jquery-3.7.1.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/static/css/base.css" />" />
	<%-- 엑셀 다운로드시 필요 js . 현재 csv 는 상관 없음
	<script lang="javascript" src="https://cdn.sheetjs.com/xlsx-0.20.2/package/dist/xlsx.full.min.js"></script>
	--%>

	<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-date-picker.css" />" />
	<script src="<c:url value="/static/js/toastgrid/tui-date-picker.js" />"></script>

	<link rel="stylesheet" href="<c:url value="/static/js/toastgrid/tui-grid.css" />" />
	<script src="<c:url value="/static/js/toastgrid/tui-grid.js" />"></script>


	<script src="<c:url value="/static/js/comm/siteComm.js" />"></script>

	<script>

	var DatePicker = tui.DatePicker;

    DatePicker.localeTexts['ko'] = {
        titles: {
            DD: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
            D: ['일', '월', '화', '수', '목', '금', '토'],
            MMM: ['1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월'],
            MMMM: ['1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월']
        },
        titleFormat: 'yyyy년 MMM',
        todayFormat: 'yyyy년 MMM d일 (DD)'
    };
	
		
		let pageGrid;
	
		let gridData = [
			{
				SEQ : 549731,
				TITLE : 'Birdy',
				REG_DT : '2019-12-11',
				grade : '3',
			}, 
			{
				SEQ : 436461,
				TITLE : 'Ed Sheeran',
				REG_DT : '2019-02-08',
				grade : '1',
			},
		];
		
		class CustomSliderRenderer {
		      constructor(props) {
		        const el = document.createElement('input');
		        const { min, max } = props.columnInfo.renderer.options;

		        el.type = 'range';
		        el.min = String(min);
		        el.max = String(max);
		        el.disabled = true;

		        this.el = el;
		        this.render(props);
		      }

		      getElement() {
		        return this.el;
		      }

		      render(props) {
		        this.el.value = String(props.value);
		      }
		    }		
		
		$(function() {
			pageGrid = new tui.Grid({
				el : $("#toastGrid")[0],
				data : gridData,
				scrollX : false,
				scrollY : false,
				columns : [ 
					{
						header : '글 번호',
						name : 'SEQ',
						sortable: true,			<%-- 정렬 여부 --%>
					},
					{
						header : '제목',
						name : 'TITLE',
						sortable: true,			<%-- 정렬 여부 --%>
					},
					{
						header: 'Grade',
						name: 'grade',
						renderer: {
							type: CustomSliderRenderer,
							options: {
								min: 1,
								max: 5
							}
						}
					},
					{
						header : '날짜',
						name : 'REG_DT',
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
		})
	</script>

</head>
<body>
	<div>
		<div>
			본문
		</div>

		<div>
			<div id="toastGrid"></div>
<%--
<div>
<span><span>first</span></span>
<span><span>prev</span></span>
<strong>1</strong>
<a href="#" >2</a>
<a href="#" >3</a>
<a href="#" >4</a>
<a href="#" ><span >next</span></a>
<a href="#" ><span >last</span></a>
</div>
--%>

		</div>
	</div>
</body>
</html>