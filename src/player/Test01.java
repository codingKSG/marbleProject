package player;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;

public class Test01 extends JFrame {

	/**
	 * Create the panel.
	 */
	public Test01() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("\uC8FC\uC0AC\uC704 \uAD74\uB9AC\uAE30");
		btnNewButton.setFont(new Font("±¼¸²", Font.PLAIN, 10));
		btnNewButton.setBounds(100, 110, 100, 23);
		getContentPane().add(btnNewButton);
		
		JPanel player1 = new JPanel();
		player1.setBorder(new LineBorder(new Color(0, 0, 0)));
		player1.setForeground(Color.BLUE);
		player1.setBackground(Color.BLUE);
		player1.setBounds(184, 180, 40, 40);
		getContentPane().add(player1);
		player1.setLayout(null);
		
		JLayeredPane board0 = new JLayeredPane();
		board0.setBorder(new LineBorder(new Color(0, 0, 0)));
		board0.setBounds(200, 200, 100, 100);
		getContentPane().add(board0);
		
		JLayeredPane board1 = new JLayeredPane();
		board1.setBounds(0, 0, 100, 100);
		getContentPane().add(board1);
		
		JLayeredPane board2 = new JLayeredPane();
		board2.setBounds(0, 100, 100, 100);
		getContentPane().add(board2);
		
		JLayeredPane board3 = new JLayeredPane();
		board3.setBounds(0, 200, 100, 100);
		getContentPane().add(board3);
		
		JLayeredPane board4 = new JLayeredPane();
		board4.setBounds(100, 200, 100, 100);
		getContentPane().add(board4);
		
		JLayeredPane board5 = new JLayeredPane();
		board5.setBounds(100, 0, 100, 100);
		getContentPane().add(board5);
		
		JLayeredPane board6 = new JLayeredPane();
		board6.setBounds(200, 0, 100, 100);
		getContentPane().add(board6);
		
		JLayeredPane board7 = new JLayeredPane();
		board7.setBounds(200, 100, 100, 100);
		getContentPane().add(board7);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(124, 143, 57, 15);
		getContentPane().add(lblNewLabel);
		
		setTitle("hi");
		setSize(350,350);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Test01();
	}
}
