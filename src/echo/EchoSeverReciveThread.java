package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class EchoSeverReciveThread extends Thread {

	private Socket socket;
	
	public EchoSeverReciveThread(Socket socket)
	{
		this.socket=socket;
	}

	@Override
	public void run() {
		
		try {
			//4. IOStream 받아오기
				
			while(true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));	
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
			String line = br.readLine();
			if(line ==null)
			{
				System.out.println("closed by client");
				break;
			}
			System.out.println("received: "+line);
			pw.println(line);
			}
			}
			
			catch(SocketException e)
			{
				System.out.println("[server] abnormal closed by client");
			}
			catch(IOException e) {
				System.out.println("Error :"+e);
			}finally {
				try {
					if(socket !=null && socket.isClosed()==false)
				socket.close();
				}catch(IOException e)
				{
					e.printStackTrace();
				}
				}
		
		
	}

}
