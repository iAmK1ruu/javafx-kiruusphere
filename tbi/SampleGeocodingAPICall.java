import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
public class SampleGeocodingAPICall {
    public static String[] getJSONResponse(String fetched_search) {
        try {
            String provider = "https://geocoding-api.open-meteo.com/v1/search?name=" + fetched_search + "&count=10&language=en&format=json";
            URL url = new URL(provider);
            HttpsURLConnection obj = (HttpsURLConnection)url.openConnection();
            obj.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(obj.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return returnStringArrays(response.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error calling the Geocoding API.");
        }
        String[] nores = {"NO_RESPONSE"};
        return nores;
    }
    public static String[] returnStringArrays(String JSONResponse) {
        ArrayList<String> responses = new ArrayList<>();
        JSONResponse = JSONResponse.substring(13, JSONResponse.length() - 1);
        String[] jsonObjects = JSONResponse.split("\\},\\{");
        return jsonObjects;
    }
    
    public static String getPlace(String input) {
        String[] words = input.split(" ");
        String fetchedInput = "";
        for (int i = 0; i < words.length; i++) {
            fetchedInput += words[i];
            if (i < words.length - 1)
                fetchedInput += "+";
        }
        return fetchedInput;
    }
    
    public static void main(String[] args) {
        String[] retrieved = getJSONResponse(getPlace("Baguio City"));
        for (int i = 0; i < retrieved.length; i++) {
            System.out.println(retrieved[i]);
        }
    }
        
}
