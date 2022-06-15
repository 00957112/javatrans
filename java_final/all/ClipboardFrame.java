import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.border.Border;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import net.sf.json.*;
import java.util.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Timer;


public class ClipboardFrame  extends JFrame implements ClipboardHandler.EntryListener,ActionListener {
    public boolean isFirst=false;
    private static Formatter  output;
    private static FileWriter fw;
    private static Scanner input;
    private final JTextArea text;
    JScrollPane scroll;
    private static Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
    private  GlobalListener globalListener;
    private boolean open=false;
    private ClipboardHandler handler;
    public static JButton b1;
    public String s;
    public String d;
    public static String readstr;
    class toDo extends TimerTask {//自動複製
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
    //build frame & register listener
    public ClipboardFrame(){
        //UI內容設定
        setUndecorated(true);
        text=new JTextArea("",100,300);
        text.setLineWrap(true);
        Border border = BorderFactory.createLineBorder(new Color(230,200,0,255),5);
        text.setFont(new Font("標楷體", Font.PLAIN,18));
        text.setLineWrap(true);
        text.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        scroll = new JScrollPane(text);
        scroll.setSize(600,200);

        //text.add(b1);
        this.getContentPane().add(scroll,BorderLayout.CENTER);

        //UI frame設定
        this.setSize(650,200);
        this.setAlwaysOnTop(true);
        //text.setEnabled(false);

        handler=new ClipboardHandler();

    }

    public void copyToClipboard(String val){
        Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection data=new StringSelection(val);
        clipboard.setContents(data,data);
    }
    @Override
    public void onCopy(String data){
        s="";
        d=data;
        try {
            s=Translator2.translate2(data);//翻譯
        }catch (Exception e){
            e.printStackTrace();
        }
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        wfileopen("translation");
        addtext(dtf.format(LocalDateTime.now())+'\n'+d+'\n'+s+'\n'+'\n'); // play 2000 games of craps
        wclosefile();
        if(!open)return;
        text.setText(s);
        double x=MouseInfo.getPointerInfo().getLocation().x;
        double y=MouseInfo.getPointerInfo().getLocation().y;
        if(x+this.getWidth()>screenSize.getWidth())x=screenSize.getWidth()-this.getWidth();
        if(y+this.getHeight()> screenSize.getHeight())y=screenSize.getHeight()-this.getHeight();
        setLocation((int)x,(int)y);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("儲存")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
            readfileopen("translation.txt");
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
        }
    }

    public static void main(String[] args) {//how to use
        try {                                   ///**********must!!!!!
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        ClipboardFrame test=new ClipboardFrame();
        test.start();
        try{
            Thread.sleep(20000);//20秒後關閉
            test.close();
            System.out.println("--end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void start(){//開啟
        open=true;
        handler.setEntryListener(this);
        handler.run();
        //全域滑鼠事件設定
        globalListener=new DoIt();
        globalListener.preAssignment();
        text.addFocusListener(globalListener);
        if(isFirst)setVisible(true);
        else isFirst=true;
    }
    public void close(){//關閉
        handler.mode=true;
        isFirst=false;
        try {
            open=false;
            globalListener.close();
            text.removeFocusListener(globalListener);
            this.setVisible(false);

        }catch (Exception e){}
    }

    public static void wfileopen(String filename){//寫入開檔
        try {
            fw = new FileWriter(filename, true);
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

    class DoIt extends GlobalListener{//自動複製

        @Override
        void unToDo() {
            setVisible(false);
        }
        @Override
        void setToDo() {
            timer=new Timer();
            super.timer.schedule(new ClipboardFrame.toDo(),2000);
        }
    }

    // close file


}
