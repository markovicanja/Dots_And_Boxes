package etf.dotsandboxes.ma170420d;

import java.util.ArrayList;
import java.util.Collections;
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
	
	public Edge formSquare() {
		ArrayList<Edge> edgesToRemove = new ArrayList<>();
		Edge returnEdge = null;
		Collections.shuffle(edges);
		for (Edge edge: edges) {
			if (edge.isFilled()) {
				edgesToRemove.add(edge);
				continue;
			}
			int i = edge.getI();
			int j = edge.getJ();
			if (edge.isHorizontal()) {
				if (i + 1 <= m && horizontal[i + 1][j].isFilled() && vertical[i][j].isFilled() && vertical[i][j + 1].isFilled()) {
					returnEdge = edge;
					break;
				}
				if (i - 1 >= 0 && horizontal[i - 1][j].isFilled() && vertical[i - 1][j].isFilled() && vertical[i - 1][j + 1].isFilled()) {
					returnEdge = edge;
					break;
				}
			}
			else {
				if (j + 1 <= n && vertical[i][j + 1].isFilled() && horizontal[i][j].isFilled() && horizontal[i + 1][j].isFilled()) {
					returnEdge = edge;
					break;
				}
				if (j - 1 >= 0 && vertical[i][j - 1].isFilled() && horizontal[i][j - 1].isFilled() && horizontal[i + 1][j - 1].isFilled()) {
					returnEdge = edge;
					break;
				}
			}
		}
		edges.removeAll(edgesToRemove);
		return returnEdge;
	}
}
