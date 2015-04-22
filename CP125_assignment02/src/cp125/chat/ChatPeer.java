package cp125.chat;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * The ChatPeer is one 'user' in a 2-user conversation. It is actually a program
 * (class) which mimics a real user, by 'saying' things back to another User.
 */

public class ChatPeer {

	Conversation c = null;
	File f = null;
	String fileName = null;
	long startTime = 0;

	public ChatPeer(InputStream input, OutputStream output) {
		this.input = input;
		this.output = output;

	}

	public void chatWith(User u, boolean save) throws IOException {
		User u2 = new User("program", "program", "comp");
		OutputStreamWriter output1 = new OutputStreamWriter(output);
		PrintWriter pw = new PrintWriter(output1, true);
		if (save) {
			startTime = System.currentTimeMillis();
			Date startTimeDate = new Date(startTime);
			pw.println(startTimeDate.toString());
			c = new Conversation(u, u2, startTimeDate);
		}

		InputStreamReader filter1 = new InputStreamReader(input);
		LineNumberReader filter2 = new LineNumberReader(filter1);
		String line;
		pw.println("Hello " + u.getFirstName()
				+ ". Its nice to speak with you.");
		pw.println("When you are done speaking with me, type exit or quit.");
		pw.println("What would you like to talk about? :> ");

		try {
			while ((line = filter2.readLine()) != null) {
				if (line.equalsIgnoreCase("quit")
						|| line.equalsIgnoreCase("exit")) {
					break;
				}
				pw.println(getReply(line));
				pw.println("Your Turn: > ");
			}
		} catch (IOException ioe) {
			// recover
			System.err.println(ioe);
		}

		if (save) {
			System.out
					.println("Would you like to save a transcript of this chat? (y or n) ");
			try {
				while ((line = filter2.readLine()) != null) {
					if (line.equalsIgnoreCase("y")) {
						save = true;
						break;
					} else if (line.equalsIgnoreCase("n")) {
						save = false;
						break;

					} else {
						System.out.println("please type a y or n.");
					}
				}
			} catch (IOException ioe) {
				// recover
				System.err.println(ioe);
			}

			long endTime = System.currentTimeMillis();
			Date endTimeDate = new Date(endTime);
			c.setEndTime(endTimeDate);
			fileName = "chatsession_" + startTime + ".txt";
			String homeDir = System.getProperty("user.home");

			try {
				f = new File(homeDir + "\\" + fileName);
				PrintWriter pw2 = new PrintWriter(f);
				pw2.println("Start time: \t" + c.getStartTime());
				pw2.println("End Time: \t" + endTimeDate);
				pw2.println("Chat user 1: \t" + u.getFirstName() + " "
						+ u.getLastName() + ", ID = " + u.getId());
				pw2.println("Chat user 2: \t" + u2.getFirstName() + " "
						+ u2.getLastName() + ", ID = " + u2.getId());

				pw2.close();

			} catch (IOException ioe) {
				System.err.println(ioe);
			}
			c.save(f);
		}

		filter2.close();
	}

	/*
	 * Feel free to change this method, perhaps so it can reply with some random
	 * replies?? Perhaps define an array of canned replies and use a random
	 * number to sample from that array??
	 */
	private String getReply(String userSaid) {
		return "Really? You think " + userSaid;
	}

	private final InputStream input;
	private final OutputStream output;

}

// eof
