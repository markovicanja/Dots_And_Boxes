package etf.dotsandboxes.ma170420d;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Board extends Canvas {
	private int m, n;
	private boolean turn1 = true, turn2 = false;
	private static Color blue = new Color(0, 204, 255), red = new Color(255, 0, 0), lightBlue = new Color(148, 201, 255), lightRed = new Color(255, 158, 158);
	private static int widthHorizontal = 80, widthVertical = 20, heightHorizontal = 20, heightVertical = 80, arc = 10;
	private static int R = 20, r = 10;
	private static int tileWidth = 80, tileHeight = 80;
	private static Color edgeColor = new Color(62, 118, 114), currentColor = blue;
	private ArrayList<Edge> currentEdges = new ArrayList<>();
	private static boolean scored = false;
	
	private Edge[][] horizontal, vertical;
	private Tile[][] tiles;
	
	private static class Tile {
		private Color color;
		private boolean isColored = false;
		private int x, y;
		
		public Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public boolean isColored() {
			return isColored;
		}

		public void setColor(Color color) {
			this.color = color; 
			isColored = true;
		}
		
		public void paintTile(Graphics g) {
			g.setColor(color);
			g.fillRect(x + 10, y + 10, widthHorizontal - 10, heightVertical - 10);
		}
	}
	
	private static class Edge {
		private int x, y;
		private boolean horizontal, filled = false;
		private Color color;
		
		public Edge(int xx, int yy, boolean h) {
			x = xx; y = yy; horizontal = h;
		}	
		
		public void fillEdge() {
			filled = true;
		}

		public boolean isFilled() {
			return filled;
		}

		public void setFilled(boolean filled, Color color) {
			this.filled = filled;
			this.color = color;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public boolean isHorizontal() {
			return horizontal;
		}
		
		public void paintEdge(Graphics g, boolean paintColor) {
			if (paintColor)
				g.setColor(color);
			else g.setColor(edgeColor);
			if (horizontal)
				g.fillRoundRect(x, y, widthHorizontal, heightHorizontal, arc, arc);
			else g.fillRoundRect(x, y, widthVertical, heightVertical, arc, arc);
		}
		
		public boolean isClicked(int i, int j) {
			if (horizontal && i >= x && i <= x + widthHorizontal && j >= y && j <= y + heightHorizontal) return true;
			if (!horizontal && i >= x && i <= x + widthVertical && j >= y && j <= y + heightVertical) return true;
			return false;
		}
	}
	
	public Board(int m, int n, GamePlay gameplay) {
		this.m = m; 
		this.n = n;
		int d = 10;
		int x = d, y = d, dx = 80, dy = 80;
		horizontal = new Edge[m + 1][n];
		vertical = new Edge[m][n + 1];
		tiles = new Tile[m][n];
		for (int i = 0; i < n + 1; i++) { 
			for (int j = 0; j < m + 1; j++) {
				if (j < m) horizontal[i][j] = new Edge(x + r, y, true);
				if (i < n) vertical[i][j] = new Edge(x, y + r, false);
				if (j < m && i < n) tiles[i][j] = new Tile(x, y);
				x += dx;
			}	
			y += dy; x = d;
		}
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!scored) currentEdges.clear();
				scored = false;
				int scoreNum = 0;
				int x = e.getX();
				int y = e.getY();
				boolean found = false;
				for (int i = 0; i < m + 1; i++) {
					for (int j = 0; j < n + 1; j++) {
						if (j < m && horizontal[i][j].isClicked(x, y)) {
							found = true;
							if (turn1) horizontal[i][j].setFilled(true, lightBlue);
							else horizontal[i][j].setFilled(true, lightRed);
							currentEdges.add(horizontal[i][j]);
							if (i + 1 <= m && horizontal[i + 1][j].isFilled() && vertical[i][j].isFilled() && vertical[i][j + 1].isFilled()) {
								tiles[i][j].setColor(currentColor);
								scored = true;
								scoreNum++;
							}
							if (i - 1 >= 0 && horizontal[i - 1][j].isFilled() && vertical[i - 1][j].isFilled() && vertical[i - 1][j + 1].isFilled()) {
								tiles[i - 1][j].setColor(currentColor);
								scored = true;
								scoreNum++;
							}
							break;
						}
						if (i < n && vertical[i][j].isClicked(x, y)) {
							found = true;
							if (turn1) vertical[i][j].setFilled(true, lightBlue);
							else vertical[i][j].setFilled(true, lightRed);
							currentEdges.add(vertical[i][j]);
							if (j + 1 <= n && vertical[i][j + 1].isFilled() && horizontal[i][j].isFilled() && horizontal[i + 1][j].isFilled()) {
								tiles[i][j].setColor(currentColor);
								scored = true;
								scoreNum++;
							}
							if (j - 1 >= 0 && vertical[i][j - 1].isFilled() && horizontal[i][j - 1].isFilled() && horizontal[i + 1][j - 1].isFilled()) {
								tiles[i][j - 1].setColor(currentColor);
								scored = true;
								scoreNum++;
							}
							break;
						}
					}
					if (found) {
						repaint();
						break;
					}
				}
				if (!scored && found) {
					if (turn1) {
						currentColor = red;
						gameplay.enableLables(false);
					}
					else {
						currentColor = blue;
						gameplay.enableLables(true);
					}
					turn1 = !turn1;
					turn2 = !turn2;
				}		
				else if (found) {
					if (turn1) gameplay.setScore(true, scoreNum);
					else gameplay.setScore(false, scoreNum);
					scoreNum = 0;
				}
			}
		});
	}
	
	public void paint(Graphics g) {
		int d = 10;
		int x = d, y = d, dx = 80, dy = 80;
		for (int i = 0; i < n + 1; i++)
			for (int j = 0; j < m + 1; j++) {
				if (i < n && j < m && tiles[i][j].isColored()) 
					tiles[i][j].paintTile(g);
				if (j < m && horizontal[i][j].isFilled()) 
					horizontal[i][j].paintEdge(g, false);
				if (i < n && vertical[i][j].isFilled()) 
					vertical[i][j].paintEdge(g, false);
			}
		for (Edge edge : currentEdges) {
			if (turn1) edge.paintEdge(g, true);
			else edge.paintEdge(g, true);
		}
		for (int i = 0; i < n + 1; i++) {
			for (int j = 0; j < m + 1; j++) {;
				g.setColor(Color.gray);
				g.fillOval(x + 2, y + 2, R, R);
				g.setColor(Color.white);
				g.fillOval(x, y, R, R);
				x += dx;
			}	
			y += dy; x = d;
		}
	}
}