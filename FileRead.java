package etf.dotsandboxes.ma170420d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import etf.dotsandboxes.ma170420d.Board.Edge;

public class FileRead {
	private String directory;
	private int m, n;
	private ArrayList<String> moves = new ArrayList<>(); 
	public Map<String, Edge> hashMap = new HashMap<>();
	
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
	
	public void makeHashMap(Edge[][] horizontal, Edge[][] vertical) {
		String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		for (int i = 0; i < m + 1; i++) {
			for (int j = 0; j < n + 1; j++) {
				if (j < n) {
					String key = "" + i + letters[j];
					hashMap.put(key, horizontal[i][j]);
					System.out.println(key+" horizontal "+i+" "+j);
				}
				if (i < m) {
					String key = letters[i] + j;
					hashMap.put(key, vertical[i][j]);
					System.out.println(key+" vertical "+i+" "+j);
				}
			}
		}
	}
}
