package etf.dotsandboxes.ma170420d;

import java.util.ArrayList;
import etf.dotsandboxes.ma170420d.Board.Edge;

public abstract class GameSolver {
	protected ArrayList<Edge> edges;
	protected Edge[][] horizontal, vertical;
	protected int m, n;
	
	public GameSolver(Edge[][] horizontal, Edge[][] vertical, int m, int n) {		
		edges = new ArrayList<>();
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.m = m; 
		this.n = n;
		for (int i = 0; i < m + 1; i++) { 
			for (int j = 0; j < n + 1; j++) {
				if (j < n) edges.add(horizontal[i][j]);
				if (i < m) edges.add(vertical[i][j]);
			}	
		}
	}
	
	public abstract Edge getNextMove();
}
