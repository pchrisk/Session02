package cp125.chat;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * The ChatPeer is one 'user' in a 2-user conversation.  It is
 * actually a program (class) which mimics a real user, by 'saying'
 * things back to another User.
 */
 
public class ChatPeer {

	public ChatPeer( InputStream input, OutputStream output ) {
		this.input = input;
		this.output = output;
	}

	public void chatWith( User u, boolean save ) throws IOException {
		User u2 = new User("program", "program", "comp");
		if (save = true) {
			long starttime = System.currentTimeMillis();
			
			Date startTimeDate = new Date(starttime);
			System.out.println(starttime + "\t" + startTimeDate.toString());
			Conversation c = new Conversation(u, u2, startTimeDate);
		}
		
			
		
		InputStreamReader filter1 = new InputStreamReader(input);
		LineNumberReader filter2 = new LineNumberReader(filter1);
		String line; 
		System.out.println("Hello " + u.getFirstName() + ". What is on your mind?");
		
		
		try {
			System.out.println("before loop");
//			line = filter2.readLine();
//			System.out.println(line);
			boolean cond_exit = false;
//			(line = filter2.readLine()).equalsIgnoreCase("quit")
			while ((line = filter2.readLine()) != null) {
				if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
					break;
				}
				System.out.println("in loop");
				System.out.println(getReply(line));
			}
		} catch( IOException ioe ) {
		// recover
		}
	}

	/*
	  Feel free to change this method, perhaps so it can reply with some
	  random replies??  Perhaps define an array of canned replies and
	  use a random number to sample from that array??
	*/
	private String getReply( String userSaid ) {
		return "Really? You think " + userSaid;
	}
	
	private final InputStream input;
	private final OutputStream output;

}

// eof
