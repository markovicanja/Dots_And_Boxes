package etf.dotsandboxes.ma170420d;

import java.util.ArrayList;
import java.util.Collections;

import etf.dotsandboxes.ma170420d.Board.Edge;

public class Beginner extends GameSolver {

	public Beginner(Edge[][] horizontal, Edge[][] vertical, int m, int n) {
		super(horizontal, vertical, m, n);
	}

	@Override
	public Edge getNextMove() {
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
		if (returnEdge == null && edges.size() > 0) returnEdge = edges.get(0);
		return returnEdge;
	}
		
}
