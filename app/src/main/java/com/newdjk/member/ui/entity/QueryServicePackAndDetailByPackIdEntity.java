package com.newdjk.member.ui.entity;

import java.io.Serializable;
import java.util.List;

public class QueryServicePackAndDetailByPackIdEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"OrgId":67,"ServicePackId":61,"ServicePackName":"妇幼母胎监测服务包","OriginalPrice":0.04,"Price":0.04,"Description":"妇幼母胎监测服务包","PackStatus":1,"ServiceLong":90,"ServiceLongType":1,"FitPeople":null,"BuyNum":2,"MasterPicture":"http://resource.newstarthealth.cn/ServicePack/7f28fe62-64a4-43f1-9187-578271e3bc3e.png","EndTime":"2019-02-05 16:59:31","Details":[{"ServiceDetailId":167,"ServicePackId":61,"ServiceItemId":46,"ServiceItemName":"耦合剂费用","MasterPicture":"http://resource.newstarthealth.cn/ServiceItem/6e1d37f1-2d1c-43c5-abfe-676962bd1185.png","SericeItemCode":"0","NumValue":1,"NumType":1,"NumTypeName":"次","Description":"0","EachPrice":0.01,"TotalPrice":0.01},{"ServiceDetailId":168,"ServicePackId":61,"ServiceItemId":45,"ServiceItemName":"判读次数","MasterPicture":"http://resource.newstarthealth.cn/ServiceItem/6dfc05b5-517d-4a8f-bd44-d1423fdc5825.png","SericeItemCode":"1502","NumValue":5,"NumType":1,"NumTypeName":"次","Description":"0","EachPrice":0.01,"TotalPrice":0.01},{"ServiceDetailId":169,"ServicePackId":61,"ServiceItemId":44,"ServiceItemName":"租金","MasterPicture":"http://resource.newstarthealth.cn/ServiceItem/cbb3d394-e797-4986-a9a6-56073dd6d17c.png","SericeItemCode":"1503","NumValue":1,"NumType":1,"NumTypeName":"次","Description":"0","EachPrice":0.01,"TotalPrice":0.01},{"ServiceDetailId":170,"ServicePackId":61,"ServiceItemId":43,"ServiceItemName":"胎心监护仪","MasterPicture":"http://resource.newstarthealth.cn/ServiceItem/bf0b7b05-06ac-4805-b1b5-b3ad28facaa3.png","SericeItemCode":"1501","NumValue":1,"NumType":1,"NumTypeName":"次","Description":"领取地址：保定市人民医院\r\n联系人：王林\r\n联系电话：13526356632\r\n时间：周一至周五","EachPrice":0.01,"TotalPrice":0.01}]}
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

    public static class DataBean {
        /**
         * OrgId : 67
         * ServicePackId : 61
         * ServicePackName : 妇幼母胎监测服务包
         * OriginalPrice : 0.04
         * Price : 0.04
         * Description : 妇幼母胎监测服务包
         * PackStatus : 1
         * ServiceLong : 90
         * ServiceLongType : 1
         * FitPeople : null
         * BuyNum : 2
         * MasterPicture : http://resource.newstarthealth.cn/ServicePack/7f28fe62-64a4-43f1-9187-578271e3bc3e.png
         * EndTime : 2019-02-05 16:59:31
         * Details : [{"ServiceDetailId":167,"ServicePackId":61,"ServiceItemId":46,"ServiceItemName":"耦合剂费用","MasterPicture":"http://resource.newstarthealth.cn/ServiceItem/6e1d37f1-2d1c-43c5-abfe-676962bd1185.png","SericeItemCode":"0","NumValue":1,"NumType":1,"NumTypeName":"次","Description":"0","EachPrice":0.01,"TotalPrice":0.01},{"ServiceDetailId":168,"ServicePackId":61,"ServiceItemId":45,"ServiceItemName":"判读次数","MasterPicture":"http://resource.newstarthealth.cn/ServiceItem/6dfc05b5-517d-4a8f-bd44-d1423fdc5825.png","SericeItemCode":"1502","NumValue":5,"NumType":1,"NumTypeName":"次","Description":"0","EachPrice":0.01,"TotalPrice":0.01},{"ServiceDetailId":169,"ServicePackId":61,"ServiceItemId":44,"ServiceItemName":"租金","MasterPicture":"http://resource.newstarthealth.cn/ServiceItem/cbb3d394-e797-4986-a9a6-56073dd6d17c.png","SericeItemCode":"1503","NumValue":1,"NumType":1,"NumTypeName":"次","Description":"0","EachPrice":0.01,"TotalPrice":0.01},{"ServiceDetailId":170,"ServicePackId":61,"ServiceItemId":43,"ServiceItemName":"胎心监护仪","MasterPicture":"http://resource.newstarthealth.cn/ServiceItem/bf0b7b05-06ac-4805-b1b5-b3ad28facaa3.png","SericeItemCode":"1501","NumValue":1,"NumType":1,"NumTypeName":"次","Description":"领取地址：保定市人民医院\r\n联系人：王林\r\n联系电话：13526356632\r\n时间：周一至周五","EachPrice":0.01,"TotalPrice":0.01}]
         */

        private int OrgId;
        private int ServicePackId;
        private String ServicePackName;
        private double OriginalPrice;
        private double Price;
        private String Description;
        private int PackStatus;
        private int ServiceLong;
        private int ServiceLongType;
        private Object FitPeople;
        private int BuyNum;
        private String MasterPicture;
        private String EndTime;
        private List<DetailsBean> Details;

        public int getOrgId() {
            return OrgId;
        }

        public void setOrgId(int OrgId) {
            this.OrgId = OrgId;
        }

        public int getServicePackId() {
            return ServicePackId;
        }

        public void setServicePackId(int ServicePackId) {
            this.ServicePackId = ServicePackId;
        }

        public String getServicePackName() {
            return ServicePackName;
        }

        public void setServicePackName(String ServicePackName) {
            this.ServicePackName = ServicePackName;
        }

        public double getOriginalPrice() {
            return OriginalPrice;
        }

        public void setOriginalPrice(double OriginalPrice) {
            this.OriginalPrice = OriginalPrice;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getPackStatus() {
            return PackStatus;
        }

        public void setPackStatus(int PackStatus) {
            this.PackStatus = PackStatus;
        }

        public int getServiceLong() {
            return ServiceLong;
        }

        public void setServiceLong(int ServiceLong) {
            this.ServiceLong = ServiceLong;
        }

        public int getServiceLongType() {
            return ServiceLongType;
        }

        public void setServiceLongType(int ServiceLongType) {
            this.ServiceLongType = ServiceLongType;
        }

        public Object getFitPeople() {
            return FitPeople;
        }

        public void setFitPeople(Object FitPeople) {
            this.FitPeople = FitPeople;
        }

        public int getBuyNum() {
            return BuyNum;
        }

        public void setBuyNum(int BuyNum) {
            this.BuyNum = BuyNum;
        }

        public String getMasterPicture() {
            return MasterPicture;
        }

        public void setMasterPicture(String MasterPicture) {
            this.MasterPicture = MasterPicture;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public List<DetailsBean> getDetails() {
            return Details;
        }

        public void setDetails(List<DetailsBean> Details) {
            this.Details = Details;
        }

        public static class DetailsBean implements Serializable{
            /**
             * ServiceDetailId : 167
             * ServicePackId : 61
             * ServiceItemId : 46
             * ServiceItemName : 耦合剂费用
             * MasterPicture : http://resource.newstarthealth.cn/ServiceItem/6e1d37f1-2d1c-43c5-abfe-676962bd1185.png
             * SericeItemCode : 0
             * NumValue : 1
             * NumType : 1
             * NumTypeName : 次
             * Description : 0
             * EachPrice : 0.01
             * TotalPrice : 0.01
             */

            private int ServiceDetailId;
            private int ServicePackId;
            private int ServiceItemId;
            private String ServiceItemName;
            private String MasterPicture;
            private String SericeItemCode;
            private int NumValue;
            private int NumType;
            private String NumTypeName;
            private String Description;
            private double EachPrice;
            private double TotalPrice;

            public int getServiceDetailId() {
                return ServiceDetailId;
            }

            public void setServiceDetailId(int ServiceDetailId) {
                this.ServiceDetailId = ServiceDetailId;
            }

            public int getServicePackId() {
                return ServicePackId;
            }

            public void setServicePackId(int ServicePackId) {
                this.ServicePackId = ServicePackId;
            }

            public int getServiceItemId() {
                return ServiceItemId;
            }

            public void setServiceItemId(int ServiceItemId) {
                this.ServiceItemId = ServiceItemId;
            }

            public String getServiceItemName() {
                return ServiceItemName;
            }

            public void setServiceItemName(String ServiceItemName) {
                this.ServiceItemName = ServiceItemName;
            }

            public String getMasterPicture() {
                return MasterPicture;
            }

            public void setMasterPicture(String MasterPicture) {
                this.MasterPicture = MasterPicture;
            }

            public String getSericeItemCode() {
                return SericeItemCode;
            }

            public void setSericeItemCode(String SericeItemCode) {
                this.SericeItemCode = SericeItemCode;
            }

            public int getNumValue() {
                return NumValue;
            }

            public void setNumValue(int NumValue) {
                this.NumValue = NumValue;
            }

            public int getNumType() {
                return NumType;
            }

            public void setNumType(int NumType) {
                this.NumType = NumType;
            }

            public String getNumTypeName() {
                return NumTypeName;
            }

            public void setNumTypeName(String NumTypeName) {
                this.NumTypeName = NumTypeName;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public double getEachPrice() {
                return EachPrice;
            }

            public void setEachPrice(double EachPrice) {
                this.EachPrice = EachPrice;
            }

            public double getTotalPrice() {
                return TotalPrice;
            }

            public void setTotalPrice(double TotalPrice) {
                this.TotalPrice = TotalPrice;
            }
        }
    }
}
