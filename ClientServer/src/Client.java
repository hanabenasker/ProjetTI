import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class Client extends JFrame {

	/**
	 * Declaring class attributes.
	 */
	
	private JPanel contentPane;
	private JTextField idTextField;
	JTextArea infosTextArea;
	Socket soc;
	String stdID;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor.
	public Client() {
		
		//Setting up the app icon.
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/unied_frame_icon.png")));
		setResizable(false);
		setTitle("UniEd Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		//Setting up the content pane.
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Setting up the side panel.
		JPanel sidePanel = new JPanel();
		sidePanel.setBounds(0, 39, 33, 222);
		sidePanel.setBackground(new Color(0, 139, 139));
		contentPane.add(sidePanel);
		
		//Setting up the head panel.
		JPanel headPanel = new JPanel();
		headPanel.setBounds(0, 0, 434, 39);
		headPanel.setBackground(new Color(0, 139, 139));
		contentPane.add(headPanel);
		headPanel.setLayout(null);
		
		//Setting up the informations text area.
		infosTextArea = new JTextArea();
		infosTextArea.setText("Hello!");
		infosTextArea.setFont(new Font("Samsung Sans", Font.PLAIN, 13));
		infosTextArea.setEditable(false);
		infosTextArea.setBounds(43, 126, 249, 124);
		contentPane.add(infosTextArea);
		
		//Setting the app icon label.	
		JLabel appIconLabel = new JLabel("");
		appIconLabel.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/unied_icon.png")).getImage()));
		appIconLabel.setBounds(0, 0, 36, 40);
		headPanel.add(appIconLabel);
		
		//Setting up the app name label.
		JLabel appNameLabel = new JLabel("UniEd");
		appNameLabel.setFont(new Font("Russo One", Font.PLAIN, 30));
		appNameLabel.setForeground(new Color(255, 250, 250));
		appNameLabel.setBounds(40, 0, 106, 40);
		headPanel.add(appNameLabel);
		
		//Setting up the input panel.
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(43, 50, 381, 37);
		contentPane.add(inputPanel);
		inputPanel.setLayout(null);
		
		//Setting up the enter label.
		JLabel enterLabel = new JLabel("Enter a student ID:");
		enterLabel.setForeground(new Color(0, 139, 139));
		enterLabel.setFont(new Font("Samsung Sans", Font.PLAIN, 13));
		enterLabel.setBounds(27, 0, 111, 37);
		inputPanel.add(enterLabel);
		
		//Setting up the id text field.
		idTextField = new JTextField();
		idTextField.setBounds(147, 8, 95, 22);
		inputPanel.add(idTextField);
		idTextField.setColumns(10);
		
		//Setting up the student picture label.
		JLabel imgLabel = new JLabel("");
		imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imgLabel.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/no_pic_icon.png")).getImage()));
		imgLabel.setBounds(305, 105, 119, 119);
		contentPane.add(imgLabel);
		
		//Setting up the error label.
		JLabel errLabel = new JLabel("");
		errLabel.setFont(new Font("Tahoma", Font.PLAIN, 8));
		errLabel.setToolTipText("");
		errLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errLabel.setForeground(new Color(255, 0, 0));
		errLabel.setBounds(302, 227, 122, 23);
		contentPane.add(errLabel);
		
		//Setting up the split panel.
		JPanel splitPanel = new JPanel();
		splitPanel.setForeground(new Color(0, 139, 139));
		splitPanel.setBackground(new Color(0, 139, 139));
		splitPanel.setBounds(300, 98, 1, 152);
		contentPane.add(splitPanel);
		
		//Setting up the title label.
		JLabel titleLabel = new JLabel("Student informations");
		titleLabel.setForeground(new Color(0, 139, 139));
		titleLabel.setFont(new Font("Samsung Sans", Font.BOLD, 14));
		titleLabel.setBounds(43, 98, 169, 23);
		contentPane.add(titleLabel);
	
		
		//Setting up the connect button.
		JButton connectButton = new JButton("");
		connectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		connectButton.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/connect_icon.png")).getImage()));
		connectButton.setBackground(new Color(0, 139, 139));
		connectButton.setForeground(new Color(255, 250, 250));
		connectButton.setFont(new Font("Samsung Sans", Font.PLAIN, 16));
		connectButton.setBounds(380, 0, 44, 40);
		headPanel.add(connectButton);
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					//Creating a socket.
					soc = new Socket("localhost",9806);
					
					infosTextArea.setText("Connected to the server!");
					connectButton.setEnabled(false);
				} catch (UnknownHostException e3) {
					e3.printStackTrace();
				} catch (IOException e3) {
					infosTextArea.setText("Server Down, try later!");
				}
				
			}
		});
	
		//Setting up the show picture button
		JButton showPicButton = new JButton("");
		showPicButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showPicButton.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/image_query_icon.png")).getImage()));
		showPicButton.setBounds(293, 1, 35, 35);
		inputPanel.add(showPicButton);
		showPicButton.setForeground(new Color(255, 250, 250));
		showPicButton.setBackground(new Color(0, 139, 139));
		showPicButton.setEnabled(false);
		showPicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					//Printing the id on the socket output stream. 
					PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
					out.println(stdID+"show");
					
					//Reading from the socket input stream. 
					InputStream  in = new DataInputStream(soc.getInputStream()); 
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte buffer[] = new byte[100000000];
					baos.write(buffer, 0 , in.read(buffer));
					byte img[] = baos.toByteArray();
					
					//If the server sends an image.
					if(img.length>1) {
						showPicButton.setEnabled(false);
						errLabel.setText("");
						ImageIcon image = new ImageIcon(img);
	                    Image im = image.getImage();
	                    Image myImg = im.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(),Image.SCALE_SMOOTH);
	                    ImageIcon newImage = new ImageIcon(myImg);
	                    imgLabel.setIcon(newImage);
	                    
	                //If the server sends an error.   
					}else {
						errLabel.setText("A server side error occurred, try again!");
					}	
					
				}catch(Exception eee) {
					eee.printStackTrace();
				}
			
			}
		});
		
		//Setting up the search button
		JButton searchButton = new JButton("");
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBackground(new Color(0, 139, 139));
		searchButton.setForeground(new Color(0, 139, 139));
		searchButton.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/search.png")).getImage()));
		searchButton.setBounds(252, 1, 35, 35);
		inputPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Action initialization.
				showPicButton.setEnabled(false);
				imgLabel.setIcon(new ImageIcon( new ImageIcon(this.getClass().getResource("/no_pic_icon.png")).getImage()));
				stdID = idTextField.getText();
				
				//If the text area is empty.
				if(stdID.isEmpty()) {
					infosTextArea.setText("Please enter a student ID!");
					
				//If the the text area contains other than numbers
				}else if(!(stdID.matches("[0-9]+"))) {
					infosTextArea.setText("Please enter a valid student ID!");

				//If the user input is correct.
				}else {
					
					try {
						
						//Printing the id on the socket output stream.
						PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
						out.println(stdID+"search");
						
						//Reading the result from the input stream.
						BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
						String res=in.readLine();
						
						res=res.replace( "\\n", System.lineSeparator());
						
						//If the student informations are ready.
						if(res.contains("\n")) {
							showPicButton.setEnabled(true);
							infosTextArea.setText(res);
							
						//If the student was not found.
						}else if(res.contains("A student with such ID does not exist!")) {
							infosTextArea.setText(res);
							
						//If an error happened.	
						}else {
							infosTextArea.setText("A server side error occurred, try again!");
						}
						                
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch ( NullPointerException e2) {
						infosTextArea.setText("You have to connect to the server!");
					}
				
				}

			}
		});
	
	}
}
