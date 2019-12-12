package com.newdjk.member.ui.entity;

import java.util.List;

public class GetMonitorSetEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"OpenAutoStart":0,"OpenAlarm":1,"OpenAutoFinish":1,"WifiAutoUpload":0,"OpenAutoFh":0,"AlarmOptions":[{"Id":1,"Value":5,"IsSelected":false},{"Id":2,"Value":10,"IsSelected":false},{"Id":3,"Value":15,"IsSelected":false},{"Id":4,"Value":20,"IsSelected":true},{"Id":5,"Value":25,"IsSelected":false},{"Id":6,"Value":30,"IsSelected":false}],"FinishOptions":[{"Id":1,"Value":20,"IsSelected":false},{"Id":2,"Value":30,"IsSelected":true},{"Id":3,"Value":40,"IsSelected":false}]}
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
         * OpenAutoStart : 0
         * OpenAlarm : 1
         * OpenAutoFinish : 1
         * WifiAutoUpload : 0
         * OpenAutoFh : 0
         * AlarmOptions : [{"Id":1,"Value":5,"IsSelected":false},{"Id":2,"Value":10,"IsSelected":false},{"Id":3,"Value":15,"IsSelected":false},{"Id":4,"Value":20,"IsSelected":true},{"Id":5,"Value":25,"IsSelected":false},{"Id":6,"Value":30,"IsSelected":false}]
         * FinishOptions : [{"Id":1,"Value":20,"IsSelected":false},{"Id":2,"Value":30,"IsSelected":true},{"Id":3,"Value":40,"IsSelected":false}]
         */

        private int OpenAutoStart;
        private int OpenAlarm;
        private int OpenAutoFinish;
        private int WifiAutoUpload;
        private int OpenAutoFh;
        private List<AlarmOptionsBean> AlarmOptions;
        private List<FinishOptionsBean> FinishOptions;

        public int getOpenAutoStart() {
            return OpenAutoStart;
        }

        public void setOpenAutoStart(int OpenAutoStart) {
            this.OpenAutoStart = OpenAutoStart;
        }

        public int getOpenAlarm() {
            return OpenAlarm;
        }

        public void setOpenAlarm(int OpenAlarm) {
            this.OpenAlarm = OpenAlarm;
        }

        public int getOpenAutoFinish() {
            return OpenAutoFinish;
        }

        public void setOpenAutoFinish(int OpenAutoFinish) {
            this.OpenAutoFinish = OpenAutoFinish;
        }

        public int getWifiAutoUpload() {
            return WifiAutoUpload;
        }

        public void setWifiAutoUpload(int WifiAutoUpload) {
            this.WifiAutoUpload = WifiAutoUpload;
        }

        public int getOpenAutoFh() {
            return OpenAutoFh;
        }

        public void setOpenAutoFh(int OpenAutoFh) {
            this.OpenAutoFh = OpenAutoFh;
        }

        public List<AlarmOptionsBean> getAlarmOptions() {
            return AlarmOptions;
        }

        public void setAlarmOptions(List<AlarmOptionsBean> AlarmOptions) {
            this.AlarmOptions = AlarmOptions;
        }

        public List<FinishOptionsBean> getFinishOptions() {
            return FinishOptions;
        }

        public void setFinishOptions(List<FinishOptionsBean> FinishOptions) {
            this.FinishOptions = FinishOptions;
        }

        public static class AlarmOptionsBean {
            /**
             * Id : 1
             * Value : 5
             * IsSelected : false
             */

            private int Id;
            private int Value;
            private boolean IsSelected;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }

            public boolean isIsSelected() {
                return IsSelected;
            }

            public void setIsSelected(boolean IsSelected) {
                this.IsSelected = IsSelected;
            }
        }

        public static class FinishOptionsBean {
            /**
             * Id : 1
             * Value : 20
             * IsSelected : false
             */

            private int Id;
            private int Value;
            private boolean IsSelected;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }

            public boolean isIsSelected() {
                return IsSelected;
            }

            public void setIsSelected(boolean IsSelected) {
                this.IsSelected = IsSelected;
            }
        }
    }
}
