webpackJsonp([75],{"0OM0":function(t,e,n){"use strict";function o(t){n("XoUy")}Object.defineProperty(e,"__esModule",{value:!0});var i=n("3cXf"),r=n.n(i),a=n("pKZN"),c=n("7Adc"),s=n("y0YP"),d=(a.a,{components:{back:a.a},data:function(){return{content:"",doctor:this.$store.state.doctorInfo,patient:null,user:this.$store.state.userInfo,title:this.$route.query.code,timer:null,showConfirm:!1,showConfirm1:!1,showConfirm2:!1,showConfirm3:!1,showConfirm4:!1,showConfirm5:!1,showConfirm6:!1,record:null,order:null,payMsg:null,type:1}},created:function(){this.$store.state.content&&(this.content=this.$store.state.content),this.$route.query.code&&"null"!=this.$route.query.code&&"undefined"!=this.$route.query.code&&this.$store.dispatch("pushCode",this.$route.query.code),this.pullDr(),this.getDefault()},beforeDestroy:function(){this.$store.dispatch("pushcontent",this.content),s.a.loadOut(),s.a.loadOut2(),Object(c.clearTimeout)(this.timer)},methods:{pullDr:function(){var t=this;this.$route.query.DrId&&this.$get("PlatFormAPI/Doctor/QueryDoctorInfoByDrId",{DrId:this.$route.query.DrId}).then(function(e){t.doctor=e,t.$store.dispatch("pushDoctorInfo",e)})},getRecord:function(){var t=this;if(this.$route.query.DrId){var e=1101==this.title?1:2;this.$post("ConsultAPI/Consult/GetConsultRecordByPatientDoctor",{ApplicantId:this.user.User.AccountId,PatientId:this.patient.PatientId,DoctorId:this.$route.query.DrId,DoctorType:e}).then(function(n){return t.record=n,n&&n.Code&&1==n.Code?(t.order=n.Data,t.record=n.Data,void(2==e?t.showConfirm5=!0:t.showConfirm3=!0)):n&&4==n.Code?(t.record=n.Data,void(2==e?t.showConfirm6=!0:t.showConfirm2=!0)):n&&3==n.Code?(t.record=n.Data,void(2==e?t.showConfirm4=!0:t.showConfirm1=!0)):void 0})}else{var n=1101==this.title?1:2;console.log(this.title),this.$post("ConsultAPI/Consult/GetConsultRecordByPatientDoctor",{ApplicantId:this.user.User.AccountId,PatientId:this.patient.PatientId,DoctorId:this.doctor.DrId,DoctorType:n}).then(function(e){return t.record=e,e&&e.Code&&1==e.Code?(t.record=e.Data,t.order=e.Data,void(2==n?t.showConfirm5=!0:t.showConfirm3=!0)):e&&4==e.Code?(t.record=e.Data,void(2==n?t.showConfirm6=!0:t.showConfirm2=!0)):e&&3==e.Code?(t.record=e.Data,void(2==n?t.showConfirm4=!0:t.showConfirm1=!0)):void 0})}},choosePatient:function(){if(this.$route.query.ConsultNow)return!1;this.patient&&this.patient.PatientId?this.$router.push({path:"/Patient-list",query:{PatientId:this.patient.PatientId,toChoose:1}}):this.$router.push("/Patient-list?toChoose=1")},goDoctorList:function(){1101==this.title?this.$router.replace("doctor-list?code="+this.title):1201==this.title&&this.$router.replace("nurse-list?code="+this.title)},confirm1:function(){var t=this,e=this.$store.state,n=e.doctorInfo,o=e.Code;this.$route.query.DrId&&(n.DrId=this.$route.query.DrId),this.$post("IMAPI/Account/GetIMAccount",{UserId:n.DrId,UserType:2}).then(function(e){console.log(t.record);var i={DrName:n.DrName,DrId:n.DrId,FaceUrl:n.PicturePath,Identifier:e.Identifier,serviceCode:o,RelationId:t.record.Id};t.$store.dispatch("pushPatient",null),t.$router.back(),t.$bridge.callhandler("ImInfo",r()(i))})},confirm3:function(){this.$router.replace("/img-order")},confirm5:function(){this.$router.replace("/nursing-order")},cancelOrder:function(){var t=this;this.$get("/PlatFormAPI/ServicePackItem/CancelPayOrder",{PayOrderId:this.order.Id}).then(function(e){t.$toast("取消成功")})},toPay:function(){var t=this;if(this.content.length<10)return this.$toast("请填写至少10个字的问诊内容");if(!this.patient.Age||!this.patient.PatientSex)return this.$toast("根据互联网医院相关法规，就诊人需完善信息才可继续问诊。"),Object(c.setTimeout)(function(){t.$router.push("/Patient-add?fromCenter=1")},2e3),!1;var e=1101==this.title?1:2;this.$post("ConsultAPI/Consult/SaveConsultRecord",{Content:this.content,PatientId:this.patient.PatientId,PatientName:this.patient.PatientName,DoctorId:this.doctor.DrId,DoctorName:this.doctor.DrName,DoctorType:e,OrgId:this.doctor.OrgId||0,ApplicantId:this.user.User.AccountId,ApplicantName:this.user.User.Name}).then(function(e){e&&t.$route.query.ConsultNow?(s.a.loadIn2(),t.record=e,t.$post("PlatFormAPI/ServicePackItem/PrepareServiceItem",{PatientId:t.patient.PatientId,DrId:t.doctor.DrId,OrgId:t.doctor.OrgId||0,SericeItemCode:t.$store.state.Code,RelationId:t.record.Id,PayOrderId:t.$route.query.PayOrderId,DetailType:t.$route.query.DetailType,DetailId:t.$route.query.DetailId}).then(function(n){t.payMsg=n,t.$store.dispatch("pushImInfo",n),t.$post("PlatFormAPI/ServicePackItem/BuyServiceItem",{PatientId:t.patient.PatientId,PatientName:t.patient.PatientName,DrId:t.doctor.DrId,DrName:t.doctor.DrName,ServiceItemId:t.payMsg.ServiceItemId,RelationId:t.payMsg.RelationId,CouponRecordId:t.payMsg.CouponRecordId||0,PayChannel:t.type,PayType:1,ServiceContent:"",Application:0,RecommendId:0,RecommendName:"",APPId:0}).then(function(n){if(n.Body||n.appid){t.$store.dispatch("pushCoupons",null);var o=r()(e);t.$bridge.callhandler("pay",o,function(e){s.a.loadOut2(),"success"==e?t.confirm1():"fail"==e&&(t.$route.query.fromOrder?t.$router.push("/pay-fail?price="+(t.payMsg.CouponRecordId?"0":t.payMsg.Price)+"&payType="+t.type+"&fromOrder="+t.$route.query.fromOrder):t.$router.push("/pay-fail?price="+(t.payMsg.CouponRecordId?"0":t.payMsg.Price)+"&payType="+t.type))})}else s.a.loadOut2(),t.confirm1()})})):e&&(t.$store.dispatch("pushPayInfo",e),t.$route.query.DrId?t.$router.push("/payFor"):t.$router.replace("/payFor"))})},getDefault:function(){var t=this;this.$store.state.patient?(this.patient=this.$store.state.patient,this.getRecord()):this.$get("PlatFormAPI/PatientAccount/QueryDefaultPatientByAccountId",{AccountId:this.user.User.AccountId}).then(function(e){if(!e.Age||!e.PatientSex)return t.$toast("根据互联网医院相关法规，就诊人需完善信息才可继续问诊。"),Object(c.setTimeout)(function(){t.$router.push("/Patient-add?fromCenter=1")},2e3),!1;t.patient=e,t.getRecord()})},confirm:function(){this.$router.push("/Patient-add?fromCenter=1")}}}),l=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"pdt"},[n("div",{ref:"el",staticClass:"title"},[n("back"),t._v(" "),n("span",{staticClass:"name"},[t._v(t._s(t._f("ServiceCode")(t.title)))]),t._v(" "),n("a")],1),t._v(" "),n("p",{staticClass:"tip"},[t._v("请如实填写病情诉求，以便医生给您合适的建议。")]),t._v(" "),n("p",{staticClass:"pat",on:{click:function(e){return t.choosePatient()}}},[t.patient?n("span",[n("i",{staticClass:"icon1"}),t._v(t._s("未知"==t.patient.PatientName?"":t.patient.PatientName)+"\n        ")]):t._e(),t._v(" "),n("i",{staticClass:"icon2"})]),t._v(" "),n("div",{staticClass:"content"},[t._m(0),t._v(" "),n("div",[n("textarea",{directives:[{name:"model",rawName:"v-model",value:t.content,expression:"content"}],attrs:{maxlength:"200",placeholder:"请准确描述你的症状（问题），比如：哪里不舒服，症状持续时间，是否接受治疗，效果如何。病情描述越准确，医生对你的帮助越大。至少输入10个字。"},domProps:{value:t.content},on:{input:function(e){e.target.composing||(t.content=e.target.value)}}}),t._v(" "),n("p",{staticClass:"max"},[n("span",[t._v(t._s(t.content.length)+"/200")])])])]),t._v(" "),n("span",{staticClass:"btn",on:{click:t.toPay}},[t._v("提交")]),t._v(" "),n("confirm",{on:{"on-cancel":function(e){t.showConfirm=!1},"on-confirm":t.confirm},model:{value:t.showConfirm,callback:function(e){t.showConfirm=e},expression:"showConfirm"}},[t._v("根据互联网医院相关法规，就诊人需完善信息才可继续问诊，是否前往完善就诊人信息")]),t._v(" "),n("confirm",{attrs:{"confirm-text":"去咨询","cancel-text":"重新找医生",title:"提示"},on:{"on-cancel":t.goDoctorList,"on-confirm":t.confirm1},model:{value:t.showConfirm1,callback:function(e){t.showConfirm1=e},expression:"showConfirm1"}},[t._v("您有一个正在进行中的图文问诊")]),t._v(" "),n("confirm",{attrs:{"confirm-text":"去查看","show-cancel-button":!1,title:"提示"},on:{"on-confirm":t.confirm3},model:{value:t.showConfirm3,callback:function(e){t.showConfirm3=e},expression:"showConfirm3"}},[t._v("您有一笔未支付的图文问诊订单")]),t._v(" "),n("confirm",{attrs:{"confirm-text":"重新找医生","show-cancel-button":!1,title:"提示"},on:{"on-confirm":t.goDoctorList},model:{value:t.showConfirm2,callback:function(e){t.showConfirm2=e},expression:"showConfirm2"}},[t._v("该医生今日的号源已用完，是否需要寻找其他的医生问诊？")]),t._v(" "),n("confirm",{attrs:{"confirm-text":"去咨询","cancel-text":"重新找护士",title:"提示"},on:{"on-cancel":t.goDoctorList,"on-confirm":t.confirm1},model:{value:t.showConfirm4,callback:function(e){t.showConfirm4=e},expression:"showConfirm4"}},[t._v("您有一个正在进行中的护理咨询")]),t._v(" "),n("confirm",{attrs:{"confirm-text":"去查看","show-cancel-button":!1,title:"提示"},on:{"on-confirm":t.confirm5},model:{value:t.showConfirm5,callback:function(e){t.showConfirm5=e},expression:"showConfirm5"}},[t._v("您有一笔未支付的护理咨询订单")]),t._v(" "),n("confirm",{attrs:{"confirm-text":"重新找护士","show-cancel-button":!1,title:"提示"},on:{"on-confirm":t.goDoctorList},model:{value:t.showConfirm6,callback:function(e){t.showConfirm6=e},expression:"showConfirm6"}},[t._v("该护士今日的号源已用完，是否需要寻找其他的护士问诊？")])],1)},u=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("p",[n("i",{staticClass:"icon3"}),t._v("问诊内容")])}],f={render:l,staticRenderFns:u},h=f,p=n("C7Lr"),m=o,y=p(d,h,!1,m,"data-v-ae1c16c6",null);e.default=y.exports},XoUy:function(t,e,n){var o=n("cgLx");"string"==typeof o&&(o=[[t.i,o,""]]),o.locals&&(t.exports=o.locals);n("FIqI")("7bde8bf6",o,!0,{})},cgLx:function(t,e,n){var o=n("L4zZ");e=t.exports=n("UTlt")(!1),e.push([t.i,"\n.tip[data-v-ae1c16c6] {\n  height: 1.06667rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  background: #ffa417;\n  color: #fff;\n}\n.tips[data-v-ae1c16c6] {\n  height: 1.06667rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  color: #999;\n  font-size: 13px;\n}\n.bt[data-v-ae1c16c6] {\n  border-top: 1px solid #ececec;\n}\n.pat[data-v-ae1c16c6] {\n  height: 1.17333rem;\n  padding: 0 0.4rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: justify;\n  -webkit-justify-content: space-between;\n          justify-content: space-between;\n  background: #fff;\n}\n.pat span[data-v-ae1c16c6] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n}\n.pat i[data-v-ae1c16c6] {\n    display: block;\n    width: 0.4rem;\n    height: 0.4rem;\n}\n.pat .icon1[data-v-ae1c16c6] {\n    background: url("+o(n("sTkO"))+") no-repeat center;\n    background-size: contain;\n    margin-right: 0.13333rem;\n}\n.pat .icon2[data-v-ae1c16c6] {\n    background: url("+o(n("i9Xb"))+") no-repeat center;\n    background-size: contain;\n}\n.content[data-v-ae1c16c6] {\n  margin-top: 0.26667rem;\n  background: #fff;\n}\n.content p[data-v-ae1c16c6] {\n    padding: 0.4rem;\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-align: center;\n    -webkit-align-items: center;\n            align-items: center;\n}\n.content .icon3[data-v-ae1c16c6] {\n    display: block;\n    width: 0.4rem;\n    height: 0.4rem;\n    margin-right: 0.13333rem;\n    background: url("+o(n("zv6d"))+") no-repeat center;\n    background-size: contain;\n}\n.content textarea[data-v-ae1c16c6] {\n    width: 90%;\n    height: 3.33333rem;\n    display: block;\n    margin: auto;\n    border-radius: 10px;\n    border: 1px solid #ececec;\n    resize: none;\n    padding: 0.4rem;\n    font-size: 14px;\n    line-height: 1.5;\n}\n.content .max[data-v-ae1c16c6] {\n    display: -webkit-box;\n    display: -webkit-flex;\n    display: flex;\n    -webkit-box-pack: end;\n    -webkit-justify-content: flex-end;\n            justify-content: flex-end;\n    color: #999;\n}\n.btn[data-v-ae1c16c6] {\n  width: 80%;\n  height: 1.17333rem;\n  display: -webkit-box;\n  display: -webkit-flex;\n  display: flex;\n  -webkit-box-align: center;\n  -webkit-align-items: center;\n          align-items: center;\n  -webkit-box-pack: center;\n  -webkit-justify-content: center;\n          justify-content: center;\n  margin: 0.66667rem auto;\n  background: #2f68ca;\n  border-radius: 30px;\n  font-size: 18px;\n  color: #fff;\n}\n",""])}});