package packet;


public class EthernetPacket {

	private byte[] flag;
	private byte[] desAddr;
	private byte[] sorAddr;
	private byte[] control;
	private byte[] data;
	private byte[] FCS;

	private int len;
	private String CRCSting;
	// public static int unsignedByteToInt(byte b) {
	// return (int) b & 0xFF;
	// }

	public String getCRCSting() {
		return CRCSting;
	}

	public void setCRCSting(String cRCSting) {
		CRCSting = cRCSting;
	}

	public EthernetPacket() {

	}

	public EthernetPacket(byte[] flag, byte[] desAddr, byte[] sorAddr) {
		this.flag = flag;
		this.desAddr = desAddr;
		this.sorAddr = sorAddr;
	}

	public byte[] toByte() {
		byte[] all = new byte[1500];
		int j = 0;
		int i = 0;

		for (; i < flag.length; i++) {
			all[i] = flag[i];
		}
		j += i;
		for (i = 0; i < desAddr.length; i++) {
			all[j + i] = desAddr[i];
		}
		j += i;
		for (i = 0; i < sorAddr.length; i++) {
			all[j + i] = sorAddr[i];
		}
		j += i;
		for (i = 0; i < control.length; i++) {
			all[j + i] = control[i];
		}
		j += i;
		for (i = 0; i < data.length; i++) {
			all[j + i] = data[i];
		}
		j += i;
		FCS = MakePacket.checkSumRetureByte(all, j);
		this.CRCSting = MakePacket.checkSumRetureString(all, j);
		for (i = 0; i < FCS.length; i++) {
			all[j + i] = FCS[i];
		}
		j += i;
		for (i = 0; i < flag.length; i++) {
			all[j + i] = flag[i];
		}
		j += i;

		this.len = j;
		return all;
	}

	public byte[] getFlag() {
		return flag;
	}

	public void setFlag(byte[] flag) {
		this.flag = flag;
	}

	public byte[] getDesAddr() {
		return desAddr;
	}

	public void setDesAddr(byte[] desAddr) {
		this.desAddr = desAddr;
	}

	public byte[] getSorAddr() {
		return sorAddr;
	}

	public void setSorAddr(byte[] sorAddr) {
		this.sorAddr = sorAddr;
	}

	public byte[] getControl() {
		return control;
	}

	public void setControl(byte[] control) {
		this.control = control;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}
}
