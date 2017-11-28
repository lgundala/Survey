import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

class Survey_Upload extends JFrame implements ActionListener {

    JFileChooser fc;
    JButton b, b1,b2;
    JTextField tf, tf1,tf2;
    FileInputStream in;
    Socket s;
    DataOutputStream dout;
    DataInputStream din;
    int i;
    String absolutePath;
    Survey_Upload() {
    	super("client");
        tf = new JTextField();
        tf.setBounds(20, 50, 190, 30);
        add(tf);
        b = new JButton("Student responses");
        b.setBounds(225, 50, 180, 30);
        add(b);
        tf1 = new JTextField();
        tf1.setBounds(20, 100, 190, 30);
        add(tf1);
        b.addActionListener(this);
        b1 = new JButton("Instructor responses");
        b1.setBounds(225, 100, 180, 30);
        add(b1);
        b1.addActionListener(this);
        tf2 = new JTextField();
        tf2.setBounds(20, 150, 190, 30);
        add(tf2);
        b2 = new JButton("student survey link");
        b2.setBounds(225, 150, 180, 30);
        add(b2);
        b2.addActionListener(this);
        fc = new JFileChooser();
        setLayout(null);
        setSize(420, 300);
        setVisible(true);
       
        try {
            s = new Socket("localhost", 10);
            dout = new DataOutputStream(s.getOutputStream());
            din = new DataInputStream(s.getInputStream());
            send();
        } catch (Exception e) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
        	String buttonid;
            if (e.getSource() == b) {
            	buttonid="student";
                int x = fc.showOpenDialog(null);
                if (x == JFileChooser.APPROVE_OPTION) {
                    copy(buttonid);
                }
            }
            if (e.getSource() == b1) {
            	buttonid="instructor";
            	 int x = fc.showOpenDialog(null);
                 if (x == JFileChooser.APPROVE_OPTION) {
                	 copy(buttonid);
                 }
            }
            if (e.getSource() == b2) {
            	buttonid="survey link";
            	 int x = fc.showOpenDialog(null);
                 if (x == JFileChooser.APPROVE_OPTION) {
                	 copy(buttonid);
                 }
            }
        } catch (Exception ex) {
        }
    }
    public void copy(String buttonid) throws IOException, SQLException {
    	if(buttonid.equals("student")){
            File f1 = fc.getSelectedFile();
            tf.setText(f1.getAbsolutePath());
            DBConnect2.getSurveyDetails(f1.getAbsolutePath(), buttonid);
            absolutePath = f1.getAbsolutePath();
         
        	}
        	if(buttonid.equals("instructor")){
                File f1 = fc.getSelectedFile();
                tf1.setText(f1.getAbsolutePath());
                DBConnect2.getSurveyDetails(f1.getAbsolutePath(), buttonid);
                absolutePath = f1.getAbsolutePath();
            }
        	if(buttonid.equals("survey link")){
                File f1 = fc.getSelectedFile();
                tf2.setText(f1.getAbsolutePath());
                DBConnect2.getSurveyDetails(f1.getAbsolutePath(), buttonid);
                absolutePath = f1.getAbsolutePath();
            }

    }

    public void send() throws IOException {
        dout.write(i);
        dout.flush();
        System.out.println("absolutepath" +absolutePath);

    }

    public static void main(String... d) {
        new Survey_Upload();
    }
}