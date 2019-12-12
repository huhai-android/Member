package com.newdjk.member.ui.entity;

import java.util.List;

public class FHREntity {

    /**
     * AccountId : 28
     * PatientId : 45
     * DeviceNo : LCeFM6018051010
     * FhrData : {"type":"3p","bluenum":"LCeFM6018051010","key":{"fhr1":0,"fhr2":1,"afm":2,"toco":3,"fhrsign":4,"afmcount":5,"fmcount":6,"battery":7,"charge":8,"tocoreset":9},"data":[0,0,20,7,1,4,0,0,0,0,0,0,0,7,1,4,0,0,0,0,0,0,0,7,1,4,0,0,0,0,0,0,0,7,1,4,0,0,0,0,0,0,20,7,1,4,0,0,0,0,0,0,19,8,1,4,0,0,0,0,0,0,19,38,1,4,0,0,0,0,0,0,20,62,1,4,0,0,0,0,0,0,20,79,1,4,0,0,0,0,0,0,20,53,1,4,0,0,0,0,0,0,20,61,1,4,0,0,0,0,0,0,20,50,1,4,0,0,0,0,0,0,20,38,1,4,0,0,0,0,0,0,20,39,1,4,0,0,0,0,0,0,20,41,1,4,0,0,0,0,0,0,19,34,1,4,0,0,0,0,0,0,19,39,1,4,0,0,0,0,0,0,15,47,1,4,0,0,0,0,0,0,15,36,1,4,0,0,0,0,0,0,8,43,1,4,0,0,0,0,0,0,8,46,1,4,0,0,0,0,0,0,9,44,1,4,0,0,0,0,0,0,9,48,1,4,0,0,0,0,0,0,20,41,1,4,0,0,0,0,0,0,20,47,1,4,0,0,0,0,0,0,20,34,1,4,0,0,0,0,0,0,20,25,1,4,0,0,0,0,0,0,16,16,1,4,0,0,0,0,0,0,16,13,1,4,0,0,0,0,0,0,13,11,1,4,0,0,0,0],"tlong":14,"begindate":1542619810}
     */

    private int AccountId;
    private int PatientId;
    private String DeviceNo;
    private FhrDataBean FhrData;

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int AccountId) {
        this.AccountId = AccountId;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int PatientId) {
        this.PatientId = PatientId;
    }

    public String getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(String DeviceNo) {
        this.DeviceNo = DeviceNo;
    }

    public FhrDataBean getFhrData() {
        return FhrData;
    }

    public void setFhrData(FhrDataBean FhrData) {
        this.FhrData = FhrData;
    }

    public static class FhrDataBean {
        /**
         * type : 3p
         * bluenum : LCeFM6018051010
         * key : {"fhr1":0,"fhr2":1,"afm":2,"toco":3,"fhrsign":4,"afmcount":5,"fmcount":6,"battery":7,"charge":8,"tocoreset":9}
         * data : [0,0,20,7,1,4,0,0,0,0,0,0,0,7,1,4,0,0,0,0,0,0,0,7,1,4,0,0,0,0,0,0,0,7,1,4,0,0,0,0,0,0,20,7,1,4,0,0,0,0,0,0,19,8,1,4,0,0,0,0,0,0,19,38,1,4,0,0,0,0,0,0,20,62,1,4,0,0,0,0,0,0,20,79,1,4,0,0,0,0,0,0,20,53,1,4,0,0,0,0,0,0,20,61,1,4,0,0,0,0,0,0,20,50,1,4,0,0,0,0,0,0,20,38,1,4,0,0,0,0,0,0,20,39,1,4,0,0,0,0,0,0,20,41,1,4,0,0,0,0,0,0,19,34,1,4,0,0,0,0,0,0,19,39,1,4,0,0,0,0,0,0,15,47,1,4,0,0,0,0,0,0,15,36,1,4,0,0,0,0,0,0,8,43,1,4,0,0,0,0,0,0,8,46,1,4,0,0,0,0,0,0,9,44,1,4,0,0,0,0,0,0,9,48,1,4,0,0,0,0,0,0,20,41,1,4,0,0,0,0,0,0,20,47,1,4,0,0,0,0,0,0,20,34,1,4,0,0,0,0,0,0,20,25,1,4,0,0,0,0,0,0,16,16,1,4,0,0,0,0,0,0,16,13,1,4,0,0,0,0,0,0,13,11,1,4,0,0,0,0]
         * tlong : 14
         * begindate : 1542619810
         */

        private String type;
        private String bluenum;
        private KeyBean key;
        private int tlong;
        private long begindate;
        private List<Integer> data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBluenum() {
            return bluenum;
        }

        public void setBluenum(String bluenum) {
            this.bluenum = bluenum;
        }

        public KeyBean getKey() {
            return key;
        }

        public void setKey(KeyBean key) {
            this.key = key;
        }

        public int getTlong() {
            return tlong;
        }

        public void setTlong(int tlong) {
            this.tlong = tlong;
        }

        public long getBegindate() {
            return begindate;
        }

        public void setBegindate(long begindate) {
            this.begindate = begindate;
        }

        public List<Integer> getData() {
            return data;
        }

        public void setData(List<Integer> data) {
            this.data = data;
        }

        public static class KeyBean {
            /**
             * fhr1 : 0
             * fhr2 : 1
             * afm : 2
             * toco : 3
             * fhrsign : 4
             * afmcount : 5
             * fmcount : 6
             * battery : 7
             * charge : 8
             * tocoreset : 9
             */

            private int fhr1;
            private int fhr2;
            private int afm;
            private int toco;
            private int fhrsign;
            private int afmcount;
            private int fmcount;
            private int battery;
            private int charge;
            private int tocoreset;

            public int getFhr1() {
                return fhr1;
            }

            public void setFhr1(int fhr1) {
                this.fhr1 = fhr1;
            }

            public int getFhr2() {
                return fhr2;
            }

            public void setFhr2(int fhr2) {
                this.fhr2 = fhr2;
            }

            public int getAfm() {
                return afm;
            }

            public void setAfm(int afm) {
                this.afm = afm;
            }

            public int getToco() {
                return toco;
            }

            public void setToco(int toco) {
                this.toco = toco;
            }

            public int getFhrsign() {
                return fhrsign;
            }

            public void setFhrsign(int fhrsign) {
                this.fhrsign = fhrsign;
            }

            public int getAfmcount() {
                return afmcount;
            }

            public void setAfmcount(int afmcount) {
                this.afmcount = afmcount;
            }

            public int getFmcount() {
                return fmcount;
            }

            public void setFmcount(int fmcount) {
                this.fmcount = fmcount;
            }

            public int getBattery() {
                return battery;
            }

            public void setBattery(int battery) {
                this.battery = battery;
            }

            public int getCharge() {
                return charge;
            }

            public void setCharge(int charge) {
                this.charge = charge;
            }

            public int getTocoreset() {
                return tocoreset;
            }

            public void setTocoreset(int tocoreset) {
                this.tocoreset = tocoreset;
            }
        }
    }
}
