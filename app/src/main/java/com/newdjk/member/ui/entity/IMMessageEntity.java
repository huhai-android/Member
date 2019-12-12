package com.newdjk.member.ui.entity;

public class IMMessageEntity {

    /**
     * Code : 0
     * Message :
     * Data : {"Id":452,"UserType":1,"UserId":568,"Identifier":"pat_568","UserSig":"eJxlj0FPgzAYhu-8iobrjLaFIjPxgIAG3Q4Kc3IihRbWCKxCB5vG-*6GGpv4Hb7L8*R9v*-DAACYySI*p0Wx3bUqUwfJTXAFTGie-UEpBcuoyqyO-YN8L0XHM1oq3k0QEUIwhLojGG*VKMWPIY9RxHE1oWev2dTynWBDiFyC546uiGqCy3DlR-7Q0yEhl*SmfnnylnGEFs9NhMd1UO8e6xSW4X0Lq9s9cpQnQm*zuvAL10sPdwyncZMna-a*eQtmUfgQy2DEUWXTfKZyPPbXWqUSDf99CdnWcesHDbzrxbadBAwRQdiCpzGNT*MLXp1c7A__","Nick":"","FaceUrl":"http://userImage.newstarthealth.cn/pat/0/959.jpg","UpdateTime":"2019-03-01 17:12:36","CreateTime":"2019-03-01 17:12:36"}
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
         * Id : 452
         * UserType : 1
         * UserId : 568
         * Identifier : pat_568
         * UserSig : eJxlj0FPgzAYhu-8iobrjLaFIjPxgIAG3Q4Kc3IihRbWCKxCB5vG-*6GGpv4Hb7L8*R9v*-DAACYySI*p0Wx3bUqUwfJTXAFTGie-UEpBcuoyqyO-YN8L0XHM1oq3k0QEUIwhLojGG*VKMWPIY9RxHE1oWev2dTynWBDiFyC546uiGqCy3DlR-7Q0yEhl*SmfnnylnGEFs9NhMd1UO8e6xSW4X0Lq9s9cpQnQm*zuvAL10sPdwyncZMna-a*eQtmUfgQy2DEUWXTfKZyPPbXWqUSDf99CdnWcesHDbzrxbadBAwRQdiCpzGNT*MLXp1c7A__
         * Nick :
         * FaceUrl : http://userImage.newstarthealth.cn/pat/0/959.jpg
         * UpdateTime : 2019-03-01 17:12:36
         * CreateTime : 2019-03-01 17:12:36
         */

        private int Id;
        private int UserType;
        private int UserId;
        private String Identifier;
        private String UserSig;
        private String Nick;
        private String FaceUrl;
        private String UpdateTime;
        private String CreateTime;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getUserType() {
            return UserType;
        }

        public void setUserType(int UserType) {
            this.UserType = UserType;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getIdentifier() {
            return Identifier;
        }

        public void setIdentifier(String Identifier) {
            this.Identifier = Identifier;
        }

        public String getUserSig() {
            return UserSig;
        }

        public void setUserSig(String UserSig) {
            this.UserSig = UserSig;
        }

        public String getNick() {
            return Nick;
        }

        public void setNick(String Nick) {
            this.Nick = Nick;
        }

        public String getFaceUrl() {
            return FaceUrl;
        }

        public void setFaceUrl(String FaceUrl) {
            this.FaceUrl = FaceUrl;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
