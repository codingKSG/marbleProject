package test;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
 
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
public class ChatGuiClient extends JFrame implements ActionListener, Runnable {
 
  JTextArea textArea = new JTextArea();
  JScrollPane jp = new JScrollPane(textArea);
  JTextField input_Text = new JTextField();
  Socket sk;
  BufferedReader br;
  PrintWriter pw; // �ٸ� �޼��忡�� ����ϱ� ���� ����������
 
  public ChatGuiClient() {
      super("�̴� Chat GuiClient"); //������ ����ǥ����
 
      textArea.setBackground(Color.pink);
      textArea.setEditable(false);// TextArea �Է��ϱ� ��Ȱ��ȭ
 
      add(jp, "Center");
      add(input_Text, "South");
      setSize(400, 500);
      setVisible(true);
      input_Text.requestFocus(); // ����� Ŀ�� ����, ȭ���� ������ �� �۾��ؾ���
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
      input_Text.addActionListener(this); //�̺�Ʈ ���
 
  }// ������ ��
 
  /**
   * �������� ���ӱ�� ����ϴ� �޼ҵ� �ۼ�
   * */
  public void serverConnection() {
      try {
          sk = new Socket("127.0.0.1", 7000);
          String name = JOptionPane.showInputDialog(this, "��ȭ���� �Է����ּ���.",
                  JOptionPane.INFORMATION_MESSAGE);
 
          // �б��غ�
          br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
 
          // �����غ�
          pw = new PrintWriter(sk.getOutputStream(), true);
          pw.println(name); // �������� �����ϱ�
 
          new Thread(this).start();
 
      } catch (Exception e) {
          System.out.println(e + "Socket ���� ����");
      }
  }
 
  public static void main(String[] args) {
      new ChatGuiClient().serverConnection(); // ��ü������ ���ÿ� �޼��� ȣ��
 
  }
 
  // ������
  /**
   * ������ �������� �����͸� �о TextArea�� �ø���
   */
  @Override
  public void run() {
      String data = null;
      try {
          while ((data = br.readLine()) != null) {
              textArea.append(data + "\n");
             
              //textArea�ڽ��� ��ũ�ѹ��� ��ġ�� �Էµ� Text���� ��ŭ ������
              textArea.setCaretPosition(textArea.getText().length());
          }
      } catch (Exception e) {
          System.out.println(e + "--> Client run fail");
      }
  }
 
  // ���������� �̺�Ʈ �߻�
  @Override
  public void actionPerformed(ActionEvent e) {
      String data = input_Text.getText();
      pw.println(data); // �������� ����
      input_Text.setText("");
  }
}