webpackJsonp([121],{"5Bev":function(n,e,t){"use strict";function a(n){t("K7jj")}Object.defineProperty(e,"__esModule",{value:!0});var i=t("8pLc"),r=t("pKZN"),o=(i.a,r.a,{components:{noData:i.a,back:r.a},data:function(){return{search:this.$route.query.desname||"",show3:!1,inputFlag:!1,page2:1,doctors:null,haveMore2:!0,temp:!1,scrollTopDistance:0,isFirstIn:!0}},beforeDestroy:function(){window.onscroll=null,this.$refs.el3.style.overflowY="auto"},beforeRouteLeave:function(n,e,t){window.onscroll=null,e.meta.keepAlive&&(e.meta.scollTopPosition=this.scrollTopDistance),"/cooperate/orgidIntro"==n.path&&this.$store.dispatch("pushIsFresh",!0),this.sortIdx=null,this.$refs.el3.style.overflowY="auto",t()},beforeRouteEnter:function(n,e,t){t(function(t){t.getMore(),t.reload(),t.$refs.el3.style.overflowY="auto";var a=!0;"/doctor-index"!=e.path&&"/nurse-index"!=e.path||(a=!1),t.isFirstIn&&(t.isFirstIn=!1),a||t.$store.state.isFresh?(t.$store.dispatch("pushIsFresh",!1),t.page2=1,t.haveMore2=!0,t.temp=!1,n.meta.scollTopPosition=0,t.search=t.$route.query.desname||"",t.doctors=null,t.pull(!1)):setTimeout(function(){t.$refs.el3.scrollTop=n.meta.scollTopPosition},1)})},created:function(){this.pull(!1)},mounted:function(){this.getMore(),this.reload()},methods:{searchFor:function(){this.inputFlag||(this.page2=1,this.pull(!1))},pull:function(n){var e=this;this.showTab=!1,this.$post("/PlatFormAPI/SpecialistHosGroup/QuerySpecialistHosGroupMemberPage",{KeyName:this.search,SpecialistHosGroupId:this.$route.query.id,IsHead:-1,IsSpecialReferral:-1,PageIndex:this.page2,PageSize:15}).then(function(t){e.show3=!1,n?(t.ReturnData=t.ReturnData||[],t.ReturnData.forEach(function(n){e.doctors.push(n)}),e.temp=!0):(e.$refs.el3.scrollTop=0,e.doctors=t.ReturnData||[],e.temp=!0),!t.ReturnData||t.ReturnData.length<15?e.haveMore2=!1:e.haveMore2=!0})},goIndex:function(n){2==n.DrType?this.$router.push("/nurse-index?DrId="+n.DrId):this.$router.push("/doctor-index?DrId="+n.DrId)},getMore:function(){var n=this,e=this.$refs.el3,t=null;e.onscroll=function(){clearTimeout(t),t=setTimeout(function(){var t=e.scrollTop||e.pageYOffset||e.scrollTop;e.scrollHeight,window.innerHeight;n.scrollTopDistance=t},100)}},reload:function(){var n=this,e=this.$refs.el3,t=null,a=null;e.addEventListener("touchstart",function(n){try{var e=n.touches[0],i=Number(e.pageX),r=Number(e.pageY);t=i,a=r}catch(n){alert(n)}}),e.addEventListener("touchmove",function(t){var i=t.touches[0],r=e.scrollTop,o=e.scrollHeight,d=e.offsetHeight,l=o-d;0===r?(i.clientY>a&&t.preventDefault(),i.clientY-a>30&&(n.show3=!0)):r===l&&i.clientY<a&&t.preventDefault(),o-r-d<100&&n.haveMore2&&n.temp&&i.clientY<a&&(n.temp=!1,n.page2+=1,n.pull(!0))}),e.addEventListener("touchend",function(t){var i=t.changedTouches[0],r=e.scrollTop;n.show3&&0==r&&i.clientY>a&&(n.page2=1,n.haveMore2=!0,n.pull(!1))})}}}),d=function(){var n=this,e=n.$createElement,a=n._self._c||e;return a("div",{staticClass:"pdt"},[a("div",{staticClass:"title"},[a("back"),n._v(" "),a("span",{staticClass:"name"},[n._v("专科团队")]),n._v(" "),a("a")],1),n._v(" "),a("div",{ref:"el2"},[a("div",{staticClass:"headBox"},[a("div",{staticClass:"search-c"},[a("div",[a("input",{directives:[{name:"model",rawName:"v-model",value:n.search,expression:"search"}],attrs:{type:"text",placeholder:"输入医院、医生、科室"},domProps:{value:n.search},on:{input:[function(e){e.target.composing||(n.search=e.target.value)},n.searchFor],keyup:function(e){return!e.type.indexOf("key")&&n._k(e.keyCode,"enter",13,e.key,"Enter")?null:n.searchFor(e)},compositionend:function(e){n.inputFlag=!1},compositionstart:function(e){n.inputFlag=!0}}})]),n._v(" "),a("span",{staticClass:"btn",on:{click:n.searchFor}},[a("i"),n._v("\n                \t搜索\n                ")])])]),n._v(" "),a("div",{ref:"el3",staticClass:"content content-1"},[n.show3?a("p",{staticClass:"reload"},[n._v("下拉刷新列表")]):n._e(),n._v(" "),n._l(n.doctors,function(e,i){return a("div",{key:i,staticClass:"doctorCard-c",on:{click:function(t){return n.goIndex(e)}}},[a("div",{staticClass:"info-pic"},[e.PicturePath?a("img",{attrs:{src:e.PicturePath}}):a("img",{attrs:{src:t("2XyM")}})]),n._v(" "),a("div",{staticClass:"info"},[1==e.DrStatus?a("p",{staticClass:"count"},[n._m(0,!0)]):n._e(),n._v(" "),a("p",{staticClass:"line"},[a("span",{staticClass:"name"},[n._v(n._s(e.DrName))]),n._v(" "),a("span",{staticClass:"pname"},[n._v(n._s(e.PositionName)+" | "+n._s(e.DepartmentName))])]),n._v(" "),a("p",{staticClass:"gray"},[a("span",{staticClass:"l"},[n._v(n._s(e.HospitalName))])]),n._v(" "),e.DoctorSkill?a("p",{staticClass:"skill"},[n._v("擅长："+n._s(e.DoctorSkill))]):n._e(),n._v(" "),a("p",{staticClass:"skill skill-1"},[a("span",[n._v("问诊量：")]),a("i",[n._v(n._s(e.ManuInterVolume))]),n._v(" "),a("span",[n._v("关注量：")]),a("i",[n._v(n._s(e.ManuPatientAttentDrNum))])]),n._v(" "),a("p",{staticClass:"s-items"},[n._l(e.DrServiceItems,function(e,t){return a("span",{key:t,staticClass:"service"},[n._v("\n                            "+n._s(e.SericeItemName)+"\n                        ")])}),e.IsHasPack?a("span",{staticClass:"service"},[n._v("\n                                   服务包\n                        ")]):n._e()],2)])])}),n._v(" "),!n.haveMore2&&n.doctors.length?a("p",{staticClass:"reload"},[n._v("已显示全部内容")]):n._e(),n._v(" "),a("p",{directives:[{name:"show",rawName:"v-show",value:n.page2>1&&!n.temp,expression:"(page2 > 1) && !temp"}],staticClass:"reload"},[n._v("正在加载")]),n._v(" "),n.doctors&&0==n.doctors.length?a("no-data",{attrs:{txt:"暂无符合条件的医生"}}):n._e()],2)])])},l=[function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("i",{staticClass:"legalize"},[t("i",[n._v("已认证")])])}],s={render:d,staticRenderFns:l},c=s,f=t("C7Lr"),p=a,b=f(o,c,!1,p,"data-v-2ae8d305",null);e.default=b.exports},K7jj:function(n,e,t){var a=t("WiB2");"string"==typeof a&&(a=[[n.i,a,""]]),a.locals&&(n.exports=a.locals);t("FIqI")("53abfb86",a,!0,{})},WiB2:function(n,e,t){var a=t("L4zZ");e=n.exports=t("UTlt")(!1),e.push([n.i,"\n.search-c[data-v-2ae8d305] {\n  background: #fff;\n  height: 1.33333rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  padding: 0 0.4rem;\n  font-size: 0.37333rem;\n}\n.search-c .area[data-v-2ae8d305] {\n    display: block;\n    margin-right: 0.13333rem;\n    width: 2.13333rem;\n    color: #333;\n}\n.search-c .area i[data-v-2ae8d305] {\n      display: inline-block;\n      position: relative;\n      top: -0.05333rem;\n      min-width: 0.2rem;\n      min-height: 0.2rem;\n      background: url("+a(t("F2I8"))+") no-repeat center;\n      background-size: contain;\n      margin-left: 0.13333rem;\n}\n.search-c > div[data-v-2ae8d305] {\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n}\n.search-c > div input[data-v-2ae8d305] {\n      height: 0.8rem;\n      border: 1px solid #ececec;\n      border-radius: 0.4rem;\n      padding: 0 1em;\n      min-width: 4rem;\n      width: 98%;\n}\n.search-c .btn[data-v-2ae8d305] {\n    display: block;\n    width: 1.33333rem;\n    height: 0.77333rem;\n    line-height: 0.85333rem;\n    margin-left: 0.26667rem;\n    background: #3069CF;\n    text-align: center;\n    border-radius: 0.4rem;\n    color: #fff;\n}\n.search[data-v-2ae8d305] {\n  background: #fff;\n  height: 1.6rem;\n  padding: 0 0.13333rem 0 0.4rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n}\n.search input[data-v-2ae8d305] {\n    height: 0.8rem;\n    border: 1px solid #ececec;\n    width: 75%;\n    border-radius: 30px;\n    padding: 0 0.4rem;\n}\n.search span[data-v-2ae8d305] {\n    -webkit-box-flex: 0;\n    -webkit-flex: none;\n            flex: none;\n    margin-right: 0.13333rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    height: 100%;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    color: #333;\n}\n.search span i[data-v-2ae8d305] {\n      display: block;\n      min-width: 0.2rem;\n      min-height: 0.2rem;\n      background: url("+a(t("F2I8"))+") no-repeat center;\n      background-size: contain;\n      margin-left: 0.13333rem;\n}\n.search .sBtn[data-v-2ae8d305] {\n    width: 1.6rem;\n    height: 0.8rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n    background: #306bce;\n    color: #fff;\n    border-radius: 30px;\n    margin-left: 0.26667rem;\n}\n.blue[data-v-2ae8d305] {\n  background: #3069cf;\n}\n.blue .name[data-v-2ae8d305] {\n    color: #fff;\n}\n.headBox[data-v-2ae8d305] {\n  position: fixed;\n  z-index: 2;\n  left: 0;\n  right: 0;\n}\n.headBox .chooseOn[data-v-2ae8d305] {\n    color: #3069cf;\n}\n.headBox .depOn[data-v-2ae8d305] {\n    background: #fff;\n}\n.headBox .sortOn[data-v-2ae8d305] {\n    background: #3069cf !important;\n    color: #fff;\n}\n.sort[data-v-2ae8d305] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  min-height: 0.93333rem;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  background: #fff;\n  font-size: 14px;\n  padding: 0 0.4rem;\n  border-bottom: 1px solid #ececec;\n}\n.sort p[data-v-2ae8d305] {\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n}\n.sort p span[data-v-2ae8d305] {\n      display: block;\n      width: 1.86667rem;\n      text-overflow: ellipsis;\n      overflow: hidden;\n      white-space: nowrap;\n      text-align: center;\n}\n.sort p i[data-v-2ae8d305] {\n      display: block;\n      min-width: 0.2rem;\n      min-height: 0.2rem;\n      background: url("+a(t("F2I8"))+") no-repeat center;\n      background-size: contain;\n      margin-left: 0.13333rem;\n}\n.sort p .on[data-v-2ae8d305] {\n      background: url("+a(t("VqDI"))+") no-repeat center;\n      background-size: contain;\n}\n.hospital[data-v-2ae8d305] {\n  background: #fff;\n  padding: 0 0.4rem;\n  max-height: 9.33333rem;\n  overflow: scroll;\n  -webkit-overflow-scrolling: touch;\n  -webkit-transform: translateZ(0px);\n}\n.hospital p[data-v-2ae8d305] {\n    padding: 0.26667rem 0;\n}\n.Department[data-v-2ae8d305] {\n  background: #fff;\n  max-height: 9.33333rem;\n  overflow: hidden;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n}\n.Department .type[data-v-2ae8d305] {\n    width: 2.66667rem;\n    background: #f3f3f3;\n    overflow: scroll;\n    -webkit-overflow-scrolling: touch;\n    -webkit-transform: translateZ(0px);\n}\n.Department .type p[data-v-2ae8d305] {\n      padding-left: 0.26667rem;\n}\n.Department .list[data-v-2ae8d305] {\n    overflow: scroll;\n    padding-left: 0.26667rem;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    -webkit-overflow-scrolling: touch;\n    -webkit-transform: translateZ(0px);\n}\n.Department p[data-v-2ae8d305] {\n    padding: 0.26667rem 0;\n}\n.else[data-v-2ae8d305] {\n  background: #fff;\n  color: #999;\n  font-size: 12px;\n  font-weight: bold;\n}\n.else .card[data-v-2ae8d305] {\n    padding: 0.26667rem 0.4rem;\n    border-bottom: 1px solid #ececec;\n}\n.else .card p[data-v-2ae8d305] {\n      padding-bottom: 0.26667rem;\n}\n.else .card span[data-v-2ae8d305] {\n      display: inline-block;\n      background: #f3f3f3;\n      padding: 0.06667rem 0.2rem;\n      border-radius: 15px;\n      margin-right: 0.13333rem;\n      margin-bottom: 0.26667rem;\n}\n.else .btnBox[data-v-2ae8d305] {\n    height: 1.2rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    font-size: 14px;\n}\n.else .btnBox p[data-v-2ae8d305] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      -webkit-box-pack: center;\n      -webkit-justify-content: center;\n              justify-content: center;\n}\n.else .btnBox p[data-v-2ae8d305]:last-child {\n        background: #3069cf;\n        color: #fff;\n}\n.mask[data-v-2ae8d305] {\n  position: fixed;\n  top: 0;\n  bottom: 0;\n  right: 0;\n  left: 0;\n  z-index: 1;\n  background: rgba(0, 0, 0, 0.5);\n}\n.content[data-v-2ae8d305] {\n  position: fixed;\n  bottom: 0;\n  top: 3.73333rem;\n  left: 0;\n  right: 0;\n  min-height: 77vh;\n  overflow: scroll;\n  -webkit-overflow-scrolling: touch;\n  -webkit-transform: translateZ(0px);\n}\n.content .reload[data-v-2ae8d305] {\n    text-align: center;\n    color: #999;\n    padding: 0.13333rem 0;\n}\n.content-1[data-v-2ae8d305] {\n  top: 2.66667rem;\n}\n.heig[data-v-2ae8d305] {\n  height: 74.5vh;\n}\n.pdtz[data-v-2ae8d305] {\n  top: 1.33333rem;\n  height: calc(100vh - 1.4rem);\n}\n.doctorCard[data-v-2ae8d305] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  padding: 0.26667rem 0.4rem;\n  background: #fff;\n  border-bottom: 1px solid #ececec;\n  position: relative;\n}\n.doctorCard img[data-v-2ae8d305] {\n    width: 1.6rem;\n    height: 1.6rem;\n    border-radius: 10px;\n    object-fit: cover;\n    box-shadow: 0 0 5px #ccc;\n}\n.doctorCard .info[data-v-2ae8d305] {\n    margin-left: 0.26667rem;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    margin-top: -0.06667rem;\n}\n.doctorCard .count[data-v-2ae8d305] {\n    font-size: 12px;\n    position: absolute;\n    right: 0;\n    top: 0.4rem;\n}\n.doctorCard .count .rz[data-v-2ae8d305] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      background: -webkit-linear-gradient(left, #00c7ff, #009fff);\n      background: linear-gradient(90deg, #00c7ff, #009fff);\n      color: #fff;\n      max-width: 1.86667rem;\n      padding: 0 0.26667rem;\n      -webkit-box-pack: center;\n      -webkit-justify-content: center;\n              justify-content: center;\n      border-radius: 20px 0 0 20px;\n      height: 0.46667rem;\n}\n.doctorCard .name[data-v-2ae8d305] {\n    font-size: 17px;\n    font-weight: bolder;\n}\n.doctorCard .atNum[data-v-2ae8d305] {\n    font-size: 12px;\n    color: #333;\n    margin-bottom: 0.13333rem;\n}\n.doctorCard .atNum span[data-v-2ae8d305] {\n      margin-right: 0.26667rem;\n}\n.doctorCard .atNum span span[data-v-2ae8d305] {\n        color: #fea514;\n}\n.doctorCard .line[data-v-2ae8d305] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    padding-right: 1.73333rem;\n    font-size: 12px;\n    margin-bottom: 0.10667rem;\n    -webkit-box-pack: justify;\n    -webkit-justify-content: space-between;\n            justify-content: space-between;\n}\n.doctorCard .line span[data-v-2ae8d305] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n}\n.doctorCard .line .pname[data-v-2ae8d305] {\n      position: relative;\n      top: 0.08rem;\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n}\n.doctorCard .line .DepartmentName[data-v-2ae8d305] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n}\n.doctorCard .line .PositionName[data-v-2ae8d305] {\n      color: #333;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      -webkit-box-flex: 2;\n      -webkit-flex: 2;\n              flex: 2;\n}\n.doctorCard .gray[data-v-2ae8d305] {\n    color: #999999;\n    -webkit-box-pack: start;\n    -webkit-justify-content: flex-start;\n            justify-content: flex-start;\n    padding: 0;\n}\n.doctorCard .gray span[data-v-2ae8d305] {\n      -webkit-box-flex: 0;\n      -webkit-flex: none;\n              flex: none;\n}\n.doctorCard .gray span[data-v-2ae8d305]:first-child {\n        display: block;\n        width: 26%;\n}\n.doctorCard .gray span[data-v-2ae8d305]:last-child {\n        width: 66%;\n        white-space: nowrap;\n}\n.doctorCard .gray1[data-v-2ae8d305] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-align-content: center;\n            align-content: center;\n    font-size: 12px;\n    margin-bottom: 0.13333rem;\n    color: #999999;\n}\n.doctorCard .gray1 .l[data-v-2ae8d305] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      color: #333;\n}\n.doctorCard .gray1 .r[data-v-2ae8d305] {\n      width: 1.6rem;\n      text-align: right;\n      font-size: 0.42667rem;\n      color: #ffa417;\n      white-space: nowrap;\n}\n.doctorCard .service[data-v-2ae8d305] {\n    font-size: 10px;\n    color: #3069cf;\n    border: 1px solid #ccc;\n    border-radius: 10px;\n    text-align: center;\n    padding: 0 0.13333rem;\n    display: inline-block;\n    margin-right: 0.13333rem;\n    white-space: nowrap;\n}\n.doctorCard .skill[data-v-2ae8d305] {\n    color: #999999;\n    font-size: 12px;\n    width: 7.06667rem;\n    text-overflow: ellipsis;\n    overflow: hidden;\n    -webkit-line-clamp: 2;\n    display: -webkit-box;\n    -webkit-box-orient: vertical;\n    margin: 0.10667rem 0;\n}\n.doctorCard .skill > span[data-v-2ae8d305] {\n      color: #666;\n}\n.doctorCard .skill > i[data-v-2ae8d305] {\n      color: #FFA417;\n      font-style: normal;\n}\n.doctorCard .skill > span[data-v-2ae8d305]:nth-of-type(2) {\n      margin-left: 0.53333rem;\n}\n.doctorCard .s-items > span[data-v-2ae8d305] {\n    margin-bottom: 0.13333rem;\n}\n.doctorCard-c[data-v-2ae8d305] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  padding: 0.26667rem 0.4rem;\n  background: #fff;\n  border-bottom: 1px solid #ececec;\n}\n.doctorCard-c .info-pic[data-v-2ae8d305] {\n    text-align: center;\n    width: 1.6rem;\n}\n.doctorCard-c .info-pic img[data-v-2ae8d305] {\n      max-width: 1.6rem;\n      min-width: 1.6rem;\n      max-height: 1.6rem;\n      min-height: 1.6rem;\n      border-radius: 0.26667rem;\n      object-fit: cover;\n      overflow: hidden;\n      margin: 0 auto;\n      /* For Firefox3.6+ */\n      /* For Chrome5+, Safari5+ */\n      box-shadow: 0 0 5px #ccc;\n      /* For Latest Opera */\n}\n.doctorCard-c .info[data-v-2ae8d305] {\n    margin-left: 0.26667rem;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    position: relative;\n    top: -0.08rem;\n    font-size: 0.32rem;\n}\n.doctorCard-c .count[data-v-2ae8d305] {\n    font-size: 12px;\n    position: absolute;\n    right: 0;\n    top: 0;\n}\n.doctorCard-c .count span[data-v-2ae8d305] {\n      color: #fea514;\n}\n.doctorCard-c .count .legalize[data-v-2ae8d305] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      position: relative;\n      top: 0.10667rem;\n      right: -0.4rem;\n      color: #fefefe;\n}\n.doctorCard-c .count .legalize > i[data-v-2ae8d305] {\n        display: block;\n        width: 1.33333rem;\n        height: 0.45333rem;\n        font-size: 0.26667rem;\n        font-style: normal;\n        background-size: 100% auto;\n        text-align: center;\n        line-height: 0.50667rem;\n        border-top-left-radius: 0.22667rem;\n        border-bottom-left-radius: 0.22667rem;\n        background: -webkit-gradient(linear, 0 100%, 0 0, from(#00c7ff), to(#009fff));\n        background: -webkit-linear-gradient(90deg, #00c7ff, #009fff);\n        background: -webkit-linear-gradient(left, #00c7ff, #009fff);\n        background: linear-gradient(90deg, #00c7ff, #009fff);\n}\n.doctorCard-c .name[data-v-2ae8d305] {\n    font-size: 17px;\n    font-weight: bolder;\n}\n.doctorCard-c .line[data-v-2ae8d305] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    padding-right: 1.06667rem;\n    font-size: 12px;\n    margin-bottom: 0.10667rem;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n}\n.doctorCard-c .line span[data-v-2ae8d305] {\n      white-space: nowrap;\n      overflow: hidden;\n      text-overflow: ellipsis;\n}\n.doctorCard-c .line .name[data-v-2ae8d305] {\n      width: auto;\n      color: #333;\n      margin-right: 0.4rem;\n}\n.doctorCard-c .line .DepartmentName[data-v-2ae8d305] {\n      color: #333;\n      position: relative;\n      top: 0.08rem;\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      font-size: 0.32rem;\n}\n.doctorCard-c .line .pname[data-v-2ae8d305] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      position: relative;\n      top: 0.06667rem;\n      flex: 1;\n      color: #333;\n      font-size: 0.34667rem;\n}\n.doctorCard-c .gray[data-v-2ae8d305] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-align-content: center;\n            align-content: center;\n    font-size: 0.32rem;\n    margin-bottom: 0.10667rem;\n    color: #333;\n}\n.doctorCard-c .gray .l[data-v-2ae8d305] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n}\n.doctorCard-c .gray .l-sec[data-v-2ae8d305] {\n      -webkit-box-flex: 0;\n      -webkit-flex: none;\n              flex: none;\n      width: 4.8rem;\n      overflow: hidden;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n}\n.doctorCard-c .gray .r[data-v-2ae8d305] {\n      width: 2.53333rem;\n      text-align: right;\n      font-size: 0.42667rem;\n      color: #ffa417;\n}\n.doctorCard-c .skill[data-v-2ae8d305] {\n    color: #999;\n    font-size: 12px;\n    width: 7.33333rem;\n    text-overflow: ellipsis;\n    overflow: hidden;\n    -webkit-line-clamp: 2;\n    display: -webkit-box;\n    -webkit-box-orient: vertical;\n    margin: 0.10667rem 0;\n}\n.doctorCard-c .skill > span[data-v-2ae8d305] {\n      color: #666;\n}\n.doctorCard-c .skill > i[data-v-2ae8d305] {\n      color: #FFA417;\n}\n.doctorCard-c .skill > span[data-v-2ae8d305]:nth-of-type(2) {\n      margin-left: 0.53333rem;\n}\n.doctorCard-c .skill-1[data-v-2ae8d305] {\n    margin-bottom: 0.13333rem;\n}\n.doctorCard-c .skill-1 > span[data-v-2ae8d305] {\n      color: #666;\n}\n.doctorCard-c .s-items > span[data-v-2ae8d305] {\n    margin-bottom: 0.13333rem;\n}\n.doctorCard-c .service[data-v-2ae8d305] {\n    font-size: 10px;\n    color: #3069cf;\n    border: 1px solid #ccc;\n    border-radius: 10px;\n    text-align: center;\n    padding: 0 0.13333rem;\n    display: inline-block;\n    margin-right: 0.18667rem;\n}\n",""])}});