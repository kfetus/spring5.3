"use strict";(self["webpackChunkvue_test"]=self["webpackChunkvue_test"]||[]).push([[589],{7211:function(e,a,t){t.d(a,{X:function(){return l}});const l=e=>e.replaceAll("&lt;","<").replaceAll("&gt;",">").replaceAll("&#40;","(").replaceAll("&#41;",")")},589:function(e,a,t){t.r(a),t.d(a,{default:function(){return h}});var l=t(6768),o=t(4232),n=t(5130),u=t(5603),c=t(3902),d=t(144),s=t(8355),r=t(782),i=t(1387),v=t(7211);const L=(0,l.Lk)("label",{for:"srcCategory"},"분류",-1),g=(0,l.Lk)("option",{disabled:"",value:""},"선택하세요",-1),p=["value"],m=(0,l.Lk)("label",{for:"srcTitle"},"제목",-1),E=(0,l.Lk)("div",{style:{padding:"10px"}},null,-1),k=(0,l.Lk)("label",{for:"srcContents"},"내용",-1);var A={__name:"boardEdit",setup(e){const a=(0,r.Pj)(),t=(0,i.rd)(),A=(0,i.lq)();(0,l.KC)((()=>{C(),t.isReady(),R(),console.log(A.params.pageKey)}));const T=(0,d.KR)(""),h=(0,d.KR)(""),y=(0,d.KR)(""),S=(0,d.KR)(""),b=(0,d.KR)([]),C=async()=>{a.commit("changeLoadingStatus",!0),await s.A.post("/codeList.do",{codeType:"BOARD"}).then((e=>{b.value=e.data.RESULT_LIST,console.log("조회결과",e.data.RESULT_LIST)})).catch((e=>{console.error("에러",e)})),a.commit("changeLoadingStatus",!1)},R=async()=>{a.commit("changeLoadingStatus",!0),await s.A.post("/boardOne.do",{SEQ:A.params.pageKey}).then((e=>{console.log("조회결과",e.data.RESULT_DATA),T.value=e.data.RESULT_DATA.CATEGORY,h.value=(0,v.X)(e.data.RESULT_DATA.TITLE),y.value=(0,v.X)(e.data.RESULT_DATA.BODY_TEXT),S.value=e.data.RESULT_DATA.FILE_NAME})).catch((e=>{console.error("에러",e)})),a.commit("changeLoadingStatus",!1)},_=()=>""==T.value?(alert("분류가 없어"),document.getElementById("srcCategory").focus(),!1):""==h.value?(alert("제목이 없어"),document.getElementById("srcTitle").focus(),!1):""==y.value?(alert("내용이 없어"),document.getElementById("srcContents").focus(),!1):(a.commit("changeLoadingStatus",!0),s.A.post("/updateBoardOne.do",{seq:A.params.pageKey,category:T.value,title:h.value,bodyText:y.value}).then((e=>{if(console.log("조회결과",e.data),"0000"!=e.data.RESCODE)return alert(e.data.RESMSG),!1;t.go(-1)})).catch((e=>{alert(e),console.error("에러",e)})),a.commit("changeLoadingStatus",!1),void console.log(T.value,h.value,y.value)),f=()=>{t.go(-1)};return(e,a)=>((0,l.uX)(),(0,l.CE)(l.FK,null,[(0,l.bF)(u.A,{title:"게시판 수정"}),(0,l.Lk)("div",null,[(0,l.Lk)("main",null,[(0,l.Lk)("div",null,[(0,l.Lk)("div",null,[L,(0,l.bo)((0,l.Lk)("select",{"onUpdate:modelValue":a[0]||(a[0]=e=>T.value=e),id:"srcCategory"},[g,((0,l.uX)(!0),(0,l.CE)(l.FK,null,(0,l.pI)(b.value,(e=>((0,l.uX)(),(0,l.CE)("option",{value:e.CODE,key:e},(0,o.v_)(e.CODE_NAME),9,p)))),128))],512),[[n.u1,T.value]]),m,(0,l.bo)((0,l.Lk)("input",{"onUpdate:modelValue":a[1]||(a[1]=e=>h.value=e),id:"srcTitle",placeholder:"제목입력"},null,512),[[n.Jo,h.value]])]),E,(0,l.Lk)("div",null,[k,(0,l.bo)((0,l.Lk)("textarea",{"onUpdate:modelValue":a[2]||(a[2]=e=>y.value=e),id:"srcContents",cols:"54",rows:"10",placeholder:"내용입력"},null,512),[[n.Jo,y.value]])]),(0,l.Lk)("div",null,[(0,l.Lk)("span",null,"첨부파일:"+(0,o.v_)(S.value),1)])]),(0,l.Lk)("div",{class:"divRightAlign"},[(0,l.Lk)("button",{type:"button",id:"searchBtn",onClick:_},"수정"),(0,l.Lk)("button",{type:"button",id:"searchBtn",onClick:f},"목록")])])]),(0,l.bF)(c.A)],64))}};const T=A;var h=T}}]);
//# sourceMappingURL=589.3a83267a.js.map