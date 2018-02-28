package iut.paci.classroomcomunnity.bean;

/**
 * Created by dupeyrat on 28/02/18.
 *
 * Class qui représente une reponse.
 * Elle est composé de la reponse et
 * d'un tratus (bonne ou mauvaise reponse)
 */

public class Reponse {

    // TODO: @SerializedName("") avec le bon nom
    private String reponse;
    // TODO: @SerializedName("") avec le bon nom
    private int bonneReponse;

    public Reponse(String reponse, int bonneReponse) {
        this.reponse = reponse;
        this.bonneReponse = bonneReponse;
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
