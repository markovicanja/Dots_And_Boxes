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

import javax.swing.JTextArea;

import etf.dotsandboxes.ma170420d.Board.Edge;

public class FileIO {
	private File readFile, writeFile;
	private int m, n;
	private JTextArea ta;
	private ArrayList<String> moves = new ArrayList<>(); 
	public Map<String, Edge> hashMap = new HashMap<>();
	public Map<Edge, String> toStringMap = new HashMap<>();
	
	public FileIO (File readFile, File writeFile, int m, int n) {
		this.readFile = readFile;
		this.writeFile = writeFile;
		this.m = m;
		this.n = n;
		if (writeFile != null) {
			try {
				FileWriter fw = new FileWriter(writeFile, false);
				fw.flush();
				fw.close();
			} catch (IOException e) {
			}
		}
	}
	
	public File getReadFile() {
		return readFile;
	}

	public boolean read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(readFile));
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
		ta.append(text+"\n");
		if (writeFile == null) return;
		try {
			FileWriter fw = new FileWriter(writeFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw); 
			out.println(text);
			out.close();
		} catch (IOException e) {
			System.err.println("Writing in file error...");
		}
	}
	
	public void setTextArea(JTextArea ta) {
		this.ta = ta;
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
					toStringMap.put(horizontal[i][j], key);
				}
				if (i < m) {
					String key = letters[i] + j;
					hashMap.put(key, vertical[i][j]);
					toStringMap.put(vertical[i][j], key);
				}
			}
		}
	}
}
