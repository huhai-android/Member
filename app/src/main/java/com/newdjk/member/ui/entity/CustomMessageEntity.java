package com.newdjk.member.ui.entity;

import java.util.List;

public class CustomMessageEntity {


    /**
     * Title : 续方详情如下，共2种：
     * Content : [{"Type":"TextDetail","ContentElem":{"Text":"头孢呋辛酯0.25g*12片","Detail":"X1"}},{"Type":"Text","ContentElem":{"Text":"用法：每日2次 每次0.25g 用药3天\u2026","Detail":null}}]
     * ExtData : {"Type":31,"Data":{"PrescriptionId":"10000"}}
     * IsSystem : false
     */
    private String FocusTitle;
    private String Title;
    private ExtDataBean ExtData;
    private boolean IsSystem;
    private List<ContentBean> Content;
    private String LinkUrl;

    public String getPushDesc() {
        return PushDesc;
    }

    public void setPushDesc(String pushDesc) {
        PushDesc = pushDesc;
    }

    private String PushDesc;
    private boolean IsShowDividingLine;
    private  int ShowType;


    public boolean isShowDividingLine() {
        return IsShowDividingLine;
    }

    public void setShowDividingLine(boolean showDividingLine) {
        IsShowDividingLine = showDividingLine;
    }

    public int getShowType() {
        return ShowType;
    }

    public void setShowType(int showType) {
        ShowType = showType;
    }

    public String getFocusTitle() {
        return FocusTitle;
    }

    public void setFocusTitle(String focusTitle) {
        FocusTitle = focusTitle;
    }

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

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        LinkUrl = linkUrl;
    }

    public static class ExtDataBean {
        /**
         * Type : 31
         * Data : {"PrescriptionId":"10000"}
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
             * PrescriptionId : 10000
             */
            private String PatientName;
            private int ImageId;
            private int PrescriptionId;
            private  int serviceId;
            private int  MedicalTempId;
            private String LinkURL;
            private String Stamp;
            private int AVRoomID;
            private String Targets;
            private String userSig;
            private String ServiceType;
            private String RecordId;
            private String DoctorId;
            private String DoctorName;
            private String PayOrderId;
            private String time ;
            private String CensorateRecordId;
            private String OrderTime;

            public String getOrderTime() {
                return OrderTime;
            }

            public void setOrderTime(String orderTime) {
                OrderTime = orderTime;
            }

            public String getCensorateRecordId() {
                return CensorateRecordId;
            }

            public void setCensorateRecordId(String censorateRecordId) {
                CensorateRecordId = censorateRecordId;
            }

            public String getPayOrderId() {
                return PayOrderId;
            }

            public void setPayOrderId(String payOrderId) {
                PayOrderId = payOrderId;
            }

            public String getServiceType() {
                return ServiceType;
            }

            public void setServiceType(String serviceType) {
                ServiceType = serviceType;
            }

            public String getRecordId() {
                return RecordId;
            }

            public void setRecordId(String recordId) {
                RecordId = recordId;
            }

            public String getDoctorId() {
                return DoctorId;
            }

            public void setDoctorId(String doctorId) {
                DoctorId = doctorId;
            }

            public String getDoctorName() {
                return DoctorName;
            }

            public void setDoctorName(String doctorName) {
                DoctorName = doctorName;
            }

            public String getUserSig() {
                return userSig;
            }

            public void setUserSig(String userSig) {
                this.userSig = userSig;
            }

            public int getAVRoomID() {
                return AVRoomID;
            }

            public void setAVRoomID(int AVRoomID) {
                this.AVRoomID = AVRoomID;
            }

            public String getTargets() {
                return Targets;
            }

            public void setTargets(String targets) {
                Targets = targets;
            }

            public String getStamp() {
                return Stamp;
            }

            public void setStamp(String stamp) {
                Stamp = stamp;
            }

            public String getLinkURL() {
                return LinkURL;
            }

            public void setLinkURL(String linkURL) {
                LinkURL = linkURL;
            }

            public String getPatientName() {
                return PatientName;
            }

            public void setPatientName(String patientName) {
                PatientName = patientName;
            }

            public int getImageId() {
                return ImageId;
            }

            public void setImageId(int imageId) {
                ImageId = imageId;
            }

            public int getMedicalTempId() {
                return MedicalTempId;
            }

            public void setMedicalTempId(int medicalTempId) {
                MedicalTempId = medicalTempId;
            }

            public int getPrescriptionId() {
                return PrescriptionId;
            }

            public void setPrescriptionId(int PrescriptionId) {
                this.PrescriptionId = PrescriptionId;
            }

            public int getServiceId() {
                return serviceId;
            }

            public void setServiceId(int serviceId) {
                serviceId = serviceId;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

    }

    public static class ContentBean {
        /**
         * Type : TextDetail
         * ContentElem : {"Text":"头孢呋辛酯0.25g*12片","Detail":"X1"}
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
             * Text : 头孢呋辛酯0.25g*12片
             * Detail : X1
             */

            private String Text;
            private String Detail;

            public String getText() {
                return Text;
            }

            public void setText(String Text) {
                this.Text = Text;
            }

            public String getDetail() {
                return Detail;
            }

            public void setDetail(String Detail) {
                this.Detail = Detail;
            }
        }
    }
}
