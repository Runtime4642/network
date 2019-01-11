package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {

	private static final String SERVER_IP="192.168.178.1";
	private static final int SERVER_PORT=5000;
	public static void main(String[] args) {	
		Socket socket=null;
		try {
		
		//1. 소켓생성
		socket = new Socket();
		
		//Socket Buffer Size 참조
		int receiveBufferSize = socket.getReceiveBufferSize();
		int sendBufferSize = socket.getSendBufferSize();

		//Socket Buffer Size 변경
		socket.setReceiveBufferSize( 1024*10 );
		socket.setSendBufferSize( 1024*10 );
		
		//(Nagle 알고리즘 off)
		socket.setTcpNoDelay(true);
		
		
		//데이터 읽기에 타임아웃 설정
		socket.setSoTimeout(1);
		
		
		
		//2. 서버 연결
		socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
		System.out.println("[client] connected");
		
		//3.IOStream 받아오기
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		//4.쓰기
		String data = "Hello World\n";
		os.write(data.getBytes("UTF-8"));
		
		//5.읽기
		byte[] buffer = new byte[255];
		int readByteCount = is.read(buffer); //Blocking
		if(readByteCount == -1 )
		{
			System.out.println("[client] closed by server");
			return;
		}
		data = new String(buffer,0,readByteCount,"UTF-8");
		System.out.println("[client] received:"+data);
		
		} catch(SocketTimeoutException ex)
		{
			System.out.println("[client] time out");
		}
		catch (IOException e) {
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
