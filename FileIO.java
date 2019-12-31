package etf.dotsandboxes.ma170420d;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import etf.dotsandboxes.ma170420d.Board.Edge;

public class FileIO {
	private String readDirectory, writeDirectory;
	private int m, n;
	private ArrayList<String> moves = new ArrayList<>(); 
	public Map<String, Pair> hashMap = new HashMap<>();
	public Map<Edge, String> toStringMap = new HashMap<>();
	
	public class Pair {
		private Edge edge;
		private int i, j;
		public Pair(Edge e, int x, int y) {
			edge = e;
			i = x; j = y;
		}
		public Edge getEdge() {
			return edge;
		}
		public int getI() {
			return i;
		}
		public int getJ() {
			return j;
		}
	}
	
	public FileIO (String readDirectory, String writeDirectory) {
		this.readDirectory = readDirectory;
		this.writeDirectory = writeDirectory;
	}
	
	public String getReadDirectory() {
		return readDirectory;
	}

	public boolean read() {
		File file=new File(readDirectory);
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
	
	public void write(String text) {
		try {
			FileWriter fw = new FileWriter(writeDirectory, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw); 
			out.println(text);
			out.close();
		} catch (IOException e) {
			System.err.println("Writing in file error...");
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
					Pair pair = new Pair(horizontal[i][j], i, j);
					hashMap.put(key, pair);
					toStringMap.put(horizontal[i][j], key);
				}
				if (i < m) {
					String key = letters[i] + j;
					Pair pair = new Pair(vertical[i][j], i, j);
					hashMap.put(key, pair);
					toStringMap.put(vertical[i][j], key);
				}
			}
		}
	}
}
