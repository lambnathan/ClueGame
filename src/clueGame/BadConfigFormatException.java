//--------------------BadConfigFormatException--------------------

//Authors: Nathan Lambert and Elliott McCabe

package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		//pass message into super
		super("Something is wrong with the config files.");
	}
	
	public BadConfigFormatException(String message) {
		//pass message into super
		super(message);
		PrintWriter out = null;
		try {
			//output to "log.txt"
			out = new PrintWriter("log.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//add to file, not overwrite
		out.append(message);
		out.close();
		
	}
	
	@Override
	public String toString() {
		return "Invalid files";
	}

	
}
