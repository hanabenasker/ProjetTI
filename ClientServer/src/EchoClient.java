
import java.net.Socket;
import java.io.*;

public class EchoClient {
	public static void main(String[] args) {
		try {
			System.out.println("Client Started...");
			Socket soc = new Socket("localhost",9806);
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));		
			System.out.println("Enter a student id");
			String stdID = userInput.readLine();
			PrintWriter out = new PrintWriter(soc.getOutputStream(),true);
			out.println(stdID);
			
			
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			String res=in.readLine();
			res=res.replace( "\\n", System.lineSeparator());
			System.out.println(res);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
