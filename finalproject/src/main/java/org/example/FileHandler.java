package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Scanner;
import net.sf.json.*;

public class FileHandler {

    private static Formatter  output;
    private static FileWriter fw;
    private static Scanner input;
    private static String readstr;

    public static void writeFileOpen(String filename,String context,boolean append){//寫入開檔
        try {
            fw = new FileWriter(filename, append);
            output = new Formatter(fw);
        }
        catch(IOException e){
            System.out.println(e);
        }
        addText(context);
        wcloseFile();
    }
    public static String readFileOpen(String filename){//讀
        try {
            input = new Scanner(Paths.get(filename));
            if(input.hasNext())
                readstr=input.nextLine();
        }
        catch(IOException e){
            System.out.println(e);
        }
        //input.next();//格式
        rclosefile();
        return readstr;
    }
    private static void addText(String context){//寫入
        output.format("%s",context);
    }
    private static void wcloseFile(){//關閉寫入
        if (output != null)
            output.close();
    }
    private static void rclosefile(){//關閉讀出
        if (input != null)
            input.close();
    }

    public static String jsonread(String filename){

        String recordText="";
        readstr=readFileOpen(filename);

        JSONObject string_to_json
                =JSONObject.fromObject(readstr);

        JSONObject json_to_data
                = string_to_json.getJSONObject("data");//data層

        JSONArray json_to_strings = json_to_data.getJSONArray("pages");//page Array層


        for (Object object : json_to_strings) {//讀
            JSONObject json_to_string = JSONObject.fromObject(object);
            json_to_string.get("pages");
            recordText+=json_to_string.get("date")+"\n"+json_to_string.get("eg")+"\n"+json_to_string.get("ch")+'\n'+'\n';
        }
        return recordText;

    }

    public static void jsonwrite(String filename,String d,String s){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        readstr=readFileOpen(filename);
        JSONObject string_to_json
                =JSONObject.fromObject(readstr);
        JSONObject json_to_data
                = string_to_json.getJSONObject("data");//data層

        JSONArray json_to_strings = json_to_data.getJSONArray("pages");//page Array層

        JSONObject jsonobj=new JSONObject();//創新
        jsonobj.accumulate("date",dtf.format(LocalDateTime.now()));
        jsonobj.accumulate("eg",d);
        jsonobj.accumulate("ch",s);
        json_to_strings.add(jsonobj);//新增

        writeFileOpen("vocabulary.txt",string_to_json.toString(),false);

    }
}
