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
 * @author Stuart Maclean. A chat word counter class, which reads a transcript
 *         file of a previous conversation and calculates word frequencies.
 *         Expected we will use classes from java.util.
 */

public class WordCounter {

	static List<String> chatParticipant1 = null;
	static List<String> chatParticipant2 = null;

	static Map<Integer, String> speakers = null;
	static String speaker1 = null;
	static String speaker2 = null;

	static Map<String, Integer> frequency1 = null;
	static Map<String, Integer> frequency2 = null;

	static int numberOfSpeakers = 0;

	static public void main(String[] args) {

		chatParticipant1 = new ArrayList<String>();
		chatParticipant2 = new ArrayList<String>();

		speakers = new HashMap<Integer, String>();

		// check that we can read the file...
		File f = new File("transcript.txt");
		if (!(f.isFile() && f.canRead())) {
			System.err.println("Cannot load: " + f);
			System.exit(1);
		}

		try {
			FileReader source = new FileReader(f);
			BufferedReader filter = new BufferedReader(source);

			String line = filter.readLine();

			while (line != null) {

				String[] parts = line.split(":");
				String speaker = parts[0].trim();
				if (speaker1 == null) {
					speaker1 = speaker;
				} else if (speaker2 == null) {
					speaker2 = speaker;
				}
				String sentence = parts[1].trim();

				if (!speakers.containsValue(speaker)) {
					numberOfSpeakers++;
					speakers.put(numberOfSpeakers, speaker);
				}

				String[] words = sentence.split("\\s+");

				if (speaker.equalsIgnoreCase(speaker1)) {

					for (String w : words) {
						chatParticipant1.add(removePunc(w));
					}

				} else if (speaker.equalsIgnoreCase(speaker2)) {

					for (String w : words) {
						chatParticipant2.add(removePunc(w));
					}

				}

				line = filter.readLine();

			}

			filter.close();

		} catch (IOException ioe) {
			System.out.println(ioe);

		}

		level1();

		level2();

		level3();
	}

	private static String removePunc(String w) {
		return w.toLowerCase().replaceAll("\\p{Punct}", "");
	}

	/*
	 * Feel free to change the signature of this method if you wish. It could
	 * take arguments and/or return a result. Ditto for level2 and level3
	 * methods.
	 */

	static void level1() {

		frequency1 = new HashMap<String, Integer>();
		frequency2 = new HashMap<String, Integer>();

		System.out
				.println("A count of the frequency of each word said by each user");
		System.out.println();

		System.out.println(speaker1 + ":");
		frequency1 = wordFrequency(chatParticipant1);
		printMap(frequency1);

		System.out.println();

		System.out.println(speaker2 + ":");
		frequency2 = wordFrequency(chatParticipant2);
		printMap(frequency2);
	}

	private static Map<String, Integer> wordFrequency(
			List<String> chatParticipant) {
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
			System.out.println(entry.getKey() + " - " + entry.getValue()
					+ " times");
		}
	}

	static void level2() {

		System.out.println();
		System.out.println("Total number of words by each person.");
		System.out.println();
		System.out
				.println(speaker1 + ": " + chatParticipant1.size() + " words");
		System.out
				.println(speaker2 + ": " + chatParticipant2.size() + " words");
		System.out.println();
	}

	static void level3() {

		System.out.println();
		System.out
				.println("A sorted count of the frequency of each word said by each user");
		System.out.println();

		System.out.println(speaker1 + ":");
		Map<String, Integer> treeMap1 = new TreeMap<String, Integer>(frequency1);
		printMap(treeMap1);

		System.out.println();

		System.out.println(speaker2 + ":");
		Map<String, Integer> treeMap2 = new TreeMap<String, Integer>(frequency2);
		printMap(treeMap2);
	}
}

// eof
