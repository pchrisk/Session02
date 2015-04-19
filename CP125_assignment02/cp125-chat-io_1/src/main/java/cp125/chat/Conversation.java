package cp125.chat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * A Conversation is between two Users and has a known duration (end
 * time minus start time)
 */

public class Conversation {

	public Conversation( User u1, User u2, Date startTime ) {
		this.u1 = u1;
		this.u2 = u2;
		this.startTime = startTime;
	}

	public void setEndTime( Date endTime ) {
		this.endTime = endTime;
	}

	public void save( File f ) throws IOException {
	}

	public static Conversation load( File f ) throws IOException {
		return null;
	}

	private final User u1, u2;
	private final Date startTime;
	private Date endTime;

}

// eof
