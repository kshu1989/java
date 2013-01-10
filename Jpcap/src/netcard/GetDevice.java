package netcard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class GetDevice {

	
	
	public static void main(String[] args) {

		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		NetworkInterface device = null;

		int j = 0;
		String str = "";
		System.out.println("> Choose the NIC you want to use: ");

		for (NetworkInterface a : devices) {
			System.out.print(j++ + " : MAC address : "
					+ byteToStringForMAC(a.mac_address));
			for (NetworkInterfaceAddress b : a.addresses) {
				if (b.address instanceof Inet4Address
						&& !b.address.toString().equalsIgnoreCase("/0.0.0.0"))
					System.out.println("  IPv4 address: " + b.address);
			}
			System.out.println();
		}

		try {
			str = new BufferedReader(new InputStreamReader(System.in))
					.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		j = Integer.parseInt(str);

		device = devices[j];

		if (device == null)
			throw new IllegalArgumentException(" is not a local address");
		// �ñ���IP��ַ��MAC��ַ

		try {
			JpcapCaptor capture = JpcapCaptor.openDevice(device, 2147483647,
					true, 20);

			while (true) {
				Packet packet = capture.getPacket();
				
				if (packet != null && packet instanceof UDPPacket) {
//					analysisTCPPacket((TCPPacket) packet);
					packet = (UDPPacket) packet;
					System.out.println(packet);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// mac��ַbyte[]���String
	public static String byteToStringForMAC(byte[] buf) {
		String value = "";
		for (int i = 0; i < buf.length; i++) {
			String sTemp = Integer.toHexString(0xFF & buf[i]);
			value = value + sTemp + ":";
		}
		value = value.substring(0, value.lastIndexOf(":"));
		return value;
	}

	public static void analysisTCPPacket(TCPPacket tcpPacket) {
		// System.out.println(packet);

		EthernetPacket ethernetPacket = (EthernetPacket) tcpPacket.datalink;
		System.out.println("ԴIP: " + tcpPacket.src_ip + " Ŀ��IP:"
				+ tcpPacket.dst_ip + " ���Ͷ˿�: " + tcpPacket.src_port + " ���ն˿�: "
				+ tcpPacket.dst_port);
		System.out.println("Դ MAC: " + ethernetPacket.getSourceAddress()
				+ " Ŀ�� MAC: " + ethernetPacket.getDestinationAddress());
		System.out.println("Э��: " + tcpPacket.protocol);
		System.out.println("����: ");
		for (int i = 0; i < tcpPacket.data.length; i++) {
			System.out.print((char) tcpPacket.data[i]);
		}

	}
}
