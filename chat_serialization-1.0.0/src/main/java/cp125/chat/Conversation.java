package cp125.chat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * @author Stuart Maclean
 *
 * A hypothetical class that might be part of some larger chat
 * application. A Conversation records a list of sentences said by two
 * chat users, with each utterance timestamped for good measure.
 *
 * Note the use of Collections classes, specifically an ArrayList.
 * The element type of the ArrayList we use is a local nested class:
 * WhoWhatWhen.  It is Serializable, and ArrayList is already defined
 * Serializable, so collections of serializable are themselves
 * serializable.
 *
 * If serialized to JSON form, using e.g. Gson, would NOT need to
 * marked Serializable. The Serializable interface is purely for
 * Java's built-in serialization only.
 *
 * @see ChatUser
 */

public class Conversation implements Serializable {

	public Conversation() {
		transcript = new ArrayList<WhoWhatWhen>();
	}

	public void add( ChatUser who, String what, Date when ) {
		WhoWhatWhen www = new WhoWhatWhen( who, what, when );
		transcript.add( www );
	}

	@Override
	public String toString() {
		/*
		  This is NOT serialization, just a debug tool for
		  visual inspection of a Conversation.
		*/
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter( sw );
		for( WhoWhatWhen www : transcript ) {
			pw.println( www.who.id + "@" + www.when + ": " +
						www.what );
		}
		return sw.toString();
	}
	
	/*
	  Captures the triple of 'who said what and when'.  Note how this
	  has to be marked as Serializable independently of its outer
	  class Conversation.
	*/
	static class WhoWhatWhen implements Serializable {
		WhoWhatWhen( ChatUser who, String what, Date when ) {
			this.who = who;
			this.what = what;
			this.when = when;
		}
		final ChatUser who;
		final String what;
		final Date when;
	}
	
	private final List<WhoWhatWhen> transcript;
}

// eof
