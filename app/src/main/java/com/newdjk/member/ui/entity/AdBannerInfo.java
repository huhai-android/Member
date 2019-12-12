package com.newdjk.member.ui.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EDZ on 2018/9/19.
 */

public class AdBannerInfo  implements Serializable{


    /**
     * Code : 0
     * Message :
     * Data : [{"AdClassName":"患者APP首页Banner","Id":1,"AdClassId":3,"Name":"活动1","ContentType":2,"ContentLink":"/VIPchannel/index","Content":"http://resource.newstarthealth.cn//ad/image/20190428/6369206814584202539715489.png","Sort":1,"EffectiveTime":"2000-12-31 00:00:00","Status":1,"CreateTime":"2018-09-21 16:15:11","LinkContent":null,"IsShared":1,"ShareTitle":null,"ShareCotent":null,"ShareLink":null}]
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

    public static class DataBean implements Serializable{
        /**
         * AdClassName : 患者APP首页Banner
         * Id : 1
         * AdClassId : 3
         * Name : 活动1
         * ContentType : 2
         * ContentLink : /VIPchannel/index
         * Content : http://resource.newstarthealth.cn//ad/image/20190428/6369206814584202539715489.png
         * Sort : 1
         * EffectiveTime : 2000-12-31 00:00:00
         * Status : 1
         * CreateTime : 2018-09-21 16:15:11
         * LinkContent : null
         * IsShared : 1
         * ShareTitle : null
         * ShareCotent : null
         * ShareLink : null
         */

        private String AdClassName;
        private int Id;
        private int AdClassId;
        private String Name;
        private int ContentType;
        private String ContentLink;
        private String Content;
        private int Sort;
        private String EffectiveTime;
        private int Status;
        private String CreateTime;
        private String LinkContent;
        private int IsShared;
        private String ShareTitle;
        private String ShareCotent;
        private String ShareLink;

        public String getAdClassName() {
            return AdClassName;
        }

        public void setAdClassName(String AdClassName) {
            this.AdClassName = AdClassName;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getAdClassId() {
            return AdClassId;
        }

        public void setAdClassId(int AdClassId) {
            this.AdClassId = AdClassId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getContentType() {
            return ContentType;
        }

        public void setContentType(int ContentType) {
            this.ContentType = ContentType;
        }

        public String getContentLink() {
            return ContentLink;
        }

        public void setContentLink(String ContentLink) {
            this.ContentLink = ContentLink;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public String getEffectiveTime() {
            return EffectiveTime;
        }

        public void setEffectiveTime(String EffectiveTime) {
            this.EffectiveTime = EffectiveTime;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getLinkContent() {
            return LinkContent;
        }

        public void setLinkContent(String LinkContent) {
            this.LinkContent = LinkContent;
        }

        public int getIsShared() {
            return IsShared;
        }

        public void setIsShared(int IsShared) {
            this.IsShared = IsShared;
        }

        public String getShareTitle() {
            return ShareTitle;
        }

        public void setShareTitle(String ShareTitle) {
            this.ShareTitle = ShareTitle;
        }

        public String getShareCotent() {
            return ShareCotent;
        }

        public void setShareCotent(String ShareCotent) {
            this.ShareCotent = ShareCotent;
        }

        public String getShareLink() {
            return ShareLink;
        }

        public void setShareLink(String ShareLink) {
            this.ShareLink = ShareLink;
        }
    }
}
