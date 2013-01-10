package arpsoft;

import java.net.InetAddress;
import java.util.Date;
import java.io.*;

import jpcap.*;
import jpcap.packet.*;

public class ARPMonitor {
	
	private jpcap.JpcapCaptor cap;
	private jpcap.NetworkInterface device;
	
	public ARPMonitor() throws Exception {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		device = devices[1];
		cap = JpcapCaptor.openDevice(device, 2000, false, 5000);
	}
	
	public void Monitor(final String monitoring_addr) {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
			try {
				Date time = new Date();
				String fileStore = "statout"+time.getTime()+".txt";
				PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileStore)));
				while(true) {
					Packet recvPacket = cap.getPacket();
					if(recvPacket instanceof IPPacket) {
						IPPacket ipPacket = (IPPacket)recvPacket;
						if(ipPacket.src_ip.toString().compareTo(
							InetAddress.getByName(monitoring_addr).toString()) == 0)
							fileOut.println(ipPacket.src_ip + "  " + ipPacket.dst_ip + "  " + ipPacket.length);
						
						fileOut.flush();
						} 
				}
				}catch (Exception e) {System.out.println(e + "! Who cares!");}
			}
		});
		
		t.start();
		
	}
	
}
