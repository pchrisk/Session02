package cp125.chat;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The entry point into this week's chat program.
 */
public class Main {

	static public void main( String[] args ) {
		InputStream is = System.in;
		OutputStream os = System.out;
		
		ChatPeer main = new ChatPeer( is, os );

		/*
		  Build a User to represent the person running the program.
		  You need 3 attributes for that user.  Hint: derive the
		  user logged in and use that string for all three fields
		*/
		User u = null;
		
		String userName = System.getProperty("user.name");
		u = new User(userName, userName, "user");		

		boolean save = true;
		try {
			main.chatWith( u, save );
		} catch( IOException ioe ) {
			System.err.println( ioe );
		}

		try {
			os.close();
			is.close();
		} catch( IOException ioe ) {
		}
	}
}

// eof
