package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
 
/**
* ��Ƽ ä���� ���� ���� ����
*/
public class ChatGuiServer {
  /**
   *  �ʿ��� Field ����  
   */
  ServerSocket server;
  Socket sk;
  ArrayList<ServerThread> list = new ArrayList<ServerThread>();
 
  /*
   * ServerSocket�� �����Ͽ� client ���� ���� ������
   */
  public ChatGuiServer(){ //������
      try {
          server = new ServerSocket(7000);
          while(true){
              System.out.println("\nClient������ ������Դϴ�.");
              sk = server.accept(); // Ŭ���̾�Ʈ ���� �����
              System.out.println(sk.getInetAddress()+"�� �ּҰ� ����Ǿ����ϴ�. ");
             
              //���ӵ� Ŭ���̾�Ʈ�� ������� ����� ArrayList�� �߰�
              ServerThread st = new ServerThread(this);
              addThread(st); //ArrayList �� �߰��ϱ�
              st.start(); //������ �����ϱ�                    
         
          } //while�� ��
      } catch (IOException e) {
          System.out.println(e + " -> ServerSocket fil");
      }
  }//������ ��
 
  /**
   * ���ӵ� Ŭ���̾�Ʈ�� �����ϱ�
   */
  public void addThread(ServerThread st){
          list.add(st);  //�߰�
  }
 
  /**
   * ������ ���� Ŭ���̾�Ʈ�� ArrayList�� �����ϱ�
   **/
  public void removeThread(ServerThread st){
          list.remove(st); //����
  }
 
  /**
   * ���ӵ� ������ Ŭ���̾�Ʈ���� ������ �����ϱ�.
   */
  public void broadCast(String message){
     
      for(ServerThread st : list){
          st.pw.println(message);
      }              
  }
//������ Ŭ���̾�Ʈ�� Trhrea�� ����
class ServerThread extends Thread{
 
  ChatGuiServer server;
  PrintWriter pw;
  String name;
  public ServerThread(ChatGuiServer server){
      this.server = server;
  }
  @Override
  public void run() {
      try {
      //�б� �غ�
      BufferedReader br= new BufferedReader
              (new InputStreamReader(server.sk.getInputStream()));
     
      //���� �غ�
      pw = new PrintWriter(server.sk.getOutputStream(),true);
             
      name = br.readLine(); //��ȭ�� �б�
     
      server.broadCast("["+name+"]�� �����ϼ̽��ϴ�.");
     
      String data= null;
      while((data = br.readLine()) != null){
          server.broadCast("[ "+name+" ] "+ data);          
      }
     
     
     
      } catch (Exception e) {
          //���� �����带 ArrayList�� �����Ѵ�.
          server.removeThread(this);
          server.broadCast("[ "+name+" ] ���� �����ϼ̽��ϴ�.");
          System.out.println
          (server.sk.getInetAddress()+"�ּ��� ["+name+" ] ���� �����ϼ̽��ϴ�.");
          System.out.println(e + "----> ");
      }      
     
  }
}
 
  public static void main(String[] args) {
      new ChatGuiServer();
     
  }
 
} //Ŭ���� ��