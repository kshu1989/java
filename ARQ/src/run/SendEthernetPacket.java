package run;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import packet.EthernetPacket;
import packet.MakePacket;
import packet.PacketForPrint;

public class SendEthernetPacket extends Thread {
	private Socket socket;
	private Block block;
	private DataInputStream in = null;
	private DataOutputStream out = null;

	public SendEthernetPacket(Block block) {
		this.block = block;
		try {
			sleep(1000);
			socket = new Socket("127.0.0.1", 8000);
			if (socket == null) {
				System.out.println("不能连接！");
			}
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		byte[] buffer = new byte[1500];
		int reNum = -1;
		int ackNum = 0;
		PacketForPrint print = new PacketForPrint();
		
		while (true) {
			EthernetPacket packet = MakePacket.getEthernetPacket(ackNum); // 序列号为0
			
			byte[] send = packet.toByte();
			try {
				sleep(100);
				out.write(send, 0, packet.getLen());
				out.flush();
				MakePacket.ByteToString(send, packet.getLen(), print);
				System.out.println("                                                                             发送端发送：");
				System.out.println("                                                     "+print.getSrcAddrString() + " -------> " + print.getDesAddrString());
				System.out.println("                                                     CRC String : " + print.getFCSString());
				System.out.println("                                                     ACK number : " + print.getControlString());
				
				block.notifyMess();
				block.waitMess();
				
				reNum = in.read(buffer, 0, 1500);
				MakePacket.ByteToString(buffer, reNum, print);
				System.out.println("                                                                             发送端接收：");
				System.out.println("                                                     " + print.getDesAddrString() + " -------> " + print.getSrcAddrString());
				System.out.println("                                                     CRC String : " + print.getFCSString());
				System.out.println("                                                     ACK number : " + print.getControlString());
				ackNum = Integer.parseInt(print.getControlString());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
