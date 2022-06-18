package org.example;
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

    public boolean mode=true;
    private  EntryListener entryListener;

    private boolean toTranslet=false;
    public boolean transletable(){
        return toTranslet;
    }
    public void  setEntryListener(EntryListener entryListener){
        this.entryListener=entryListener;
    }
    @Override  public void lostOwnership(Clipboard clipboard,Transferable content){
        if(mode){
            System.out.print("===");
            return;
        }
        try{
            Thread.sleep(200);//時間
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
        mode=false;
        Transferable t=clipboard.getContents(this);
        regainOwnership(t);
    }

    public void clearClipboard(){
        StringSelection clear=new StringSelection("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clear,this);
    }


}
