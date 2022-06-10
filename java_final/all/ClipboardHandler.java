import java.awt.datatransfer.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.Toolkit;

public class ClipboardHandler implements ClipboardOwner {
    interface EntryListener{
        void  onCopy(String data);
    }
    private Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
    private Transferable transferable;
    private  EntryListener entryListener;

    private boolean toTranslet=false;
    public boolean transletable(){
        return toTranslet;
    }
    public void  setEntryListener(EntryListener entryListener){
        this.entryListener=entryListener;
    }
    @Override  public void lostOwnership(Clipboard clipboard,Transferable content){
        try{
            Thread.sleep(200);
            System.out.println((String) this.clipboard.getData(DataFlavor.stringFlavor));
            toTranslet=true;
        }catch (Exception e){
            e.printStackTrace();
            toTranslet=false;
        }
        Transferable cont=this.clipboard.getContents(this);
        if(processContents(cont))toTranslet=true;
        regainOwnership(cont);

    }

    public boolean processContents(Transferable cont){
        try{
            //Transferable co=clipboard.getContents(this);
            String r=(String) cont.getTransferData(DataFlavor.stringFlavor);
            if(entryListener!=null){
                entryListener.onCopy(r);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void regainOwnership(Transferable cont){
        clipboard.setContents(cont,this);
    }

    public void  run(){
        Transferable t=clipboard.getContents(this);
        regainOwnership(t);
        //while (true);
    }

    public void clearClipboard(){
        StringSelection clear=new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clear,this);
    }


}
