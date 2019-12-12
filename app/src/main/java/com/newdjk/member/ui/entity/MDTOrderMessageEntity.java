package com.newdjk.member.ui.entity;

import java.util.List;

/**
 * Created by Struggle on 2018/10/15.
 */

public class MDTOrderMessageEntity {


    /**
     * Title : [奥巴马]推荐了多学科会诊医生
     * Content : [{"Type":"Text","ContentElem":{"Text":"奥巴马医生为您推荐：","Detail":null}},{"Type":"Text","ContentElem":{"Text":"多学科会诊（MDT）","Detail":null}},{"Type":"Text","ContentElem":{"Text":"","Detail":null}},{"Type":"Text","ContentElem":{"Text":"服务医生：","Detail":null}},{"Type":"Text","ContentElem":{"Text":"张国荣 主任护师医生","Detail":null}},{"Type":"Text","ContentElem":{"Text":"安贞医院 内科","Detail":null}},{"Type":"Text","ContentElem":{"Text":"","Detail":null}}]
     * ExtData : {"Type":311,"Data":{"DrId":"58","DrName":"张国荣","SpecialistHosGroupId":"5","HosGroupName":"专治疑难杂症","RecordSource":"2","SourceId":"57","SourceDrId":"57","SourceDrName":"奥巴马","PatientId":"186","PatientName":"庄周","PatientSex":"1","PatientAge":"30岁","SubjectBuyRecordId":"1219"}}
     * IsSystem : false
     * IsShowDividingLine : false
     * ShowType : 0
     * LinkUrl :
     * LinkUrlOfDoctor : null
     * LinkUrlOfPatient : null
     * IsSendWxMsg : false
     * IsPushOffline : false
     * PushDesc :
     */

    private String Title;
    private ExtDataBean ExtData;
    private boolean IsSystem;
    private boolean IsShowDividingLine;
    private int ShowType;
    private String LinkUrl;
    private Object LinkUrlOfDoctor;
    private Object LinkUrlOfPatient;
    private boolean IsSendWxMsg;
    private boolean IsPushOffline;
    private String PushDesc;
    private List<ContentBean> Content;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public ExtDataBean getExtData() {
        return ExtData;
    }

    public void setExtData(ExtDataBean ExtData) {
        this.ExtData = ExtData;
    }

    public boolean isIsSystem() {
        return IsSystem;
    }

    public void setIsSystem(boolean IsSystem) {
        this.IsSystem = IsSystem;
    }

    public boolean isIsShowDividingLine() {
        return IsShowDividingLine;
    }

    public void setIsShowDividingLine(boolean IsShowDividingLine) {
        this.IsShowDividingLine = IsShowDividingLine;
    }

    public int getShowType() {
        return ShowType;
    }

    public void setShowType(int ShowType) {
        this.ShowType = ShowType;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String LinkUrl) {
        this.LinkUrl = LinkUrl;
    }

    public Object getLinkUrlOfDoctor() {
        return LinkUrlOfDoctor;
    }

    public void setLinkUrlOfDoctor(Object LinkUrlOfDoctor) {
        this.LinkUrlOfDoctor = LinkUrlOfDoctor;
    }

    public Object getLinkUrlOfPatient() {
        return LinkUrlOfPatient;
    }

    public void setLinkUrlOfPatient(Object LinkUrlOfPatient) {
        this.LinkUrlOfPatient = LinkUrlOfPatient;
    }

    public boolean isIsSendWxMsg() {
        return IsSendWxMsg;
    }

    public void setIsSendWxMsg(boolean IsSendWxMsg) {
        this.IsSendWxMsg = IsSendWxMsg;
    }

    public boolean isIsPushOffline() {
        return IsPushOffline;
    }

    public void setIsPushOffline(boolean IsPushOffline) {
        this.IsPushOffline = IsPushOffline;
    }

    public String getPushDesc() {
        return PushDesc;
    }

    public void setPushDesc(String PushDesc) {
        this.PushDesc = PushDesc;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ExtDataBean {
        /**
         * Type : 311
         * Data : {"DrId":"58","DrName":"张国荣","SpecialistHosGroupId":"5","HosGroupName":"专治疑难杂症","RecordSource":"2","SourceId":"57","SourceDrId":"57","SourceDrName":"奥巴马","PatientId":"186","PatientName":"庄周","PatientSex":"1","PatientAge":"30岁","SubjectBuyRecordId":"1219"}
         */

        private int Type;
        private DataBean Data;

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public DataBean getData() {
            return Data;
        }

        public void setData(DataBean Data) {
            this.Data = Data;
        }

        public static class DataBean {
            /**
             * DrId : 58
             * DrName : 张国荣
             * SpecialistHosGroupId : 5
             * HosGroupName : 专治疑难杂症
             * RecordSource : 2
             * SourceId : 57
             * SourceDrId : 57
             * SourceDrName : 奥巴马
             * PatientId : 186
             * PatientName : 庄周
             * PatientSex : 1
             * PatientAge : 30岁
             * SubjectBuyRecordId : 1219
             */

            private String DrId;
            private String DrName;
            private String SpecialistHosGroupId;
            private String HosGroupName;
            private String RecordSource;
            private String SourceId;
            private String SourceDrId;
            private String SourceDrName;
            private String PatientId;
            private String PatientName;
            private String PatientSex;
            private String PatientAge;
            private String SubjectBuyRecordId;

            public String getDrId() {
                return DrId;
            }

            public void setDrId(String DrId) {
                this.DrId = DrId;
            }

            public String getDrName() {
                return DrName;
            }

            public void setDrName(String DrName) {
                this.DrName = DrName;
            }

            public String getSpecialistHosGroupId() {
                return SpecialistHosGroupId;
            }

            public void setSpecialistHosGroupId(String SpecialistHosGroupId) {
                this.SpecialistHosGroupId = SpecialistHosGroupId;
            }

            public String getHosGroupName() {
                return HosGroupName;
            }

            public void setHosGroupName(String HosGroupName) {
                this.HosGroupName = HosGroupName;
            }

            public String getRecordSource() {
                return RecordSource;
            }

            public void setRecordSource(String RecordSource) {
                this.RecordSource = RecordSource;
            }

            public String getSourceId() {
                return SourceId;
            }

            public void setSourceId(String SourceId) {
                this.SourceId = SourceId;
            }

            public String getSourceDrId() {
                return SourceDrId;
            }

            public void setSourceDrId(String SourceDrId) {
                this.SourceDrId = SourceDrId;
            }

            public String getSourceDrName() {
                return SourceDrName;
            }

            public void setSourceDrName(String SourceDrName) {
                this.SourceDrName = SourceDrName;
            }

            public String getPatientId() {
                return PatientId;
            }

            public void setPatientId(String PatientId) {
                this.PatientId = PatientId;
            }

            public String getPatientName() {
                return PatientName;
            }

            public void setPatientName(String PatientName) {
                this.PatientName = PatientName;
            }

            public String getPatientSex() {
                return PatientSex;
            }

            public void setPatientSex(String PatientSex) {
                this.PatientSex = PatientSex;
            }

            public String getPatientAge() {
                return PatientAge;
            }

            public void setPatientAge(String PatientAge) {
                this.PatientAge = PatientAge;
            }

            public String getSubjectBuyRecordId() {
                return SubjectBuyRecordId;
            }

            public void setSubjectBuyRecordId(String SubjectBuyRecordId) {
                this.SubjectBuyRecordId = SubjectBuyRecordId;
            }
        }
    }

    public static class ContentBean {
        /**
         * Type : Text
         * ContentElem : {"Text":"奥巴马医生为您推荐：","Detail":null}
         */

        private String Type;
        private ContentElemBean ContentElem;

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public ContentElemBean getContentElem() {
            return ContentElem;
        }

        public void setContentElem(ContentElemBean ContentElem) {
            this.ContentElem = ContentElem;
        }

        public static class ContentElemBean {
            /**
             * Text : 奥巴马医生为您推荐：
             * Detail : null
             */

            private String Text;
            private Object Detail;

            public String getText() {
                return Text;
            }

            public void setText(String Text) {
                this.Text = Text;
            }

            public Object getDetail() {
                return Detail;
            }

            public void setDetail(Object Detail) {
                this.Detail = Detail;
            }
        }
    }
}
