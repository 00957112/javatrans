import java.io.*;
import java.net.URL;//url處理
import java.net.URLEncoder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.jsoup.Jsoup; // Jsoup.parse
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;


public class vocabulary {
    public vocabulary(){}
    static String voca(String text) throws UnsupportedEncodingException {
        for(int i=0;i<text.length();i++)
        {
            if(text.charAt(i)>='A'&&text.charAt(i)<='z') {
                continue;
            }
            else return "抓的不是單字或選取到不是字母的符號,請重新選一次。";
        }
        System.out.println(text);
        String ans="";
        try
        {
            String getUrl = "https://tw.dictionary.search.yahoo.com/search?p=" + text; //yahoo
            Document doc =  Jsoup.parse(new URL(getUrl).openStream(), "utf-8", getUrl);
            //轉編碼抓url 避免亂碼
            //System.out.println(doc);//測試用完整輸出   //.title()

            Elements ele=doc.select("div.compList.d-f.fz-16");//有單字那欄
            Elements e1=ele.get(0).getElementsByClass("dictionaryExplanation");
            //從ele中取解釋
            Elements e2=ele.get(0).getElementsByClass("pos_button");//從ele中取詞性

            int id=0;
            try{if(!e2.get(0).hasText())return "not found";}
            catch(IndexOutOfBoundsException ee){return "not found";}
            //錯誤無結果 先利用例外判斷
            try{while(e1.get(id)!=null){ //印出詞性+解釋
                ans+=e2.get(id).text();//詞性
                ans+='\n';
                ans+=e1.get(id).text();//解釋
                ans+='\n';
                id++;
            }}
            catch (IndexOutOfBoundsException eee){//終止例外
                ans+="end";//可換
            }
            System.out.println(ans);
            return ans;
        }

        catch (IOException e)//io例外
        {
            e.printStackTrace();
        }
        return ans;
    }
}
