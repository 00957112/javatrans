import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GlobalTips extends JFrame implements ClipboardHandler.EntryListener{//單字模式小框框
    private JTextArea aaa;//小框框內容

    private DoIt doIt;

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
        if(handler.transletable()&&!data.equals("")){
            try {
                data=Translator2.translate2(data);//翻譯
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("--Translated");
            if(!open)return;
            aaa.setText(data);
            if(aaa.getText().equals(""))setVisible(false);
            else  {
                setLocation(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
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
        aaa.setFont(new Font("Consolas", Font.PLAIN, 20));
        aaa.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        add(aaa);
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
        try{
            Thread.sleep(30000);//20秒後關閉
            ma.close();
            System.out.println("--end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){//開啟
        open=true;
        //全域滑鼠事件設定
        doIt=new DoIt();
        doIt.preAssignment();
        handler.setEntryListener(this);
        handler.run();
    }
    public void close(){//關閉
        handler.mode=true;
        try{
        open=false;
        doIt.close();
        doIt.unToDo();
        doIt.timer.cancel();

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
            super.timer.schedule(new toDo(),2000);
        }
    }

}