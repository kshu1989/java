package run;

public class Main {

	public static void main(String[] args) {
		Block object = new Block();

		ReceiveEthernetPacket re = new ReceiveEthernetPacket(object);
		re.start();
		SendEthernetPacket se = new SendEthernetPacket(object);
		se.start();
		
	}
}
