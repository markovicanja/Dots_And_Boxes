package etf.dotsandboxes.ma170420d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead {
	private String directory;
	private int m, n;
	private ArrayList<String> moves = new ArrayList<>(); 
	
	public FileRead(String directory) {
		this.directory = directory;
	}
	
	public boolean read() {
		File file=new File(directory);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line;
			if ((line = br.readLine()) != null) {
				if (line.length() != 3 || line.charAt(1) != ' ') {
					br.close();
					throw new IOException();
				}
				m = Integer.parseInt(line.substring(0, 1));
				n = Integer.parseInt(line.substring(2, 3));
			}
			while ((line = br.readLine()) != null) {
				if (line.length() != 2) {
					br.close();
					throw new IOException();
				}
				moves.add(line);
			}
			br.close();
			return true;
		} 
		catch (FileNotFoundException e) {
			System.err.println("File not found...");
			return false;
		}
		catch (IOException e) {
			System.err.println("Bad file format...");
			return false;
		}
		catch(NumberFormatException e) {
			System.err.println("Bad file format...");
			return false;
		}
	}

	public int getM() {
		return m;
	}

	public int getN() {
		return n;
	}

	public ArrayList<String> getMoves() {
		return moves;
	}
}
