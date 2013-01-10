package inter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import run.Block;
import run.ReceiveEthernetPacket;
import run.SendEthernetPacket;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JFrame extends javax.swing.JFrame {
	private JLabel jLabelSend;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JButton jButtonStart;
	private JTextArea jTextAreaReceive;
	private JLabel jLabelReceive;
	private JTextArea jTextAreaSend;
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame inst = new JFrame();
				inst.setLocationRelativeTo(null);
				inst.setSize(300, 300);
				inst.setVisible(true);
			}
		});
	}
	
	public JFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS);
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jLabelSend = new JLabel();
				getContentPane().add(jLabelSend);
				jLabelSend.setText("Send:");
				jLabelSend.setPreferredSize(new java.awt.Dimension(318, 17));
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setPreferredSize(new java.awt.Dimension(345, 205));
				jScrollPane1.setEnabled(true);
				{
					jTextAreaSend = new JTextArea();
					jScrollPane1.setViewportView(jTextAreaSend);
				}
			}
			{
				jLabelReceive = new JLabel();
				getContentPane().add(jLabelReceive);
				jLabelReceive.setText("Receive:");
			}
			{
				jScrollPane2 = new JScrollPane();
				getContentPane().add(jScrollPane2);
				jScrollPane2.setPreferredSize(new java.awt.Dimension(345, 205));
				jScrollPane2.setEnabled(true);
				{
					jTextAreaReceive = new JTextArea();
					jScrollPane2.setViewportView(jTextAreaReceive);
					jTextAreaReceive.setEditable(false);
				}
			}
			{
				jButtonStart = new JButton();
				getContentPane().add(jButtonStart);
				jButtonStart.setText("Start");
				jButtonStart.setPreferredSize(new java.awt.Dimension(308, 24));
				jButtonStart.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButtonStartActionPerformed(evt);
					}
				});
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButtonStartActionPerformed(ActionEvent evt) {
		Block object = new Block();

		ReceiveEthernetPacket re = new ReceiveEthernetPacket(object, jTextAreaReceive);
		re.start();
		SendEthernetPacket se = new SendEthernetPacket(object);
		se.start();
		
//		System.out.println("jButtonStart.actionPerformed, event="+evt);
	}

}
