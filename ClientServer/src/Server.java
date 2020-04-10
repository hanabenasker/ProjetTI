import java.awt.Color;


import java.awt.EventQueue;
import java.awt.Font;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;

public class Server extends JFrame {

	/**
	 * Declaring class attributes.
	 */
	
	private JPanel contentPane;
	JLabel statusLabel;
	ServerSocket ss;
	Socket soc;
	String stdID;
	

	Connection myConn;
	
	Statement myStmt;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Server frame = new Server();
					
					//Getting a database connection.
					frame.myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","123456789hanabenasker");
					
					//Create a statement.
					frame.myStmt = frame.myConn.createStatement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor
	public Server() {
		
		//Setting up the app icon.
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/unied_frame_icon.png")));
		setResizable(false);
		setTitle("UniEd Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		//Setting up the content pane.
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Setting up the head panel.
		JPanel headPanel = new JPanel();
		headPanel.setBackground(new Color(0, 139, 139));
		headPanel.setBounds(0, 0, 434, 39);
		contentPane.add(headPanel);
		headPanel.setLayout(null);
				
		//Setting the app icon label.	
		JLabel appIconLabel = new JLabel("");
		appIconLabel.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/unied_icon.png")).getImage()));
		appIconLabel.setBounds(0, 0, 46, 39);
		headPanel.add(appIconLabel);
		
		//Setting up the app name label.
		JLabel appNameLabel = new JLabel("UniEd");
		appNameLabel.setForeground(new Color(255, 250, 250));
		appNameLabel.setFont(new Font("Russo One", Font.PLAIN, 30));
		appNameLabel.setBounds(40, 0, 121, 39);
		headPanel.add(appNameLabel);
		
		//Setting up the side panel.
		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(new Color(0, 139, 139));
		sidePanel.setBounds(0, 39, 33, 222);
		contentPane.add(sidePanel);
		
		//Setting up the server status label.
		statusLabel = new JLabel("Server Down");
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusLabel.setFont(new Font("Samsung Sans", Font.PLAIN, 15));
		statusLabel.setBounds(43, 227, 381, 23);
		contentPane.add(statusLabel);
		
		//Setting up the server says label.
		JLabel serverSaysLabel = new JLabel("Server says");
		serverSaysLabel.setHorizontalAlignment(SwingConstants.CENTER);
		serverSaysLabel.setFont(new Font("Samsung Sans", Font.PLAIN, 13));
		serverSaysLabel.setForeground(new Color(0, 139, 139));
		serverSaysLabel.setBounds(43, 213, 381, 14);
		contentPane.add(serverSaysLabel);
		
		//Setting up the notification icon.
		JLabel notifIconLabel = new JLabel("");
		notifIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notifIconLabel.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/notif.png")).getImage()));
		notifIconLabel.setBounds(43, 177, 381, 39);
		contentPane.add(notifIconLabel);
		
		//Setting up the connect button.	
		JButton connectButton = new JButton("");
		connectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		connectButton.setOpaque(false);
		connectButton.setBounds(389, 2, 35, 35);
		connectButton.setBackground(new Color(0, 139, 139));
		connectButton.setHorizontalTextPosition(SwingConstants.CENTER);
		connectButton.setFont(new Font("Samsung Sans", Font.PLAIN, 16));
		connectButton.setForeground(new Color(255, 250, 250));
		headPanel.add(connectButton);		
		connectButton.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/unlock.png")).getImage()));
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					//Create a server socket.
					ss = new ServerSocket(9806);
					
					//Waiting for client to connect.
					soc = ss.accept();
					
					//Client connected!
					statusLabel.setText("Client connected, waiting for queries!");
					connectButton.setEnabled(false);
					connectButton.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/lock.png")).getImage()));
						
				}catch(Exception excep) {
					excep.printStackTrace();
				}
							
			}
		});
		
		//Setting up the button that will retrieve the student picture
		JButton picButton = new JButton("");
		picButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		picButton.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/image_query_icon.png")).getImage()));
		picButton.setBackground(new Color(0, 139, 139));
		picButton.setForeground(new Color(255, 250, 250));
		picButton.setFont(new Font("Samsung Sans", Font.PLAIN, 16));
		picButton.setEnabled(false);
		picButton.setBounds(235, 50, 106, 108);
		contentPane.add(picButton);
		
		//When picButton is clicked.
		picButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					//Reading through the socket input stream.
					BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
					stdID = in.readLine();
				
					//If the stream came from showPicButton in Client class
					if(stdID.contains("show")) {
						
						//Delete the "show" message from stdID.
						stdID=stdID.replaceAll("show", "");
																		
						//Execute a sql query.
						ResultSet myRs = myStmt.executeQuery("SELECT * FROM STUDENT WHERE ID="+stdID);
						
						byte[]img = null;
						if(myRs.next()){
							
							//Getting the student_pic column from the resultset
							img = myRs.getBytes("student_pic");
							
							picButton.setEnabled(false);
								
						}
						
						//Sending the result to the client through the output stream.
						OutputStream  out = new DataOutputStream(soc.getOutputStream());
						out.write(img);
						
						
					//If the action command came from the queryButton
					}else {
						
						//Send error message.
						PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
						out.println("Server side error");
					}
					
				}catch(Exception exception) {
					
				}
			}
		});
		
		//Setting up the button that will retrieve the student informations from the database.
		JButton queryButton = new JButton("");
		queryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		queryButton.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/database_icon.png")).getImage()));
		queryButton.setBackground(new Color(0, 139, 139));
		queryButton.setFont(new Font("Samsung Sans", Font.PLAIN, 16));
		queryButton.setForeground(new Color(255, 250, 250));
		queryButton.setBounds(125, 50, 106, 108);
		contentPane.add(queryButton);
		
		//When query button is clicked.
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					//Creating a buffred reader from the socket input stream.
					BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
					
					//Initializing the result string.
					String res="";
					
					//If there is data in the socket input stream.
					if(soc.getInputStream().available()>0) {
						
						//Reading the input.
						stdID = in.readLine();
						
						//If the data in the input stream comes from showPicButton.
						if(stdID.contains("show")) {
							statusLabel.setText("You already have the results, try to look for pics");
							
							//Sending error to the client through the output stream.
							byte[] err= {0};
							OutputStream  out = new DataOutputStream(soc.getOutputStream());
							out.write(err);
							
						//If the data comes from queryButton.
						}else {
							
							//Delete the "search" keyword.
							stdID=stdID.replaceAll("search", "");
							
							statusLabel.setText("Searching for student with ID="+stdID);
							
							
							
							//Execute a sql query.
							ResultSet myRs=null;
							
							myRs = myStmt.executeQuery("SELECT * FROM STUDENT WHERE ID="+stdID);
							
							
							//Process the result set.
							
							if(myRs.next()) {
								res= "Full Name: "+myRs.getString("firstname")+" "+myRs.getString("lastname")
								    +"\\nDate Of Birth: "+myRs.getString("birth_date")
								    +"\\nClass: "+myRs.getString("class")
								    +"\\nAddress: "+myRs.getString("address")
								    +"\\nPhone Number: "+myRs.getString("num_tel")
								    +"\\nEmail: "+myRs.getString("email");
								
								picButton.setEnabled(true);
								
								statusLabel.setText("Search for "+myRs.getString("firstname") + " completed successfully!");
							
							//There's 0 row with ID=stdID.
							}else {
								res="A student with such ID does not exist!";
								statusLabel.setText("A student with such ID does not exist!");
							}
							
							//Sending the result through an output stream
							PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
							out.println(res);		
							
						}
					
					//If there is no data in the socket input stream.	
					}else {
						statusLabel.setText("No queries recieved!");
					}
					
				}catch (NullPointerException e1) {
					statusLabel.setText("Please Open the server to accept queries!");
				
				}catch(Exception exe) {
					exe.printStackTrace();
				}
			}
		});
			
	}
}
