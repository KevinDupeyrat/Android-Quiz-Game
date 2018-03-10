package iut.paci.classroomcomunnity.tools;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import iut.paci.classroomcomunnity.activity.MainActivity;

/**
 * Created by dupeyrat on 02/03/18.
 */

public class PropertiesTools {

    /**
     * Méthode de chagement du fichier
     * properties
     * @throws IOException
     */
    private static Map<String, String> getProperties(Context context, String action) throws IOException {

        Map<String, String> map = new HashMap<>();
        Properties appProps = new Properties();
        appProps.load(context.getAssets().open("server.properties"));

        map.put("protocole", appProps.getProperty("protocole"));
        map.put("ip_adress", appProps.getProperty("ip_adress"));
        map.put("my_id", appProps.getProperty("my_id"));


        if(action.equals("friends"))
            map.put("path", appProps.getProperty("get_friend_path"));
        else if(action.equals("question"))
            map.put("path", appProps.getProperty("get_question_path"));
        else if(action.equals("attending"))
            map.put("path", appProps.getProperty("check_attending_path"));
        else if(action.equals("logout"))
            map.put("path", appProps.getProperty("disconnect"));
        else if(action.equals("put_request_friend"))
            map.put("path", appProps.getProperty("put_request_friend"));
        else if(action.equals("get_response_friend"))
            map.put("path", appProps.getProperty("get_response_friend"));
        else if(action.equals("ckeck_request"))
            map.put("path", appProps.getProperty("ckeck_request"));
        else if(action.equals("put_responce_request_friend"))
            map.put("path", appProps.getProperty("put_responce_request_friend"));
        else if(action.equals("put_response"))
            map.put("path", appProps.getProperty("put_response"));
        else if(action.equals("get_score_advers"))
            map.put("path", appProps.getProperty("get_score_advers"));


        return map;
    }


    /**
     * Méthode qui permet de retourner la bonne URL
     * en fonction de l'action que nous voulons effectuer
     *
     * @param action
     * @return
     */
    public static String genURL(Context context, String action) {

        Map<String, String> prop = new HashMap<>();

        try {
            prop = PropertiesTools.getProperties(context, action);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = prop.get("protocole")
                + prop.get("ip_adress")
                + prop.get("path");

        Log.i("URL QUESTION", url);

        return url;

    }
}
