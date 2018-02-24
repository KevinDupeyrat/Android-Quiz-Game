package iut.paci.classroomcomunnity.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by dupeyrat on 24/02/18.
 */

public class Question {

    private String question;
    private Map<String, Boolean> reponse;

    public Question(String q, Map<String, Boolean> r) {

        this.question = q;
        this.reponse = r;

    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Boolean> getReponse() {
        return reponse;
    }

    public void setReponse(Map<String, Boolean> reponse) {
        this.reponse = reponse;
    }
}

