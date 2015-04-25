package cp125.chat;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * The entry point into this week's chat conversation analysis
 * program.
 *
 * Some prior conversations have been saved to disk, each in a '.cnv'
 * file, all in a single directory D.  To complicate things, some .cnv
 * file are bogus (empty), and have to be discarded.  Also, there are
 * some non-cnv files in D.  The goal of the assignment is to load all
 * the conversations and locate the one with the longest duration.

 * Recall that each User is a triple: first name, last name, id.  In
 * the recorded conversation files, only the ids are recorded (acting
 * like primary keys in a relational database).  To locate the full
 * User given just an id, we thus supply a canned database, see class
 * Users.
 
 * The task is to complete the implementations for
 *
 * 1 Conversation.load
 *
 * 2 ConversationsReader.loadAll
 *
 * and finally
 *
 * 3 print out details of the longest conversation.  User names suffice
 *
 * @see Conversation.getDuration
 * @see Users
 */
public class Main {

	static public void main( String[] args ) {

		File dir = new File( "data" );

		try {
			Conversation c = ConversationsReader.loadAll( dir, Users.ALL );
			if( c != null ) {
				User u1 = c.getUser1();
				User u2 = c.getUser2();
				System.out.println( "User1: \t\t" + u1.getId() + "; " + u1.getFirstName() + " " + u1.getLastName() );
				System.out.println( "User2: \t\t" + u2.getId() + "; " + u2.getFirstName() + " " + u2.getLastName() );
				System.out.println( "Duration: \t" + c.getDuration() + " or " + convertMilliToHMmSsMs(c.getDuration()));
			}
		} catch( IOException ioe ) {
			System.err.println( ioe );
		}
	}
	
	public static String convertMilliToHMmSsMs(long millisec) {
	    long ms = millisec % 1000;
	    long seconds = millisec / 1000;
		long s = seconds % 60;
	    long m = (seconds / 60) % 60;
	    long h = (seconds / (60 * 60)) % 24;
	    return String.format("%d:%02d:%02d:%03d", h,m,s,ms);
	}
}

// eof
