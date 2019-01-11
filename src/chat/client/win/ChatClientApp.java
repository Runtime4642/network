package chat.client.win;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import chat3.ChatClientThread;

public class ChatClientApp {
	private static final String SERVER_IP="192.168.178.1";
	private static final int SERVER_PORT=5003;

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);
		Socket socket=null;
		BufferedReader br=null;
		PrintWriter pw =null;
			
				try {
					//1. 소켓생성
					socket = new Socket();
					//2. 서버 연결
					socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
					
					System.out.println("[client] connected");
					
					br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
					 pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
					 
					 
						while( true ) {
							
							System.out.println("대화명을 입력하세요.");
							System.out.print(">>> ");
							name = scanner.nextLine();
							
							if (name.isEmpty()) {
								System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
								continue;
							}
							pw.println("join:"+name);
							String read= br.readLine();
							if (read.equals("joinOk"))	
								break;
							else if(read.equals("joinFail"))
							{
								System.out.println("이미 존재하는 대화명 입니다.");
							}
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		scanner.close();
		
		//
		// JOIN 처리
		// Response가 "JOIN:OK" 이면
		//
		
		ChatWindow cw = new ChatWindow(name,pw);	
	//	new ChatClientThread(socket,cw).start();
		
		cw.show();
	}

}