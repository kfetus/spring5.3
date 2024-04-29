/**
 * 일반적인 공통 함수
 */

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
