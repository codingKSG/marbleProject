package server;

import java.sql.Timestamp;

public class Test01 {
	Timestamp timestamp;
	
	public static void main(String[] args) {
		long a = Timestamp.UTC(2020, 12, 04, 14, 17, 0);
		System.out.println(a);
	}
}
