package com.newdjk.member.ui.entity;

import java.util.List;

public class QueryServicePackPageEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"Total":1,"ReturnData":[{"ServicePackId":61,"ServicePackName":"妇幼母胎监测服务包","OriginalPrice":0.04,"Price":0.04,"Description":"妇幼母胎监测服务包","MasterPicture":"http://resource.newstarthealth.cn/ServicePack/7f28fe62-64a4-43f1-9187-578271e3bc3e.png","PackStatus":1,"ServiceLong":90,"ServiceLongType":1,"FitPeople":null,"BuyNum":1,"OrgId":67}]}
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
         * Total : 1
         * ReturnData : [{"ServicePackId":61,"ServicePackName":"妇幼母胎监测服务包","OriginalPrice":0.04,"Price":0.04,"Description":"妇幼母胎监测服务包","MasterPicture":"http://resource.newstarthealth.cn/ServicePack/7f28fe62-64a4-43f1-9187-578271e3bc3e.png","PackStatus":1,"ServiceLong":90,"ServiceLongType":1,"FitPeople":null,"BuyNum":1,"OrgId":67}]
         */

        private int Total;
        private List<ReturnDataBean> ReturnData;

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public List<ReturnDataBean> getReturnData() {
            return ReturnData;
        }

        public void setReturnData(List<ReturnDataBean> ReturnData) {
            this.ReturnData = ReturnData;
        }

        public static class ReturnDataBean {
            /**
             * ServicePackId : 61
             * ServicePackName : 妇幼母胎监测服务包
             * OriginalPrice : 0.04
             * Price : 0.04
             * Description : 妇幼母胎监测服务包
             * MasterPicture : http://resource.newstarthealth.cn/ServicePack/7f28fe62-64a4-43f1-9187-578271e3bc3e.png
             * PackStatus : 1
             * ServiceLong : 90
             * ServiceLongType : 1
             * FitPeople : null
             * BuyNum : 1
             * OrgId : 67
             */

            private int ServicePackId;
            private String ServicePackName;
            private double OriginalPrice;
            private double Price;
            private String Description;
            private String MasterPicture;
            private int PackStatus;
            private int ServiceLong;
            private int ServiceLongType;
            private Object FitPeople;
            private int BuyNum;
            private int OrgId;

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

            public String getMasterPicture() {
                return MasterPicture;
            }

            public void setMasterPicture(String MasterPicture) {
                this.MasterPicture = MasterPicture;
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

            public int getOrgId() {
                return OrgId;
            }

            public void setOrgId(int OrgId) {
                this.OrgId = OrgId;
            }
        }
    }
}
