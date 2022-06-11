import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.util.Timer;
import java.util.TimerTask;

abstract public class AfterSeconds implements NativeMouseInputListener {//單字模式小框框全域滑鼠事件
    protected Timer timer;

    abstract void unToDo();
    abstract void setToDo();

    private AfterSeconds afterSeconds;

    public AfterSeconds(){

    }
    public void nativeMouseClicked(NativeMouseEvent e) {
        unToDo();

        //System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        //setToDo();
        //if(showtime!=null)timer.schedule(,3000);
        //System.out.println("Mouse Pressed: " + e.getButton());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        setToDo();
        //System.out.println("Mouse Released: " + e.getButton());
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        //if(timer!=null)timer.cancel();
        unToDo();
        //setToDo();
        //System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        //timer.cancel();
        //System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
    public void preAssignment(){

        // Construct the example object.
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        afterSeconds=this;
        //setToDo();
    }
    public void close(){
        try{

            GlobalScreen.removeNativeMouseListener(afterSeconds);
            GlobalScreen.removeNativeMouseMotionListener(afterSeconds);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static <GlobalMouseListenerExample> void main(String[] args) {
        //preAssignment();
    }
}
