import  java.awt.Robot;
import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("click");
    }

    @Override
    public void mousePressed(MouseEvent e){
        System.out.println("mouse press");
    }
    @Override
    public void mouseReleased(MouseEvent e){
        System.out.println("mouse release");
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        System.out.println("mouse enter");
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        System.out.println("mouse exit");
    }
}
