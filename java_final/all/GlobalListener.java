import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import  java.util.Timer;
import  java.util.TimerTask;
import java.awt.Robot;
public class GlobalListener implements NativeMouseInputListener {
    protected static Timer timer;
    private TimerTask showtime;
    private static int sec=0;
    private static GlobalListener example;

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

    public void nativeMouseClicked(NativeMouseEvent e) {
        //System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        timer=new Timer();
        showtime= new TimerTask(){
            @Override
            public void run() {
                ctrlC();
            }
        };
        if(showtime!=null)timer.schedule(showtime,2000);
        System.out.println("Mouse Pressed: " + e.getButton());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        timer.cancel();
        sec=0;
        //System.out.println("Mouse Released: " + e.getButton());
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        //System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        timer.cancel();
        sec=0;
        //System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
    public void preAssignment(){
        //try {                                   ///**********must!!!!!
        //    GlobalScreen.registerNativeHook();
        //}
        //catch (NativeHookException ex) {
        //    System.err.println("There was a problem registering the native hook.");
        //    System.err.println(ex.getMessage());
        //    System.exit(1);
        //}
        // Construct the example object.
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
    }
    public void close(){
        try{
            GlobalScreen.removeNativeMouseMotionListener(this);
            GlobalScreen.removeNativeMouseListener(this);
            timer.cancel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static <GlobalMouseListenerExample> void main(String[] args) {

    }
}
