package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MarbleLogin extends JFrame {
	
	private MarbleLogin marbleLogin = this;
	private static final String TAG = "MarbleLogin : ";
	private String id;
	
	private JTextField tfId;
	private JPanel jpCetner;
	private JLabel laText1;
	private JLabel laText2;
	private JButton btnLogin;
	
	public MarbleLogin() {
		init();
		
		setting();
		
		batch();
		
		listener();
		
		setVisible(true);
	}
	
	private void init() {
		tfId = new JTextField("", 10);
		jpCetner = new JPanel();
		laText1 = new JLabel("10자 이내의 ID를 설정해주세요.");
		laText2 = new JLabel("");
		btnLogin = new JButton("로그인");
	}
	
	private void setting() {
		setSize(350, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Marble Login");
		setLocationRelativeTo(null);
		
		laText1.setFont(new Font("D2Coding", Font.BOLD, 20));
		laText2.setFont(new Font("D2Coding", Font.BOLD, 20));
		laText2.setForeground(Color.RED);
	}
	
	private void batch() {
		add(tfId, BorderLayout.NORTH);
		add(jpCetner, BorderLayout.CENTER);
		add(btnLogin, BorderLayout.SOUTH);
		jpCetner.add(laText1);
		jpCetner.add(laText2);
	}
	
	private void listener() {
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
		tfId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
	}
	
	private void login() {
		String userInput = tfId.getText();
		if (userInput.length() > 10) {
			laText2.setText("10자 이내의 ID로 변경해주세요 !");
			return;
		}
		if (userInput.equals("")) {
			laText2.setText("ID를 입력해주세요 !");
			return;
		}
		this.id = userInput;
		setVisible(false);
		new MarbleClient(id);
	}
	
	public static void main(String[] args) {
		new MarbleLogin();
	}
}
