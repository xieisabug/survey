package com.survey.mvc.dto;

/**
 * 问卷结果
 *
 * @author aisino_lzw
 */
public class AnswersDto {

    private Integer aid;
    private Integer qid;
    private Integer cid;
    private String answer;
    private String feedback;

    private String title;


    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        int intAnswer = Integer.valueOf(answer);
        switch (intAnswer) {
            case 1:
                this.answer = "非常满意";
                break;
            case 2:
                this.answer = "满意";
                break;
            case 3:
                this.answer = "基本满意";
                break;
            case 4:
                this.answer = "不满意";
                break;
            case 5:
                this.answer = "不了解";
                break;
        }
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AnswersDto{" +
                "aid=" + aid +
                ", qid=" + qid +
                ", cid=" + cid +
                ", answer='" + answer + '\'' +
                ", feedback='" + feedback + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
