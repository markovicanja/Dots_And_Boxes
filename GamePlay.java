package etf.dotsandboxes.ma170420d;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GamePlay extends JFrame {
	private int m, n;
	private static Font font = new Font("Algerian", Font.PLAIN, 30);
	private static Color blue = new Color(0, 204, 255), red = new Color(255, 0, 0);
	private Board board;  
	private JLabel player1Label, player2Label, score1Label, score2Label;
	private FileRead fileReader;
	
	public GamePlay(int m, int n, String player1, String player2, String diff1, String diff2, FileRead fr) {
		super("Dots and boxes");
		this.m = m; 
		this.n = n;
		fileReader = fr;
		setSize((m + 1) * 100 + 20, (n + 1) * 100);
		this.setLocationRelativeTo(null);
		addComponents();
		setVisible(true);
//		if (player1.equals("Human") && player2.equals("Human")) ako nijedan nije human zabrani misa
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		}); 
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
			this.dispose();
		}
	}
}
