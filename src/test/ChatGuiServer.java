package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
 
/**
* 멀티 채팅을 위한 서버 구성
*/
public class ChatGuiServer {
  /**
   *  필요한 Field 선언  
   */
  ServerSocket server;
  Socket sk;
  ArrayList<ServerThread> list = new ArrayList<ServerThread>();
 
  /*
   * ServerSocket을 생성하여 client 접속 대할 생성자
   */
  public ChatGuiServer(){ //생성자
      try {
          server = new ServerSocket(7000);
          while(true){
              System.out.println("\nClient접속이 대기중입니다.");
              sk = server.accept(); // 클라이언트 접속 대기중
              System.out.println(sk.getInetAddress()+"의 주소가 연결되었습니다. ");
             
              //접속된 클라이언트를 스레드로 만들어 ArrayList에 추가
              ServerThread st = new ServerThread(this);
              addThread(st); //ArrayList 에 추가하기
              st.start(); //쓰레드 시작하기                    
         
          } //while문 끝
      } catch (IOException e) {
          System.out.println(e + " -> ServerSocket fil");
      }
  }//생성자 끝
 
  /**
   * 접속된 클라이언트를 저장하기
   */
  public void addThread(ServerThread st){
          list.add(st);  //추가
  }
 
  /**
   * 접속이 끊긴 클라이언트를 ArrayList에 제거하기
   **/
  public void removeThread(ServerThread st){
          list.remove(st); //제거
  }
 
  /**
   * 접속된 각각의 클라이언트에게 데이터 전송하기.
   */
  public void broadCast(String message){
     
      for(ServerThread st : list){
          st.pw.println(message);
      }              
  }
//각각의 클라이언트를 Trhrea로 구현
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
      //읽기 준비
      BufferedReader br= new BufferedReader
              (new InputStreamReader(server.sk.getInputStream()));
     
      //쓰기 준비
      pw = new PrintWriter(server.sk.getOutputStream(),true);
             
      name = br.readLine(); //대화명 읽기
     
      server.broadCast("["+name+"]님 입장하셨습니다.");
     
      String data= null;
      while((data = br.readLine()) != null){
          server.broadCast("[ "+name+" ] "+ data);          
      }
     
     
     
      } catch (Exception e) {
          //현재 쓰레드를 ArrayList에 제거한다.
          server.removeThread(this);
          server.broadCast("[ "+name+" ] 님이 퇴장하셨습니다.");
          System.out.println
          (server.sk.getInetAddress()+"주소의 ["+name+" ] 님이 퇴장하셨습니다.");
          System.out.println(e + "----> ");
      }      
     
  }
}
 
  public static void main(String[] args) {
      new ChatGuiServer();
     
  }
 
} //클래스 끝