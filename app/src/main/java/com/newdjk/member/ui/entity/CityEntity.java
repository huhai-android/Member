package com.newdjk.member.ui.entity;

import java.util.List;

public class CityEntity {

    /**
     * Code : 0
     * Message :
     * Data : [{"AreaId":1,"AreaName":"北京","ParentId":0,"Path":",0,1,","AreaOrder":99,"IsHot":0,"EnglishName":"Beijing"},{"AreaId":2,"AreaName":"上海","ParentId":0,"Path":",0,2,","AreaOrder":98,"IsHot":0,"EnglishName":"Shanghai"},{"AreaId":3,"AreaName":"天津","ParentId":0,"Path":",0,3,","AreaOrder":97,"IsHot":0,"EnglishName":"Tianjin"},{"AreaId":6,"AreaName":"广东省","ParentId":0,"Path":",0,6,","AreaOrder":96,"IsHot":0,"EnglishName":"Guangdong"},{"AreaId":31,"AreaName":"内蒙古区","ParentId":0,"Path":",0,31,","AreaOrder":95,"IsHot":0,"EnglishName":"Inner Mongolia"},{"AreaId":21,"AreaName":"江西省","ParentId":0,"Path":",0,21,","AreaOrder":94,"IsHot":0,"EnglishName":"Jiangxi"},{"AreaId":15,"AreaName":"山东省","ParentId":0,"Path":",0,15,","AreaOrder":93,"IsHot":0,"EnglishName":"Shandong"},{"AreaId":19,"AreaName":"湖南省","ParentId":0,"Path":",0,19,","AreaOrder":92,"IsHot":0,"EnglishName":"Hunan"},{"AreaId":18,"AreaName":"湖北省","ParentId":0,"Path":",0,18,","AreaOrder":91,"IsHot":0,"EnglishName":"Hubei"},{"AreaId":7,"AreaName":"江苏省","ParentId":0,"Path":",0,7,","AreaOrder":90,"IsHot":0,"EnglishName":"Jiangsu"},{"AreaId":4,"AreaName":"重庆","ParentId":0,"Path":",0,4,","AreaOrder":0,"IsHot":0,"EnglishName":"Chongqing"},{"AreaId":5,"AreaName":"浙江省","ParentId":0,"Path":",0,5,","AreaOrder":0,"IsHot":0,"EnglishName":"Zhejiang"},{"AreaId":8,"AreaName":"河北省","ParentId":0,"Path":",0,8,","AreaOrder":0,"IsHot":0,"EnglishName":"Hebei"},{"AreaId":9,"AreaName":"山西省","ParentId":0,"Path":",0,9,","AreaOrder":0,"IsHot":0,"EnglishName":"Shanxi"},{"AreaId":10,"AreaName":"四川省","ParentId":0,"Path":",0,10,","AreaOrder":0,"IsHot":0,"EnglishName":"Sichuan"},{"AreaId":11,"AreaName":"河南省","ParentId":0,"Path":",0,11,","AreaOrder":0,"IsHot":0,"EnglishName":"Henan"},{"AreaId":12,"AreaName":"辽宁省","ParentId":0,"Path":",0,12,","AreaOrder":0,"IsHot":0,"EnglishName":"Liaoning"},{"AreaId":13,"AreaName":"吉林省","ParentId":0,"Path":",0,13,","AreaOrder":0,"IsHot":0,"EnglishName":"Jilin"},{"AreaId":14,"AreaName":"黑龙江省","ParentId":0,"Path":",0,14,","AreaOrder":0,"IsHot":0,"EnglishName":"Heilongjiang"},{"AreaId":16,"AreaName":"安徽省","ParentId":0,"Path":",0,16,","AreaOrder":0,"IsHot":0,"EnglishName":"Anhui"},{"AreaId":17,"AreaName":"福建省","ParentId":0,"Path":",0,17,","AreaOrder":0,"IsHot":0,"EnglishName":"Fujian"},{"AreaId":20,"AreaName":"海南省","ParentId":0,"Path":",0,20,","AreaOrder":0,"IsHot":0,"EnglishName":"Hainan"},{"AreaId":22,"AreaName":"贵州省","ParentId":0,"Path":",0,22,","AreaOrder":0,"IsHot":0,"EnglishName":"Guizhou"},{"AreaId":23,"AreaName":"云南省","ParentId":0,"Path":",0,23,","AreaOrder":0,"IsHot":0,"EnglishName":"Yunnan"},{"AreaId":24,"AreaName":"陕西省","ParentId":0,"Path":",0,24,","AreaOrder":0,"IsHot":0,"EnglishName":"Shanxi"},{"AreaId":25,"AreaName":"甘肃省","ParentId":0,"Path":",0,25,","AreaOrder":0,"IsHot":0,"EnglishName":"Gansu"},{"AreaId":26,"AreaName":"广西区","ParentId":0,"Path":",0,26,","AreaOrder":0,"IsHot":0,"EnglishName":"Guangxi"},{"AreaId":27,"AreaName":"宁夏区","ParentId":0,"Path":",0,27,","AreaOrder":0,"IsHot":0,"EnglishName":"Ningxia"},{"AreaId":28,"AreaName":"青海省","ParentId":0,"Path":",0,28,","AreaOrder":0,"IsHot":0,"EnglishName":"Qinghai"},{"AreaId":29,"AreaName":"新疆区","ParentId":0,"Path":",0,29,","AreaOrder":0,"IsHot":0,"EnglishName":"Xinjiang"},{"AreaId":30,"AreaName":"西藏区","ParentId":0,"Path":",0,30,","AreaOrder":0,"IsHot":0,"EnglishName":"Tibet"},{"AreaId":32,"AreaName":"香港","ParentId":0,"Path":",0,32,","AreaOrder":0,"IsHot":0,"EnglishName":"Hongkong"},{"AreaId":33,"AreaName":"澳门","ParentId":0,"Path":",0,33,","AreaOrder":0,"IsHot":0,"EnglishName":"Macao"},{"AreaId":34,"AreaName":"台湾","ParentId":0,"Path":",0,34,","AreaOrder":0,"IsHot":0,"EnglishName":"Taiwan"}]
     */

    private int Code;
    private String Message;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * AreaId : 1
         * AreaName : 北京
         * ParentId : 0
         * Path : ,0,1,
         * AreaOrder : 99
         * IsHot : 0
         * EnglishName : Beijing
         */

        private int AreaId;
        private String AreaName;
        private int ParentId;
        private String Path;
        private int AreaOrder;
        private int IsHot;
        private String EnglishName;

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

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }

        public String getPath() {
            return Path;
        }

        public void setPath(String Path) {
            this.Path = Path;
        }

        public int getAreaOrder() {
            return AreaOrder;
        }

        public void setAreaOrder(int AreaOrder) {
            this.AreaOrder = AreaOrder;
        }

        public int getIsHot() {
            return IsHot;
        }

        public void setIsHot(int IsHot) {
            this.IsHot = IsHot;
        }

        public String getEnglishName() {
            return EnglishName;
        }

        public void setEnglishName(String EnglishName) {
            this.EnglishName = EnglishName;
        }
    }
}
