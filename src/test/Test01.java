package test;
import java.awt.Graphics;
 
import javax.swing.JFrame;
 
public class Test01 extends JFrame{
     
    public Test01() {
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        // �� 0.5 �� ���� ���ο� �������� �׸��� ���� �ݺ����� �̿�
        while(true) {
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            // �������� ���� �׷��� �ϹǷ� repaint() �� ȣ��
            repaint();
        }
         
    }
    public void paint(Graphics g) {
        super.paint(g);
         
        int x[] = {
                (int)(Math.random()*300),
                (int)(Math.random()*300),
                (int)(Math.random()*300)
        };
        int y[] = {
                (int)(Math.random()*300),
                (int)(Math.random()*300),
                (int)(Math.random()*300)
        };
        g.drawPolygon(x, y, 3);     
    }
     
    public static void main(String[] args) {
        new Test01();
    }
}