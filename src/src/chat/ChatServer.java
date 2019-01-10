package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static final int PORT=5003;
	public static void main(String[] args)
	{
			ServerSocket serverSocket = null;
			
			//writer 를 담을 list 생성
			List<PrintWriter> listWrites = new ArrayList<>();
			List<User> users = new ArrayList<>();
			
			
			try {
				//서버 소켓생성
				serverSocket = new ServerSocket();
				
				//로컬 호스트 주소
				InetAddress inetAddress = InetAddress.getLocalHost();
				String localHostAddress = inetAddress.getHostAddress();
					
				//바인딩
				serverSocket.bind(new InetSocketAddress(localHostAddress, PORT));
				conlog("binding "+localHostAddress);
				
				Socket socket = null;
				try {
					while(true) {
					//3. accept
						socket = serverSocket.accept();
						new ChatSeverThread(socket,listWrites,users).start();
					}
					
				}catch(SocketException e)
				{
					conlog("abnormal closed by client");
				}
				catch(IOException e) {
					System.out.println("Error :"+e);
				}
				finally {
					try {
						if(socket !=null && socket.isClosed()==false)
					socket.close();
					}catch(IOException e)
					{
						e.printStackTrace();
					}
					}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error :"+e);
			} finally {
				
				//7.자원정리
				try {
					if(serverSocket!=null && serverSocket.isClosed()==false)
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
	}
	public static void conlog(String str)
	{
		System.out.println("[server] "+str);
	}
	
	
}
