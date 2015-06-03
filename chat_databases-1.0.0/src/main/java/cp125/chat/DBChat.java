package cp125.chat;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Random;

import org.apache.commons.cli.*;

/**
 * @author Stuart Maclean
 *
 * First connect to a 'Derby/JavaDB' database on localhost (or some
 * local/remote Mysql if you prefer and can edit the db url
 * accordingly).  As part of the connection set up, we create a schema of
 * two tables:
 *
 * 1: conversations - A record of all chat sessions.  Record with whom
 * we spoke (just a simple string representing the real user), and our
 * own name, which can be something as simple as 'server'.  Record
 * also when each conversation started.
 *
 * 2: conversation - For a single chat session, record who said what and when.
 *
 * We then read a line of input from the real user from the keyboard,
 * fake up some replay based on the user's text, and print this reply
 * to the screen.  Each line of input and output is to be timestamped,
 * attributed to one of the two parties, and recorded in the database.
 * This continues until the user has had enough and says quit/exit.
 *
 * The 'chat' logic is mostly already written.  What remains is to
 * fill in the missing database logic in two methods:
 *
 * 1: setupConversation - where we want to record, in the 'conversations' table,
 * the two users and a timestamp representing chat start.
 *
 * 2: conversation - where we want keep reading text from the real
 * user as long as the user is interested, and recording all data to the DB.
 *
 * By default, the real user's id comes from the user.name system
 * property.  To make the database more interesting (in that it has
 * some different chat users), the real user can supply an alternative
 * id via the -i command line option.
 *
 * To run, we need our classpath to include the derby driver jar, e.g.
 *
 * java -cp derbyclient.jar:target/classes cp125.chat.DBChat [-i USER]
 *
 * Alternatively, using Maven's exec plugin, this would be
 *
 * mvn exec:java -Dexec.mainClass=cp125.chat.DBChat
 *
 * or even, with extra args:
 *
 * mvn exec:java -Dexec.mainClass=cp125.chat.DBChat -Dexec.args="-i foo"
 *
 * @see DBChatReader - for a printout of all stored conversations
 */

public class DBChat {

	static public void main( String[] args ) {

		DBChat main = new DBChat();
		main.readArgs( args );
		try {
			main.start();
		} catch( Exception e ) {
			System.err.println( e );
		}
	}

	DBChat() {
		/*
		  Our particular conversations will be between two 'users'.
		  One is a real person and one is this program.  Input
		  from the real user will come from standard input, likely the
		  keyboard.  Output to the real user will go to standard output,
		  likely the screen.  But remember that by using shell
		  redirection, we could say read from a file and write to a
		  file too.

		  In a larger, networked application, these streams would likely
		  be derived from java.net.Socket objects.
		*/
		
		is = System.in;
		os = System.out;
		rng = new Random();
	}
	
	private void readArgs( String[] args ) {
		/*
		  By default, identify ourselves as logged-in user name
		*/
		String DEFAULTID = System.getProperty( "user.name" );
		
		String USAGE = getClass().getName() +  " [-i USERID]";
		
		/*
		  commons-cli is a nice package for parsing a command line.
		  See the pom for its artifact details
		*/
		Options os = new Options();
		os.addOption( "i", true, "current chat user's id, defaults to " +
					  DEFAULTID );

		CommandLineParser clp = new PosixParser();
		CommandLine cl = null;
		try {
			cl = clp.parse( os, args );
		} catch( Exception e ) {
			printUsage( USAGE, os );
			System.exit(-1);
		}

		user = DEFAULTID;
		if( cl.hasOption( "i" ) ) {
			user = cl.getOptionValue( "i" );
		}
	}

	static void printUsage( String usage, Options os ) {
		HelpFormatter hf = new HelpFormatter();
		hf.setWidth( 80 );
		final String HEADER = "";
		final String FOOTER = "";
		hf.printHelp( usage, HEADER, os, FOOTER );
	}

	void start() throws Exception {
		Connection c = null;
		try {
			c = getConnection();
		} catch( SQLException se ) {
			// An error at the connection stage means we are done...
			System.err.println( se );
			return;
		}
		try {
			chat( c );
		} catch( Exception e ) {
			System.err.println( e );
		} finally {
			c.close();
		}
	}

	void chat( Connection c ) throws IOException, SQLException {
		String peer = user;
		String me = "server";
		Timestamp start = new Timestamp( System.currentTimeMillis() );
		setupConversation( peer, me, start, c );
		conversation( peer, me, start, c );
	}

	/**
	 * @param user1 - some string identifying the user chatting with
	 * the program. Likely the logged in user name.  This name becomes
	 * the 'peer' column in the conversations table.
	 *
	 * @param user2 - some string identifying the fake user which is
	 * actually this program.  In a real chat app with two real chat
	 * participants, we would of course not have a fake user at all.
	 */
	void setupConversation( String user1, String user2, Timestamp start,
							Connection c )
		throws SQLException {
		/*
		  FILL ME IN.  Need to add a row to the 'conversations'
		  table. Add the row such that the startTime and peer columns
		  are both filled in.
		  
		  We can use a java.sql.PreparedStatement object to do the insert,
		  or a plainer java.sql.Statement
		*/
		
		PreparedStatement ps = null;
		String statement = "insert into CONVERSATIONS(id, user1, user2) values (?, ?, ?)";
		System.out.println(statement);
		try {
			ps = c.prepareStatement(statement);
			ps.setTimestamp(1, start);
			ps.setString(2, user1);
			ps.setString(3, user2);
			ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException icve) {
			// perhaps expected if this point previously stored
		} finally {
			ps.close();
		}
	}

	void conversation( String user1, String user2, Timestamp conversationID,
					   Connection c ) throws IOException {

		BufferedReader br = new BufferedReader
			( new InputStreamReader( is ) );
		PrintWriter pw = new PrintWriter( os, true );

		pw.println( "Hi " + user1 + ". Let's chat..." );
		
		String line;
		PreparedStatement ps = null;

		// FILL ME IN. Create a preparedstatement to record each sentence said
		
			String psSql =  "insert into CONVERSATION( id, who, what, when ) values( ?,?,?,? )" ;
				
				

		while( true ) {
			// get a line from user1, the real user at the keyboard
			line = br.readLine();
			if( line == null )
				break;
			line = line.trim();
			if( line.isEmpty() )
				continue;
			if( line.equals( "quit" ) || line.equals( "exit" ) ) {
				break;
			}
 
			/*
			  FILL ME IN. Record this line to the db, along with who
			  said it and when
			*/
			try {
				ps = c.prepareStatement (psSql);
				ps.setTimestamp(1, conversationID);;
				ps.setString(2, user1);
				ps.setString(3, line);
				ps.setTimestamp(4, new Timestamp( System.currentTimeMillis() ));
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			// Generate a reply from user2, who is actually this program
			String reply = buildReply( line );

			/*
			  FILL ME IN. Record this reply to the db, along with who
			  said it and when
			*/
			
			try {
				ps = c.prepareStatement (psSql);
				ps.setTimestamp(1, conversationID);;
				ps.setString(2, user2);
				ps.setString(3, reply);
				ps.setTimestamp(4, new Timestamp( System.currentTimeMillis() ));
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				

			// and show the reply to the real user...
			pw.println( reply );
		}

		br.close();
		pw.close();
	}

	String buildReply( String line ) {
		String[] replies = { "Really?",
							 "Yes, I agree",
							 "You think so?",
							 "Java beats Python",
							 "Miranda beats Java",
							 "I am not a machine!" };
		int which = rng.nextInt( replies.length );
		String result = replies[which];
		boolean b = rng.nextBoolean();
		if( b )
			result = line + ". " + result;
		return result;
	}
		
	Connection getConnection() throws SQLException {

		String host = "127.0.0.1";
		String database = "chat";

		/*
		  The jdbc url for JavaDB (aka derby) includes host and
		  database. No need for user credentials with derby, likely
		  NOT so with Mysql.

		  For this connection to succeed, you must have JavaDB up and
		  running.  Run the startNetworkServer[.bat] file in
		  /path/to/your/jdk/db/bin
		*/
		Connection c = DriverManager.getConnection
			( "jdbc:derby://" + host + "/" + database + ";create=true" );
		createSchema( c );
		return c;
	}

	/*
	  Here we create two tables on the fly.  Unlikely any real
	  database administrator would allow this in production, but
	  saves us from needing extra steps to create the database
	  infrastructure
	*/
	static void createSchema( Connection c ) throws SQLException {
		Statement s = c.createStatement();
		try {
			s.executeUpdate( "create " + CONVERSATIONS );
		} catch( SQLException se ) {
			// If the table already exists, we'll see this
			System.err.println( se );
		}

		try {
			s.executeUpdate( "create " + CONVERSATION );
		} catch( SQLException se ) {	
			// If the table already exists, we'll see this
			System.err.println( se );
		}
		
	}

	/*
	  The CONVERSATIONS table holds just the start time and
	  participant ids but no actual chat data. The start time of a
	  conversation becomes the primary key here.
	*/
	static private final String CONVERSATIONS = "table conversations(" +
		"id timestamp primary key," +
		"user1 varchar(32) not null," +
		"user2 varchar(32) not null" +
		")";

	/*
	  The CONVERSATION table holds the actual chat text, attributed to
	  each participant, and with each sentence timestamped too
	*/
	static private final String CONVERSATION = "table conversation(" +
		// The id column is a foreign key into the conversations table
		"id timestamp references conversations," +
		"who varchar(32) not null," +
		"when timestamp not null," +
		"what varchar(255) not null" +
		")";

	private final InputStream is;
	private final OutputStream os;
	private String user;
	private Random rng;
}
// eof

