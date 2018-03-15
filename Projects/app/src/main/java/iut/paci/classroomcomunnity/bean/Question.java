package iut.paci.classroomcomunnity.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin Dupeyrat on 24/02/18.
 *
 * Class qui représente une Question.
 * Elle est composé d'une Question et d'une liste
 * de reponses
 */

public class Question {


    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String question;
    @SerializedName("duration")
    private int duration;
    @SerializedName("answers")
    private List<Reponse> reponse;

    public Question(int id, String q, List<Reponse> r, int d) {

        this.id = id;
        this.question = q;
        this.reponse = r;
        this.duration = d;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Reponse> getReponses() {

        return this.reponse;
    }

    public void setReponse(List<Reponse> reponse) {
        this.reponse = reponse;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

