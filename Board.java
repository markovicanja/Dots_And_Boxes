package etf.dotsandboxes.ma170420d;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends Canvas {
	private int m, n;
	private Tile[][] tiles;
	private JLabel player1Label, player2Label, score1Label, score2Label;
	private boolean turn1 = true, turn2 = false;
	private static Color blue = new Color(0, 204, 255), red = new Color(255, 0, 0);
	private static int widthHorizontal = 80, widthVertical = 20, heightHorizontal = 20, heightVertical = 80, arc = 10;
	private static int R = 20, r = 10;
	private static int tileWidth = 80, tileHeight = 80;
	
	public static class Tile {
		boolean right, left, up, down;
		int numberOfSetEdges = 0;
		
		public Tile() {
			right = left = up = down = false;
		}
		
		public int numberOfSetEdges() {
			return numberOfSetEdges;
		}

		public boolean isRight() {
			return right;
		}

		public void setRight(boolean right) {
			this.right = right;
			numberOfSetEdges++;
		}

		public boolean isLeft() {
			return left;
		}

		public void setLeft(boolean left) {
			this.left = left;
			numberOfSetEdges++;
		}

		public boolean isUp() {
			return up;
		}

		public void setUp(boolean up) {
			this.up = up;
			numberOfSetEdges++;
		}

		public boolean isDown() {
			return down;
		}

		public void setDown(boolean down) {
			this.down = down;
			numberOfSetEdges++;
		}
	}
	
	public Board(int m, int n, JLabel p1l, JLabel p2l, JLabel s1l, JLabel s2l) {
		this.m = m; 
		this.n = n;
		player1Label = p1l;
		player2Label = p2l;
		score1Label = s1l;
		score2Label = s2l;
		tiles = new Tile[m][n];
		for (int i = 0; i < m; i++) 
			for (int j = 0; j < n; j++) {
				tiles[i][j] = new Tile();
			}
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
			}
		});
	}
	
	public boolean fillEdge(Color c, int x, int y) {
		return true;
	}

	public void fillEdges(Graphics g, int x, int y) {
		
	}
	
	public void fillTiles(Graphics g, int x, int y) {
		
	}
	
	public void paint(Graphics g) {
		int d = 10;
		int x = d, y = d, dx = 80, dy = 80;
		for (int i = 0; i < n + 1; i++) {
			for (int j = 0; j < m + 1; j++) {
				g.setColor(Color.lightGray);
				if (j < m) g.drawRoundRect(x + r, y, widthHorizontal, heightHorizontal, arc, arc);
				if (i < n) g.drawRoundRect(x, y + r, widthVertical, heightVertical, arc, arc);
				fillTiles(g, x, y);
				fillEdges(g, x, y);
				g.fillOval(x + 2, y + 2, R, R);
				g.setColor(Color.white);
				g.fillOval(x, y, R, R);
				x += dx;
			}	
			y += dy; x = d;
		}
	}
}