package test;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import protocol.JFrameSet;

public class TestPlayer extends JPanel implements JFrameSet{
	
	private TestPlayer player = this;
	public final static String TAG = "TestPlayer : ";
	
	private ImageIcon player01;
	
	private int x = 250;
	private int y = 250;
	
	public TestPlayer() {
		init();
		setting();
		batch();
		listener();
	}

	@Override
	public void init() {
		player01 = new ImageIcon("images/img_player04.png");
		
	}

	@Override
	public void setting() {
		setSize(50,50);
		setLocation(x, y);
		
	}

	@Override
	public void batch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listener() {
		// TODO Auto-generated method stub
		
	}
	

}
