package chat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


public class ChatClientThread extends Thread {

	Socket socket=null;
	
	public ChatClientThread (Socket socket) {
		this.socket=socket;
		
	}
	@Override
	public void run() {
		try {
			while(true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
			String str = br.readLine();
			if(str==null)
			{
				System.out.println("[client] closed by server");
				return;
			}
			else if(str.equals("joinOk"))
			{
				System.out.println("감사합니다. 즐거운 채팅 되세요");
			}
			else if(str.equals("joinFail"))
			{
				System.out.println("동일한 이름의 사용자가 존재합니다.");
				System.out.println("입력할 아이디를 join: 과함께 입력하세요 ex)id가 둘리라면 join:둘리");
			}
			else
				System.out.println(str);
			}
		}catch (SocketException e) {
			System.out.println("서버와 연결이 끊어졌습니다.");
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	

}
