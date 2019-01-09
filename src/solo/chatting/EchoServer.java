package solo.chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {

	private static final int PORT =5003;

	public static void main(String[] args) {
				ServerSocket serverSocket=null;
				List<PrintWriter> listWrites = new ArrayList<>();
			try {
				// 1. 서버소켓 생성
				serverSocket = new ServerSocket();
				
				// 2. Binding -> Socket의 SocketAddress(IPAddress + port) 를 바인딩한다.
				// 2.1 로컬호스트 주소를 알아냄
				InetAddress inetAddress = InetAddress.getLocalHost();
				String localhostAdress = inetAddress.getHostAddress();
				// 2.2 바인딩				
				serverSocket.bind(new InetSocketAddress(localhostAdress,PORT));
				System.out.println("[server] binding " +localhostAdress);
				
				
				Socket socket=null;
				try {
					while(true) {
				//3. accept -> 클라이언트로 부터 연결요청을 기다린다.
				 socket = serverSocket.accept(); // Blcoking
				new ChatServerProcessThread(socket, listWrites).start();	
				
				}
				}	
				
				catch(SocketException e)
				{
					System.out.println("[server] abnormal closed by client");
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
}
