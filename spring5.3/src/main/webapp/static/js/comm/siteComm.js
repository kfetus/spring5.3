/**
 * 일반적인 공통 함수
 */

	const G_TOKEN_KEY = 'AccessKeyJwt';
	/**
	 * LocalStorage 관리
	 */
	function fnSetLocalStorage(key, value){
		localStorage.setItem(key, value);
	}
	
	function fnGetLocalStorage(key){
		return localStorage.getItem(key);
	}
	
	function fnDelLocalStorage(key){
		 localStorage.removeItem(key);
	}
	
	/**
	 * SessionStorage 관리
	 */
	function fnSetSessionStorage(key, value){
		sessionStorage.setItem(key, value);
	}
	
	function fnGetSessionStorage(key){
		return sessionStorage.getItem(key);
	}
	
	function fnDelSessionStorage(key){
		 sessionStorage.removeItem(key);
	}


	//nowPage가 시작. 최대 5개까지만 보여주기.
	function makePaging(nowPage,totalCnt,pagePerCnt,divName,fn) {
//			$("#paging").empty();
		$('#'+divName).empty();
		
		let showPagingCnt = 5;//페이징은 5개까지만
		let maxPagingCnt = 0;//
		
		let totalPageCnt = Math.floor(totalCnt / pagePerCnt) ;
		let remainder = totalCnt % pagePerCnt;
		if ( remainder != 0) {
			totalPageCnt += 1; 
		}
		if(nowPage > totalPageCnt) {
			nowPage = totalPageCnt;
		}
		
		if( (nowPage + showPagingCnt - 1) < totalPageCnt ) {
			maxPagingCnt = nowPage + showPagingCnt -1;
		} else {
			maxPagingCnt = totalPageCnt;
		}
		
		console.log(totalPageCnt);
		
		let pagingHtml =  '';
		if( nowPage == 1 ) {
			pagingHtml = pagingHtml + '';
		} else if ( nowPage > 1) {
			pagingHtml = pagingHtml + '<span><span onclick="'+fn+'(1);"> 처음 </span></span>';
			if ( nowPage >= 2) {
				pagingHtml = pagingHtml + '<span onclick="'+fn+'('+(nowPage-1)+');"><span> 이전 </span></span>';
			}
		}
		
		for(var i = nowPage ; i <= maxPagingCnt ; i++) {
			if( i == nowPage) {
				pagingHtml = pagingHtml + '<strong> '+nowPage+' </strong>';
			} else {
				pagingHtml = pagingHtml + '<a href="javascript:'+fn+'('+i+');" > '+i+' </a>';
			}
		}
		if ( maxPagingCnt < totalPageCnt) {
			pagingHtml = pagingHtml + '<a href="javascript:'+fn+'('+(nowPage+1)+');" ><span > 다음 </span></a>';
		}
		if ( nowPage < totalPageCnt) {
			pagingHtml = pagingHtml + '<a href="javascript:'+fn+'('+totalPageCnt+');" ><span > 마지막 </span></a>';
		}
		
		$('#'+divName).append(pagingHtml);
	}
