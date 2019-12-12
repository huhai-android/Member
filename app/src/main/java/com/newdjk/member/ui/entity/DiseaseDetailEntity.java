package com.newdjk.member.ui.entity;

import java.util.List;

/**
 * Created by EDZ on 2018/11/6.
 */

public class DiseaseDetailEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"DiseaseId":670,"DiseaseName":"糖尿病肾病","DepartmentId":40,"Symptom":"腹水  食欲减退  水肿  高血压  贫血  肾小球硬化  糖尿  胸水  血尿 蛋白尿","Summary":"糖尿病肾病是指由于糖尿病糖代谢异常为主因所致的肾小球硬化，并伴尿蛋白含量超过正常，称为糖尿病肾病。糖尿病肾病是由不同病因与发病机制引起体内胰岛素绝对与相对不足以致糖蛋白质和脂肪代谢障碍，而以慢性高血糖为主要临床表现的全身性疾病。","SymptomItems":["腹水","食欲减退","水肿","高血压","贫血","肾小球硬化","糖尿","胸水","血尿","蛋白尿"]}
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
         * DiseaseId : 670
         * DiseaseName : 糖尿病肾病
         * DepartmentId : 40
         * Symptom : 腹水  食欲减退  水肿  高血压  贫血  肾小球硬化  糖尿  胸水  血尿 蛋白尿
         * Summary : 糖尿病肾病是指由于糖尿病糖代谢异常为主因所致的肾小球硬化，并伴尿蛋白含量超过正常，称为糖尿病肾病。糖尿病肾病是由不同病因与发病机制引起体内胰岛素绝对与相对不足以致糖蛋白质和脂肪代谢障碍，而以慢性高血糖为主要临床表现的全身性疾病。
         * SymptomItems : ["腹水","食欲减退","水肿","高血压","贫血","肾小球硬化","糖尿","胸水","血尿","蛋白尿"]
         */

        private int DiseaseId;
        private String DiseaseName;
        private int DepartmentId;
        private String Symptom;
        private String Summary;
        private List<String> SymptomItems;

        public int getDiseaseId() {
            return DiseaseId;
        }

        public void setDiseaseId(int DiseaseId) {
            this.DiseaseId = DiseaseId;
        }

        public String getDiseaseName() {
            return DiseaseName;
        }

        public void setDiseaseName(String DiseaseName) {
            this.DiseaseName = DiseaseName;
        }

        public int getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(int DepartmentId) {
            this.DepartmentId = DepartmentId;
        }

        public String getSymptom() {
            return Symptom;
        }

        public void setSymptom(String Symptom) {
            this.Symptom = Symptom;
        }

        public String getSummary() {
            return Summary;
        }

        public void setSummary(String Summary) {
            this.Summary = Summary;
        }

        public List<String> getSymptomItems() {
            return SymptomItems;
        }

        public void setSymptomItems(List<String> SymptomItems) {
            this.SymptomItems = SymptomItems;
        }
    }
}
