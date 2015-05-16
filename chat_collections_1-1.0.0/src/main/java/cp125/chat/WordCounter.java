package cp125.chat;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stuart Maclean.  A chat word counter class, which reads a
 * transcript file of a previous conversation and calculates word
 * frequencies.  Expected we will use classes from java.util.
 */

public class WordCounter {

	static public void main( String[] args ) {

		// check that we can read the file...
		File f = new File( "transcript.txt" );
		if( !( f.isFile() && f.canRead() ) ) {
			System.err.println( "Cannot load: " + f );
			System.exit(1);
		} else {
			System.out.println("file length: " + f.length());
		}

		level1();

		level2();

		level3();
	}

	/*
	  Feel free to change the signature of this method if you wish.
	  It could take arguments and/or return a result.  Ditto for
	  level2 and level3 methods.
	*/
	static void level1() {
		// TO COMPLETE
	}

	static void level2() {
		// TO COMPLETE
	}
	
	static void level3() {
		// TO COMPLETE
	}
}

// eof
