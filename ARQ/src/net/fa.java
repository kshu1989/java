package net;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

class Cl extends Frame implements ActionListener {
	TextArea ta = new TextArea(5, 10);
	TextField tf = new TextField(10);
	Socket c;
	double m;
	int i = 0;
	int q = 0;
	static int n;
	String ss = "";
	static char abc[];
	DataInputStream in = null;
	DataOutputStream out = null;
	String s;
	double m1;

	public Cl() {
		super("发送方");
		s = null;
		setSize(250, 250);
		tf.addActionListener(this);
		add("North", tf);
		add("Center", ta);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		show();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tf) {
			String sss = tf.getText();
			abc = sss.toCharArray();
			try {
				c = new Socket("127.0.0.1", 8888);
				in = new DataInputStream(c.getInputStream());
				out = new DataOutputStream(c.getOutputStream());
				while (true) {
					n = 0;
					m = Math.random() * 100;
					m1 = Math.random() * 100;
					if ((m1 >= 0) && (m1 < 50)) {
						q = 1;
					} else {
						q = 0;
					}
					ss = String.valueOf(abc[i]);
					String ss1 = String.valueOf(q);
					ss1 = ss1.concat(" ");
					ss1 = ss1.concat(ss);
					ta.append("发送:" + ss1);
					ta.append("\n");
					if (m > 20 && m <= 100) {
						out.writeUTF(ss1);
						ss1 = "";
					}
					System.out.println("启动超时计时器:");
					for (int zz = 0; zz < 3; zz++) {
						System.out.println(n++);
						Thread.sleep(1000);
						if (m > 20 && m <= 100) {
							break;

						}
					}
					if (n == 3) {
						ta.append("超时重发");
						ta.append("\n");
						continue;
					}
					while (true) {
						s = in.readUTF();
						if (s.equals("ack")) {
							ta.append("发送正确继续发" + "\n");
							i = i + 1;
							break;
						}
						if (s.equals("nak")) {
							ta.append("发送错误重新发" + "\n");
							break;
						}
					}
					if (i == abc.length) {
						break;
					}
				}
			} catch (InterruptedException e1) {
			} catch (IOException e2) {
			}
		}
	}
}

public class fa {
	public static void main(String args[]) {
		new Cl();
	}
}