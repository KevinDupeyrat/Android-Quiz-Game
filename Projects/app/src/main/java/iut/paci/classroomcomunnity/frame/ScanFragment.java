package iut.paci.classroomcomunnity.frame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import iut.paci.classroomcomunnity.R;
import iut.paci.classroomcomunnity.activity.MainActivity;
import iut.paci.classroomcomunnity.tools.PropertiesTools;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Fragment qui gère le scan du code QR
 */
public class ScanFragment extends Fragment implements ZBarScannerView.ResultHandler{

    private ZBarScannerView scannerView;

    public ScanFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Récupération de la vue
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        // Création du Scanner
        scannerView = new ZBarScannerView(getActivity());
        // Liste des format pris en compte par le scanner
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QRCODE);
        // Ajout de cette liste au scanner
        scannerView.setFormats(formats);

        // Récupération du Layout
        FrameLayout contentFrame = (FrameLayout) view.findViewById(R.id.photoFragment);
        // On y ajoute le Scanner
        contentFrame.addView(scannerView);

        return view;


    }

    /**
     * Méthode appelé par Android
     * quand l'activité est rapelé après un onPause()
     */
    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    /**
     * Méthode appelé par Android
     * quand l'activité est mis en pause
     */
    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    /**
     * Méthode qui récupère le resultat du scan
     * effectué par le scanner.
     * Il s'agit d'une méthode de l'interface ZBarScannerView.ResultHandler
     * qui est appelé à la fin du scan.
     *
     * @param result
     */
    @Override
    public void handleResult(Result result) {


        Map<String, String> prop = new HashMap<>();

        // On récupère le resultat sous forme de String
        String code = result.getContents();
        // On l'affecte à l'activité principale
        MainActivity.setServerCode(code);
        prop.put("code_server", code);
        Log.i("Attente", "QRcode : " + code);

        try {
            prop = PropertiesTools.getProperties(getContext(), "attending");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = prop.get("protocole")
                + prop.get("ip_adress")
                + prop.get("path")
                +"?key=" + code +"&id="+ MainActivity.getMy_id();

        Log.i("URL", url);




        // On envoie la demande de connexion
        // au serveur
        Ion.with(getContext())
                .load( url )
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {  }
                });

        MainActivity.getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        // On redirige en suite vers la liste d'amis
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFrame, new FriendFragment()).commit();

    }

}
