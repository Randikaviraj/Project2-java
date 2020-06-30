import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.*;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Panel implements ActionListener{
	private  JFrame frame;
	private  JPanel userPanel;

	private  JLabel usernamelabel;
	private 	JTextField usernamefield;
	private  JButton submitButtonname;

	private  JLabel usersymbollabel;
	private 	JTextField usersymbolfield;
	private  JButton submitButtonsymbol;

	private  JLabel userbidlabel;
	private 	JTextField userbidfield;
	private  JButton submitButtonbid;


	private  JButton exitButton;
	private  JButton joinButton;
	private  JPanel textPanel;
	private  JTextArea textarea;

	private Socket clientsocket;
	private InputStream clientin;
	private OutputStream clientout;
	private BufferedReader datain;
	private PrintStream dataout;

	private String serverlink;
	private int port;



	public Panel() {

		// Create a frame
		frame = new JFrame("Bid Client");
		

		
		// Create a panel container to display log information
		userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		userPanel.setBackground(Color.black);

		//user name
		usernamelabel = new JLabel();
		usernamelabel.setText("Enter your name");
		usernamelabel.setForeground(Color.WHITE);
		usernamelabel.setFont(new Font("Calibri",Font.PLAIN,20));
		usernamelabel.setEnabled(false);
		
		
		usernamefield = new JTextField(2);
		usernamefield.setFont(new Font("Calibri",Font.PLAIN,20));
		usernamefield.setPreferredSize(new Dimension(50, 20));
		usernamefield.setMaximumSize(new Dimension(1000, 20));
		usernamefield.setEnabled(false);

		submitButtonname = new JButton();
		submitButtonname.setText("Send Name");
		submitButtonname.setFont(new Font("Calibri",Font.BOLD,15));
		submitButtonname.addActionListener(this);
		submitButtonname.setEnabled(false);
		
		userPanel.add(usernamelabel);
		userPanel.add(usernamefield);
		userPanel.add(submitButtonname);
		

	
		

		//user symbol
		usersymbollabel = new JLabel();
		usersymbollabel.setText("Enter Symbol");
		usersymbollabel.setForeground(Color.WHITE);
		usersymbollabel.setFont(new Font("Calibri",Font.PLAIN,20));
		usersymbollabel.setEnabled(false);

		usersymbolfield = new JTextField(2);
		usersymbolfield.setFont(new Font("Calibri",Font.PLAIN,20));
		usersymbolfield.setPreferredSize(new Dimension(50, 20));
		usersymbolfield.setMaximumSize(new Dimension(1000, 20));
		usersymbolfield.setEnabled(false);
		
		
		submitButtonsymbol = new JButton();
		submitButtonsymbol.setText("Send Symbol");
		submitButtonsymbol.setFont(new Font("Calibri",Font.BOLD,15));
		submitButtonsymbol.addActionListener(this);
		submitButtonsymbol.setEnabled(false);

		userPanel.add(usersymbollabel);
		userPanel.add(usersymbolfield);
		userPanel.add(submitButtonsymbol);
		

		//user bid
		userbidlabel = new JLabel();
		userbidlabel.setText("Bid price");
		userbidlabel.setForeground(Color.WHITE);
		userbidlabel.setFont(new Font("Calibri",Font.PLAIN,20));
		userbidlabel.setEnabled(false);

		userbidfield = new JTextField(2);
		userbidfield.setFont(new Font("Calibri",Font.PLAIN,20));
		userbidfield.setPreferredSize(new Dimension(50, 20));
		userbidfield.setMaximumSize(new Dimension(300, 20));
		userbidfield.setEnabled(false);
		
		
		
		submitButtonbid = new JButton();
		submitButtonbid.setText("Bid");
		submitButtonbid.setFont(new Font("Calibri",Font.BOLD,15));
		submitButtonbid.addActionListener(this);
		submitButtonbid.setEnabled(false);

		userPanel.add(userbidlabel);
		userPanel.add(userbidfield);
		userPanel.add(submitButtonbid);
		
		
		
		joinButton = new JButton();
		joinButton.setText("CONNECT");
		joinButton.setFont(new Font("Calibri",Font.BOLD,15));
		joinButton.setBackground(Color.GREEN);
		joinButton.setLocation(300,400);
		joinButton.addActionListener(this);
		userPanel.add(joinButton);

		exitButton = new JButton();
		exitButton.setText("EXIT ");
		exitButton.setFont(new Font("Calibri",Font.BOLD,15));
		exitButton.setBackground(Color.RED);
		exitButton.setLocation(300,400);
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				System.exit(-1);
		
			}
		});
		userPanel.add(exitButton);

		

		
		
		//bid pannel
		


		//message test pannel
		textPanel = new JPanel();
		textarea = new JTextArea();
		textPanel.add(textarea);
		textarea.setFont(new Font("TimesRoman", Font.PLAIN, 14)); 
		textarea.setBackground(Color.black);
		textarea.setForeground(Color.white);
		textPanel.setBackground(Color.blue);

		// Border layout is the default layout for a frame
	    frame.getContentPane().add(BorderLayout.NORTH, textPanel);
	    frame.getContentPane().add(BorderLayout.CENTER, userPanel);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		// Set the panel size 
		frame.setPreferredSize(new Dimension(550,450));    
		// Size the frame
		frame.pack(); 
		frame.setLocationRelativeTo(null); 
		// Display the frame
		frame.setVisible(true);
	}

	public void setServerPort(String serverlink,int port){
		this.serverlink=serverlink;
		this.port=port;
	}


	public void actionPerformed(ActionEvent e) {

		String command=e.getActionCommand();
		

		try {

			if (command.equals("CONNECT")) {
				if (this.connectServer()) {
					this.textarea.setText(this.datain.readLine());
					this.usernamelabel.setEnabled(true);
					this.usernamefield.setEnabled(true);
					this.submitButtonname.setEnabled(true);
	
					this.joinButton.setText("DISCONNECT");
					this.joinButton.setBackground(Color.YELLOW);
					
				}else{
					this.textarea.setText("Connection error");
					
				}
				return;
				
			}

			if (command.equals("Bid")) {
				try {
					Float.parseFloat(this.userbidfield.getText());
				} catch (Exception err) {
					//TODO: handle exception
					this.textarea.setText("Error !..Enter a valid price");
					return;
				}
				this.dataout.println(this.userbidfield.getText());
				this.textarea.setText(this.datain.readLine());
				return;
			}
	
			if (command.equals("Send Name")) {
				String name=this.usernamefield.getText();
				if (name.isEmpty()) {
					this.textarea.setText("Security name cannt be empty");
					return;
				}
				this.dataout.println(name);
				this.usernamelabel.setEnabled(false);
				this.usernamefield.setEnabled(false);
				this.submitButtonname.setEnabled(false);
				this.textarea.setText(this.datain.readLine());
				this.usersymbollabel.setEnabled(true);
				this.usersymbolfield.setEnabled(true);
				this.submitButtonsymbol.setEnabled(true);

				return;
			}

			if (command.equals("Send Symbol")) {
				this.dataout.println(this.usersymbolfield.getText());
				float value=Float.parseFloat(this.datain.readLine());
				
				if (value > 0) {
					this.textarea.setText("Current bid on your symbol is "+value+" Lets bid on");
					this.usersymbollabel.setEnabled(false);
					this.usersymbolfield.setEnabled(false);
					this.submitButtonsymbol.setEnabled(false);
	
					this.userbidlabel.setEnabled(true);
					this.userbidfield.setEnabled(true);
					this.submitButtonbid.setEnabled(true);
				} else {
					this.textarea.setText("Your entered symbol is incorrect,enter correct one");
				}

				return;
			}

			if (command.equals("DISCONNECT")) {
				this.textarea.setText("Disconnected.......!!!!");

				this.usersymbollabel.setEnabled(false);
				this.usersymbolfield.setEnabled(false);
				this.submitButtonsymbol.setEnabled(false);

				this.usernamelabel.setEnabled(false);
				this.usernamefield.setEnabled(false);
				this.submitButtonname.setEnabled(false);

				this.userbidlabel.setEnabled(false);
				this.userbidfield.setEnabled(false);
				this.submitButtonbid.setEnabled(false);

				this.joinButton.setText("CONNECT");
				this.joinButton.setBackground(Color.GREEN);
				this.clientsocket.close();
			}
			
		} catch (Exception error) {
			this.textarea.setText("Error occured");

			this.usersymbollabel.setEnabled(false);
			this.usersymbolfield.setEnabled(false);
			this.submitButtonsymbol.setEnabled(false);

			this.usernamelabel.setEnabled(false);
			this.usernamefield.setEnabled(false);
			this.submitButtonname.setEnabled(false);

			this.userbidlabel.setEnabled(false);
			this.userbidfield.setEnabled(false);
			this.submitButtonbid.setEnabled(false);

			this.joinButton.setText("CONNECT");
			this.joinButton.setBackground(Color.GREEN);
			return;
		}	

	}

	private boolean connectServer(){
		try {
			this.clientsocket=new Socket(this.serverlink,this.port);

			this.clientin=this.clientsocket.getInputStream();
			this.clientout=this.clientsocket.getOutputStream();
			this.datain=new BufferedReader(new InputStreamReader(this.clientin));
			this.dataout=new PrintStream(this.clientout);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	
                
	}
	
}