package etf.dotsandboxes.ma170420d;

import java.awt.*;
import java.awt.event.*;

import etf.dotsandboxes.ma170420d.GamePlay.Status;

public class Board extends Canvas {
	private int m, n;
	private boolean turn1 = true, turn2 = false;
	private static Color blue = new Color(0, 204, 255), red = new Color(255, 0, 0), lightBlue = new Color(148, 201, 255), lightRed = new Color(255, 158, 158);
	private static int widthHorizontal = 80, widthVertical = 20, heightHorizontal = 20, heightVertical = 80, arc = 10;
	private static int R = 20, r = 10;
	private static int tileWidth = 70, tileHeight = 70;
	private static Color edgeColor = new Color(62, 118, 114), currentColor = blue;
	private Edge currentEdge = null;
	private static boolean scored = false;
	private GamePlay gamePlay;
	
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
			g.fillRect(x + 10, y + 10, tileWidth, tileHeight);
		}
	}
	
	public static class Edge {
		private int x, y;
		private boolean horizontal, filled = false;
		private Color color;
		private int i, j;
		
		public Edge(int xx, int yy, boolean h, int i, int j) {
			x = xx; y = yy; horizontal = h;
			this.i = i; this.j = j;
		}	
		
		public int getI() {
			return i;
		}
		
		public int getJ() {
			return j;
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
		gamePlay = gameplay;
		horizontal = new Edge[m + 1][n];
		vertical = new Edge[m][n + 1];
		tiles = new Tile[m][n];
		for (int i = 0; i < m + 1; i++) { 
			for (int j = 0; j < n + 1; j++) {
				if (j < n) horizontal[i][j] = new Edge(x + r, y, true, i, j);
				if (i < m) vertical[i][j] = new Edge(x, y + r, false, i, j);
				if (i < m && j < n) tiles[i][j] = new Tile(x, y);
				x += dx;
			}	
			y += dy; x = d;
		}
		if (gamePlay.getFileIO() != null) gamePlay.getFileIO().makeHashMap(horizontal, vertical);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!gamePlay.mouseEnabled) return;
				int x = e.getX();
				int y = e.getY();
				boolean found = false;
				Edge edge = null;
				int i = 0, j = 0;
				for (i = 0; i < m + 1; i++) {
					for (j = 0; j < n + 1; j++) {
						if (j < n && horizontal[i][j].isClicked(x, y)) {
							found = true;
							edge = horizontal[i][j];
							break;
						}
						if (i < m && vertical[i][j].isClicked(x, y)) {
							found = true;
							edge = vertical[i][j];
							break;
						}
					}
					if (found) {
						repaint();
						makeMove(edge, i, j);
						break;
					}
				}
			}
		});
	}
	
	public Edge[][] getHorizontal() {
		return horizontal;
	}
	
	public Edge[][] getVertical() {
		return vertical;
	}
	
	public int colorTile(Edge edge, int i, int j) {
		int scoreNum = 0;
		if (edge.isHorizontal()) {
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
		}
		else {
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
		}
		return scoreNum;
	}	
	
	public void makeMove(Edge edge, int i, int j) {
		scored = false;
		int scoreNum = 0;
		if (edge.isFilled()) return;
		if (turn1) edge.setFilled(true, lightBlue);
		else edge.setFilled(true, lightRed);
		currentEdge = edge;
		gamePlay.getFileIO().write(gamePlay.getFileIO().toStringMap.get(edge));
		scoreNum = colorTile(edge, i, j);
		if (!scored) {
			if (turn1) {
				currentColor = red;
				gamePlay.enableLables(false);
				if (gamePlay.status == Status.PLAYING1) {
					gamePlay.player1().pauseThread();
					gamePlay.player2().continueThread();
				}
				gamePlay.status = Status.PLAYING2;
				if (gamePlay.player2().isBot()) gamePlay.mouseEnabled = false;
				else gamePlay.mouseEnabled = true;
			}
			else {
				currentColor = blue;
				gamePlay.enableLables(true);
				if (gamePlay.status == Status.PLAYING2) {
					gamePlay.player2().pauseThread();
					gamePlay.player1().continueThread();
				}
				gamePlay.status = Status.PLAYING1;
				if (gamePlay.player1().isBot()) gamePlay.mouseEnabled = false;
				else gamePlay.mouseEnabled = true;
			}
			turn1 = !turn1;
			turn2 = !turn2;
		}
		this.repaint();
		if (turn1) gamePlay.setScore(true, scoreNum);
		else gamePlay.setScore(false, scoreNum);
	}
	
	public void paint(Graphics g) {
		int d = 10;
		int x = d, y = d, dx = 80, dy = 80;
		for (int i = 0; i < m + 1; i++)
			for (int j = 0; j < n + 1; j++) {
				if (i < m && j < n && tiles[i][j].isColored()) 
					tiles[i][j].paintTile(g);
				if (j < n && horizontal[i][j].isFilled()) 
					horizontal[i][j].paintEdge(g, false);
				if (i < m && vertical[i][j].isFilled()) 
					vertical[i][j].paintEdge(g, false);
			}
		if (turn1 && currentEdge != null) currentEdge.paintEdge(g, true);
		else if (currentEdge != null) currentEdge.paintEdge(g, true);
		for (int i = 0; i < m + 1; i++) {
			for (int j = 0; j < n + 1; j++) {;
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