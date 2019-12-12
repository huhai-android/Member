package com.newdjk.member.ui.entity;

import java.util.List;

/**
 * Created by EDZ on 2018/11/5.
 */

public class HotSearchEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"Total":2,"ReturnData":[{"DrSearchId":2,"SearchKey":"糖尿病","SearchNum":2,"SearchTime":"2018-11-05 12:03:17","CreateTime":"2018-11-05 12:03:18","UpdateTime":"2018-11-05 12:03:18"},{"DrSearchId":1,"SearchKey":"高血压","SearchNum":1,"SearchTime":"2018-11-05 12:01:56","CreateTime":"2018-11-05 12:02:31","UpdateTime":"2018-11-05 12:02:31"}]}
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
         * Total : 2
         * ReturnData : [{"DrSearchId":2,"SearchKey":"糖尿病","SearchNum":2,"SearchTime":"2018-11-05 12:03:17","CreateTime":"2018-11-05 12:03:18","UpdateTime":"2018-11-05 12:03:18"},{"DrSearchId":1,"SearchKey":"高血压","SearchNum":1,"SearchTime":"2018-11-05 12:01:56","CreateTime":"2018-11-05 12:02:31","UpdateTime":"2018-11-05 12:02:31"}]
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
             * DrSearchId : 2
             * SearchKey : 糖尿病
             * SearchNum : 2
             * SearchTime : 2018-11-05 12:03:17
             * CreateTime : 2018-11-05 12:03:18
             * UpdateTime : 2018-11-05 12:03:18
             */

            private int DrSearchId;
            private String SearchKey;
            private int SearchNum;
            private String SearchTime;
            private String CreateTime;
            private String UpdateTime;

            public int getDrSearchId() {
                return DrSearchId;
            }

            public void setDrSearchId(int DrSearchId) {
                this.DrSearchId = DrSearchId;
            }

            public String getSearchKey() {
                return SearchKey;
            }

            public void setSearchKey(String SearchKey) {
                this.SearchKey = SearchKey;
            }

            public int getSearchNum() {
                return SearchNum;
            }

            public void setSearchNum(int SearchNum) {
                this.SearchNum = SearchNum;
            }

            public String getSearchTime() {
                return SearchTime;
            }

            public void setSearchTime(String SearchTime) {
                this.SearchTime = SearchTime;
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
        }
    }
}
