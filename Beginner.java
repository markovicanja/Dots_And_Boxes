package etf.dotsandboxes.ma170420d;

import etf.dotsandboxes.ma170420d.Board.Edge;

public class Beginner extends GameSolver {

	public Beginner(Edge[][] horizontal, Edge[][] vertical, int m, int n) {
		super(horizontal, vertical, m, n);
	}

	@Override
	public Edge getNextMove() {
		Edge edge = super.formSquare();
		if (edge == null && edges.size() > 0) edge = edges.get(0);
		return edge;
	}
}
