//Authors: Nathan Lambert and Elliott McCabe

package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super("Something is wrong with the config files.");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
		PrintWriter out = null;
		try {
			out = new PrintWriter("log.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out.append(message);
		out.close();
		
	}
	
	@Override
	public String toString() {
		return "Invalid files";
	}

	
}
