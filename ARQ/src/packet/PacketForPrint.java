package packet;

public class PacketForPrint {

	private String desAddrString;
	private String srcAddrString;
	private String controlString;
	private String FCSString;
	private int len;

	public PacketForPrint() {
	}
	
	public PacketForPrint(String desAddrString, String srcAddrString,
			String controlString, String fCSString, int len) {
		super();
		this.desAddrString = desAddrString;
		this.srcAddrString = srcAddrString;
		this.controlString = controlString;
		FCSString = fCSString;
		this.len = len;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public String getDesAddrString() {
		return desAddrString;
	}

	public void setDesAddrString(String desAddrString) {
		this.desAddrString = desAddrString;
	}

	public String getSrcAddrString() {
		return srcAddrString;
	}

	public void setSrcAddrString(String srcAddrString) {
		this.srcAddrString = srcAddrString;
	}

	public String getControlString() {
		return controlString;
	}

	public void setControlString(String controlString) {
		this.controlString = controlString;
	}

	public String getFCSString() {
		return FCSString;
	}

	public void setFCSString(String fCSString) {
		FCSString = fCSString;
	}
}
