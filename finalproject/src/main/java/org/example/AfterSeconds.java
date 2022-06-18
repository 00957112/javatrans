package org.example;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

/** 單字listener*/
abstract public class AfterSeconds implements NativeMouseInputListener,FocusListener {//單字模式小框框全域滑鼠事件
    protected Timer timer;
    private boolean isSelect=false; //#小框框出現條件優化
    abstract void unToDo();
    abstract void setToDo();
    private AfterSeconds afterSeconds;
    public AfterSeconds(){}
    //#小框框消失事件修正-

    @Override
    public void focusGained(FocusEvent e){}
    @Override
    public void focusLost(FocusEvent e){unToDo();}
    public void nativeMouseClicked(NativeMouseEvent e) {}

    public void nativeMousePressed(NativeMouseEvent e) {}

    public void nativeMouseReleased(NativeMouseEvent e) {
        //#小框框出現條件優化
        if(isSelect){
            setToDo();
            isSelect=false;
        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {}
    //#小框框消失事件修正-選下一個消失 #小框框出現條件優化
    public void nativeMouseDragged(NativeMouseEvent e) {
        isSelect=true;
    }
    public void preAssignment(){
        // Construct the example object.
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        afterSeconds=this;
    }
    public void close(){
        try{
            GlobalScreen.removeNativeMouseListener(afterSeconds);
            GlobalScreen.removeNativeMouseMotionListener(afterSeconds);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static <GlobalMouseListenerExample> void main(String[] args) {}
}
