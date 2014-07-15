package com.survey.mvc.dto;

public class QuestionDto {

    private int qid;
    private String content;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "qid=" + qid +
                ", content='" + content + '\'' +
                '}';
    }
}
