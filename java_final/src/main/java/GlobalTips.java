import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GlobalTips extends JFrame implements ClipboardHandler.EntryListener{//單字模式小框框
    private JTextArea aaa;//小框框內容

    private DoIt doIt=new DoIt();
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
        if(handler.transletable()){
            try {
                data=Translator2.translate2(data);//翻譯
            }catch (Exception e){
                e.printStackTrace();
            }
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
        aaa=new JTextArea("---",10,20);
        Border border = BorderFactory.createLineBorder(new Color(230,200,0,255),5);
        aaa.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        add(aaa);
        pack();

        //全域滑鼠事件設定
        doIt.preAssignment(doIt);
        handler.setEntryListener(this);

        handler.run();

    }
    public static void main(String[] args) {
        GlobalTips ma = new GlobalTips();
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
        super.timer.schedule(new toDo(),3000);
    }
}

}

