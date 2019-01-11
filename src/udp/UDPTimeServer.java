package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	public static final int PORT = 6000;
	public static final int BUFFER_SIZE = 1024;
	private static final String SERVER_IP="192.168.178.1";
	public static void main(String[] args) {
		DatagramSocket socket=null;
		try {
			//1. socket 생성
			 socket = new DatagramSocket(PORT);
			 
			 while(true) {
			 //2. 데이터 수신
			 DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
			 socket.receive(receivePacket);
			 	
			 byte[] data = receivePacket.getData();
			 int length = receivePacket.getLength();
			 String message = new String(data,0,length,"UTF-8");
			 Date now = new Date();
			 System.out.println("[현재시간 :"+printDate(now)+"] received:"+message);
			 message = "[현재시간 :"+printDate(now)+"] received:"+message;
			 //3. 데이터 전송
			 byte[] sendData = message.getBytes("UTF-8");
			 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,receivePacket.getAddress(),receivePacket.getPort());
			 socket.send(sendPacket);
			 
					 
			 
			 
			 
			 }
			 
			 
		} catch (SocketException e) {
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(socket !=null && socket.isClosed()==false)
				socket.close();
		}
	}
	public static String printDate(Date d)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초");
		String s =sdf.format(d);
		return s;
	}
}
