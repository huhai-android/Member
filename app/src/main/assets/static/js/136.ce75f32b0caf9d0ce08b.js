webpackJsonp([136],{"4qas":function(e,n,t){"use strict";function i(e){t("CNxh")}Object.defineProperty(n,"__esModule",{value:!0});var a=t("8pLc"),o=t("pKZN"),s=(a.a,o.a,{components:{noData:a.a,back:o.a},data:function(){return{search:"",areaMsg:null,sortList:["全部医院","病症","排序","筛选"],sortList2:[{SortField:"MedicalRecord",name:"问诊量"},{SortField:"MedicalPrice",name:"价格"}],sortList3:[{code:"1101",name:"图文问诊"},{code:"1102",name:"视频问诊"},{code:"1103",name:"名医续方"}],sortList4:[{code:"1",name:"有服务包"},{code:"0",name:"无服务包"}],sortIdx:null,page:1,page2:1,hospital:[],Department:[],secDepartment:[],HospitalLevels:[],Positions:[],doctors:null,haveMore:!0,haveMore2:!0,hos:{HospitalId:-1},dep:null,secDep:{DepartmentId:-1},sort:null,IsHasPack:-1,SericeItemCode:null,HospitalLevel:null,PositionPick:null,hsotMsg:null,disease:[],secDisease:[],dise:null,secDise:null,page3:1,haveMore3:!0,pageShow:!1,scrollTopDistance:0,DiseaseId:this.$route.query.DiseaseId,name:this.$route.query.name,DiseaseTypeId:this.$route.query.DiseaseTypeId}},watch:{sortIdx:function(e){this.$refs.el3.style=null===e?"overflow-y: auto;":"overflow-y: hidden;"}},created:function(){this.$route.query.servicePackage&&(this.IsHasPack=this.$route.query.servicePackage),this.$route.query.search&&(this.search=this.$route.query.search),this.DiseaseId&&this.name&&(this.secDise={DiseaseId:this.DiseaseId,DiseaseName:this.name},this.sortList[1]=this.name),this.DiseaseTypeId&&(this.dise={DiseaseTypeId:this.DiseaseTypeId}),this.pullDisease(!1),this.pull(!1),this.pullHospital(!1),this.pullElse(),this.dise&&this.pullSecDisea()},mounted:function(){this.getMore()},beforeDestroy:function(){window.onscroll=null},beforeRouteLeave:function(e,n,t){window.onscroll=null,n.meta.keepAlive&&(n.meta.scollTopPosition=this.scrollTopDistance),t()},beforeRouteEnter:function(e,n,t){t(function(t){t.getMore(),t.hos={HospitalId:-1},t.IsHasPack=-1,t.SericeItemCode=null,t.HospitalLevel=null,t.PositionPick=null,t.dise=null,t.secDise=null,t.$store.state.area&&(t.areaMsg&&t.areaMsg.AreaId==t.$store.state.area.AreaId||(t.areaMsg=t.$store.state.area,t.page2=1,t.haveMore2=!0,e.meta.scollTopPosition=0,t.doctors=[],t.pageShow=!1,t.pull(!1))),(n.path!=t.$store.state.prevUrl&&null!=t.$store.state.prevUrl||t.$store.state.isFresh||"/introduction"==n.path)&&(t.DiseaseId=t.$route.query.DiseaseId,t.name=t.$route.query.name,t.DiseaseTypeId=t.$route.query.DiseaseTypeId,t.secDise={DiseaseId:t.DiseaseId,DiseaseName:t.name},t.sortList[0]="全部医院",t.sortList[1]=t.name||"病症",t.dise={DiseaseTypeId:t.DiseaseTypeId},t.page2=1,t.haveMore2=!0,e.meta.scollTopPosition=0,t.doctors=[],t.pageShow=!1,t.pull(!1)),setTimeout(function(){t.$refs.el3.scrollTop=e.meta.scollTopPosition},1)})},methods:{sortDoc:function(e){this.sortIdx===e?this.sortIdx=null:this.sortIdx=e},searchFor:function(){this.page2=1,this.pull(!1)},pullHospital:function(e){var n=this;this.$post("/PlatFormAPI/Doctor/QueryDrHospitalPage",{HospitalLevel:-1,AreaId:this.areaMsg?this.areaMsg.AreaId:0,PageIndex:this.page,PageSize:15}).then(function(t){!t.ReturnData||t.ReturnData.length<15?n.haveMore=!1:n.haveMore=!0,e?t.ReturnData.forEach(function(e){n.hospital.push(e)}):n.hospital=t.ReturnData})},pullDisease:function(e){var n=this;this.$get("/SecondDiagnosisAPI/MedicalService/QueryAllDiseaseType").then(function(e){n.disease=e})},pullElse:function(){var e=this;this.$post("/PlatFormAPI/Category/QueryCategoryItem",{CategoryIds:[9,10]}).then(function(n){e.Positions=n[0].Items,e.HospitalLevels=n[1].Items})},pull:function(e){var n=this;this.$post("SecondDiagnosisAPI/MedicalRecord/QueryMedicalDoctorInfoByPage",{KeyName:this.search,DrType:1,HospitalId:this.hos.HospitalId,DiseaseId:this.secDise?this.secDise.DiseaseId:-1,Position:this.PositionPick?this.PositionPick.CategoryItemValue:-1,OrgId:this.areaMsg?this.areaMsg.OrgId:-1,HosGroupId:-1,IsHasPack:this.IsHasPack,HospitalLevel:this.HospitalLevel?this.HospitalLevel.CategoryItemValue:-1,SericeItemCode:null,PageIndex:this.page2,PageSize:15,AreaId:this.areaMsg?this.areaMsg.AreaId:0,SortField:this.sort?this.sort.SortField:""}).then(function(t){!t.ReturnData||t.ReturnData.length<15?n.haveMore2=!1:n.haveMore2=!0,e?t.ReturnData.forEach(function(e){n.doctors.push(e)}):n.doctors=t.ReturnData,n.pageShow=!0})},hosPick:function(e){this.hos=e,this.sortList[0]=e.HospitalName,this.sortIdx=null,this.page2=1,this.pull(!1)},disePick:function(e){-1==e.DiseaseTypeId?(this.dise=e,this.sortList[1]=e.DiseaseName,this.sortIdx=null,this.page2=1,this.secDise=null,this.secDisease=[],this.pull(!1)):(this.dise=e,this.pullSecDisea())},secDisePic:function(e){this.secDise=e,this.sortList[1]=e.DiseaseName,this.sortIdx=null,this.pull(!1)},pullSecDisea:function(){var e=this;this.$get("/SecondDiagnosisAPI/MedicalService/QueryDiseaseByTypeId",{DiseaseTypeId:this.dise.DiseaseTypeId}).then(function(n){e.secDisease=n})},sortPick:function(e){this.sort=e,this.sortList[2]=e.name,this.sortIdx=null,this.page2=1,this.pull(!1)},reset:function(){this.IsHasPack=-1,this.SericeItemCode=null,this.HospitalLevel=null,this.PositionPick=null},confirm:function(){this.sortIdx=null,this.page2=1,this.pull(!1)},getMore:function(){var e=this,n=this.$refs.el,t=null;n.onscroll=function(){clearTimeout(t),t=setTimeout(function(){var t=n.scrollTop||n.pageYOffset||n.scrollTop;n.scrollHeight-t-n.offsetHeight<100&&e.haveMore&&(e.page+=1,e.pullHospital(!0))},100)};var i=this.$refs.el3,a=null;i.onscroll=function(){clearTimeout(a),a=setTimeout(function(){var n=i.scrollTop||i.pageYOffset||i.scrollTop,t=i.scrollHeight,a=window.innerHeight;e.scrollTopDistance=n,t-n-a<100&&e.haveMore2&&(e.page2+=1,e.loading=!0,e.pull(!0))},100)}}}}),r=function(){var e=this,n=e.$createElement,i=e._self._c||n;return i("div",{ref:"el2",staticClass:"pdt"},[i("div",{staticClass:"title"},[i("back"),e._v(" "),i("span",{staticClass:"name"},[e._v("找医生")]),e._v(" "),i("a")],1),e._v(" "),i("div",{staticClass:"headBox"},[i("div",{staticClass:"search"},[i("span",{on:{click:function(n){return e.$router.push("/area")}}},[e._v(e._s(e.areaMsg?e.areaMsg.AreaName:"选择地区")),i("i")]),e._v(" "),i("input",{directives:[{name:"model",rawName:"v-model",value:e.search,expression:"search"}],attrs:{type:"search",placeholder:"输入医院、医生、疾病"},domProps:{value:e.search},on:{keyup:function(n){return!n.type.indexOf("key")&&e._k(n.keyCode,"enter",13,n.key,"Enter")?null:e.searchFor(n)},input:function(n){n.target.composing||(e.search=n.target.value)}}}),e._v(" "),i("span",{staticClass:"sBtn",on:{click:e.searchFor}},[e._v("搜索")])]),e._v(" "),i("div",{staticClass:"sort"},e._l(e.sortList,function(n,t){return i("p",{key:t,on:{click:function(n){return e.sortDoc(t)}}},[i("span",[e._v(e._s(n))]),e._v(" "),i("i",{class:{on:e.sortIdx===t}})])}),0),e._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:0===e.sortIdx,expression:"sortIdx === 0"}],ref:"el",staticClass:"hospital"},[i("p",{class:{chooseOn:e.hos&&-1==e.hos.HospitalId},on:{click:function(n){return e.hosPick({HospitalId:-1,HospitalName:"全部医院"})}}},[e._v("全部医院")]),e._v(" "),e._l(e.hospital,function(n,t){return i("p",{key:t,class:{chooseOn:e.hos&&e.hos.HospitalId==n.HospitalId},on:{click:function(t){return e.hosPick(n)}}},[e._v("\n\t\t\t\t\t"+e._s(n.HospitalName)+"\n\t\t\t\t")])})],2),e._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:1===e.sortIdx,expression:"sortIdx === 1"}],staticClass:"Department"},[i("div",{staticClass:"type"},[i("p",{class:{chooseOn:e.dise&&-1==e.dise.DiseaseTypeId},on:{click:function(n){return e.disePick({DiseaseTypeId:-1,DiseaseName:"全部病症"})}}},[e._v("全部病症")]),e._v(" "),e._l(e.disease,function(n,t){return i("p",{key:t,class:{chooseOn:e.dise&&e.dise.DiseaseTypeId==n.DiseaseTypeId},on:{click:function(t){return e.disePick(n)}}},[e._v("\n\t\t\t\t\t\t"+e._s(n.DiseaseTypeName)+"\n\t\t\t\t\t")])})],2),e._v(" "),i("div",{staticClass:"list"},e._l(e.secDisease,function(n,t){return i("p",{key:t,class:{chooseOn:e.secDise&&e.secDise.DiseaseId==n.DiseaseId},on:{click:function(t){return e.secDisePic(n)}}},[e._v("\n\t\t\t\t\t\t"+e._s(n.DiseaseName)+"\n\t\t\t\t\t")])}),0)]),e._v(" "),2===e.sortIdx?i("div",{staticClass:"hospital"},e._l(e.sortList2,function(n,t){return i("p",{key:t,class:{chooseOn:e.sort&&e.sort.SortField==n.SortField},on:{click:function(t){return e.sortPick(n)}}},[e._v("\n\t\t\t\t\t"+e._s(n.name)+"\n\t\t\t\t")])}),0):e._e(),e._v(" "),3===e.sortIdx?i("div",{staticClass:"else"},[i("div",{staticClass:"card"},[i("p",[e._v("医院等级")]),e._v(" "),e._l(e.HospitalLevels,function(n,t){return i("span",{key:t,class:{sortOn:e.HospitalLevel&&e.HospitalLevel==n},on:{click:function(t){e.HospitalLevel=n}}},[e._v("\n                        "+e._s(n.CategoryItemName)+"\n                    ")])})],2),e._v(" "),i("div",{staticClass:"card"},[i("p",[e._v("医生职称")]),e._v(" "),e._l(e.Positions,function(n,t){return i("span",{key:t,class:{sortOn:e.PositionPick&&e.PositionPick==n},on:{click:function(t){e.PositionPick=n}}},[e._v("\n                        "+e._s(n.CategoryItemName)+"\n                    ")])})],2),e._v(" "),i("div",{staticClass:"card"},[i("p",[e._v("定制服务包")]),e._v(" "),e._l(e.sortList4,function(n,t){return i("span",{key:t,class:{sortOn:e.IsHasPack&&e.IsHasPack==n.code},on:{click:function(t){e.IsHasPack=n.code}}},[e._v("\n                        "+e._s(n.name)+"\n                    ")])})],2),e._v(" "),i("div",{staticClass:"btnBox"},[i("p",{on:{click:e.reset}},[e._v("重置")]),e._v(" "),i("p",{on:{click:e.confirm}},[e._v("确定")])])]):e._e()]),e._v(" "),e.sortIdx||0===e.sortIdx?i("div",{staticClass:"mask",on:{click:function(n){e.sortIdx=null}}}):e._e(),e._v(" "),i("div",{ref:"el3",staticClass:"content"},[e._l(e.doctors,function(n,a){return i("div",{key:a,staticClass:"doctorCard",on:{click:function(t){return e.$router.push("/secDrIndex?id="+n.DrId)}}},[n.PicturePath?i("img",{attrs:{src:n.PicturePath}}):i("img",{attrs:{src:t("2XyM")}}),e._v(" "),1==n.DrStatus?i("p",{staticClass:"count"},[i("span",{staticClass:"rz"},[e._v("已认证")])]):e._e(),e._v(" "),i("div",{staticClass:"info"},[i("p",{staticClass:"line"},[i("span",{staticClass:"name"},[e._v(e._s(n.DrName))]),e._v(" "),i("span",{staticClass:"pname"},[e._v(e._s(n.PositionName))]),e._v(" "),i("span",{staticClass:"DepartmentName"},[e._v(e._s(n.DepartmentName))])]),e._v(" "),i("p",{staticClass:"gray1"},[i("span",{staticClass:"l"},[e._v(e._s(n.HospitalName))]),e._v(" "),i("span",{staticClass:"r"},[e._v("￥"+e._s(n.MedicalPrice))])]),e._v(" "),n.DoctorSkill?i("p",{staticClass:"skill"},[e._v("擅长："+e._s(n.DoctorSkill))]):e._e(),e._v(" "),i("p",{staticClass:"skill"},[i("span",[e._v("问诊量：")]),i("i",[e._v(e._s(n.ManuInterVolume))]),e._v(" "),i("span",[e._v("关注量：")]),i("i",[e._v(e._s(n.ManuPatientAttentDrNum))])]),e._v(" "),i("p",{staticClass:"s-items"},e._l(n.DrServiceItems,function(n,t){return i("span",{key:t,staticClass:"service"},[e._v("\n                            "+e._s(n.SericeItemName)+"\n                        ")])}),0)])])}),e._v(" "),e.doctors&&0==e.doctors.length&&e.pageShow?i("no-data",{attrs:{txt:"暂无符合条件的医生"}}):e._e()],2)])},l=[],c={render:r,staticRenderFns:l},d=c,p=t("C7Lr"),f=i,m=p(s,d,!1,f,"data-v-1394e295",null);n.default=m.exports},CNxh:function(e,n,t){var i=t("SSm6");"string"==typeof i&&(i=[[e.i,i,""]]),i.locals&&(e.exports=i.locals);t("FIqI")("7473fe6d",i,!0,{})},SSm6:function(e,n,t){var i=t("L4zZ");n=e.exports=t("UTlt")(!1),n.push([e.i,"\n.search-c[data-v-1394e295] {\n  background: #fff;\n  height: 1.33333rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  padding: 0 0.4rem;\n  font-size: 0.37333rem;\n}\n.search-c .area[data-v-1394e295] {\n    display: block;\n    margin-right: 0.13333rem;\n    width: 2.13333rem;\n    color: #333;\n}\n.search-c .area i[data-v-1394e295] {\n      display: inline-block;\n      position: relative;\n      top: -0.05333rem;\n      min-width: 0.2rem;\n      min-height: 0.2rem;\n      background: url("+i(t("F2I8"))+") no-repeat center;\n      background-size: contain;\n      margin-left: 0.13333rem;\n}\n.search-c > div[data-v-1394e295] {\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n}\n.search-c > div input[data-v-1394e295] {\n      height: 0.8rem;\n      border: 1px solid #ececec;\n      border-radius: 0.4rem;\n      padding: 0 1em;\n      min-width: 4rem;\n      width: 98%;\n}\n.search-c .btn[data-v-1394e295] {\n    display: block;\n    width: 1.33333rem;\n    height: 0.77333rem;\n    line-height: 0.85333rem;\n    margin-left: 0.26667rem;\n    background: #3069CF;\n    text-align: center;\n    border-radius: 0.4rem;\n    color: #fff;\n}\n.search[data-v-1394e295] {\n  background: #fff;\n  height: 1.6rem;\n  padding: 0 0.13333rem 0 0.4rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n}\n.search input[data-v-1394e295] {\n    height: 0.8rem;\n    border: 1px solid #ececec;\n    width: 75%;\n    border-radius: 30px;\n    padding: 0 0.4rem;\n}\n.search span[data-v-1394e295] {\n    -webkit-box-flex: 0;\n    -webkit-flex: none;\n            flex: none;\n    margin-right: 0.13333rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    height: 100%;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    color: #333;\n}\n.search span i[data-v-1394e295] {\n      display: block;\n      min-width: 0.2rem;\n      min-height: 0.2rem;\n      background: url("+i(t("F2I8"))+") no-repeat center;\n      background-size: contain;\n      margin-left: 0.13333rem;\n}\n.search .sBtn[data-v-1394e295] {\n    width: 1.6rem;\n    height: 0.8rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n    background: #306bce;\n    color: #fff;\n    border-radius: 30px;\n    margin-left: 0.26667rem;\n}\n.blue[data-v-1394e295] {\n  background: #3069cf;\n}\n.blue .name[data-v-1394e295] {\n    color: #fff;\n}\n.headBox[data-v-1394e295] {\n  position: fixed;\n  z-index: 2;\n  left: 0;\n  right: 0;\n}\n.headBox .chooseOn[data-v-1394e295] {\n    color: #3069cf;\n}\n.headBox .depOn[data-v-1394e295] {\n    background: #fff;\n}\n.headBox .sortOn[data-v-1394e295] {\n    background: #3069cf !important;\n    color: #fff;\n}\n.sort[data-v-1394e295] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  min-height: 0.93333rem;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  background: #fff;\n  font-size: 14px;\n  padding: 0 0.4rem;\n  border-bottom: 1px solid #ececec;\n}\n.sort p[data-v-1394e295] {\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-pack: center;\n    -webkit-justify-content: center;\n            justify-content: center;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n}\n.sort p span[data-v-1394e295] {\n      display: block;\n      width: 1.86667rem;\n      text-overflow: ellipsis;\n      overflow: hidden;\n      white-space: nowrap;\n      text-align: center;\n}\n.sort p i[data-v-1394e295] {\n      display: block;\n      min-width: 0.2rem;\n      min-height: 0.2rem;\n      background: url("+i(t("F2I8"))+") no-repeat center;\n      background-size: contain;\n      margin-left: 0.13333rem;\n}\n.sort p .on[data-v-1394e295] {\n      background: url("+i(t("VqDI"))+") no-repeat center;\n      background-size: contain;\n}\n.hospital[data-v-1394e295] {\n  background: #fff;\n  padding: 0 0.4rem;\n  max-height: 9.33333rem;\n  overflow: scroll;\n  -webkit-overflow-scrolling: touch;\n  -webkit-transform: translateZ(0px);\n}\n.hospital p[data-v-1394e295] {\n    padding: 0.26667rem 0;\n}\n.Department[data-v-1394e295] {\n  background: #fff;\n  max-height: 9.33333rem;\n  overflow: hidden;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n}\n.Department .type[data-v-1394e295] {\n    width: 2.66667rem;\n    background: #f3f3f3;\n    overflow: scroll;\n    -webkit-overflow-scrolling: touch;\n    -webkit-transform: translateZ(0px);\n}\n.Department .type p[data-v-1394e295] {\n      padding-left: 0.26667rem;\n}\n.Department .list[data-v-1394e295] {\n    overflow: scroll;\n    padding-left: 0.26667rem;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    -webkit-overflow-scrolling: touch;\n    -webkit-transform: translateZ(0px);\n}\n.Department p[data-v-1394e295] {\n    padding: 0.26667rem 0;\n}\n.else[data-v-1394e295] {\n  background: #fff;\n  color: #999;\n  font-size: 12px;\n  font-weight: bold;\n}\n.else .card[data-v-1394e295] {\n    padding: 0.26667rem 0.4rem;\n    border-bottom: 1px solid #ececec;\n}\n.else .card p[data-v-1394e295] {\n      padding-bottom: 0.26667rem;\n}\n.else .card span[data-v-1394e295] {\n      display: inline-block;\n      background: #f3f3f3;\n      padding: 0.06667rem 0.2rem;\n      border-radius: 15px;\n      margin-right: 0.13333rem;\n      margin-bottom: 0.26667rem;\n}\n.else .btnBox[data-v-1394e295] {\n    height: 1.2rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    font-size: 14px;\n}\n.else .btnBox p[data-v-1394e295] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      -webkit-box-pack: center;\n      -webkit-justify-content: center;\n              justify-content: center;\n}\n.else .btnBox p[data-v-1394e295]:last-child {\n        background: #3069cf;\n        color: #fff;\n}\n.mask[data-v-1394e295] {\n  position: fixed;\n  top: 0;\n  bottom: 0;\n  right: 0;\n  left: 0;\n  z-index: 1;\n  background: rgba(0, 0, 0, 0.5);\n}\n.content[data-v-1394e295] {\n  position: fixed;\n  bottom: 0;\n  top: 3.73333rem;\n  left: 0;\n  right: 0;\n  min-height: 77vh;\n  overflow: scroll;\n  -webkit-overflow-scrolling: touch;\n  -webkit-transform: translateZ(0px);\n}\n.content .reload[data-v-1394e295] {\n    text-align: center;\n    color: #999;\n    padding: 0.13333rem 0;\n}\n.content-1[data-v-1394e295] {\n  top: 2.66667rem;\n}\n.heig[data-v-1394e295] {\n  height: 74.5vh;\n}\n.pdtz[data-v-1394e295] {\n  top: 1.33333rem;\n  height: calc(100vh - 1.4rem);\n}\n.doctorCard[data-v-1394e295] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  padding: 0.26667rem 0.4rem;\n  background: #fff;\n  border-bottom: 1px solid #ececec;\n  position: relative;\n}\n.doctorCard img[data-v-1394e295] {\n    width: 1.6rem;\n    height: 1.6rem;\n    border-radius: 10px;\n    object-fit: cover;\n    box-shadow: 0 0 5px #ccc;\n}\n.doctorCard .info[data-v-1394e295] {\n    margin-left: 0.26667rem;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    margin-top: -0.06667rem;\n}\n.doctorCard .count[data-v-1394e295] {\n    font-size: 12px;\n    position: absolute;\n    right: 0;\n    top: 0.4rem;\n}\n.doctorCard .count .rz[data-v-1394e295] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      background: -webkit-linear-gradient(left, #00c7ff, #009fff);\n      background: linear-gradient(90deg, #00c7ff, #009fff);\n      color: #fff;\n      max-width: 1.86667rem;\n      padding: 0 0.26667rem;\n      -webkit-box-pack: center;\n      -webkit-justify-content: center;\n              justify-content: center;\n      border-radius: 20px 0 0 20px;\n      height: 0.46667rem;\n}\n.doctorCard .name[data-v-1394e295] {\n    font-size: 17px;\n    font-weight: bolder;\n}\n.doctorCard .atNum[data-v-1394e295] {\n    font-size: 12px;\n    color: #333;\n    margin-bottom: 0.13333rem;\n}\n.doctorCard .atNum span[data-v-1394e295] {\n      margin-right: 0.26667rem;\n}\n.doctorCard .atNum span span[data-v-1394e295] {\n        color: #fea514;\n}\n.doctorCard .line[data-v-1394e295] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    padding-right: 1.73333rem;\n    font-size: 12px;\n    margin-bottom: 0.10667rem;\n    -webkit-box-pack: justify;\n    -webkit-justify-content: space-between;\n            justify-content: space-between;\n}\n.doctorCard .line span[data-v-1394e295] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n}\n.doctorCard .line .pname[data-v-1394e295] {\n      position: relative;\n      top: 0.08rem;\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n}\n.doctorCard .line .DepartmentName[data-v-1394e295] {\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n}\n.doctorCard .line .PositionName[data-v-1394e295] {\n      color: #333;\n      display: -webkit-box;\n      display: -webkit-flex;\n      display: flex;\n      -webkit-box-align: center;\n      -webkit-align-items: center;\n              align-items: center;\n      -webkit-box-flex: 2;\n      -webkit-flex: 2;\n              flex: 2;\n}\n.doctorCard .gray[data-v-1394e295] {\n    color: #999999;\n    -webkit-box-pack: start;\n    -webkit-justify-content: flex-start;\n            justify-content: flex-start;\n    padding: 0;\n}\n.doctorCard .gray span[data-v-1394e295] {\n      -webkit-box-flex: 0;\n      -webkit-flex: none;\n              flex: none;\n}\n.doctorCard .gray span[data-v-1394e295]:first-child {\n        display: block;\n        width: 26%;\n}\n.doctorCard .gray span[data-v-1394e295]:last-child {\n        width: 66%;\n        white-space: nowrap;\n}\n.doctorCard .gray1[data-v-1394e295] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-align-content: center;\n            align-content: center;\n    font-size: 12px;\n    margin-bottom: 0.13333rem;\n    color: #999999;\n}\n.doctorCard .gray1 .l[data-v-1394e295] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      color: #333;\n}\n.doctorCard .gray1 .r[data-v-1394e295] {\n      width: 1.6rem;\n      text-align: right;\n      font-size: 0.42667rem;\n      color: #ffa417;\n      white-space: nowrap;\n}\n.doctorCard .service[data-v-1394e295] {\n    font-size: 10px;\n    color: #3069cf;\n    border: 1px solid #ccc;\n    border-radius: 10px;\n    text-align: center;\n    padding: 0 0.13333rem;\n    display: inline-block;\n    margin-right: 0.13333rem;\n    white-space: nowrap;\n}\n.doctorCard .skill[data-v-1394e295] {\n    color: #999999;\n    font-size: 12px;\n    width: 7.06667rem;\n    text-overflow: ellipsis;\n    overflow: hidden;\n    -webkit-line-clamp: 2;\n    display: -webkit-box;\n    -webkit-box-orient: vertical;\n    margin: 0.10667rem 0;\n}\n.doctorCard .skill > span[data-v-1394e295] {\n      color: #666;\n}\n.doctorCard .skill > i[data-v-1394e295] {\n      color: #FFA417;\n      font-style: normal;\n}\n.doctorCard .skill > span[data-v-1394e295]:nth-of-type(2) {\n      margin-left: 0.53333rem;\n}\n.doctorCard .s-items > span[data-v-1394e295] {\n    margin-bottom: 0.13333rem;\n}\n.doctorCard-c[data-v-1394e295] {\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  padding: 0.26667rem 0.4rem;\n  background: #fff;\n  border-bottom: 1px solid #ececec;\n}\n.doctorCard-c .info-pic[data-v-1394e295] {\n    text-align: center;\n    width: 1.6rem;\n}\n.doctorCard-c .info-pic img[data-v-1394e295] {\n      max-width: 1.6rem;\n      min-width: 1.6rem;\n      max-height: 1.6rem;\n      min-height: 1.6rem;\n      border-radius: 0.26667rem;\n      object-fit: cover;\n      overflow: hidden;\n      margin: 0 auto;\n      /* For Firefox3.6+ */\n      /* For Chrome5+, Safari5+ */\n      box-shadow: 0 0 5px #ccc;\n      /* For Latest Opera */\n}\n.doctorCard-c .info[data-v-1394e295] {\n    margin-left: 0.26667rem;\n    -webkit-box-flex: 1;\n    -webkit-flex: 1;\n            flex: 1;\n    position: relative;\n    top: -0.08rem;\n    font-size: 0.32rem;\n}\n.doctorCard-c .count[data-v-1394e295] {\n    font-size: 12px;\n    position: absolute;\n    right: 0;\n    top: 0;\n}\n.doctorCard-c .count span[data-v-1394e295] {\n      color: #fea514;\n}\n.doctorCard-c .count .legalize[data-v-1394e295] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      position: relative;\n      top: 0.10667rem;\n      right: -0.4rem;\n      color: #fefefe;\n}\n.doctorCard-c .count .legalize > i[data-v-1394e295] {\n        display: block;\n        width: 1.33333rem;\n        height: 0.45333rem;\n        font-size: 0.26667rem;\n        font-style: normal;\n        background-size: 100% auto;\n        text-align: center;\n        line-height: 0.50667rem;\n        border-top-left-radius: 0.22667rem;\n        border-bottom-left-radius: 0.22667rem;\n        background: -webkit-gradient(linear, 0 100%, 0 0, from(#00c7ff), to(#009fff));\n        background: -webkit-linear-gradient(90deg, #00c7ff, #009fff);\n        background: -webkit-linear-gradient(left, #00c7ff, #009fff);\n        background: linear-gradient(90deg, #00c7ff, #009fff);\n}\n.doctorCard-c .name[data-v-1394e295] {\n    font-size: 17px;\n    font-weight: bolder;\n}\n.doctorCard-c .line[data-v-1394e295] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    padding-right: 1.06667rem;\n    font-size: 12px;\n    margin-bottom: 0.10667rem;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n}\n.doctorCard-c .line span[data-v-1394e295] {\n      white-space: nowrap;\n      overflow: hidden;\n      text-overflow: ellipsis;\n}\n.doctorCard-c .line .name[data-v-1394e295] {\n      width: auto;\n      color: #333;\n      margin-right: 0.4rem;\n}\n.doctorCard-c .line .DepartmentName[data-v-1394e295] {\n      color: #333;\n      position: relative;\n      top: 0.08rem;\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      font-size: 0.32rem;\n}\n.doctorCard-c .line .pname[data-v-1394e295] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n      position: relative;\n      top: 0.06667rem;\n      flex: 1;\n      color: #333;\n      font-size: 0.34667rem;\n}\n.doctorCard-c .gray[data-v-1394e295] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-align-content: center;\n            align-content: center;\n    font-size: 0.32rem;\n    margin-bottom: 0.10667rem;\n    color: #333;\n}\n.doctorCard-c .gray .l[data-v-1394e295] {\n      -webkit-box-flex: 1;\n      -webkit-flex: 1;\n              flex: 1;\n}\n.doctorCard-c .gray .l-sec[data-v-1394e295] {\n      -webkit-box-flex: 0;\n      -webkit-flex: none;\n              flex: none;\n      width: 4.8rem;\n      overflow: hidden;\n      text-overflow: ellipsis;\n      white-space: nowrap;\n}\n.doctorCard-c .gray .r[data-v-1394e295] {\n      width: 2.53333rem;\n      text-align: right;\n      font-size: 0.42667rem;\n      color: #ffa417;\n}\n.doctorCard-c .skill[data-v-1394e295] {\n    color: #999;\n    font-size: 12px;\n    width: 7.33333rem;\n    text-overflow: ellipsis;\n    overflow: hidden;\n    -webkit-line-clamp: 2;\n    display: -webkit-box;\n    -webkit-box-orient: vertical;\n    margin: 0.10667rem 0;\n}\n.doctorCard-c .skill > span[data-v-1394e295] {\n      color: #666;\n}\n.doctorCard-c .skill > i[data-v-1394e295] {\n      color: #FFA417;\n}\n.doctorCard-c .skill > span[data-v-1394e295]:nth-of-type(2) {\n      margin-left: 0.53333rem;\n}\n.doctorCard-c .skill-1[data-v-1394e295] {\n    margin-bottom: 0.13333rem;\n}\n.doctorCard-c .skill-1 > span[data-v-1394e295] {\n      color: #666;\n}\n.doctorCard-c .s-items > span[data-v-1394e295] {\n    margin-bottom: 0.13333rem;\n}\n.doctorCard-c .service[data-v-1394e295] {\n    font-size: 10px;\n    color: #3069cf;\n    border: 1px solid #ccc;\n    border-radius: 10px;\n    text-align: center;\n    padding: 0 0.13333rem;\n    display: inline-block;\n    margin-right: 0.18667rem;\n}\n",""])}});