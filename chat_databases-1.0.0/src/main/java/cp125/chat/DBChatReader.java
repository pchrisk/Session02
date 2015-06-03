package cp125.chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Stuart Maclean
 *
 * Connect to a 'Derby/JavaDB' database on localhost.  Inspect tables in
 * a 'chat' database, creating the schema tables if required.
 *
 * A support program for the assignment DBChat program, but
 * <em>not</em> an active 'chat component'.  Rather, reads and prints
 * out chat data previously recorded by the DBChat program.
 *
 * To run, we need our classpath to include the derby driver jar, e.g.
 *
 * java -cp derbyclient.jar:target/classes cp125.chat.DBChatReader
 *
 * Alternatively, using Maven's exec plugin, this would be
 *
 * mvn exec:java -Dexec.mainClass=cp125.chat.DBChatReader
 *
 * @see DBChat
 */

public class DBChatReader {

	static public void main( String[] args ) {

		String host = "127.0.0.1";
		String database = "chat";
		
		try {
			// url includes host and database...
			Connection c = DriverManager.getConnection
				( "jdbc:derby://" + host + "/" + database + ";create=true" );
			DBChat.createSchema( c );
			Statement s = c.createStatement();

			ResultSet rs = s.executeQuery
				( "select count(*) as C from conversations" );
			if( rs.next() ) {
				int count = rs.getInt( "C" );
				System.out.println( "Found " + count + " conversations" );
			}
			
			rs = s.executeQuery( "select * from conversations " +
								 "order by id" );
			// loop over all conversations...
			while( rs.next() ) {
				Timestamp startTime = rs.getTimestamp( "id" );
				String user1 = rs.getString( "user1" );
				String user2 = rs.getString( "user2" );
				System.out.println();
				System.out.println( "Conversation started " + startTime +
									", between '" + user1 + "' and '" +
									user2 + "':" );
				showConversation( startTime, c );
				System.out.println();
			}

			rs.close();
			c.close();
		} catch( SQLException se ) {
			System.err.println( se );
		}
	}

	static void showConversation( Timestamp id, Connection c ) {
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery
				( "select * from conversation where id = '" + id + "' " +
				  "order by when" );
			while( rs.next() ) {
				String who = rs.getString( "who" );
				Timestamp when = rs.getTimestamp( "when" );
				String what = rs.getString( "what" );
				System.out.println( who + " at " + when );
				System.out.println( "  " + what );
			}
		} catch( SQLException se ) {
			System.err.println( se );
		}			
	}							 

}

// eof

