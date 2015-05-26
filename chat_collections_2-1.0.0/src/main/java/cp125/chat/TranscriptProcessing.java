package cp125.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Stuart Maclean.
 *
 * A template for assignment 7.  We load a transcript file of some
 * text attributed to two chat users Bill and Ben.  We then want to
 * answer 3 questions about each of the two users:

 * Longest word said
 *
 * Shortest word said
 *
 * Whether the user said any particular words, to be supplied.
 *
 * For 'longest' and 'shortest' words, don't worry about identifying
 * ALL of the longest, shortest ones ,we just pick one and say 'no word
 * was longer than this', etc.
 */
 
public class TranscriptProcessing {

	static public void main( String[] args ) {

		File f = new File( "transcript.txt" );
		if( !( f.isFile() && f.canRead() ) ) {
			System.err.println( "Cannot load: " + f );
			System.exit(1);
		}

		List<String> allWordsBill = new ArrayList<String>();
		List<String> allWordsBen  = new ArrayList<String>();

		// load the data from the file...
		loadTranscript( f, allWordsBill, allWordsBen );

		// and print out what we've got...
		System.out.println( "Bill:" );
		System.out.println( allWordsBill );
		System.out.println();
		System.out.println();
		System.out.println( "Ben:" );
		System.out.println( allWordsBen );

		// sort the data and identify longest and shortest words said...
		sorting( "Bill", allWordsBill );
		sorting( "Ben", allWordsBen );

		// search the data for particular words said...
		String[] didBillSay = { "written", "brackets", "count", "Python" };
		searching( "Bill", allWordsBill, didBillSay );
		String[] didBenSay = { "Sounders", "Blah", "blah", "funny" };
		searching( "Ben", allWordsBen, didBenSay );
	}

	static void sorting( String who, List<String> words ) {

		// FILL THIS IN

		String longest = ""; 
		String shortest = "";
		System.out.println( who + " longest  word = " + longest );
		System.out.println( who + " shortest word = " + shortest );
	}

	static void searching( String who, List<String> words,
						   String[] needles ) {
		/*
		  If we don't know for SURE that the data is sorted according
		  to the 'natural order' as defined by String.compareTo, we'd
		  better sort it according to that natural order. Note then
		  that any binarySearch call does NOT have to supply its
		  own Comparator.  In other words, the sort and the search are
		  using the same notion of String comparable, that provided by
		  the String class itself.
		*/

		// FILL THIS IN such that we can say, for all supplied needles:

		System.out.println( who + " said 'needle': true/false" );
	}

	
	static void loadTranscript( File f,
								List<String> billsWords,
								List<String> bensWords ) {
		try {
			LineNumberReader lnr = new LineNumberReader
				( new FileReader( f ) );
			String line;
			while( (line = lnr.readLine()) != null ) {
				String spoken = null;
				List<String> whichList = null;
				if( false ) {
					// this makes the following else/ifs line up nice!
				} else if( line.startsWith( "Bill:" ) ) {
					// I prefer "Bill:".length() over just '5'
					spoken = line.substring( "Bill:".length() );
					whichList = billsWords;
				} else if( line.startsWith( "Ben:" ) ) {
					spoken = line.substring( "Ben:".length() );
					whichList = bensWords;
				} else {
					// hmm, bad line, discard..
					continue;
				}
				spoken = spoken.trim();
				String[] words = spoken.split( "\\s+" );
				for( String word : words ) {
					// We don't care about punctuation...
					word = word.replaceAll( "\\p{Punct}", "" );
					whichList.add( word );
				}
			}
			lnr.close();
		} catch( IOException ioe ) {
			System.err.println( ioe );
			System.exit(1);
		}
	}
}

// eof
