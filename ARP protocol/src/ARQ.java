import java.awt.*;
import java.awt.event.*;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import javax.swing.text.Document;
import java.io.FileWriter;
import java.io.IOException;

public class ARQ extends JFrame {
	private static final long serialVersionUID = -5437589209629747957L;

	private int NUM;
	private Time T;
	private JTextField[] status;
	private JButton b;
	private JRadioButton[] r;
	DefaultTableModel m_data;
	JTable m_view;
	FileWriter f1;
	FileWriter f2;
	FileWriter f3;

	public ARQ() throws IOException
   {
  	 super("ARQ");
  	 NUM=0;
  	 Container c=getContentPane();
  	 c.setLayout(new FlowLayout()); 
  	  	 JPanel  []P=new JPanel[2];
  	   	 P[0]=new JPanel();
  	 P[0].setLayout(new BoxLayout(P[0],BoxLayout.X_AXIS));
  	 c.add(P[0]);
  	 P[1]=new JPanel();
  	 c.add(P[1]);
  	 JPanel  []Q=new JPanel[2]; 
 	 Q[0]=new JPanel();
 	 Q[0].setLayout(new BoxLayout(Q[0],BoxLayout.Y_AXIS));
 	 P[0].add(Q[0]);
 	 Q[1]=new JPanel();
 	 Q[1].setLayout(new BoxLayout(Q[1],BoxLayout.Y_AXIS));
 	 P[0].add(Q[1]);
 	 JPanel  []R=new JPanel[4]; 
	 R[0]=new JPanel();
	 R[0].setLayout(new FlowLayout(FlowLayout.LEFT));
	 Q[0].add(R[0]);
	 R[1]=new JPanel();
	 R[1].setLayout(new FlowLayout(FlowLayout.LEFT));
	 Q[0].add(R[1]);
	 R[2]=new JPanel();
	 R[2].setLayout(new FlowLayout(FlowLayout.LEFT));
	 Q[0].add(R[2]);
	 R[3]=new JPanel();
	 R[3].setLayout(new FlowLayout(FlowLayout.CENTER));
	 Q[0].add(R[3]);
	 //////////////////////////////�ļ�
	 f1=new  FileWriter("f1.txt",true);
	 f2=new FileWriter("f2.txt",true);
	 f3=new FileWriter("f3.txt",true);
	 
  	 //////���ݷ���״̬
       JTextField []s={new JTextField("������",7), new JTextField("�ط�",7) ,
    		              new JTextField("����ʱ",4), new JTextField("00:00:10",10),
    		              new JTextField("����",4), new JTextField("",15) };    //���ݷ���ס״̬
       status=s;
           
     status[0].setEditable(false);
     status[1].setEditable(false);
     status[2].setEditable(false);
     status[4].setEditable(false);
     status[1].setEnabled(false);
       JLabel l=new JLabel("״̬�� ");
     R[0].add(l);
     R[0].add(status[0]);
       R[1].add(status[2]);
     R[1].add(status[3]);
     R[2].add(status[4]);
     R[2].add(status[5]);
     b=new JButton("����");
     b.setEnabled(false);
     R[3].add(b);
       JLabel l1=new JLabel("ѡ�����");
     Q[1].add(l1);
     
     ///// ѡ�����
     
     JRadioButton []r1={new JRadioButton("ACK"),
    		           new JRadioButton("NAK"),
    		           new JRadioButton("������"),
    		           new JRadioButton("��ʼ")
                                                };
     r=r1;
     ButtonGroup rg=new ButtonGroup();
     for(int i=0;i<r.length-1;i++)
     {
    	 Q[1].add(r[i]);
    	 rg.add(r[i]);    	 
     }
     rg.add(r[3]); 
//////////////////////////////////////////////////////////////////////////////////////////////
     /////////�����շ�ס״̬�б�
      m_data=new DefaultTableModel();
     m_view=new JTable(m_data);
     m_view.setPreferredScrollableViewportSize(new Dimension(300,230));  ////����С
     m_view.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
   
     JScrollPane sPane=new JScrollPane(m_view);
     P[1].add(sPane);
         T=new Time(status[3]);
     ///���Ͳ���
     b.addActionListener(new ActionListener()
     {
		public void actionPerformed(ActionEvent e) {
		
		T.time_start();
	    newtore();
		status[0].setText("������");
		r[3].setSelected(true);
				}
	     }
         );
  
 ///////////////////////// ʱ����ʾ ����
     Document dt = status[3].getDocument();
     dt.addDocumentListener(new  DocumentListener(){
             	 public void changedUpdate(DocumentEvent e)   {
       	 
        };
             public void insertUpdate(DocumentEvent e)   {
             if(status[3].getText().toString().equals("00:00:00"))
             {
            	 T.time_restart();
            	 newtore();
            	 String s=status[5].getText().toString();
         	      mb_addRow(s, "��ʱ","�ط�",""); 
         	               }
                    }
         public void removeUpdate(DocumentEvent e)   {
                    };
                });
   
 //////////////////////�������    
     Document dt1 = status[5].getDocument();
     dt1.addDocumentListener(new  DocumentListener(){
                  public void changedUpdate(DocumentEvent e)   {
               }
                   public void insertUpdate(DocumentEvent e)   {
        	        	  if(!status[5].getText().toString().equals(""))
                   		  b.setEnabled(true);  
                       };
          public void removeUpdate(DocumentEvent e)   {
        	  if(status[5].getText().toString().equals(""))
                          	 b.setEnabled(false);
                  };
          });
   //////////////////////////��ť����
         r[0].addActionListener(new ActionListener()
     {
			public void actionPerformed(ActionEvent e) {
				T.time_stop();
	    retonew();
	    status[5].setText(""); 
	    status[5].requestFocus();  
					}
		     }
        );
        r[0].addActionListener(new ActionListener()
     {
		public void actionPerformed(ActionEvent e) {
		    String s=status[5].getText().toString();
			NUM++;
    	    mb_addRow(s, "ACK","����   "+NUM,s);    //////NUM+""   intװ��Ϊstring 
   		}
		
     }
       );
  
     r[1].addActionListener(new ActionListener()
     {
		public void actionPerformed(ActionEvent e) {
			T.time_restart();
			newtore();
	}
     }
        );
     r[1].addActionListener(new ActionListener()
     {
		public void actionPerformed(ActionEvent e) {
		    String s=status[5].getText().toString();
    	    mb_addRow(s, "NCK","�ط�","");       //////NUM+""   intװ��Ϊstring 
		     }
       );
  
     r[2].addActionListener(new ActionListener()
     {
		public void actionPerformed(ActionEvent e) {
		
			T.time_restart();
			newtore();
		}
     }
        );
     r[2].addActionListener(new ActionListener()
     {
public void actionPerformed(ActionEvent e) {
		    String s=status[5].getText().toString();
		    mb_addRow(s, "������","�ط�","");   //////NUM+""   intװ��Ϊstring 
		}
     } 
     );
  ///////////////////////////////////
     //////��ȡ����
     this.addWindowListener(new java.awt.event.WindowAdapter() 
     {  
    	 public void windowActivated(java.awt.event.WindowEvent e) {  
    	status[5].requestFocusInWindow(); 
    	
    	mb_addColumn();
    	}
     }
   ); 
    /////////////////�ر��ļ� 
     this.addWindowListener(new WindowAdapter()
     {
    	 public void windowClosing(WindowEvent e) {  
    	    	try {
					f1.close();
					f2.close();
	    	    	f3.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    	    	}
    	     }
    	   ); 
 }
//////�ط�״̬
public  void   newtore()
{
	status[0].setText("�ط�");
	b.setEnabled(false);
	r[0].setEnabled(true);
	r[1].setEnabled(true);
	r[2].setEnabled(true);
|}
/////����Ϣ״̬
public  void   retonew()
{
	status[0].setText("������");
	b.setEnabled(true);
	r[0].setEnabled(false);
	r[1].setEnabled(false);
	r[2].setEnabled(false);
		
}
////////////////////////���һ��
public void mb_addRow(String string,String string2,String string3,String string4)
{
	int t=m_view.getRowCount();
	if(t<0)
		t=0;
	String[] vs ={string,string2,string3,string4};
	m_data.insertRow(t, vs);
	try {
		 f1.write(string+"\r\n");
		 f2.write(string2+"\r\n");
 	     f3.write(string4+"\r\n");
	} catch (IOException e1) {
		e1.printStackTrace();
	}
}
public void mb_addColumn()
{
	m_data.addColumn("��������");
	m_data.addColumn("����");
	m_data.addColumn("���� ");
	m_data.addColumn("��������");
	
}
//////////////////��ȡ��
public void getfocus(){
{
	status[5].requestFocus();////�������ҳ����غ��ȡ
}

  public static void main(String[] args) throws IOException 
 { 
    ARQ app=new ARQ();
	app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	app.setSize(500,450);
	app.setVisible(true); 
	app.setResizable(false);

 }
   }
}
