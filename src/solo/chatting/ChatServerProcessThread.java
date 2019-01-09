package solo.chatting;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.io.*;
public class ChatServerProcessThread extends Thread {
	 private String nickname = null;
	  private Socket socket = null;
	  List<PrintWriter> listWriters = null;

	  public ChatServerProcessThread(Socket socket, List<PrintWriter> listWriters) {
		    this.socket = socket;
		    this.listWriters = listWriters;
		  }

	@Override
	public void run() {
		 try {

			 InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
				String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
				int remotePort =inetRemoteSocketAddress.getPort();
				System.out.println("[server] connected by client["+remoteHostAddress+":"+remotePort+"]");
				 System.out.println(remoteHostAddress+"님 입장");
		      BufferedReader buffereedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
		      while(true) {
		        String request = buffereedReader.readLine();
		        System.out.println(remoteHostAddress+"로부터 받음->"+request); 
		        if( request == null) {
		          consoleLog("클라이언트로부터 연결 끊김");
		     //     doQuit(printWriter);
		          break;
		        }
		         
//		        String[] tokens = request.split(":");
//		        if("join".equals(tokens[0])) {
//		          doJoin(tokens[1], printWriter);
//		        }
//		        else if("message".equals(tokens[0])) {
//		          doMessage(tokens[1]);
//		        }
//		        else if("quit".equals(tokens[0])) {
//		          doQuit(printWriter);
//		        }
		      }
		    }
		    catch(IOException e) {
		      consoleLog(this.nickname + "님이 채팅방을 나갔습니다.");
		    }finally {
		    		try {
		    			if(socket != null && socket.isClosed()==false)
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }

	}
	  private void consoleLog(String log) {
		    System.out.println(log);
		  }

//	  private void doQuit(PrintWriter writer) {
//		    removeWriter(writer);
//		     
//		    String data = this.nickname + "님이 퇴장했습니다.";
//		    broadcast(data);
//		  }
//		 
//		  private void removeWriter(PrintWriter writer) {
//		    synchronized (listWriters) {
//		      listWriters.remove(writer);
//		    }
//		  }
//		 
//		  private void doMessage(String data) {
//		    broadcast(this.nickname + ":" + data);
//		  }
//		 
//		  private void doJoin(String nickname, PrintWriter writer) {
//		    this.nickname = nickname;
//		     
//		    String data = nickname + "님이 입장하였습니다.";
//		    consoleLog(data);
//		    broadcast(data);
//		     
//		    // writer pool에 저장
//		    addWriter(writer);
//		  }
//		 
//		  private void addWriter(PrintWriter writer) {
//		    synchronized (listWriters) {
//		      listWriters.add(writer);
//		    }
//		  }
//		   
//		  private void broadcast(String data) {
//		    synchronized (listWriters) {
//		      for(PrintWriter writer : listWriters) {
//		        writer.println(data);
//		        writer.flush();
//		      }
//		    }
//		  }
		 


}
