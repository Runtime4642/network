package chat2;

import java.io.PrintWriter;

public class User {
	private String id;
	private int id_lv=0; //0- default , 1 - 관리자
	private boolean ban = false;
	private PrintWriter pw;

	public PrintWriter getPw() {
		return pw;
	}
	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getId_lv() {
		return id_lv;
	}
	public void setId_lv(int id_lv) {
		this.id_lv = id_lv;
	}
	public boolean isBan() {
		return ban;
	}
	public void setBan(boolean ban) {
		this.ban = ban;
	}
	public boolean getBan() {
		return ban;
	}
}
