package run;

public class Block {

	private Object notEmpty = new Object();

	public void waitMess() throws InterruptedException {
		synchronized (notEmpty) {

//			System.out.println("wait message!");
//			Thread.sleep(1000);
			notEmpty.wait();
		}
	}

	public void notifyMess() throws InterruptedException {
		synchronized (notEmpty) {

//			System.out.println("get message");
//			Thread.sleep(1000);
			notEmpty.notifyAll();
		}
	}

}
