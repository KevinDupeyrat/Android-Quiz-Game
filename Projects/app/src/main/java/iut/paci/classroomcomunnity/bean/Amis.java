package iut.paci.classroomcomunnity.bean;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Kevin Dupeyrat on 18/02/18.
 *
 * Class qui represente un amis
 * avec son nom et son avatar, son prénom,
 * son status de connexion et son dernier score
 */

public class Amis {

    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String nom;
    @SerializedName("last_name")
    private String prenom;
    @SerializedName("is_present")
    private int present;
    @SerializedName("photo_path")
    private String ressourseAvatar;
    @SerializedName("last_score")
    private int lastScore;

    public Amis(int id, String nom, String prenom, int present, String avatar, int ls) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.present = present;
        this.ressourseAvatar = avatar;
        this.lastScore = ls;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int isPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getLastScore() {
        return lastScore;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public String getRessourseAvatar() {
        return ressourseAvatar;
    }

    public void setRessourseAvatar(String ressourseAvatar) {
        this.ressourseAvatar = ressourseAvatar;
    }
}
