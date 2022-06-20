package org.example;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.border.Border;
import java.util.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Timer;


public class ClipboardFrame  extends JFrame implements ClipboardHandler.EntryListener, KeyListener {
    public boolean isFirst=false;
    private final JTextArea text;
    JScrollPane scroll;
    private static Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
    private  AfterSeconds afterSeconds;//***
    private boolean open=false;
    private ClipboardHandler handler;
    public static JButton b1;
    public String s;
    public String d;



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

        text.addKeyListener(this);
        text.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        scroll = new JScrollPane(text);
        scroll.setSize(600,200);

        this.getContentPane().add(scroll,BorderLayout.CENTER);

        this.setSize(650,200);
        this.setAlwaysOnTop(true);

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
            s=Translator.translation(data);//翻譯
        }catch (Exception e){
            e.printStackTrace();
        }
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        if(!open)return;
        text.setText(s);
        double x=MouseInfo.getPointerInfo().getLocation().x;
        double y=MouseInfo.getPointerInfo().getLocation().y;
        if(x+this.getWidth()>screenSize.getWidth())x=screenSize.getWidth()-this.getWidth();
        if(y+this.getHeight()> screenSize.getHeight())y=screenSize.getHeight()-this.getHeight();
        setLocation((int)x,(int)y);
        setVisible(true);
    }
    /** 測試用*/
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
        afterSeconds=new DoIt();//***
        afterSeconds.preAssignment();//***
        text.addFocusListener(afterSeconds);//***
        if(isFirst)setVisible(true);
        else isFirst=true;
    }
    public void close(){//關閉
        handler.mode=true;
        isFirst=false;
        try {
            open=false;
            afterSeconds.close();//***
            text.removeFocusListener(afterSeconds);//***
            this.setVisible(false);

        }catch (Exception e){}
    }

    class DoIt extends AfterSeconds{//自動複製

        @Override
        void unToDo() {
            setVisible(false);
        }
        @Override
        void setToDo() {
            timer=new Timer();
            super.timer.schedule(new ClipboardFrame.toDo(),500);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown()&&keyEvent.getKeyCode()==83){
            save();
            System.out.println("ctrl");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
    public void save(){
        FileHandler.jsonwrite("translation.txt",d,s);
    }
}
