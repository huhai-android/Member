package com.newdjk.member.ui.entity;

import java.util.List;

/**
 * Created by Struggle on 2018/10/15.
 */

public class DoctorIDinfoEntity {


    /**
     * Title :
     * Content : [{"Type":"Text","ContentElem":{"Text":"多学科会诊咨询已经开始","Detail":null}},{"Type":"Text","ContentElem":{"Text":"","Detail":null}}]
     * ExtData : {"Type":313,"Data":{"SubjectBuyRecordId":"1219","DrId":"57","DrName":"奥巴马"}}
     * IsSystem : false
     * IsShowDividingLine : false
     * ShowType : 0
     * LinkUrl :
     * LinkUrlOfDoctor : null
     * LinkUrlOfPatient : null
     * IsSendWxMsg : false
     * IsPushOffline : false
     * PushDesc : 多学科会诊咨询已经开始
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
         * Type : 313
         * Data : {"SubjectBuyRecordId":"1219","DrId":"57","DrName":"奥巴马"}
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
             * SubjectBuyRecordId : 1219
             * DrId : 57
             * DrName : 奥巴马
             */

            private String SubjectBuyRecordId;
            private int DrId;
            private String DrName;

            public String getSubjectBuyRecordId() {
                return SubjectBuyRecordId;
            }

            public void setSubjectBuyRecordId(String SubjectBuyRecordId) {
                this.SubjectBuyRecordId = SubjectBuyRecordId;
            }

            public int getDrId() {
                return DrId;
            }

            public void setDrId(int DrId) {
                this.DrId = DrId;
            }

            public String getDrName() {
                return DrName;
            }

            public void setDrName(String DrName) {
                this.DrName = DrName;
            }
        }
    }

    public static class ContentBean {
        /**
         * Type : Text
         * ContentElem : {"Text":"多学科会诊咨询已经开始","Detail":null}
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
             * Text : 多学科会诊咨询已经开始
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
