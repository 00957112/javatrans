import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.SecurityException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Scanner;



public class ClipboardFrame  extends JFrame implements ClipboardHandler.EntryListener {
    private static Formatter output;
    private final JTextArea text;
    private static Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
    private final Scanner input=new Scanner(System.in);
    private  GlobalListener globalListener;
    private boolean open=false;
    private ClipboardHandler handler;
    //build frame & register listener
    public ClipboardFrame(){
        //UI內容設定
        text=new JTextArea("",100,300);
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
        String s="";
        try {
            s=Translator2.translate2(data);//翻譯
        }catch (Exception e){
            e.printStackTrace();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        openFile();
        addRecords(dtf.format(LocalDateTime.now())+'\n'+data+'\n'+s+'\n'+'\n'); // play 2000 games of craps
        closeFile();
        if(!open)return;
        text.setText(s);
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

    public static void openFile()
    {
        try {
            FileWriter fw = new FileWriter("translation", true);
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
