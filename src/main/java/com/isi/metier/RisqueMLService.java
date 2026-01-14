package com.isi.metier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class RisqueMLService {

    public double predict(double revenu, double remboursement, int duree, double taux) {

        try {
            URL url = new URL("http://localhost:5000/predict");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // Création du JSON
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("revenu", revenu);
            jsonInput.put("remboursement", remboursement);
            jsonInput.put("duree", duree);
            jsonInput.put("taux", taux);

            // Envoi du JSON
            try (OutputStream os = con.getOutputStream()) {
                os.write(jsonInput.toString().getBytes("UTF-8"));
            }

            // Lecture de la réponse
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            // Parse JSON réponse
            JSONObject jsonResponse = new JSONObject(sb.toString());
            double risque = jsonResponse.getDouble("risque");

            return risque; // renvoie directement la probabilité (ex: 0.76 = 76%)

        } catch (Exception e) {
            e.printStackTrace();
            return 1.0; // risque maximal si erreur
        }
    }
}
