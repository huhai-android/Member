package com.newdjk.member.ui.entity;

/**
 * Created by user on 2018/4/17.
 */

public class LoginEntity {
//    {"Code":0,"Message":"登录成功","Data":{"Token":"eyJpZCI6NCwiaWF0IjoxNTM1NDI2MDk5LCJleHAiOjE1MzU1MTI0OTksInR5cGUiOjEsImNsaWVudCI6bnVsbCwicmVnaXN0cmF0aW9uSWQiOm51bGx9.MeeJGUytVaMDNTcLBr18AMI6obOHvPfPWrzPonNBhro","User":{"Id":4,"Name":"13265558043","Sex":3,"Mobile":"13265558043"}}}
    private int Code;
    private DataEntity Data;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public DataEntity getData() {
        return Data;
    }

    public void setData(DataEntity data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public class DataEntity {
        private String Token;
        private UserBean User;

        public String getToken() {
            return Token;
        }

        public void setToken(String token) {
            Token = token;
        }

        public UserBean getData() {
            return User;
        }

        public void setData(UserBean user) {
            User = user;
        }

        public class UserBean {
            private int Id;
            private int AccountId;
            private String Name;
            private int Sex;
            private String Mobile;

            public int getAccountId() {
                return AccountId;
            }

            public void setAccountId(int accountId) {
                AccountId = accountId;
            }

            public int getId() {
                return Id;
            }

            public void setId(int id) {
                Id = id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public int getSex() {
                return Sex;
            }

            public void setSex(int sex) {
                Sex = sex;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String mobile) {
                Mobile = mobile;
            }
        }
    }

}
