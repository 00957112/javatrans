package org.example;
import java.io.*;
import java.net.URL;//url處理
import org.jsoup.Jsoup; // Jsoup.parse
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class vocabulary {
    public vocabulary(){}
    public static String voca(String text){
        for(int i=0;i<text.length();i++)
        {
            if(text.charAt(i)>='A'&&text.charAt(i)<='z') {
                continue;
            }
            else return "抓的不是單字或選取到不是字母的符號,請重新選一次。";
        }
        //System.out.println(text);
        String vocabularytxet="";
        try
        {
            String getUrl = "https://tw.dictionary.search.yahoo.com/search?p=" + text; //yahoo
            Document doc =  Jsoup.parse(new URL(getUrl).openStream(), "utf-8", getUrl); //轉編碼抓url 避免亂碼

            Elements ele=doc.select("div.compList.d-f.fz-16");//有單字那欄
            Elements explain=ele.get(0).getElementsByClass("dictionaryExplanation");//從ele中取解釋
            Elements part=ele.get(0).getElementsByClass("pos_button");//從ele中取詞性

            int id=0;
            try{if(!part.get(0).hasText())return "not found";}
            catch(IndexOutOfBoundsException ee){return "not found";}//錯誤無結果 先利用例外判斷

            try{while(explain.get(id)!=null){ //印出詞性+解釋
                vocabularytxet+=part.get(id).text();//詞性
                vocabularytxet+='\n';
                vocabularytxet+=explain.get(id).text();//解釋
                vocabularytxet+='\n';
                id++;
            }}
            catch (IndexOutOfBoundsException eee){//終止例外
                System.out.println(eee);
            }
            return vocabularytxet;
        }
        catch (IOException e)//io例外
        {
            e.printStackTrace();
        }
        return vocabularytxet;
    }
}
