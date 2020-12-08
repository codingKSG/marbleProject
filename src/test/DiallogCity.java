package test;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import protocol.JFrameSet;

public class DiallogCity extends JFrame implements JFrameSet {
	private JLabel textLabel;

	public DiallogCity() {
		init();
		setting();
		batch();
		listener();
		
		
		setVisible(true);
	}

	@Override
	public void init() {
		textLabel = new JLabel("시티");
	}

	@Override
	public void setting() {
		setTitle("시티 타일");
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
