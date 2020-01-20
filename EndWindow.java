package etf.dotsandboxes.ma170420d;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class EndWindow extends JFrame {
	
	public EndWindow(int winner, Player player1, Player player2) {
		super("Dots and boxes");
		getContentPane().setBackground(Color.WHITE);
		setSize(500, 400);
		this.setLocationRelativeTo(null);
		
		
		String lblText = "Player " + winner;
		if (winner == 0) {
			lblText = "TIE!";
		}
		else {
			JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("winner.png")));
			imageLabel.setForeground(Color.WHITE);
			imageLabel.setBackground(Color.WHITE);
			getContentPane().add(imageLabel, BorderLayout.NORTH);
		}
		
		JLabel lbl = new JLabel(lblText);
		lbl.setVerticalAlignment(SwingConstants.TOP);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setForeground(Color.BLACK);
		lbl.setBackground(UIManager.getColor("Button.highlight"));
		lbl.setFont(new Font("Algerian", Font.PLAIN, 16));
		getContentPane().add(lbl, BorderLayout.CENTER);
		
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose(); 
				player1.stopThread();
				player2.stopThread();
			}
		});
	}	
}
