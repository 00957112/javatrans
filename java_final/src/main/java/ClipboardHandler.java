import java.awt.datatransfer.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.Toolkit;

public class ClipboardHandler implements ClipboardOwner {
    interface EntryListener{
        void  onCopy(String data);
    }
    private Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
    private  EntryListener entryListener;
    public void  setEntryListener(EntryListener entryListener){
        this.entryListener=entryListener;
    }
    @Override
    public void lostOwnership(Clipboard clipboard,Transferable content){
        try{
            Thread.sleep(200);
            System.out.println((String) this.clipboard.getData(DataFlavor.stringFlavor));
        }catch (Exception e){
            e.printStackTrace();
        }
        Transferable cont=this.clipboard.getContents(this);
        processContents(cont);
        regainOwnership(cont);

    }
    public void processContents(Transferable cont){
        try{
            //Transferable co=clipboard.getContents(this);
            String r=(String) cont.getTransferData(DataFlavor.stringFlavor);
            if(entryListener!=null){
                entryListener.onCopy(r);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void regainOwnership(Transferable cont){
        clipboard.setContents(cont,this);
    }

    public void  run(){
        Transferable t=clipboard.getContents(this);
        regainOwnership(t);
        while (true);
    }



}
