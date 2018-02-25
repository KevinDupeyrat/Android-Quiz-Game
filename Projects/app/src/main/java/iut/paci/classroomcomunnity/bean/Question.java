package iut.paci.classroomcomunnity.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dupeyrat on 24/02/18.
 */

public class Question {

    private String question;
    private HashMap<String, Boolean> reponse;

    public Question(String q, HashMap<String, Boolean> r) {

        this.question = q;
        this.reponse = r;

    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Boolean> getReponses() {
        return reponse;
    }

    public List<String> getReponseText() {

        List<String> list = new ArrayList<>();

        for (HashMap.Entry<String, Boolean> entry : reponse.entrySet()) {
            list.add(entry.getKey());
        }

        return list;
    }

    public void setReponse(HashMap<String, Boolean> reponse) {
        this.reponse = reponse;
    }
}

