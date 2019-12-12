package com.newdjk.member.model;

import com.newdjk.member.BuildConfig;

public class HttpUrl {
    //    public static String ip = "http://api.newstartcare.com";//？正式环境
    public static String ip = BuildConfig.URL;//？测试环境


    public static String ConsultWebAPI = BuildConfig.URL + "/ConsultWebAPI";// //1、咨询、复诊：
    public static String PlatFormAPI = BuildConfig.URL + "/PlatFormAPI";//	平台接口	包含用户、病人、角色权限、服务包以及知识库等各公共模块接口
    public static String ConsultAPI = BuildConfig.URL + "/ConsultAPI";//		咨询、复诊接口	和咨询以及复诊相关接口
    public static String FetalHeartAPI = BuildConfig.URL + "/FetalHeartAPI";//		母胎接口	和胎心监护相关接口
    public static String PRESCRIPTION = BuildConfig.URL + "/Prescription";//		处方接口	处方相关接口
    public static String Prescription = PRESCRIPTION + "/Prescription";//		处方接口	处方相关接口
    public static String SSOAPI = BuildConfig.URL + "/SSOAPI";//		医生  SSO接口	登录以及接口授权认证相关接口
    public static String ContinueServiceAPI = BuildConfig.URL + "/ContinueServiceAPI";//		延续服务接口	延续服务接口
    public static String IMAPI = BuildConfig.URL + "/IMAPI";//		获取IM信息接口
    public static String Doctor = PlatFormAPI + "/Doctor";//		获取IM信息接口
    public static String Banner = BuildConfig.URL + "/AdAPI/";//		获取首页的banner数据
    public static String Area = PlatFormAPI + "/Area/";//获取地区
    public static String Message = IMAPI + "/Message";//		IM即时通讯-消息 控制器api
    public static String CONSULT = ConsultAPI + "/Consult"; //咨询记录控制器api

    public static String AppMessage = PlatFormAPI + "/AppMessage";//		APP消息
    public static String MDTAPI = ip + "/MDTAPI";//		MDT




    //========================登陸===========================
    public static String login = SSOAPI + "/Patient/Login";//登录
    public static String LoginSendMobileCode = PlatFormAPI + "/PatientAccount/SendMobileCode";//登录f发送验证码
    public static String DoctorLogin = SSOAPI + "/Doctor/Login";//登录医生
    public static String loginIm = IMAPI + "/Account/GetIMAccount";//获取IM用户名和签名
    public static String updatePhoto = PlatFormAPI + "/PatientAccount/PatientImagUpload";//会员上传图片
    public static String KnowledgeBase = PlatFormAPI + "/KnowledgeBase";//		知识库(宣教、问卷)
    // ===========================  PatientAccount  ( 患者 )============================================
    public static String PatientArchives = PlatFormAPI + "/PatientArchives";//		健康档案
    public static String PatientAccount = PlatFormAPI + "/PatientAccount";
    //    POST PatientAccount/SendMobileCode 发送会员手机验证码
    public static String SendMobileCode = PatientAccount + "/SendMobileCode";
    //    POST PatientAccount/VerifyMobileCode  验证会员手机验证码(注册验证)
    public static String VerifyMobileCode = PatientAccount + "/VerifyMobileCode";
    //    POST PatientAccount/RegistPatient  注册会员
    public static String RegistPatient = PatientAccount + "/RegistPatient";
    public static String WechatRegist = PatientAccount + "/AppRegistPatient";
    //    POST PatientAccount/QueryPatientAccountByMobileCode 根据手机号码和验证码获取患者信息
    public static String QueryPatientAccountByMobileCode = PatientAccount + "/QueryPatientAccountByMobileCode";
    //    POST PatientAccount/ChangePatientAccountPassword 修改患者密码
    public static String ChangePatientAccountPassword = PatientAccount + "/ChangePatientAccountPassword";
    //    GET PatientAccount/QueryPatientInfoByPatientId?PatientId={PatientId}    根据会员Id获取患者信息
    public static String QueryPatientInfoByPatientId = PatientAccount + "/QueryPatientInfoByPatientId";
    //    GET PatientAccount/UpdatePatientDrRemark?PatientId={PatientId}&DrRemark={DrRemark}修改会员信息的医生备注
    public static String UpdatePatientDrRemark = PatientAccount + "/UpdatePatientDrRemark";
    //    POST PatientAccount/PatientImagUpload 会员证照\头像上传
    public static String PatientImagUpload = PatientAccount + "/PatientImagUpload";
    //    POST PatientAccount/PatientImagSave   会员证照保存(批量保存)
    public static String PatientImagSave = PatientAccount + "/PatientImagSave";
    //    GET PatientAccount/QueryPatientRegImagByDrId?PatientId={PatientId}根据会员Id获取证照
    public static String QueryPatientRegImagByDrId = PatientAccount + "/QueryPatientRegImagByDrId";
    //    GET PatientAccount/DeletePatientRegImag?PatientImgId={PatientImgId} 根据主键Id删除患者证照
    public static String DeletePatientRegImag = PatientAccount + "/DeletePatientRegImag";
    //    GET PatientAccount/QueryAddressByPatientId?PatientId={PatientId} 根据会员Id获取地址列表
    public static String QueryAddressByPatientId = PatientAccount + "/QueryAddressByPatientId";
    //    GET PatientAccount/DeletePatientAddress?PatientAddId={  PatientAddId }根据主键Id删除患者地址
    public static String DeletePatientAddress = PatientAccount + "/DeletePatientAddress";
    //    POST PatientAccount/SavePatientAddress  保存会员地址信息(新增或者修改)
    public static String SavePatientAddress = PatientAccount + "/SavePatientAddress";
    //    GET PatientAccount/SetDefaultAddress?PatientAddId={PatientAddId }&PatientId={PatientId}根据主键Id和会员Id设置患者默认地址
    public static String SetDefaultAddress = PatientAccount + "/SetDefaultAddress";
    //    POST PatientAccount/AddPatientAddress 保存意见与反馈
    public static String AddPatientAddress = PatientAccount + "/AddPatientAddress";
    //    POST PatientAccount/QueryCouponRecordPage     查询患者优惠券
    public static String QueryCouponRecordPage = PatientAccount + "/QueryCouponRecordPage";
    //    GET PatientAccount/QueryPatientVisitByPatientId?AccountId={AccountId}根据账号Id获取就诊人列表
    public static String QueryPatientVisitByPatientId = PatientAccount + "/QueryPatientVisitByPatientId";
    //    GET PatientAccount/DeletePatientVisit?PatientId={PatientId} 根据主键Id删除就诊人
    public static String DeletePatientVisit = PatientAccount + "/DeletePatientVisit";
    //    POST PatientAccount/SavePatientVisit 保存会员就诊人信息(新增或者修改)
    public static String SavePatientVisit = PatientAccount + "/SavePatientVisit";
    //   获取医生APP消息分页
    public static String QueryPatientAppMessageByPage = PatientAccount + "/QueryPatientAppMessageByPage";

    //首页
    public static String QueryDoctorInfoForHot = Doctor + "/QueryDoctorInfoForHot";
    //首页的ad
    public static String QueryAdBannerInfo = Banner + "/Ads/QueryAdsList";
    //修改登陆的手机号码
    public static String ModifyMobile = PlatFormAPI + "/PatientAccount/ChangeAccountMobile";

    public static String ModifyPassword = PlatFormAPI + "/PatientAccount/ChangePatientPasswordByMobile";

    //意见与反馈图片上传
    public static String FeedbackImgLoad = PlatFormAPI + "/DoctorPatient/FeedBackImgUpload";

    //根据微信授权登录OpenId和AppId获取账号信息
    public static String QueryLoginAccount = PlatFormAPI + "/PatientAccount/QueryLoginAccountByOpenId";
    //保存意见与反馈
    public static String AddFeedBack = PlatFormAPI + "/DoctorPatient/AddFeedBack";
    //关于我们
    public static String GetAboutInfo = KnowledgeBase + "/GetAboutInfo";

    //病历
    public static String ReportImageUpload = PatientArchives + "/ReportImageUpload"; //患者病历上传
    //get Area
    public static String QueryAreaByParentId = Area + "QueryAreaByParentId";
    // get hot city
    public static String QueryHostArea = Area + "QueryHostArea";

    /**************************************胎心功能****************************************/
    //获取默认就诊人(根据账号Id)
    public static String DefaultPatient = PlatFormAPI + "/PatientAccount/QueryDefaultPatientByAccountId";
    //获取用户监护设置
    public static String GetMonitorSet = FetalHeartAPI + "/Member/GetMonitorSet";
    //用户监护设置
    public static String MonitorSet = FetalHeartAPI + "/Member/MonitorSet";
    //是否有绑定设备(Code说明:0设备已绑定,1未登记用户,2未购买母胎服务包,3母胎服务包未支付,4已购服务包未绑定设备)
    public static String HaveBind = FetalHeartAPI + "/Member/HaveBind";
    //登记患者信息
    public static String PatientRegister = FetalHeartAPI + "/Member/PatientRegister";
    //获取机构服务包
    public static String QueryOrgServicePackByPage = PlatFormAPI + "/ServicePackItem/QueryOrgServicePackByPage";
    //服务包详情- 根据服务包Id获取服务包明细记录
    //public static String QueryServicePackDetailByPackId = PlatFormAPI + "/ServicePackItem/QueryServicePackDetailByPackId";
    //服务包选择(患者)- 生成订单-----------------------现在是获取详情
    public static String QueryServicePackAndDetailByPackId = PlatFormAPI + "/ServicePackItem/QueryServicePackAndDetailByPackId";
    //获取判读服务医生----------------获取详情中的接口
    public static String GetReadDoctorList = FetalHeartAPI + "/Doctor/GetReadDoctorList";
    //服务(包)套餐购买(立即支付)
    public static String BuyServicePack = PlatFormAPI + "/ServicePackItem/BuyServicePack";
    //监护服务详情
    public static String ServiceDetail = FetalHeartAPI + "/Member/ServiceDetail";
    //获取监测记录分页数据
    public static String GetMonitorPage = FetalHeartAPI + "/Member/GetMonitorPage";
    //胎心数据上传
    //public static String FhrData = "http://172.18.30.68:8589/Monitor/FhrData";//FetalHeartAPI
    public static String FhrData = FetalHeartAPI + "/Monitor/FhrData";
    //判读验证:Code返回说明(0:有剩余判读次数[Data里面会返回 总次数,已使用次数,剩余次数], 1:服务包已过期,2:无剩余判读次数,其它值为异常)
    public static String AskCheck = FetalHeartAPI + "/Member/AskCheck";
    //提交判读请求
    public static String SubmitAsk = FetalHeartAPI + "/Member/SubmitAsk";
    //获取机构联系信息
    public static String GetContactInfo = FetalHeartAPI + "/Monitor/GetContactInfo";
    //获取监测报告详情
    public static String GetReportDetail = FetalHeartAPI + "/Monitor/GetReportDetail";
    //获取监测记录数据
    public static String GetMonitorData = FetalHeartAPI + "/Monitor/GetMonitorData";

    //获取IM 消息关联业务记录
    public static String GetIMRelationRecordForMember = Message + "/GetIMRelationRecordForMember";
    public static String GetIMRelationRecord = Message + "/GetIMRelationRecord";  //获取IM 消息关联业务记录
    //咨询
    public static String GetInquiryRecord = CONSULT + "/GetInquiryRecord"; //获取问诊记录  (视频)
    public static String GetConsultRecord = CONSULT + "/GetConsultRecord"; //获取咨询记录  (图文)

    //处方单
    public static String GetPrescription = Prescription + "/GetPrescription";//获取处方单记录
    public static String GetPrescriptionApply = Prescription + "/GetPrescriptionApply";//查看续方申请单  （续方）

    public static String GetMedicationByKeyName = PRESCRIPTION + "/Medication/GetMedicationByKeyName";//根据关键字搜索药品 从适应症中查找关键字  （续方）
    public static String GetMedicationFullInfo = PRESCRIPTION + "/Medication/GetMedicationFullInfo";//根据id查找药品详情
    //获取app配置信息
    public static String GetAppConfig = KnowledgeBase + "/GetAppConfig";

    //搜索模块开始
    public static String GetDrSearchPage = PlatFormAPI + "/Doctor/GetDrSearchPage";//		热门搜索
    public static String GetDepDiseaseForPage = PlatFormAPI + "/KnowledgeBase/GetDepDiseaseForPage";//  疾病搜索
    public static String QueryDoctorInfoByPage = PlatFormAPI + "/Doctor/QueryDoctorInfoByPage";//  获取医生列表
    public static String GetDiseaseByDiseaseId = PlatFormAPI + "/KnowledgeBase/GetDepDiseaseByDiseaseId";//  疾病信息

    public static String QueryDoctorIdByQRCodeUrl = PlatFormAPI + "/Doctor/QueryDoctorIdByQRCodeUrl";//  根据扫描结果
    //结束问诊相关
    public static String UpdateInquiryRecordStatus = CONSULT + "/UpdateInquiryRecordStatus"; //修改问诊记录状态
    public static String UpdatePrescriptionApplyStatus = Prescription + "/UpdatePrescriptionApplyStatus";//修改申请单状态（1：待处理，2：已接收，3：被驳回）
    public static String UpdateConsultRecordStatus = CONSULT + "/UpdateConsultRecordStatus"; //修改咨询记录状态

    public static String QuerySelfPatientByAccountId = PlatFormAPI + "/PatientAccount/QuerySelfPatientByAccountId"; //获取本人就诊人(根据账号Id)
    public static String GetChatHistoryByPage = Message+"/GetChatHistoryByPage";

    public static String QueryDoctorInfoByDrId = Doctor + "/QueryDoctorInfoByDrId";
    //是否已购买设备
    public static String HaveBuyDevice = FetalHeartAPI + "/Member/HaveBuyDevice";

    //获取app版本信息
    public static String GetAppManage = KnowledgeBase + "/GetAppManage";

    public static String getCurrentTime = KnowledgeBase + "/GetNowDateTime";

    public static String Read = AppMessage + "/Read";    //APP消息已读

    //mdt


    public static String QueryMDTBySubjectBuyIMGroupId = MDTAPI + "/MDT/QueryMDTBySubjectBuyIMGroupId";  //分页获取医生的报告/医生的订单
    public static String GetGroupChatHistoryByPage = Message + "/GetGroupChatHistoryByPage";


    //获取默认就诊人(根据账号Id)
    public static String QueryPatientVisitByAccountId = PatientAccount + "/QueryPatientVisitByAccountId";
}
