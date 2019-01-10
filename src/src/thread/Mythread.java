package thread;

public class Mythread extends Thread {
	private int a;
	public Mythread(int a)
	{
		this.a=a;
	}
	@Override
	public void run() {
	
		for(int i=0;i<a;i++)
		{
			System.out.println(i);
		}
	}

}
