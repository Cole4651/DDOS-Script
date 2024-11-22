import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Curl {

    public static void main(String[] args) {
        String urlString = "http://localhost:8080/users"; 

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 30000) { 
                    timer.cancel();
                    return;
                }
                count++;
                try {
                    curlURL(urlString);
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0, 1000); 
    }

    public static void curlURL(String urlString) throws Exception {
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("Curl to " + urlString + " - Response code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        System.out.println("Response Content : ");
        System.out.println(content.toString());
    }
}
