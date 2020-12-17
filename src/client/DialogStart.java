package client;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import protocol.JFrameSet;

public class DialogStart extends JFrame implements JFrameSet {
	private JLabel textLabel;
	private String id;

	public DialogStart(String id) {
		this.id = id;
		
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
		setTitle(id + ": 출발 타일");
		setSize(200,200);
		setLayout(new FlowLayout());
	}

	@Override
	public void batch() {
		add(textLabel);

	}

	@Override
	public void listener() {

	}

}
