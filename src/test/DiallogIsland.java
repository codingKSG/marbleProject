package test;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import protocol.JFrameSet;

public class DiallogIsland extends JFrame implements JFrameSet {
	private JLabel textLabel;

	public DiallogIsland() {
		init();
		setting();
		batch();
		listener();
		
		
		setVisible(true);
	}

	@Override
	public void init() {
		textLabel = new JLabel("º∂");
	}

	@Override
	public void setting() {
		setTitle("º∂ ≈∏¿œ");
		setSize(200,200);
		setLayout(new FlowLayout());
	}

	@Override
	public void batch() {
		add(textLabel);

	}

	@Override
	public void listener() {
		// TODO Auto-generated method stub

	}

}
