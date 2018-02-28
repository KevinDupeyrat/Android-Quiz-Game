package iut.paci.classroomcomunnity.tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import iut.paci.classroomcomunnity.bean.Amis;
import iut.paci.classroomcomunnity.bean.Question;

/**
 * Created by dupeyrat on 26/02/18.
 *
 * Class Outil pour la gestion des fichier Json
 * Elle permetra de générer à partir de fichier Json
 * des Objets
 */

public class JsonTools {


    /**
     * Méthode qui permet de récupérer les question/reponses
     * d'un fichier JSON vers un objet Question
     * Utilisation de l'API GSON de Google
     * @param json
     * @return
     */
    public static Question getQuestion(String json) {

        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);

        return question;
    }

    /**
     * Méthode qui permet de convertir un fichier Json
     * passé en paramètre en une liste
     * de Question qui est renvoyé
     */
    public static List<Amis> getAmis(String json) {

        Gson gson = new Gson();
        Type type = new TypeToken<List<Amis>>(){}.getType();
        List<Amis> amisList = gson.fromJson(json, type);


        return amisList;
    }


    /**
     * Méthode qui permet de convertir un fichier Json
     * passé en paramètre en un Objet Amis qui est renvoyer
     * @param json
     * @return
     */
    private static Amis getUnAmis(String json) {

        Gson gson = new Gson();
        Amis amis = gson.fromJson(json, Amis.class);


        return amis;
    }
}
