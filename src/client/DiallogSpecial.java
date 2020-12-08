package client;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import protocol.JFrameSet;

public class DiallogSpecial extends JFrame implements JFrameSet {
	private JLabel textLabel;
	private String id;

	public DiallogSpecial(String id) {
		this.id = id;
		
		init();
		setting();
		batch();
		listener();
		
		setVisible(true);
	}

	@Override
	public void init() {
		textLabel = new JLabel("Ω∫∆‰º»");
	}

	@Override
	public void setting() {
		setTitle(id + ": Ω∫∆‰º» ≈∏¿œ");
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
