import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Scanner;

public class ClipboardFrame  extends JFrame implements ClipboardHandler.EntryListener {
    private final JTextArea text;
    private static Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
    private final Scanner input=new Scanner(System.in);
    private  GlobalListener globalListener;
    private boolean open=false;
    private ClipboardHandler handler;
    //build frame & register listener
    public ClipboardFrame(){
        //UI內容設定
        text=new JTextArea("aaaa",100,300);
        text.setFont(new Font("Consolos", Font.PLAIN, 20));
        text.setLineWrap(true);
        add(text);

        //UI frame設定
        this.setSize(800,200);
        this.setVisible(true);
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
        try {
            data=Translator2.translate2(data);//翻譯
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!open)return;
        text.setText(data);
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
        globalListener=new GlobalListener();
        globalListener.preAssignment();
        setVisible(true);
    }
    public void close(){//關閉
        handler.mode=true;
        try {
        open=false;
        globalListener.close();
        this.setVisible(false);

        }catch (Exception e){}
    }


}
