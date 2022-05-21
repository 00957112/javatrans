import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class Translator {
    // TODO: If you have your own Premium account credentials, put them down here:
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
    private static String fromLang="en";
    private static String toLang="zh-TW";

    public void setLang(String f,String t){
        fromLang=f;
        toLang=t;
    }
    public void  setFromLang(String fromLang){
        this.fromLang=fromLang;
    }
    public void setToLang(String toLang){
        this.toLang=toLang;
    }

    /**    * Entry Point    */
    public static void main(String[] args) throws Exception {
        // TODO: Specify your translation requirements here:
        String text = "Let's have some fun!";
        System.out.println(text);
        Translator.translate(text);   }
    /**    * Sends out a WhatsApp message via WhatsMate WA Gateway.
     */
    public static String jsonfy(String text){
        return new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(fromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(toLang)
                .append("\",")
                .append("\"text\":\"")
                .append(text)
                .append("\"")
                .append("}")
                .toString();
    }
    public static String translate(String text) throws Exception {
        // TODO: Should have used a 3rd party library to make a JSON string from an object
        String jsonPayload =jsonfy(text);
        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();
        int statusCode = conn.getResponseCode();
        System.out.println("Status Code: " + statusCode);
        BufferedReader br = new BufferedReader(new InputStreamReader(         (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()       ));
        String output,result="";
        while ((output = br.readLine()) != null) {
            //System.out.println(output);
            result+=output;
        }
        conn.disconnect();
        if (!result.isEmpty())return result;
        else return "ohhh...";
           }
}
