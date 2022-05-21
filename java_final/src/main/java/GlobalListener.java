import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

import  java.util.Timer;
import  java.util.TimerTask;
import java.awt.Robot;
public class GlobalListener implements NativeMouseInputListener {
    private  Timer timer;
    private TimerTask showtime;
    private static int sec=0;

    public boolean ctrlC(){
        try {
            Robot robot = new Robot();
            robot.keyPress(17);
            robot.keyPress(67);
            robot.keyRelease(17);
            robot.keyRelease(67);
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
        System.out.println("come");
        showtime= new TimerTask(){//也可以用匿名類別的方式，
            @Override
            public void run() {
                ctrlC();
                System.out.println("hhh");
            }
        };
        if(showtime!=null)timer.schedule(showtime,3000);
        //System.out.println("Mouse Pressed: " + e.getButton());
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
    public static void preAssignment(){
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        // Construct the example object.
        GlobalListener example = new GlobalListener();
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(example);
        GlobalScreen.addNativeMouseMotionListener(example);
    }
    public static <GlobalMouseListenerExample> void main(String[] args) {
        preAssignment();
        System.out.println("fin");
    }
}
