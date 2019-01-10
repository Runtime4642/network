package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookUp {

	public static void main(String[] args) {
		
		while(true)
		{
		Scanner sc = new Scanner(System.in);
		String line =sc.nextLine();
		if(line.equals("exit"))
			break;
			try {
				InetAddress[] inetAddress = InetAddress.getAllByName(line);
				for(InetAddress i : inetAddress)
				{
					System.out.println(i.getHostName()+" : "+i.getHostAddress());
				}
			}
			
			catch (UnknownHostException e) {
				 System.out.println("Unrecognized host");
			}
		}
	}

}
