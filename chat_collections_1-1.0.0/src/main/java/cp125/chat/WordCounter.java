package cp125.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Stuart Maclean.  A chat word counter class, which reads a
 * transcript file of a previous conversation and calculates word
 * frequencies.  Expected we will use classes from java.util.
 */

public class WordCounter {
	
	static List<String> chatParticipant1 = null;
	static List<String> chatParticipant2 = null;
	
	static Map<Integer, String> speakers = null;
	
	static Map<String, Integer> frequency1 = null;
	static Map<String, Integer> frequency2 = null;
	
	static int numberOfSpeakers = 0;

	static public void main( String[] args ) {
		
		chatParticipant1 = new ArrayList<String>();
		chatParticipant2 = new ArrayList<String>();
		
		speakers = new HashMap<Integer, String>();
		
		int user = 0;

		// check that we can read the file...
		File f = new File( "transcript.txt" );
		if( !( f.isFile() && f.canRead() ) ) {
			System.err.println( "Cannot load: " + f );
			System.exit(1);
		}
		
		/*
		 *  Read the file and populate an list with all words
		 */
		
		
		try {
			FileReader source = new FileReader(f);
			BufferedReader filter = new BufferedReader(source);
			
			String line = filter.readLine();
			
			
			while (line != null) {
				// String lineparts[] = line.split(DELIMITER);

				String[] parts = line.split(":");
				String speaker = parts[0].trim();
				String sentence = parts[1].trim();

//				System.out.println(speaker);
//				System.out.println(sentence);
				
				if (!speakers.containsValue(speaker)) {
					numberOfSpeakers++;
					speakers.put(numberOfSpeakers, speaker);
				}
									
//				user = speakers.get(speaker);
				String[] words = sentence.split("\\s+");
				
				if (speaker.equalsIgnoreCase("bill")) {
					
					for (String w : words) {
						chatParticipant1.add(w.toLowerCase().replaceAll( "\\p{Punct}", "" ));
//						System.out.println(w);
					}
				} else if (speaker.equalsIgnoreCase("ben")) {
					for (String w : words) {
						chatParticipant2.add(w.toLowerCase().replaceAll( "\\p{Punct}", "" ));
//						System.out.println(w);
					}
				}
				
				line = filter.readLine();

//				String[] words = sentence.split("\\s+");
//				for (String w : words) {
//					w = w.replaceAll( "\\p{Punct}", "" );
//					System.out.println(w);
				}
				

				// String.replaceAll( "\\p{Punct}", "" );
//			}
			filter.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
			// recover
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
	
	/*
	 * count the frequency of each word said by each user
	 * Bill:
	 * the - 4 times
	 * of - 2 times
	 * 
	 * Ben:
	 * hat - 3 times
	 * cat - 2 times
	 */
	static void level1() {
		// TO COMPLETE
		
		frequency1 = new HashMap<String, Integer>();
		frequency2 = new HashMap<String, Integer>();
		
		System.out.println("Bill:");
		frequency1 = wordFrequency(chatParticipant1);
		printMap(frequency1);
		
		System.out.println();
//		System.out.println(frequency1);	
		
		System.out.println("Ben:");
//		for (String w : chatParticipant2) {
//			if (frequency2.containsKey(w)) {
//				frequency2.put(w, (frequency2.get(w)) + 1);				
//			} else {
//				frequency2.put(w, 1);
//			}
//		}
		frequency2 = wordFrequency(chatParticipant2);
		printMap(frequency2);
	}

	private static Map wordFrequency(List<String> chatParticipant) {
		Map<String, Integer> frequency = new HashMap<String, Integer>();
		for (String w : chatParticipant) {
			if (frequency.containsKey(w)) {
				frequency.put(w, (frequency.get(w)) + 1);				
			} else {
				frequency.put(w, 1);
			}
						
		}
		return frequency;
	}

	private static void printMap(Map<String, Integer> frequency12) {
		
		for (Map.Entry<String, Integer> entry : frequency12.entrySet()) {
		    System.out.println(entry.getKey() + " - " + entry.getValue() + " times");
		    
		}
	}
	
	/*
	 * For each of the two users, calculate and print the total number of words (not the number of 
	 * distinct words) said. Print the results as follows:
	 * Bill: 220 words.
	 * Ben: 156 words.
	 */

	static void level2() {
		// TO COMPLETE
		System.out.println();
		System.out.println("Total number of words by each person.");
		System.out.println();
		System.out.println("Bill: " + chatParticipant1.size() + " words");
		System.out.println("Ben: " + chatParticipant2.size() + " words");
	}
	
	/*
	 * Revisit the level 1 program, but this time print the output sorted by word
	 */
	
	static void level3() {
		// TO COMPLETE
		System.out.println("Bill:");
		Map<String, Integer> treeMap1 = new TreeMap<String, Integer>(frequency1);
		printMap(treeMap1);
		
		System.out.println();
		
		System.out.println("Ben:");
		Map<String, Integer> treeMap2 = new TreeMap<String, Integer>(frequency2);
		printMap(treeMap2);
	}
}

// eof
