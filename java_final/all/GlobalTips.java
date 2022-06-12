import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Timer;
import java.util.TimerTask;

public class GlobalTips extends JFrame implements ClipboardHandler.EntryListener{//單字模式小框框
    private JTextArea aaa;//小框框內容
    private static Formatter output;
    private DoIt doIt;
    JScrollPane scroll;

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
        String s="";
        if(handler.transletable()&&!data.equals("")){
            try {
                s=vocabulary.voca(data);//翻譯
            }catch (Exception e){               e.printStackTrace();
            }
            System.out.println("--Translated");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            openFile();
            if(s!="抓的不是單字或選取到不是字母的符號,請重新選一次。")
                addRecords(dtf.format(LocalDateTime.now())+'\n'+data+'\n'+s+'\n'+'\n'); // play 2000 games of craps
            else addRecords(dtf.format(LocalDateTime.now())+'\n'+s+'\n'+'\n');
            closeFile();
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
        aaa.setFont(new Font("標楷體", Font.PLAIN, 16));
        aaa.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        scroll = new JScrollPane(aaa);
        this.getContentPane().add(scroll);
        add(scroll);



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
            super.timer.schedule(new toDo(),2000);
        }
    }
    public static void openFile()
    {
        try {
            FileWriter fw = new FileWriter("vocabulary", true);
            output = new Formatter(fw);
        } catch (SecurityException securityException) {
            System.err.println("Write permission denied. Terminating.");
            System.exit(1); // terminate the program
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error opening file. Terminating.");
            System.exit(1); // terminate the program
        } catch (IOException e) {
            System.err.println("I/O error. Terminating.");
            System.exit(1); // terminate the program
        }
    }

    // add records to file
    public static void addRecords(String s)
    {
        try {
            // output new record to file; assumes valid input
            // TODO
            output.format("%s",s);
        } catch (FormatterClosedException formatterClosedException) {
            System.err.println("Error writing to file. Terminating.");
        }
    }

    // close file
    public static void closeFile()
    {
        if (output != null)
            output.close();
    }
}
