package chat3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import chat3.client.win.ChatWindow;


public class ChatClientThread extends Thread {

	Socket socket=null;
	ChatWindow cw;
	
	public ChatClientThread (Socket socket,ChatWindow cw) {
		this.socket=socket;
		this.cw=cw;
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
				cw.append_Message("감사합니다. 즐거운 채팅 되세요");
			}
			else
				cw.append_Message(str);
		
			}
		}catch (SocketException e) {
			System.out.println("서버와 연결이 끊어졌습니다.");
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	

}
