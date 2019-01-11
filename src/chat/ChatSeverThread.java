package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ChatSeverThread extends Thread{

	private Socket socket;
	private List<PrintWriter> listWriters;
	private String password ="2580";
	private User user;
	private List<User> users;
	
	public ChatSeverThread(Socket socket,List<PrintWriter> listWriters,List<User> users)
	{
		this.socket=socket;
		this.listWriters=listWriters;
		user = new User();
		this.users=users;
		}
	@Override
	public void run() {
		
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
	
		int remotePort =inetRemoteSocketAddress.getPort();
		conlog("connected by client ip:"+remoteHostAddress);
		BufferedReader br=null;
		PrintWriter pw=null;
		 try {
			 br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			 pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
		 
			while(true)
			{
				
				String request = br.readLine();
				conlog(user.getId()+"(사용자로 부터 메시지 get)"+request);
				if( request == null) {
			          conlog("클라이언트로부터 연결 끊김");
			          doQuit(pw);
			          break;
			        }
				String[] tokens = request.split(":");
				if(user.getBan())
				{
					pw.println("당신은 채팅 금지를 당했습니다.");
				}
				else if("join".equals(tokens[0])) {
					if(userCheck(tokens[1])) //usercheck = true 생성 가능
		          doJoin(tokens[1], pw);
					else
						pw.println("joinFail");
		        }
		        else if("message".equals(tokens[0])) {
		          doMessage(tokens[1],pw);
		        }
		        else if("quit".equals(tokens[0])) {
		          doQuit(pw);
		        }else if("manager".equals(tokens[0]))
		        {
		        	if(tokens[1].equals(this.password))
		        	{
		        		pw.println("권한이 관리자로 변경되었습니다");
		        		doMessage("님이 관리자가 되었습니다",pw);
		        		user.setId_lv(1);
		        	}
		        	else
		        		pw.print("비밀번호가 틀립니다");
		        }
		        else if("ban".equals(tokens[0])) {
			          if(user.getId_lv()==0)
			          {
			        	  pw.println("권한이 없습니다.");
			          }
			          else if(user.getId_lv()==1)
			          {
			        	// pw.println("여기까진됨");
			        	 pw.println(userBan(tokens[1]));
			          }
		        }
			}
			
			
			
			
		 } catch (IOException e) {
			 doQuit(pw);
			 System.out.println("클라이언트 로부터 연결이 끊어졌습니다.");
			System.out.println("error :"+e);
		}finally {
    		try {
    			if(socket != null && socket.isClosed()==false)
				socket.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
    }
	}
	public static void conlog(String str)
	{
		System.out.println("[server] "+str);
	}
	public void doJoin(String id,PrintWriter pw)
	{
		conlog(id+"님이 입장하였습니다.");
		pw.println("joinOk");
		user.setId(id);;
		users.add(user);
		doMessage("님이 입장하였습니다.",pw);
		addWriter(pw);
	}
	public void doQuit(PrintWriter pw)
	{
		String data = user.getId() + "님이 퇴장했습니다.";
	    broadcast(data,pw);
		synchronized (listWriters) {
		      listWriters.remove(pw);
		    }
		synchronized (users) {
		      users.remove(user);
		    }
	}
	 private void doMessage(String data,PrintWriter this_pw) {
		    broadcast(user.getId() + ":" + data,this_pw);
		  }
	public void broadcast(String meg,PrintWriter this_pw)
	{
		synchronized (listWriters) {
		      for(PrintWriter pw : listWriters) {
		    	  if(!pw.equals(this_pw)){
		    	  pw.println(meg);
		    	  pw.flush();
		    	  }
		      }
		    }
	}
	 private void addWriter(PrintWriter writer) {
		    synchronized (listWriters) {
		      listWriters.add(writer);
		    }
	}
	 private boolean userCheck(String id) {
		 synchronized(users)
		 {
			 for(int i=0;i<users.size();i++)
			 {
				 User user=users.get(i);
				 if(user.getId().equals(id))
					 return false; //유저가 있음
			 }
			 return true; //유저가 없음
		 }
	 }
	 private String userBan(String id)
	 {
		  for(User user:users)
    	  {
    		  if(user.getId().equals(id)){
    			  user.setBan(true);
    			  return id+"유저를 채팅 금지시켰습니다";
    		  }
    	  }
		  return "해당 유저가 없습니다";
	 }

}
