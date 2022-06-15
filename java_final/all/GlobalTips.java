import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Timer;
import java.util.Formatter;
import java.util.Scanner;

public class GlobalTips extends JFrame implements ClipboardHandler.EntryListener, KeyListener {//單字模式小框框
    private JTextArea aaa;//小框框內容
    private static Formatter  output;
    private static FileWriter fw;
    private static Scanner input;
    private DoIt doIt;
    JScrollPane scroll;
    public String s;
    public String d;
    public static String readstr;
    private boolean open=false;

    class toDo extends TimerTask{//自動複製
        @Override
        public void run() {
            try {
                Robot robot = new Robot();
                robot.keyPress(17);
                robot.keyPress(67);
                robot.keyRelease(17);
                robot.keyRelease(67);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    private ClipboardHandler handler=new ClipboardHandler();

    @Override
    public void onCopy(String data){
        s="";
        d=data;
        if(handler.transletable()&&!data.equals("")){
            try {
                s=vocabulary.voca(data);//翻譯
            }catch (Exception e){               e.printStackTrace();
            }
            System.out.println("--Translated");
            //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            //openFile();
            //if(s!="抓的不是單字或選取到不是字母的符號,請重新選一次。")
            // addRecords(dtf.format(LocalDateTime.now())+'\n'+data+'\n'+s+'\n'+'\n'); // play 2000 games of craps
            //else addRecords(dtf.format(LocalDateTime.now())+'\n'+s+'\n'+'\n');
            //closeFile();
            if(!open)return;
            aaa.setText(s);
            double x=MouseInfo.getPointerInfo().getLocation().x;
            double y=MouseInfo.getPointerInfo().getLocation().y;
            if(x+this.getWidth()>screenSize.getWidth())x=screenSize.getWidth()-this.getWidth();
            if(y+this.getHeight()> screenSize.getHeight())y=screenSize.getHeight()-this.getHeight();
            if(aaa.getText().equals(""))setVisible(false);
            else  {
                setLocation((int)x,(int)y);
                setVisible(true);}
        }
    }
    public GlobalTips(){
        //UI frame設定
        setUndecorated(true);
        setSize(100,100);

        setAlwaysOnTop(true);
        setLocation(650,350);

        //UI內容設定
        aaa=new JTextArea("---",7,15);
        aaa.setLineWrap(true);
        Border border = BorderFactory.createLineBorder(new Color(230,200,0,255),5);
        aaa.setFont(new Font("標楷體", Font.PLAIN, 18));
        aaa.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        aaa.addKeyListener(this);
        scroll = new JScrollPane(aaa);
        this.getContentPane().add(scroll);
        add(scroll);
        //aaa.setEnabled(false);

        pack();



    }
    public static void main(String[] args) {//how to use
        //long s=System.currentTimeMillis();    #debug用計時器
        //Timer timer=new Timer();
        //timer.schedule(new TimerTask() {
        //    @Override
        //    public void run() {
        //        System.out.println(System.currentTimeMillis()-s);
        //    }
        //},0,1000);


        GlobalTips ma = new GlobalTips();
        ma.start();
        /*try{
            Thread.sleep(30000);//20秒後關閉
            ma.close();
            System.out.println("--end");
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    public void start(){//開啟
        open=true;
        //全域滑鼠事件設定
        doIt=new DoIt();
        doIt.preAssignment();
        handler.setEntryListener(this);
        //#小框框消失事件修正
        aaa.addFocusListener(doIt);
        handler.run();
    }
    public void close(){//關閉
        handler.mode=true;
        try{
            open=false;
            doIt.close();
            doIt.unToDo();
            doIt.timer.cancel();
            //#小框框消失事件修正
            aaa.removeFocusListener(doIt);
        }catch (Exception e){e.printStackTrace();}
        //System.out.println("--close"+this.isVisible());
    }
    class DoIt extends AfterSeconds{//自動複製

        @Override
        void unToDo() {
            setVisible(false);
            aaa.setText("");
        }
        @Override
        void setToDo() {
            timer=new Timer();
            super.timer.schedule(new toDo(),1500);
        }
    }
    /*
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("儲存")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
            readfileopen("vocabulary.txt");
            JSONObject string_to_json
                    =JSONObject.fromObject(readstr);
            JSONObject json_to_data
                    = string_to_json.getJSONObject("data");//data層
            //System.out.println(json_to_data);
            JSONArray json_to_strings = json_to_data.getJSONArray("pages");//page Array層
            JSONObject jsonobj=new JSONObject();//創新
            jsonobj.accumulate("date",dtf.format(LocalDateTime.now()));
            jsonobj.accumulate("eg",d);
            jsonobj.accumulate("ch",s);
            json_to_strings.add(jsonobj);//新增
            wfileopen("translation.txt");
            addtext(string_to_json.toString()); // play 2000 games of craps
            wclosefile();
            /*if(s!="抓的不是單字或選取到不是字母的符號,請重新選一次。")
                addtext(dtf.format(LocalDateTime.now())+'\n'+d+'\n'+s+'\n'+'\n'); // play 2000 games of craps
            else addtext(dtf.format(LocalDateTime.now())+'\n'+s+'\n'+'\n');*/
            /*wclosefile();
        }
    }*/
    public static void wfileopen(String filename){//寫入開檔
        try {
            fw = new FileWriter(filename, false);
            output = new Formatter(fw);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public static void readfileopen(String filename){//讀出
        try {
            input = new Scanner(Paths.get(filename));
            readstr=input.nextLine();
        }
        catch(IOException e){
            System.out.println(e);
        }
        //input.next();//格式
        rclosefile();
    }
    public static void addtext(String context){//寫入
        //System.out.println("add\n"+context);
        output.format("%s",context);
    }

    public static void wclosefile(){//關閉寫入
        if (output != null)
            output.close();
    }

    public static void rclosefile(){//關閉讀出
        if (input != null)
            input.close();
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown()&&keyEvent.getKeyCode()==83){
            save();
            System.out.println("ctrl");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    public void save(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        readfileopen("vocabulary.txt");
        JSONObject string_to_json
                =JSONObject.fromObject(readstr);
        JSONObject json_to_data
                = string_to_json.getJSONObject("data");//data層
        //System.out.println(json_to_data);
        JSONArray json_to_strings = json_to_data.getJSONArray("pages");//page Array層
        JSONObject jsonobj=new JSONObject();//創新
        jsonobj.accumulate("date",dtf.format(LocalDateTime.now()));
        jsonobj.accumulate("eg",d);
        jsonobj.accumulate("ch",s);
        json_to_strings.add(jsonobj);//新增
        wfileopen("vocabulary.txt");
        addtext(string_to_json.toString()); // play 2000 games of craps
        wclosefile();
    }
}
