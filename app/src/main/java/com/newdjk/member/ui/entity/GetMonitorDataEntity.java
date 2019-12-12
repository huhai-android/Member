package com.newdjk.member.ui.entity;

import java.util.List;

public class GetMonitorDataEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"type":"3p","key":{"fhr1":"0","fhr2":"1","afm":"2","toco":"3","fhrsign":"4","afmcount":"5","fmcount":"6","battery":"7","charge":"8","tocoreset":"9"},"bluenum":"LCeFM6018070001","data":[50,0,20,10,2,0,0,4,0,50,0,20,10,2,0,0,4,0,50,0,20,10,2,0,0,4,0,175,0,5,10,2,0,0,4,0,175,0,5,10,2,0,0,4,0,175,0,5,10,2,0,0,4,0,175,0,5,10,2,0,0,4,0,50,0,6,10,2,0,0,4,0,50,0,6,10,2,0,0,4,0,144,0,6,10,2,0,0,4,0,144,0,6,10,2,0,0,4,0,50,0,20,10,1,0,0,4,0,50,0,20,10,1,0,0,4,0,50,0,20,10,1,0,0,4,0,50,0,20,10,1,0,0,4,0,142,0,7,10,1,0,0,4,0,142,0,7,10,1,0,0,4,0,142,0,7,10,1,0,0,4,0,142,0,7,10,1,0,0,4,0,50,0,16,10,1,0,0,4,0],"tlong":5,"begindate":1541496214}
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
         * type : 3p
         * key : {"fhr1":"0","fhr2":"1","afm":"2","toco":"3","fhrsign":"4","afmcount":"5","fmcount":"6","battery":"7","charge":"8","tocoreset":"9"}
         * bluenum : LCeFM6018070001
         * data : [50,0,20,10,2,0,0,4,0,50,0,20,10,2,0,0,4,0,50,0,20,10,2,0,0,4,0,175,0,5,10,2,0,0,4,0,175,0,5,10,2,0,0,4,0,175,0,5,10,2,0,0,4,0,175,0,5,10,2,0,0,4,0,50,0,6,10,2,0,0,4,0,50,0,6,10,2,0,0,4,0,144,0,6,10,2,0,0,4,0,144,0,6,10,2,0,0,4,0,50,0,20,10,1,0,0,4,0,50,0,20,10,1,0,0,4,0,50,0,20,10,1,0,0,4,0,50,0,20,10,1,0,0,4,0,142,0,7,10,1,0,0,4,0,142,0,7,10,1,0,0,4,0,142,0,7,10,1,0,0,4,0,142,0,7,10,1,0,0,4,0,50,0,16,10,1,0,0,4,0]
         * tlong : 5
         * begindate : 1541496214
         */

        private String type;
        private KeyBean key;
        private String bluenum;
        private int tlong;
        private long begindate;
        private List<Integer> data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public KeyBean getKey() {
            return key;
        }

        public void setKey(KeyBean key) {
            this.key = key;
        }

        public String getBluenum() {
            return bluenum;
        }

        public void setBluenum(String bluenum) {
            this.bluenum = bluenum;
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

            private String fhr1;
            private String fhr2;
            private String afm;
            private String toco;
            private String fhrsign;
            private String afmcount;
            private String fmcount;
            private String battery;
            private String charge;
            private String tocoreset;

            public String getFhr1() {
                return fhr1;
            }

            public void setFhr1(String fhr1) {
                this.fhr1 = fhr1;
            }

            public String getFhr2() {
                return fhr2;
            }

            public void setFhr2(String fhr2) {
                this.fhr2 = fhr2;
            }

            public String getAfm() {
                return afm;
            }

            public void setAfm(String afm) {
                this.afm = afm;
            }

            public String getToco() {
                return toco;
            }

            public void setToco(String toco) {
                this.toco = toco;
            }

            public String getFhrsign() {
                return fhrsign;
            }

            public void setFhrsign(String fhrsign) {
                this.fhrsign = fhrsign;
            }

            public String getAfmcount() {
                return afmcount;
            }

            public void setAfmcount(String afmcount) {
                this.afmcount = afmcount;
            }

            public String getFmcount() {
                return fmcount;
            }

            public void setFmcount(String fmcount) {
                this.fmcount = fmcount;
            }

            public String getBattery() {
                return battery;
            }

            public void setBattery(String battery) {
                this.battery = battery;
            }

            public String getCharge() {
                return charge;
            }

            public void setCharge(String charge) {
                this.charge = charge;
            }

            public String getTocoreset() {
                return tocoreset;
            }

            public void setTocoreset(String tocoreset) {
                this.tocoreset = tocoreset;
            }
        }
    }
}
