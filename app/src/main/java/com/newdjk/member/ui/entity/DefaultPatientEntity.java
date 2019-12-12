package com.newdjk.member.ui.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class DefaultPatientEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"PatientId":223,"AccountId":49,"PatientName":"郭富城","NameLetter":"GFC","PatientSex":1,"Birthday":"2017-10-29 00:00:00","CredType":1,"CredNo":null,"Mobile":"15088886662","PicturePath":"http://userimage.newstarthealth.cn/pat/0/223.jpg?dt=201811051128921","PatientType":4,"Newborn":1,"DefaultPatient":1,"CreateTime":"2018-10-29 17:09:37","CredTypeName":"身份证","Age":"1岁"}
     */

    private int Code;
    private String Message;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean  implements Parcelable {
        /**
         * PatientId : 223
         * AccountId : 49
         * PatientName : 郭富城
         * NameLetter : GFC
         * PatientSex : 1
         * Birthday : 2017-10-29 00:00:00
         * CredType : 1
         * CredNo : null
         * Mobile : 15088886662
         * PicturePath : http://userimage.newstarthealth.cn/pat/0/223.jpg?dt=201811051128921
         * PatientType : 4
         * Newborn : 1
         * DefaultPatient : 1
         * CreateTime : 2018-10-29 17:09:37
         * CredTypeName : 身份证
         * Age : 1岁
         */

        private int PatientId;
        private int AccountId;
        private String PatientName;
        private String NameLetter;
        private int PatientSex;
        private String Birthday;
        private int CredType;
        private Object CredNo;
        private String Mobile;
        private String PicturePath;
        private int PatientType;
        private int Newborn;
        private int DefaultPatient;
        private String CreateTime;
        private String CredTypeName;
        private String Age;

        protected DataBean(Parcel in) {
            PatientId = in.readInt();
            AccountId = in.readInt();
            PatientName = in.readString();
            NameLetter = in.readString();
            PatientSex = in.readInt();
            Birthday = in.readString();
            CredType = in.readInt();
            Mobile = in.readString();
            PicturePath = in.readString();
            PatientType = in.readInt();
            Newborn = in.readInt();
            DefaultPatient = in.readInt();
            CreateTime = in.readString();
            CredTypeName = in.readString();
            Age = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getPatientId() {
            return PatientId;
        }

        public void setPatientId(int PatientId) {
            this.PatientId = PatientId;
        }

        public int getAccountId() {
            return AccountId;
        }

        public void setAccountId(int AccountId) {
            this.AccountId = AccountId;
        }

        public String getPatientName() {
            return PatientName;
        }

        public void setPatientName(String PatientName) {
            this.PatientName = PatientName;
        }

        public String getNameLetter() {
            return NameLetter;
        }

        public void setNameLetter(String NameLetter) {
            this.NameLetter = NameLetter;
        }

        public int getPatientSex() {
            return PatientSex;
        }

        public void setPatientSex(int PatientSex) {
            this.PatientSex = PatientSex;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public int getCredType() {
            return CredType;
        }

        public void setCredType(int CredType) {
            this.CredType = CredType;
        }

        public Object getCredNo() {
            return CredNo;
        }

        public void setCredNo(Object CredNo) {
            this.CredNo = CredNo;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getPicturePath() {
            return PicturePath;
        }

        public void setPicturePath(String PicturePath) {
            this.PicturePath = PicturePath;
        }

        public int getPatientType() {
            return PatientType;
        }

        public void setPatientType(int PatientType) {
            this.PatientType = PatientType;
        }

        public int getNewborn() {
            return Newborn;
        }

        public void setNewborn(int Newborn) {
            this.Newborn = Newborn;
        }

        public int getDefaultPatient() {
            return DefaultPatient;
        }

        public void setDefaultPatient(int DefaultPatient) {
            this.DefaultPatient = DefaultPatient;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getCredTypeName() {
            return CredTypeName;
        }

        public void setCredTypeName(String CredTypeName) {
            this.CredTypeName = CredTypeName;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String Age) {
            this.Age = Age;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(PatientId);
            dest.writeInt(AccountId);
            dest.writeString(PatientName);
            dest.writeString(NameLetter);
            dest.writeInt(PatientSex);
            dest.writeString(Birthday);
            dest.writeInt(CredType);
            dest.writeString(Mobile);
            dest.writeString(PicturePath);
            dest.writeInt(PatientType);
            dest.writeInt(Newborn);
            dest.writeInt(DefaultPatient);
            dest.writeString(CreateTime);
            dest.writeString(CredTypeName);
            dest.writeString(Age);
        }
    }
}
