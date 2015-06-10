package cp125.chat;

import java.io.File;
import java.io.FilenameFilter;
import java.io.RandomAccessFile;
import java.io.IOException;

import com.google.gson.Gson;

/**
 * @author Stuart Maclean
 *
 * Support program for the ChatConverter.  Reads in all the .json
 * files produced by ChatConverter and performs visual inspection, via
 * Conversation.toString, of the Conversation objects thus created.
 *
 * @see Conversation
 * @see ChatConverter
 */

public class JSONReader {

	static public void main( String[] unused ) {
		File pwd = new File( "." );

		// Filter as an anonymous class, pre Java8
		File[] fs = pwd.listFiles( new FilenameFilter() {
				public boolean accept( File dir, String name ) {
					return name.endsWith( ".json" );
				}
			} );

		// Filter as a lambda, Java8
		/*
		File[] fs2 = pwd.listFiles( (File dir, String name) ->
								   name.endsWith( ".json" ) );
		*/

		for( File f : fs ) {
			load( f );
		}
	}

	static void load( File f ) {
		System.out.println( "Loading " + f );
		try {
			// Reading IN from the JSON file
			RandomAccessFile raf = new RandomAccessFile( f, "r" );
			byte[] ba = new byte[(int)f.length()];
			raf.readFully( ba );
			raf.close();
			String s = new String( ba );
			Gson gson = new Gson();
			Conversation c = gson.fromJson( s, Conversation.class );

			// Visual Inspection, NOT true serializing (not reversible)
			System.out.println( c );
		} catch( IOException ioe ) {
			System.err.println( f + " " + ioe );
		}
	}
}

// eof
