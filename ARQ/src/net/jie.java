package net;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

class Se extends Frame {
	TextField tf = new TextField(10);
	TextArea ta = new TextArea(5, 10);
	Socket c;

	public Se() {
		super("接收方");
		setSize(250, 250);
		ServerSocket sever = null;
		Socket you = null;
		String s = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		double m1, m2;
		String s1, s2, s3;
		s1 = "";
		s2 = "";
		s3 = "";
		int i = 0;
		int q = 0;
		int n;
		add("North", tf);
		add("Center", ta);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		show();
		try {
			sever = new ServerSocket(8888);
			you = sever.accept();

			in = new DataInputStream(you.getInputStream());
			out = new DataOutputStream(you.getOutputStream());
			while (true) {
				s = in.readUTF();
				m1 = Math.random() * 100;
				m2 = Math.random() * 100;
				if (m2 > 0 && m2 < 50) {
					q = 1;
				} else {
					q = 0;
				}
				char a[] = s.toCharArray();
				if ((m1 > 20 && m1 <= 100) && ((a[0] - '0') == q)) {
					ta.append("收到:" + s);
					ta.append("\n");
					s2 = a[2] + "";
					s3 = s3.concat(s2);
					tf.setText(s3);
					out.writeUTF("ack");
				}
				if (m1 >= 0 && m1 <= 20) {
					ta.append("CRC检测错误!");
					ta.append("\n");
					out.writeUTF("nak");
				} else if ((a[0] - '0') != q) {
					ta.append("序号不对应丢弃帧");
					ta.append("\n");
					out.writeUTF("nak");
				}

			}
		} catch (IOException e1) {
		}

	}
}

public class jie {
	public static void main(String args[]) {
		new Se();
	}
}