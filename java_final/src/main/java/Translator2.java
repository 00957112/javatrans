import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class Translator2 {
    /**
     *
     * @author Ravishanker Kusuma
     */
        private String key;

        public Translator2(String apiKey) {
            key = apiKey;
        }

        String translte(String text, String from, String to) {
            StringBuilder result = new StringBuilder();
            try {
                String encodedText = URLEncoder.encode(text, "UTF-8");
                String urlStr = "https://www.googleapis.com/language/translate/v2?key=" + key + "&q=" + encodedText + "&target=" + to + "&source=" + from;

                URL url = new URL(urlStr);

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                InputStream stream;
                if (conn.getResponseCode() == 200) //success
                {
                    stream = conn.getInputStream();
                } else
                    stream = conn.getErrorStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JsonParser parser = new JsonParser();

                JsonElement element = parser.parse(result.toString());

                if (element.isJsonObject()) {
                    JsonObject obj = element.getAsJsonObject();
                    if (obj.get("error") == null) {
                        String translatedText = obj.get("data").getAsJsonObject().
                                get("translations").getAsJsonArray().
                                get(0).getAsJsonObject().
                                get("translatedText").getAsString();
                        return translatedText;

                    }
                }

                if (conn.getResponseCode() != 200) {
                    System.err.println(result);
                }

            } catch (IOException | JsonSyntaxException ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }
        public static String translate2(String str){
            Translator2 translator = new Translator2("AIzaSyBumjL9Znh_Rh0u_-ffNpl9wfF3MQOM3yc");
            return translator.translte(str, "en", "zh-TW");
        }
        public static void main(String[] args) {

            Translator2 translator = new Translator2("AIzaSyBumjL9Znh_Rh0u_-ffNpl9wfF3MQOM3yc");
            String text = translator.translte("pear", "en", "zh-TW");
            System.out.println(text);//結果
        }
    }

