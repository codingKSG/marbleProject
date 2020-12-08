package test;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import protocol.JFrameSet;

public class DiallogStart extends JFrame implements JFrameSet {
	private JLabel textLabel;

	public DiallogStart() {
		init();
		setting();
		batch();
		listener();
		
		
		setVisible(true);
	}

	@Override
	public void init() {
		textLabel = new JLabel("출발");
	}

	@Override
	public void setting() {
		setTitle("출발 타일");
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
