package chat3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Scanner;


public class ChatClient {
	private static final String SERVER_IP="192.168.178.1";
	private static final int SERVER_PORT=5003;
	public static void main(String[] args) {
		Socket socket=null;
		Scanner sc = new Scanner(System.in);
		try {
		
		//1. 소켓생성
		socket = new Socket();

		//2. 서버 연결
		socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
		System.out.println("[client] connected");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
		System.out.println("닉네임을 입력하세요:");
		String id = sc.nextLine();
		pw.println("join:"+id);
		//new ChatClientThread(socket).start();
		
		while(true) {
		//3.IOStream 받아오기
		
		String input = sc.nextLine();
		if(input.equals("quit")) {
			pw.println("quit:"+input);
			System.out.println("서버에서 나갑니다");
			break;
		}
		if(input.length()>=7) {
		 if(input.substring(0, 7).equals("manager"))
		{
			pw.println(input);
			continue;
		}
		}
		if(input.length()>=3) {
			if(input.substring(0,3).equals("ban"))
			{
				pw.println(input);
				continue;
			}
		}
		if(input.length()>=4)
			if(input.substring(0,4).equals("join"))
			{
				pw.println(input);
				continue;
			}
		pw.println("message:"+input);	
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
