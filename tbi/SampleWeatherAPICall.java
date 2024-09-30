import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
// Use Geocoding API to decode location to coordinates, and pass those coordinates to the Weather API
public class SampleWeatherAPICall {
    public static String getWeatherResponse() {
        String latitude = null, longitude = null; // REPLACE WITH ACTUAL COORDINATES
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&hourly=temperature_2m";
        try {
            URL obj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection)obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error retrieving the weather data");
            return "NO RESPONSE";
        }
    }

    public static void main(String[] args) {
        System.out.println(getWeatherResponse());
    }
}
