package run;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import packet.EthernetPacket;
import packet.MakePacket;
import packet.PacketForPrint;

public class ReceiveEthernetPacket extends Thread {
	private ServerSocket sever = null;
	private Block block;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	public ReceiveEthernetPacket(Block block) {
		this.block = block;
		try {
			sever = new ServerSocket(8000);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Socket socket;
		try {
			socket = sever.accept();
			System.out.println("连接建立：");
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		byte[] buffer = new byte[1500];
		int reNum = -1;
		int ackNum = -1;
		PacketForPrint print = new PacketForPrint();

		while (true) {

			try {
				
				block.waitMess();

				reNum = in.read(buffer, 0, 1500);
				System.out.println("接收端接收：");
				MakePacket.ByteToString(buffer, reNum, print);
				System.out.println(print.getSrcAddrString() + " -------> " + print.getDesAddrString());
				System.out.println("CRC String : " + print.getFCSString());
				System.out.println("ACK number : " + print.getControlString());
	
				ackNum = Integer.parseInt(print.getControlString());

				EthernetPacket packet = MakePacket.getEthernetPacket(++ackNum); // 序列号为1
				byte[] send = packet.toByte();

				out.write(send, 0, packet.getLen());
				MakePacket.ByteToString(send, packet.getLen(), print);
				System.out.println("接收端发送：");
				System.out.println(print.getDesAddrString() + " -------> " + print.getSrcAddrString());
				System.out.println("CRC String : " + print.getFCSString());
				System.out.println("ACK number : " + print.getControlString());
				block.notifyMess();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
