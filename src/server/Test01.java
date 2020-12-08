package server;

import java.sql.Timestamp;

public class Test01 {
	Timestamp timestamp;
	
	public static void main(String[] args) {
		long a = Timestamp.UTC(2020, 12, 07, 18, 46, 0);
		System.out.println(a);
		
		System.out.println("git 충돌 실험001 시작");
	}
}
