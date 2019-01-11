package solo.chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientThread extends Thread {

	Socket socket=null;
	
	public ChatClientThread (Socket socket) {
		this.socket=socket;
		
	}
	@Override
	public void run() {
		while(true) {
			try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
		
		

			String str = br.readLine();
			if(str==null)
			{
				System.out.println("[client] closed by server");
				return;
			}else if(str.equals("joinOk"))
			System.out.println(str);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}
	
	
	
	

}
