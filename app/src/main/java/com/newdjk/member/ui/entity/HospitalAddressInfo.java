package com.newdjk.member.ui.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class HospitalAddressInfo implements Parcelable {


    /**
     * HospitalId : 3399
     * HospitalName : 保定市第一中医院
     * HospitalLevel : 4
     * AreaId : 42
     * AreaName : 保定市
     * AreaPath : ,0,3,42,
     * Address : 河北省保定市裕华西路530号
     * Longitude : 115.496684
     * Dimension : 38.864342
     * Contact : null
     * TransportLine : null
     * Order : 0
     * IsHot : 0
     * IsDisplay : 1
     * ImgPath :
     * Description : 第一中医院1949年建院，是一所集医疗、科研、教学、康复和预防保健为一体的三级甲等综合中医院。是北京中医药大学、河北医科大学附属保定医院，省级示范中医院。是全国建院较早，在全国有一定影响的中医院。　　医院占地14652平方米，建筑面积25170平方米，固定资产1.6亿元，全院职工600余人，高级职称136人，硕导5人，博、硕研究生35人，省级名医2人，市级名医12人，国家、省级优秀临床人才10名，三三三人才工程4人，新世纪学术学科带头人18人，有较合理的人才梯队。　　近年来获得多项省、市级科研成果。医院设备齐全，全院有临床科室22个，医技科室11个，职能科室等共计62个，其中，国家级重点学科1个，国家“十二五”重点发展专科2个，国家级名医工作室1个，国家级培训基地1个，省、市级重点学科6个。年门诊量20万人次，年收治病人近万人次。　　第一中医院中医药特色优势明显，中医药临床疗效显著，综合服务能力较强，始终坚持公立医院的公益性，一切为了患者的健康，为患者提供全方位的医疗保健康复服务。
     * Invalid : 0
     * CreateTime : 2018-10-06 15:21:41
     * UpdateTime : 2018-10-31 15:29:41
     */

    private int HospitalId;
    private String HospitalName;
    private int HospitalLevel;
    private int AreaId;
    private String AreaName;
    private String AreaPath;
    private String Address;
    private String Longitude;
    private String Dimension;
    private Object Contact;
    private Object TransportLine;
    private int Order;
    private int IsHot;
    private int IsDisplay;
    private String ImgPath;
    private String Description;
    private int Invalid;
    private String CreateTime;
    private String UpdateTime;

    protected HospitalAddressInfo(Parcel in) {
        HospitalId = in.readInt();
        HospitalName = in.readString();
        HospitalLevel = in.readInt();
        AreaId = in.readInt();
        AreaName = in.readString();
        AreaPath = in.readString();
        Address = in.readString();
        Longitude = in.readString();
        Dimension = in.readString();
        Order = in.readInt();
        IsHot = in.readInt();
        IsDisplay = in.readInt();
        ImgPath = in.readString();
        Description = in.readString();
        Invalid = in.readInt();
        CreateTime = in.readString();
        UpdateTime = in.readString();
    }

    public static final Creator<HospitalAddressInfo> CREATOR = new Creator<HospitalAddressInfo>() {
        @Override
        public HospitalAddressInfo createFromParcel(Parcel in) {
            return new HospitalAddressInfo(in);
        }

        @Override
        public HospitalAddressInfo[] newArray(int size) {
            return new HospitalAddressInfo[size];
        }
    };

    public int getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(int HospitalId) {
        this.HospitalId = HospitalId;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    public int getHospitalLevel() {
        return HospitalLevel;
    }

    public void setHospitalLevel(int HospitalLevel) {
        this.HospitalLevel = HospitalLevel;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int AreaId) {
        this.AreaId = AreaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public String getAreaPath() {
        return AreaPath;
    }

    public void setAreaPath(String AreaPath) {
        this.AreaPath = AreaPath;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getDimension() {
        return Dimension;
    }

    public void setDimension(String Dimension) {
        this.Dimension = Dimension;
    }

    public Object getContact() {
        return Contact;
    }

    public void setContact(Object Contact) {
        this.Contact = Contact;
    }

    public Object getTransportLine() {
        return TransportLine;
    }

    public void setTransportLine(Object TransportLine) {
        this.TransportLine = TransportLine;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int Order) {
        this.Order = Order;
    }

    public int getIsHot() {
        return IsHot;
    }

    public void setIsHot(int IsHot) {
        this.IsHot = IsHot;
    }

    public int getIsDisplay() {
        return IsDisplay;
    }

    public void setIsDisplay(int IsDisplay) {
        this.IsDisplay = IsDisplay;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String ImgPath) {
        this.ImgPath = ImgPath;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getInvalid() {
        return Invalid;
    }

    public void setInvalid(int Invalid) {
        this.Invalid = Invalid;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(HospitalId);
        dest.writeString(HospitalName);
        dest.writeInt(HospitalLevel);
        dest.writeInt(AreaId);
        dest.writeString(AreaName);
        dest.writeString(AreaPath);
        dest.writeString(Address);
        dest.writeString(Longitude);
        dest.writeString(Dimension);
        dest.writeInt(Order);
        dest.writeInt(IsHot);
        dest.writeInt(IsDisplay);
        dest.writeString(ImgPath);
        dest.writeString(Description);
        dest.writeInt(Invalid);
        dest.writeString(CreateTime);
        dest.writeString(UpdateTime);
    }
}
