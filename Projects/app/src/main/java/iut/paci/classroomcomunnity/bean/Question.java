package iut.paci.classroomcomunnity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dupeyrat on 24/02/18.
 *
 * Class qui représente une Question.
 * Elle est composé d'une Question et d'une liste
 * de reponses
 */

public class Question {

    // TODO: @SerializedName("") avec le bon nom

    @SerializedName("question")
    private String question;
    @SerializedName("answers")
    private HashMap<Reponse, Boolean> reponse;

    public Question(String q, HashMap<Reponse, Boolean> r) {

        this.question = q;
        this.reponse = r;

    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<Reponse, Boolean> getReponses() {
        return reponse;
    }

    public List<String> getReponseText() {

        List<String> list = new ArrayList<>();

        for (HashMap.Entry<Reponse, Boolean> entry : reponse.entrySet()) {
            list.add(entry.getKey().getReponse());
        }

        return list;
    }

    public void setReponse(HashMap<Reponse, Boolean> reponse) {
        this.reponse = reponse;
    }
}

