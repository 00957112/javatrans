package org.example;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.util.Properties;

/*
 * @test
 * @key headful
 * @summary To check the functionality of newly added API getSystemSelection & make sure
 *          that it's mapped to primary clipboard
 * @author Jitender(jitender.singh@eng.sun.com) area=AWT
 * @library ../../../../lib/testlibrary
 * @build ExtendedRobot
 * @run main SystemSelectionAWTTest
 */

public class SystemSelectionAWTTest {

    Frame frame;
    TextField tf1, tf2;
    Clipboard clip;
    Transferable t;

    /** 測試用*/
    public static void main(String[] args) throws Exception {
        new SystemSelectionAWTTest().doTest();
    }

    SystemSelectionAWTTest() {
        frame = new Frame();
        frame.setSize(200, 200);
        frame.setAlwaysOnTop(true);
        tf1 = new TextField();
        tf1.addFocusListener( new FocusAdapter() {
            public void focusGained(FocusEvent fe) {
                fe.getSource();
            }
        });

        tf2 = new TextField();

        frame.add(tf2, BorderLayout.NORTH);
        frame.add(tf1, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.toFront();
        tf1.requestFocus();
        tf1.setText("Selection Testing");
    }

    // Check whether Security manager is there
    public void checkSecurity() {
        SecurityManager sm = System.getSecurityManager();

        if (sm == null) {
            System.out.println("security manager is not there");
            getPrimaryClipboard();
        } else {
            try {
                sm.checkPermission(new AWTPermission("accessClipboard"));
                getPrimaryClipboard();
            } catch(SecurityException e) {
                clip = null;
                System.out.println("Access to System selection is not allowed");
            }
        }
    }

    // Get the contents from the clipboard
    void getClipboardContent() throws Exception {
        t = clip.getContents(this);
        if ( (t != null) && (t.isDataFlavorSupported(DataFlavor.stringFlavor) )) {
            tf2.setBackground(Color.red);
            tf2.setForeground(Color.black);
            tf2.setText((String) t.getTransferData(DataFlavor.stringFlavor));
        }
    }

    // Get System Selection i.e. Primary Clipboard
    private void getPrimaryClipboard() {
        Properties ps = System.getProperties();
        String operSys = ps.getProperty("os.name");
        clip = Toolkit.getDefaultToolkit().getSystemSelection();
        if (clip == null) {
            if ((operSys.substring(0,3)).equalsIgnoreCase("Win") ||
                    (operSys.substring(0,3)).equalsIgnoreCase("Mac"))
                System.out.println(operSys + " operating system does not support system selection ");
            else
                throw new RuntimeException("Method getSystemSelection() is returning null on X11 platform");
        }
    }

    // Compare the selected text with one pasted from the clipboard
    public void compareText() {
        if ((tf2.getText()).equals(tf1.getSelectedText()) &&
                System.getProperties().getProperty("os.name").substring(0,3) != "Win") {
            System.out.println("Selected text & clipboard contents are same\n");
        } else  {
            throw new RuntimeException("Selected text & clipboard contents differs\n");
        }
    }

    public void doTest() throws Exception {
        //ExtendedRobot robot = new ExtendedRobot();

        frame.setLocation(100, 100);

        Point tf1Location = tf1.getLocationOnScreen();
        Dimension tf1Size = tf1.getSize();
        checkSecurity();

        if (clip != null) {

            getClipboardContent();
            compareText();

            getClipboardContent();
            compareText();
        }
    }
}