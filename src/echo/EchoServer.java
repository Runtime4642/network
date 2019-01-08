package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {

	private static final int PORT =5002;

	public static void main(String[] args) {
				ServerSocket serverSocket=null;
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
				//3. accept -> 클라이언트로 부터 연결요청을 기다린다.
				
			
				Socket socket = serverSocket.accept(); // Blcoking
				
				
				InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
				String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
				int remotePort =inetRemoteSocketAddress.getPort();
				System.out.println("[server] connected by client["+remoteHostAddress+":"+remotePort+"]");
				
				
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
