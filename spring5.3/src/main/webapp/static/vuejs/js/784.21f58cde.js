"use strict";(self["webpackChunkvue_test"]=self["webpackChunkvue_test"]||[]).push([[784],{7211:function(e,a,t){t.d(a,{X:function(){return l}});const l=e=>e.replaceAll("&lt;","<").replaceAll("&gt;",">").replaceAll("&#40;","(").replaceAll("&#41;",")")},7784:function(e,a,t){t.r(a),t.d(a,{default:function(){return g}});var l=t(6768),n=t(4232),u=t(8928),o=t(2982),d=t(144),c=t(8355),i=t(782),s=t(1387),r=t(7211);const v={key:0,style:{padding:"10px"}},L={key:1};var p={__name:"boardDetail",setup(e){const a=(0,i.Pj)(),t=(0,s.rd)(),p=(0,s.lq)();(0,l.KC)((()=>{t.isReady(),m(),console.log(p.params.pageKey)}));const E=()=>{t.replace("/boardEdit/"+p.params.pageKey)},g=(0,d.KR)(""),k=(0,d.KR)(""),T=(0,d.KR)(""),A=(0,d.KR)(""),_=(0,d.KR)([]),h=(0,d.KR)(!1),m=async()=>{a.commit("changeLoadingStatus",!0),await c.A.post("/boardOne.do",{SEQ:p.params.pageKey}).then((e=>{console.log("조회결과",e.data.RESULT_DATA),g.value=e.data.RESULT_DATA.CODE_NAME,k.value=(0,r.X)(e.data.RESULT_DATA.TITLE),T.value=(0,r.X)(e.data.RESULT_DATA.BODY_TEXT),A.value=e.data.RESULT_DATA.FILE_NAME,_.value=e.data.RESULT_LIST,e.data.RESULT_LIST.length>0&&(h.value=!0)})).catch((e=>{console.error("에러",e)})),a.commit("changeLoadingStatus",!1)},R=()=>""==k.value?(alert("제목이 없어"),document.getElementById("detailTitle").focus(),!1):""==T.value?(alert("내용이 없어"),document.getElementById("detailContents").focus(),!1):(a.commit("changeLoadingStatus",!0),c.A.post("/updateBoardOne.do",{seq:p.params.pageKey,title:k.value,bodyText:T.value}).then((e=>{if(console.log("조회결과",e.data),"0000"!=e.data.RESCODE)return alert(e.data.RESMSG),!1;t.go(-1)})).catch((e=>{alert(e),console.error("에러",e)})),a.commit("changeLoadingStatus",!1),void console.log(k.value,T.value)),S=()=>{t.go(-1)};return(e,a)=>((0,l.uX)(),(0,l.CE)(l.FK,null,[(0,l.bF)(u.A,{title:"게시판"}),(0,l.Lk)("div",null,[(0,l.Lk)("main",null,[(0,l.Lk)("div",null,[(0,l.eW)(" 글상세 "),(0,l.Lk)("button",{type:"button",id:"searchBtn",onClick:E},"수정")]),(0,l.Lk)("div",null,[(0,l.Lk)("h2",null,"분류:"+(0,n.v_)(g.value),1),(0,l.Lk)("h3",null,"제목:"+(0,n.v_)(k.value),1)]),(0,l.Lk)("div",null,[(0,l.Lk)("p",null,"내용:"+(0,n.v_)(T.value),1),(0,l.Lk)("h4",null,"첨부파일:"+(0,n.v_)(A.value),1)]),h.value?((0,l.uX)(),(0,l.CE)("div",v,"댓글")):(0,l.Q3)("",!0),h.value?((0,l.uX)(),(0,l.CE)("div",L,[((0,l.uX)(!0),(0,l.CE)(l.FK,null,(0,l.pI)(_.value,(e=>((0,l.uX)(),(0,l.CE)("div",{key:e},[(0,l.Lk)("h4",null,"제목:"+(0,n.v_)(e.TITLE),1),(0,l.Lk)("p",null,"내용:"+(0,n.v_)(e.BODY_TEXT),1)])))),128))])):(0,l.Q3)("",!0),(0,l.Lk)("div",{class:"divRightAlign"},[(0,l.Lk)("button",{type:"button",id:"searchBtn",onClick:R},"추가"),(0,l.Lk)("button",{type:"button",id:"searchBtn",onClick:S},"목록")])])]),(0,l.bF)(o.A)],64))}};const E=p;var g=E}}]);
//# sourceMappingURL=784.21f58cde.js.map