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
	private TextAreaClass textAreaClass;
	private JLabel player1Label, player2Label, score1Label, score2Label;
	private int score1 = 0, score2 = 0;
	private FileIO fileIO;
	private Player player1 = null, player2 = null;
	public boolean mouseEnabled = false;
	public Status status = Status.INIT;
	public enum Status { INIT, PLAYING1, PLAYING2 };
	
	private class TextAreaClass extends JFrame {	
		private JTextArea textArea;
		public TextAreaClass(GamePlay gamePlay, double dx, double dy) {			
			JLabel lblNewLabel = new JLabel("Played moves:");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Algerian", Font.PLAIN, 14));
			lblNewLabel.setBackground(Color.WHITE);
			getContentPane().add(lblNewLabel, BorderLayout.NORTH);
			
			textArea = new JTextArea(10, 10);
			textArea.setFont(new Font("Algerian", Font.PLAIN, 13));
			getContentPane().add(textArea, BorderLayout.CENTER);
			JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			getContentPane().add(scroll);
			
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setSize(200, 300);
			double x = getLocationOnScreen().getX();
			double y = getLocationOnScreen().getY();
			x += dx / 2 + 100;
			y -= dy / 2 - 100;
			this.setLocation((int)x, (int)y);
		}
		public JTextArea getTextArea() {
			return textArea;
		}
	}
	
	public GamePlay(int m, int n, String player1, String player2, String diff1, String diff2, FileIO fileio, int maxDepth, int seconds) {
		super("Dots and boxes");
		this.m = m; 
		this.n = n;
		fileIO = fileio;
		if (fileIO.getReadFile() != null) {
			this.m = fileIO.getM();
			this.n = fileIO.getN();
		}
		
		int width = this.n <= 3 ? 400 : (this.n) * 100;
		int height = (this.m + 1) * 100 + 50;
		setSize(width, height);
		this.setLocationRelativeTo(null);
		addComponents();
		
		textAreaClass = new TextAreaClass(this, width, height);
		fileIO.setTextArea(textAreaClass.getTextArea());
		
		this.player1 = new Player(player1, diff1, board, this.m, this.n, maxDepth, seconds);
		this.player2 = new Player(player2, diff2, board, this.m, this.n, maxDepth, seconds);
		
		fileIO.write("" + this.m + " " + this.n);
		if (fileIO.getReadFile() != null) {
			ArrayList<String> moves = fileIO.getMoves();
			for (String move : moves) {
				Edge edge = fileIO.hashMap.get(move);
				board.makeMove(edge, edge.getI(), edge.getJ());
			}
			if (board.isTurn1()) {
				status = Status.PLAYING1;
				this.player1.continueThread();
			}
			else {
				status = Status.PLAYING2;
				this.player2.continueThread();
			}
		}
		else {
			this.player1.continueThread();
			status = Status.PLAYING1;
		}
		setVisible(true);
		
		if (player1.equals("Human") || player2.equals("Human"))
			mouseEnabled = true;
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		}); 
	}
	
	public int getBlueScore() {
		return score1;
	}
	
	public int getRedScore() {
		return score2;
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
		int winner = 0;
		if (scored1) {
			score1 += num;
			score1Label.setText("" + score1);
		}
		else {
			score2 += num;
			score2Label.setText("" + score2);
		}
		
		if (score1 + score2 == m * n) {
			if (score1 > score2) winner = 1;
			else if (score2 > score1) winner = 2;
			new EndWindow(winner, player1, player2);
			textAreaClass.dispose();
			dispose();
		}
	}
}
