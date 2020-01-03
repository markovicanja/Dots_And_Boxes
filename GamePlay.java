package etf.dotsandboxes.ma170420d;

import java.awt.*;
import javax.swing.*;
import etf.dotsandboxes.ma170420d.Board.Edge;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePlay extends JFrame {
	private int m, n;
	private static Font font = new Font("Algerian", Font.PLAIN, 30);
	private static Color blue = new Color(0, 204, 255), red = new Color(255, 0, 0);
	private Board board;  
	private JLabel player1Label, player2Label, score1Label, score2Label;
	private FileIO fileIO;
	private Player player1 = null, player2 = null;
	public boolean mouseEnabled = false;
	public Status status = Status.INIT;
	public enum Status { INIT, PLAYING1, PLAYING2 };
	
	public GamePlay(int m, int n, String player1, String player2, String diff1, String diff2, FileIO fileio) {
		super("Dots and boxes");
		this.m = m; 
		this.n = n;
		fileIO = fileio;
		if (!fileIO.getReadDirectory().equals("")) {
			this.m = fileIO.getM();
			this.n = fileIO.getN();
		}
		int width = this.n <= 3 ? 400 : (this.n) * 100;
		int height = (this.m + 1) * 100 + 50;
		setSize(width, height);
		this.setLocationRelativeTo(null);
		addComponents();
		
		this.player1 = new Player(player1, diff1, board, this.m, this.n);
		this.player2 = new Player(player2, diff2, board, this.m, this.n);
		
		fileIO.write("" + this.m + " " + this.n);
		if (!fileIO.getReadDirectory().equals("")) {
			ArrayList<String> moves = fileIO.getMoves();
			for (String move : moves) {
				Edge edge = fileIO.hashMap.get(move);
				board.makeMove(edge, edge.getI(), edge.getJ());
			}
		}
		setVisible(true);
		
		this.player1.continueThread();
		if (player1.equals("Human") || player2.equals("Human"))
			mouseEnabled = true;
		
		status = Status.PLAYING1;
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		}); 
	}
	
	public Player player1() {
		return this.player1;
	}
	
	public Player player2() {
		return this.player2;
	}
	
	public void addComponents() {
		Panel panel=new Panel();
		getContentPane().add(panel, "Center");
		panel.setBackground(new Color(184, 223, 255));
		panel.setLayout(new BorderLayout(0, 0));
		board = new Board(m, n, this);
		panel.add(board);
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setForeground(new Color(51, 0, 102));
		panel.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		player1Label = new JLabel("Player 1");
		player1Label.setFont(font);
		player1Label.setForeground(blue);
		northPanel.add(player1Label);
		
		score1Label = new JLabel("0");
		score1Label.setFont(font);
		northPanel.add(score1Label);
		
		JLabel label = new JLabel(":");
		label.setFont(font);
		northPanel.add(label);
		
		score2Label = new JLabel("0");
		score2Label.setFont(font);
		northPanel.add(score2Label);
		
		player2Label = new JLabel("Player 2");
		player2Label.setEnabled(false);
		player2Label.setFont(font);
		player2Label.setForeground(red);
		northPanel.add(player2Label);
	}
	
	public FileIO getFileIO() {
		return fileIO;
	}
	
	public void enableLables(boolean enable1) {
		player1Label.setEnabled(enable1);
		player2Label.setEnabled(!enable1);
	}
	
	public void setScore(boolean scored1, int num) {
		int score1 = Integer.parseInt(score1Label.getText());
		int score2 = Integer.parseInt(score2Label.getText());
		int winner = 0;
		if (scored1) score1Label.setText("" + (score1 += num));
		else score2Label.setText("" + (score2 += num));
		
		if (score1 + score2 == m * n) {
			if (score1 > score2) winner = 1;
			else if (score2 > score1) winner = 2;
			new EndWindow(winner);
			this.setVisible(false);
		}
	}
}
