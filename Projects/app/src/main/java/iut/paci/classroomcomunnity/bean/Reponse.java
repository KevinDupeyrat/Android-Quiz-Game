package iut.paci.classroomcomunnity.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kevin Dupeyrat on 28/02/18.
 *
 * Class qui représente une reponse.
 * Elle est composé de la reponse et
 * d'un tratus (bonne ou mauvaise reponse)
 */

public class Reponse {

    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String reponse;
    @SerializedName("is_right")
    private int bonneReponse;

    public Reponse(int id, String reponse, int bonneReponse) {

        this.id = id;
        this.reponse = reponse;
        this.bonneReponse = bonneReponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int isBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(int bonneReponse) {
        this.bonneReponse = bonneReponse;
    }
}
