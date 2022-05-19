import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;
public class app{
	public static void main(String[] args) throws Exception{
		sysClipBoard thisClass = new sysClipBoard();
        //new JFrame1();
		while(true){
            //讓程式不會結束
		}
	}
}
-------------------------
import java.io.*;

import javax.swing.event.MenuDragMouseListener;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
public class sysClipBoard implements ClipboardOwner {
	private Clipboard sysClipBoard;
	private Transferable clipcontent;
	
	public sysClipBoard(){
		super();
		initialize();
		
	}
	
	private void initialize() {
		sysClipBoard = Toolkit.getDefaultToolkit().getSystemClipboard(); //獲取系統剪貼簿
		clipcontent = sysClipBoard.getContents(null); //取得剪貼簿內容
		sysClipBoard.setContents(clipcontent, this); //設定剪貼簿內容並註冊擁有者		
												    //擁有者可以用lostOwnership方法取得剪貼簿改變消息
	}
	
	public void lostOwnership(Clipboard clipboard, Transferable contents){
		try{
			Thread.sleep(20);   //讓執行緒小睡，等待剪貼簿準備好，這行很重要!!
			clipcontent = clipboard.getContents(null);	//再次獲得剪貼板內容
			clipboard.setContents(clipcontent, this);	//要一直監聽所以要在註冊一次
		}
		catch(Exception e){
			 System.out.println("Exception: " + e);  
		}
		try {
			Robot robot=new Robot();
			robot.setAutoDelay(500); 
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);

			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_C);
			// ...
		} catch (Exception e) {
			System.out.println("Exception: " + e);  
		}
		
		//以下是簡單處理程式就不另外寫成函式了!
		try{
			if(clipcontent.isDataFlavorSupported(DataFlavor.stringFlavor)){
				String clipData= (String) clipcontent.getTransferData(DataFlavor.stringFlavor);
				System.out.println("系統剪貼簿內容:" + clipData);
			}
		}
		catch(Exception e){
			 System.out.println("Exception: " + e);  
		}
	}
}
