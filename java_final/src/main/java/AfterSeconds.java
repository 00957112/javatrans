import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import java.util.Timer;
import java.util.TimerTask;

abstract public class AfterSeconds implements NativeMouseInputListener {//單字模式小框框全域滑鼠事件
    protected Timer timer;
    boolean close=false;

    abstract void unToDo();
    abstract void setToDo();
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
    public void preAssignment(AfterSeconds example){
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        // Construct the example object.
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(example);
        GlobalScreen.addNativeMouseMotionListener(example);
        //setToDo();
    }
    public static <GlobalMouseListenerExample> void main(String[] args) {
        //preAssignment();
    }
}
