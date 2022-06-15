import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
public class RFrame extends JFrame{
    public RFrame(String rc)
    {
        super("紀錄");
        JTextArea txt=new JTextArea();
        txt.setFont(new Font("Serif", Font.PLAIN, 14));
        txt.setEnabled(false);
        txt.setBackground(Color.WHITE);
        txt.setText(rc);
        JScrollPane scroll;
        scroll = new JScrollPane(txt);
        getContentPane().add(scroll);
    }
}
//
