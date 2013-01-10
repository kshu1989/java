package packet;

import java.util.zip.CRC32;

public class MakePacket {

	public static void main(String[] args) {
		byte[] a = {(byte) 5, (byte) 0xe, (byte) 5, (byte) 0xe, (byte) 5, (byte) 0xe};
		System.out.println(byteToStringForMAC(a));
	}
	
	public static String checkSumRetureString(byte[] buffer, int len){
		len -= 8;
		byte[] tem = new byte[len];

		for (int i = 0; i < len; i++) {
			tem[i] = buffer[i + 8];
		}
		CRC32 c = new CRC32();
		c.update(tem);
		String re = Long.toHexString(c.getValue());
		return re;
	}
	

	public static byte[] checkSumRetureByte(byte[] buffer, int len) {

		len -= 8;
		byte[] tem = new byte[len];

		for (int i = 0; i < len; i++) {
			tem[i] = buffer[i + 8];
		}
		CRC32 c = new CRC32();
		c.update(tem);
		String re = Long.toHexString(c.getValue());
		while (re.length() < 8) {
			re += '\0';
		}

		byte[] temp4 = new byte[4];
		for (int i = 0, j = 0; i < re.getBytes().length; i += 2) {
			byte temp1 = (byte) re.charAt(i);
			if (temp1 > 96)
				temp1 = (byte) (temp1 - 'a' + 10);
			else
				temp1 -= '0';
			byte temp2 = (byte) re.charAt(i + 1);
			if (temp2 > 96)
				temp2 = (byte) (temp2 - 'a' + 10);
			else
				temp2 -= '0';

			temp1 = (byte) (temp1 << 4);
			temp4[i - j] = (byte) (temp1 | temp2);
			j++;
		}
		return temp4;
	}

	public static void ByteToString(byte[] buf, int len, PacketForPrint print) {
		if (len < 36)
			return ;

		byte[] desAddr = new byte[6];
		byte[] srcAddr = new byte[6];
		byte[] control = new byte[4];
		byte[] dataForFCS = new byte[len - 20]; // flag.len * 2 + FCS.len

		for (int i = 0; i < 6; i++) {
			desAddr[i] = buf[8 + i];
//System.out.println(desAddr[i] + "  ");
		}

		for (int i = 0; i < 6; i++) {
			srcAddr[i] = buf[14 + i];
		}

		for (int i = 0; i < 4; i++) {
			control[i] = buf[20 + i];
		}

		for (int i = 0; i < (len - 20); i++) {
			dataForFCS[i] = buf[i + 8];
		}

		
		String desAddrString = null;
		String srcAddrString = null;
		String controlString = null;
		String FCSString = null;
		desAddrString = byteToStringForMAC(desAddr);
		srcAddrString = byteToStringForMAC(srcAddr);
		controlString = Integer.toString(byteToInt2(control));
		FCSString = MakePacket.checkSumRetureString(dataForFCS, len - 20);
		
		print.setDesAddrString(desAddrString);
		print.setSrcAddrString(srcAddrString);
		print.setFCSString(FCSString);
		print.setControlString(controlString);
		
//		System.out.println("desAddrString: " + desAddrString);
//		System.out.println("srcAddrString: " + srcAddrString);
//		System.out.println("controlString: " + controlString);
//		System.out.println("FCSString: " + FCSString);
	}

	public static EthernetPacket getEthernetPacket(int serialNum) {

		EthernetPacket packet = new EthernetPacket();
		byte[] flag = { (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa,
				(byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xab };
		byte[] control = intToByteArray(serialNum);
		byte[] data = {};

		packet.setFlag(flag);
		packet.setDesAddr(stringToByteForMAC("00-22-15-5b-e1-58"));
		packet.setSorAddr(stringToByteForMAC("00-32-34-11-ee-f3"));
		packet.setControl(control);
		packet.setData(flag);
		return packet;
	}

	/* byte[4] to int */

	public static int byteToInt2(byte[] b) {

		int mask = 0xff;
		int temp = 0;
		int n = 0;
		for (int i = 0; i < 4; i++) {
			n <<= 8;
			temp = b[i] & mask;
			n |= temp;
		}
		return n;
	}

	/*
	 * int to byte[] method 1
	 */
	private static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	/*
	 * int to byte method 2
	 */
	// public static byte[] intToByteArray2(int i) throws Exception {
	// ByteArrayOutputStream buf = new ByteArrayOutputStream();
	// DataOutputStream out = new DataOutputStream(buf);
	// out.writeInt(i);
	// byte[] b = buf.toByteArray();
	// out.close();
	// buf.close();
	// return b;
	// }
	// }

	
	
	// String mac = "00-22-15-5b-e1-58";
	public static byte[] stringToByteForMAC(String macAdd) {

		String m[] = macAdd.split("-");
		byte mm[] = new byte[6];
		int j = 0;
		for (String a : m) {
			char b = a.charAt(0);
			char c = a.charAt(1);
			byte d = (byte) ((byte) b - 48);
			byte e = (byte) ((byte) c - 48);
			if (d > 9)
				d = (byte) (d - 39);
			if (e > 9)
				e = (byte) (e - 39);

			d = (byte) (d << 4);
			byte f = (byte) (d + e);
			mm[j++] = f;
		}
		return mm;
	}
	
	public static String byteToStringForMAC(byte[] buf) {
		String value = "";
		for (int i = 0; i < buf.length; i++) {
			String sTemp = Integer.toHexString(0xFF & buf[i]);
			value = value + sTemp + ":";
		}
		value = value.substring(0, value.lastIndexOf(":"));
		return value;
	}
	
//	public static String byteToStringForMAC(byte[] buf){
//		byte head = -1;
//		byte tail = -1;
//		StringBuffer res = new StringBuffer();
//		
//		for(int i = 0; i < 6; i ++){
//			head = (byte) (buf[i] & 0xf0);
//			tail = (byte) (buf[i] & 0x0f);
//			head = (byte) (head >> 4);
//			
//			if(head > 9){
//				head += 87;
//				res.append((char)head);
//			}else{
//				res.append(head);
//			}
//			if(tail > 9){
//				tail += 87;
//				res.append((char)tail);
//			}else{
//				res.append(tail);
//			}
//			if(i == 5)
//				break;
//			res.append('-');
//		}
//		
//		return res.toString();
//	}
}
