package iut.paci.classroomcomunnity.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.bean.Question;

/**
 * Created by dupeyrat on 26/02/18.
 */

public class JsonTools {

    /**
     * Méthode qui permet de convertir les questions et
     * reponses du fichier Json en Objet Question
     * @throws JSONException
     */
    public static List<Question> getQuestion(InputStream ressources) throws JSONException {

        String json = null;
        List<Question> questionList = new ArrayList<>();
        try {

            JSONObject obj = new JSONObject(getJson(ressources));
            JSONArray jsonArray = obj.getJSONArray("questions");

            // On parcours les questions
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String question = jsonObject.getString("question");
                JSONArray questionsAnswers = jsonObject.getJSONArray("answers");

                HashMap<String, Boolean> reponse = new HashMap<>();

                // On parcour les réponses de chaque question
                for (int j = 0; j < questionsAnswers.length(); j++) {
                    JSONObject jsonObjectAnswer = questionsAnswers.getJSONObject(j);
                    String rep = jsonObjectAnswer.getString("answer");
                    Boolean stat = jsonObjectAnswer.getBoolean("status");
                    reponse.put(rep, stat);
                }

                questionList.add(new Question(question, reponse));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return questionList;
    }


    /**
     * Extraction des données du fichier JSON
     * @return
     * @throws IOException
     */
    private static String getJson(InputStream ressources) throws IOException {

        String json = null;

        // On ouvre le Flux vers le fichier
        InputStream is = ressources;
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        // Convertion en String de la lecture
        json = new String(buffer, "UTF-8");

        // On retourne le resultat
        return json;

    }
}
