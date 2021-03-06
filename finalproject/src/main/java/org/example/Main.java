package org.example;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
public class Main extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form Main
     */
    public static JButton recordButton;
    public static String readstr;

    public Main() {

        super("類即時翻譯器");

        readstr=FileHandler.readFileOpen("translation.txt");
        if(readstr==null)
            FileHandler.writeFileOpen("translation.txt","{\"data\":{\"pages\":[]},\"errcode\":0}",false);

        readstr=FileHandler.readFileOpen("vocabulary.txt");
        if(readstr==null)
            FileHandler.writeFileOpen("vocabulary.txt","{\"data\":{\"pages\":[]},\"errcode\":0}",false);

        initComponents();
        panel.setBackground(new Color(255, 255, 255));

        GlobalTips ma = new GlobalTips();
        ClipboardFrame test=new ClipboardFrame();
        test.start();

        switchButton2.addEventSelected(new EventSwitchSelected() {
            @Override
            public void onSelected(boolean selected) {
                if (selected) {
                    //放選之後要發生的事件
                    test.close();
                    ma.start();
                    panel.setBackground(new Color(255, 255, 255));//這是原本設置選後要改變的背景顏色
                } else {
                    ma.close();
                    test.start();
                    panel.setBackground(new Color(255, 255, 255));//不選後要變成的背景顏色
                }
            }
        });

        //全域按鍵註冊（必放一個
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        //////////////
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("查詢紀錄")) {
            String recordText="";
            recordText+=FileHandler.jsonread("translation.txt");
            recordText+="--------------------------------------------------------------------------------------------------------------------------------------------\n";
            recordText+=FileHandler.jsonread("vocabulary.txt");

            if(recordText!="")
            {
                RecordFrame fr = new RecordFrame(recordText);
                fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fr.setSize(700, 500);
                fr.setVisible(true);
            }
            else JOptionPane.showMessageDialog(null, "還沒有查詢紀錄",
                    "Message!", JOptionPane.PLAIN_MESSAGE);
            //把顯示紀錄的東東放這裡
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        switchButton2 = new switchbutton();
        recordButton=new JButton("查詢紀錄");
        recordButton.addActionListener(this);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JTextArea text2=new JTextArea();
        text2.setFont(new Font("Serif", Font.PLAIN, 14));
        text2.setEnabled(false);
        text2.setBackground(Color.WHITE);
        String s="歡迎來到類即時翻譯器~此翻譯器有兩種模式!\n關閉開關即為翻譯模式\n" +
                "打開開關即為單字模式\n點擊查詢紀錄可查看先前翻譯的結果\n" +
                "選取要翻譯的文字區段後游標停放在想顯示的地方等候翻譯顯示\n"+
                "按下ctrl+c也可達到一樣的效果\n"+
                "若想把顯示框關閉,點擊顯示框外的任一地方即可\n"+
                "按下ctrl+s可儲存查詢結果";
        text2.setText(s);

        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        p1.add(text2);
        p1.add(recordButton);
        p1.add(switchButton2);
        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)//做起來像是調整框的水平大小
                                //.addComponent(text2,javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(p1,javax.swing.GroupLayout.PREFERRED_SIZE,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                //.addComponent(switchButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(0, Short.MAX_VALUE))//離容器邊界的距離??
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(5, 5, 5)//做起來像是調整框的垂直大小
                                //.addComponent(text2,javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(p1,javax.swing.GroupLayout.PREFERRED_SIZE,  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                //.addComponent(switchButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(0, Short.MAX_VALUE))//離容器邊界的距離??
        );
        //panelLayout.setAutoCreateContainerGaps(true);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    private switchbutton switchButton2;
    // End of variables declaration//GEN-END:variables
}
