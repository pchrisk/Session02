package cp125.chat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.ListIterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cp125.chat.Conversation.WhoWhatWhen;

/**
 * @author Stuart Maclean
 *
 *         The main file for this week's assignment. Is to read in some files
 *         consisting of Conversation objects serialized to disk using Java's
 *         built-in (native) serialization. Is then to convert each loaded
 *         Conversation object into a json string, and save back out a new file.
 *         So for every X.ser file successfully loaded, there will be a new
 *         X.json created.
 *
 * @see Conversation
 */

public class ChatConverter {

	static String ext = ".ser";
	static String fname = null;

	static public void main(String[] unused) {
		File pwd = new File(".");

		// Filter as an anonymous class, pre Java8
		File[] fs = null; // FILL ME IN

		FilenameFilter fileNameFilter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (name.lastIndexOf('.') > 0) {
					// get last index for '.' char
					int lastIndex = name.lastIndexOf('.');

					// get extension
					String str = name.substring(lastIndex);
					
					// match path name extension
					if (str.equals(ext)) {
						return true;
					}
				}
				return false;
			}
		};

		fs = pwd.listFiles(fileNameFilter);

		// Filter as a lambda, Java8
		/*
		 * File[] fs2 = pwd.listFiles( (File dir, String name) -> name.endsWith(
		 * ".ser" ) );
		 */

		for (File f : fs) {
			System.out.println("Convert");
			convert(f);
		}
	}

	static void convert(File f) {
		System.out.println("Converting " + f);
		fname = f.getName().substring(0, f.getName().lastIndexOf("."));
		System.out.println(fname);
		try {
			// FILL ME IN
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Conversation cu = (Conversation)ois.readObject();
			
//			ArrayList<WhoWhatWhen> arr = (ArrayList<WhoWhatWhen>) ois.readObject();

//			List<WhoWhatWhen> l = (ArrayList<WhoWhatWhen>) cu;
//			for (WhoWhatWhen www : ) {
//				System.out.println(www.when);
//				
//			}
			System.out.println("CU printed" + cu);
			serialize(cu, fname);

			
			ois.close();

			// // THESE THROWS HERE JUST TO MAKE THIS BUILDABLE, REMOVE
			// if( true )
			// throw new IOException();
			// if( true )
			// throw new ClassNotFoundException();

		} catch (IOException ioe) {
			System.err.println(f + " " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.err.println(f + " " + cnfe);
		}
	}
	
	static void serialize( Object o, String outFile ) {
		File f = new File( outFile + ".json");
		try {
			PrintWriter pw  = new PrintWriter( f );

			Gson gson = null;
			if( false ) {
				// plain Gson object, no pretty printing, etc
				gson = new Gson();
			} else {
				// Using a GsonBuilder, more control over final json string
				gson = createGson(true);
			}
			
			String s = gson.toJson( o );
			pw.println( s );
			pw.close();
		} catch( IOException ioe ) {
			System.err.println( ioe );
		}
	}

	/*
	 * If needed, makes for nicer JSON formatting than using 'new Gson()' alone
	 */
	static Gson createGson(boolean withPrettyPrinting) {
		GsonBuilder gb = new GsonBuilder();
		if (withPrettyPrinting)
			gb.setPrettyPrinting();
		gb.serializeNulls();
		gb.disableHtmlEscaping();

		return gb.create();
	}
}
// eof
