import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.awt.event.*;
import  java.util.Timer;
import  java.util.TimerTask;
import java.awt.Robot;
public abstract class GlobalListener extends MouseAdapter implements NativeMouseInputListener, FocusListener {
    protected static Timer timer;
    private TimerTask showtime;
    private static int sec=0;
    private static GlobalListener example;

    abstract void unToDo();
    abstract void setToDo();

    public boolean ctrlC(){
        try {
            Robot robot = new Robot();
            robot.keyPress(17);
            robot.keyPress(67);
            robot.keyRelease(17);
            robot.keyRelease(67);
            System.out.println("yyy");
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private GlobalListener globalListener;
    @Override
    public void mouseClicked(MouseEvent e){
        System.out.println("+");
    }
    @Override
    public void focusGained(FocusEvent e){}
    @Override
    public void focusLost(FocusEvent e){
        unToDo();
        try{
            timer.cancel();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
        //unToDo();
        System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        setToDo();
        //if(showtime!=null)timer.schedule(,3000);
        //System.out.println("Mouse Pressed: " + e.getButton());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        try{
            timer.cancel();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //System.out.println("Mouse Released: " + e.getButton());
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        //System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }
    public void nativeMouseDragged(NativeMouseEvent e) {
        //unToDo();
        try{
            timer.cancel();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
    public void preAssignment(){
        // Construct the example object.
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        globalListener=this;
        //setToDo();
    }
    public void close(){
        try{

            GlobalScreen.removeNativeMouseListener(globalListener);
            GlobalScreen.removeNativeMouseMotionListener(globalListener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static <GlobalMouseListenerExample> void main(String[] args) {

    }
}
