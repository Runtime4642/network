package chat3.client.win;



import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;

import chat3.User;



public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private PrintWriter pw;

	public ChatWindow(String name,PrintWriter pw) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		this.pw=pw;
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if(keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
		frame.pack();
	}
	
	private void sendMessage() {
		String input = textField.getText();
		
		// MESSAGE 명령 처리 요청  
		// "MESSAGE: + message\r\n"

		// test(Thread 안에 있어야 하는 코드
		
		if(input.equals("quit")) {
			pw.println("quit:"+input);
			System.out.println("서버에서 나갑니다");
			return;
		}
		if(input.length()>=7) {
		 if(input.substring(0, 7).equals("manager"))
		{
			 pw.println(input);
			return;
		}
		}
		if(input.length()>=3) {
			if(input.substring(0,3).equals("ban"))
			{
				pw.println(input);
				return;
			}
		}
		if(input.length()>=4)
			if(input.substring(0,4).equals("join"))
			{
				pw.println(input);
				return;
			}
		pw.println("message:"+input);
		
		textArea.append("나:" + input);
		textArea.append("\n");

		textField.setText("");
		textField.requestFocus();

	}
	public void append_Message(String str) {
		textArea.append(str+"\n");
	}
}