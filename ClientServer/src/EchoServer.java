import java.io.*;
import java.sql.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		try {
			System.out.println("Waiting for clients...");
			ServerSocket ss = new ServerSocket(9806);
			Socket soc = ss.accept();
			System.out.println("Connection established!");
			BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			String stdID = in.readLine();
			
			
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC","root","123456789hanabenasker");
			//create a statement
			Statement myStmt = myConn.createStatement();
			//execute a sql query
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM STUDENT WHERE ID="+stdID);
			//process the result set
			String res="";
			while(myRs.next()) {
				res="**Student Infos** \\nFull Name: "+ myRs.getString("firstname")+" "+myRs.getString("lastname")
				    +"\\nDate Of Birth: "+myRs.getString("birth_date")
				    +"\\nClass: "+myRs.getString("class")
				    +"\\nAddress: "+myRs.getString("address")
				    +"\\nPhone Number: "+myRs.getString("num_tel")
				    +"\\nEmail: "+myRs.getString("email");
			}
					
			PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
			out.println(res);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
