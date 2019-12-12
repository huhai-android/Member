package com.newdjk.member.ui.entity;

import java.io.Serializable;

public class HaveBindEntity {

    /**
     * Code : 0
     * Message : 设备已绑定
     * Data : {"DeviceNo":"LCeFM6018051010","PayOrderId":null,"ServicePackId":null,"Patient":{"PatientId":45,"PatientName":"巢","Age":28,"DueDate":"2018-10-20 00:00:00","IdCard":null,"ContactPhone":"13145923720","EmergencyName":"张三","EmergencyPhone":"13145923720"},"ServicePhone":""}
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
         * DeviceNo : LCeFM6018051010
         * PayOrderId : null
         * ServicePackId : null
         * Patient : {"PatientId":45,"PatientName":"巢","Age":28,"DueDate":"2018-10-20 00:00:00","IdCard":null,"ContactPhone":"13145923720","EmergencyName":"张三","EmergencyPhone":"13145923720"}
         * ServicePhone :
         */

        private String DeviceNo;
        private int PayOrderId;
        private int ServicePackId;
        private PatientBean Patient;
        private String ServicePhone;

        public String getDeviceNo() {
            return DeviceNo;
        }

        public void setDeviceNo(String DeviceNo) {
            this.DeviceNo = DeviceNo;
        }

        public int getPayOrderId() {
            return PayOrderId;
        }

        public void setPayOrderId(int PayOrderId) {
            this.PayOrderId = PayOrderId;
        }

        public int getServicePackId() {
            return ServicePackId;
        }

        public void setServicePackId(int ServicePackId) {
            this.ServicePackId = ServicePackId;
        }

        public PatientBean getPatient() {
            return Patient;
        }

        public void setPatient(PatientBean Patient) {
            this.Patient = Patient;
        }

        public String getServicePhone() {
            return ServicePhone;
        }

        public void setServicePhone(String ServicePhone) {
            this.ServicePhone = ServicePhone;
        }

        public static class PatientBean implements Serializable {
            /**
             * PatientId : 45
             * PatientName : 巢
             * Age : 28
             * DueDate : 2018-10-20 00:00:00
             * IdCard : null
             * ContactPhone : 13145923720
             * EmergencyName : 张三
             * EmergencyPhone : 13145923720
             */

            private int PatientId;
            private String PatientName;
            private int Age;
            private String DueDate;
            private String IdCard;
            private String ContactPhone;
            private String EmergencyName;
            private String EmergencyPhone;

            public int getPatientId() {
                return PatientId;
            }

            public void setPatientId(int PatientId) {
                this.PatientId = PatientId;
            }

            public String getPatientName() {
                return PatientName;
            }

            public void setPatientName(String PatientName) {
                this.PatientName = PatientName;
            }

            public int getAge() {
                return Age;
            }

            public void setAge(int Age) {
                this.Age = Age;
            }

            public String getDueDate() {
                return DueDate;
            }

            public void setDueDate(String DueDate) {
                this.DueDate = DueDate;
            }

            public String getIdCard() {
                return IdCard;
            }

            public void setIdCard(String IdCard) {
                this.IdCard = IdCard;
            }

            public String getContactPhone() {
                return ContactPhone;
            }

            public void setContactPhone(String ContactPhone) {
                this.ContactPhone = ContactPhone;
            }

            public String getEmergencyName() {
                return EmergencyName;
            }

            public void setEmergencyName(String EmergencyName) {
                this.EmergencyName = EmergencyName;
            }

            public String getEmergencyPhone() {
                return EmergencyPhone;
            }

            public void setEmergencyPhone(String EmergencyPhone) {
                this.EmergencyPhone = EmergencyPhone;
            }
        }
    }
}
