package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			String hostAddress = inetAddress.getHostAddress();
			String hostname = inetAddress.getHostName();
			
			System.out.println(hostname+ " : "+hostAddress);
			//System.out.println(hostAddress);
			
			byte[] addresses = inetAddress.getAddress();
			for(byte address : addresses)
			{
				
				//System.out.print(address);
				
				//제대로 나오게하려면 밑에 코드
				System.out.print(address & 0x00000000ff);
				System.out.print(".");
			}
			System.out.println(""); 
			for(byte address : addresses)
			{
				System.out.print(Integer.toBinaryString(address));
				System.out.print(".");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

}
