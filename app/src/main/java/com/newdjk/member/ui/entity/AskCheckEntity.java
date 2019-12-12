package com.newdjk.member.ui.entity;

public class AskCheckEntity {

    /**
     * Code : 0
     * Message : 有剩余判读次数
     * Data : {"TotalNum":200,"UsedNum":0,"RemainNum":200}
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
         * TotalNum : 200
         * UsedNum : 0
         * RemainNum : 200
         */

        private int TotalNum;
        private int UsedNum;
        private int RemainNum;

        public int getTotalNum() {
            return TotalNum;
        }

        public void setTotalNum(int TotalNum) {
            this.TotalNum = TotalNum;
        }

        public int getUsedNum() {
            return UsedNum;
        }

        public void setUsedNum(int UsedNum) {
            this.UsedNum = UsedNum;
        }

        public int getRemainNum() {
            return RemainNum;
        }

        public void setRemainNum(int RemainNum) {
            this.RemainNum = RemainNum;
        }
    }
}
