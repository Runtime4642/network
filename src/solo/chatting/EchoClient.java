package solo.chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP="192.168.178.1";
	private static final int SERVER_PORT=5003;
	
	public static void main(String[] args) {
		Socket socket=null;
		try {
		
		//1. 소켓생성
		socket = new Socket();

		//2. 서버 연결
		socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
		System.out.println("[client] connected");
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
		PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
		while(true) {
		//3.IOStream 받아오기
		Scanner sc = new Scanner(System.in);
		System.out.print(">>");
		String input = sc.nextLine();
		if(input.equals("exit")) {
			System.out.println("서버에서 나갑니다");
			break;
		}
		pw.println(input);
		//String str = br.readLine();
//		if(str==null)
//		{
//			System.out.println("[client] closed by server");
//			return;
//		}
		System.out.println(input);
		}
		
		
		} catch (IOException e) {
		e.printStackTrace();
		}finally
		{
			try {
				if(socket!=null && socket.isClosed()==false)
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
