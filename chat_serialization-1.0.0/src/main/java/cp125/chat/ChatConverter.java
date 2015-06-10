package cp125.chat;

import java.io.File;
import java.io.FilenameFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Stuart Maclean
 *
 * The main file for this week's assignment.  Is to read in some files
 * consisting of Conversation objects serialized to disk using Java's
 * built-in (native) serialization.  Is then to convert each loaded
 * Conversation object into a json string, and save back out a new
 * file.  So for every X.ser file successfully loaded, there will be a
 * new X.json created.
 *
 * @see Conversation
 */

public class ChatConverter {

	static public void main( String[] unused ) {
		File pwd = new File( "." );

		// Filter as an anonymous class, pre Java8
		File[] fs = null;  // FILL ME IN

		// Filter as a lambda, Java8
		/*
		File[] fs2 = pwd.listFiles( (File dir, String name) ->
								   name.endsWith( ".ser" ) );
		*/

		for( File f : fs ) {
			convert( f );
		}
	}

	static void convert( File f ) {
		System.out.println( "Converting " + f );
		try {
			// FILL ME IN

			// THESE THROWS HERE JUST TO MAKE THIS BUILDABLE, REMOVE
			if( true )
				throw new IOException();
			if( true )
				throw new ClassNotFoundException();
			
		} catch( IOException ioe ) {
			System.err.println( f + " " + ioe );
		} catch( ClassNotFoundException cnfe ) {
			System.err.println( f + " " + cnfe );
		}
	}

	/*
	  If needed, makes for nicer JSON formatting
	  than using 'new Gson()' alone
	*/
	static Gson createGson( boolean withPrettyPrinting ) {
		GsonBuilder gb = new GsonBuilder();
		if( withPrettyPrinting )
			gb.setPrettyPrinting();
		gb.serializeNulls();
		gb.disableHtmlEscaping();

		return gb.create();
	}
}
// eof
