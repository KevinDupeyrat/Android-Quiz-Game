package iut.paci.classroomcomunnity.bean;

/**
 * Created by dupeyrat on 18/02/18.
 *
 * Class qui represente un amis
 * avec son nom et son avatar
 */

public class Amis {

    private String nom;
    private int avatarRessource;
    private int colorRessource;

    public Amis(String nom, int avatarRessource, int colorRessource) {
        this.nom = nom;
        this.avatarRessource = avatarRessource;
        this.colorRessource = colorRessource;
    }

    public int getColorRessource() {
        return colorRessource;
    }

    public void setColorRessource(int colorRessource) {
        this.colorRessource = colorRessource;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAvatarRessource() {
        return avatarRessource;
    }

    public void setAvatarRessource(int avatarRessource) {
        this.avatarRessource = avatarRessource;
    }
}
