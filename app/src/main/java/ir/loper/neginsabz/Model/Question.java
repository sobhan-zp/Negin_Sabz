package ir.loper.neginsabz.Model;

/**
 * Created by Maziar on 3/24/2018.
 */

public class Question {

    private String id;
    private String question_txt;
    private String question_a;
    private String question_b;
    private String question_c;
    private String ansver;


    public Question() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_txt() {
        return question_txt;
    }

    public void setQuestion_txt(String question_txt) {
        this.question_txt = question_txt;
    }

    public String getQuestion_a() {
        return question_a;
    }

    public void setQuestion_a(String question_a) {
        this.question_a = question_a;
    }

    public String getQuestion_b() {
        return question_b;
    }

    public void setQuestion_b(String question_b) {
        this.question_b = question_b;
    }

    public String getQuestion_c() {
        return question_c;
    }

    public void setQuestion_c(String question_c) {
        this.question_c = question_c;
    }

    public String getAnsver() {
        return ansver;
    }

    public void setAnsver(String ansver) {
        this.ansver = ansver;
    }
}
