package cp125.chat;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
