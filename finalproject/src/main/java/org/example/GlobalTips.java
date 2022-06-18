package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Timer;

public class GlobalTips extends JFrame implements ClipboardHandler.EntryListener, KeyListener {//單字模式小框框
    private JTextArea aaa;//小框框內容
    private DoIt doIt;
    JScrollPane scroll;
    public String s;
    public String d;
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
            //System.out.println("--Translated");
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

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

        pack();



    }
    public static void main(String[] args) {//how to use
        GlobalTips ma = new GlobalTips();
        ma.start();
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
            super.timer.schedule(new toDo(),500);
        }
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
    public void keyReleased(KeyEvent keyEvent) {}
    public void save(){
        FileHandler.jsonwrite("vocabulary.txt",d,s);
    }
}
