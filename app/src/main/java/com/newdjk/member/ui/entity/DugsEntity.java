package com.newdjk.member.ui.entity;

import java.util.List;

/**
 * Created by EDZ on 2018/11/8.
 */

public class DugsEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"Total":297,"ReturnData":[{"Id":25,"Name":"阿司匹林肠溶片","Manufacturer":"德国拜耳医药保健有限公司启东分公司","MedicationCompanyName":"新起点药房"},{"Id":64,"Name":"奥利司他胶囊","Manufacturer":"中山万汉医药科技有限公司","MedicationCompanyName":"新起点药房"}]}
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
         * Total : 297
         * ReturnData : [{"Id":25,"Name":"阿司匹林肠溶片","Manufacturer":"德国拜耳医药保健有限公司启东分公司","MedicationCompanyName":"新起点药房"},{"Id":64,"Name":"奥利司他胶囊","Manufacturer":"中山万汉医药科技有限公司","MedicationCompanyName":"新起点药房"}]
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
             * Id : 25
             * Name : 阿司匹林肠溶片
             * Manufacturer : 德国拜耳医药保健有限公司启东分公司
             * MedicationCompanyName : 新起点药房
             */

            private int Id;
            private String Name;
            private String Manufacturer;
            private String MedicationCompanyName;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getManufacturer() {
                return Manufacturer;
            }

            public void setManufacturer(String Manufacturer) {
                this.Manufacturer = Manufacturer;
            }

            public String getMedicationCompanyName() {
                return MedicationCompanyName;
            }

            public void setMedicationCompanyName(String MedicationCompanyName) {
                this.MedicationCompanyName = MedicationCompanyName;
            }
        }
    }
}
