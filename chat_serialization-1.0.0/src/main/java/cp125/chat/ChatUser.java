package cp125.chat;

/**
 * @author Stuart Maclean
 *
 * A hypothetical class that might be part of some larger chat
 * application.
 */

public class ChatUser implements java.io.Serializable {

	public ChatUser( String firstName, String lastName, String id ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}

	final String firstName, lastName, id;
		
}

// eof
