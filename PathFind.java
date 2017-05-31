import java.awt.geom.*;
import java.util.*;

public class PathFind {

	private int problemSize;
	private int currentDepth;
	private int maxDepth;
	private ArrayList<Line2D> path;
	
	public PathFind(int problemSize) {
		this.problemSize = problemSize;
		this.currentDepth = 0;
		this.maxDepth = 0;
		this.path = new ArrayList<Line2D>();
	}
		
	private boolean validPoint(Point2D p) {
		return ( p.getX() >= 0 && p.getY() >= 0 && p.getX() < problemSize && p.getY() < problemSize );
	}
	
	private boolean fuzzyLineEq(Line2D l1, Line2D l2) {
		if (Math.abs(l1.getX1() - l2.getX1()) < 0.001 
			&& Math.abs(l1.getX2() - l2.getX2()) < 0.001
			&& Math.abs(l1.getY1() - l2.getY1()) < 0.001
			&& Math.abs(l1.getY2() - l2.getY2()) < 0.001) {
				return true;
		}
		if (Math.abs(l1.getX1() - l2.getX2()) < 0.001 
			&& Math.abs(l1.getX2() - l2.getX1()) < 0.001
			&& Math.abs(l1.getY1() - l2.getY2()) < 0.001
			&& Math.abs(l1.getY2() - l2.getY1()) < 0.001) {
				return true;
			}
			return false;
	}
	
	private boolean intersectsPath(Line2D line) {
		if (this.path.isEmpty()) {
			return false;
		}
		Line2D last = this.path.get(this.path.size()-1);
		if (fuzzyLineEq(line, last)) {
			return true;
		}
		for (int i=0;i<this.path.size()-1;i++) {
			if (line.intersectsLine(this.path.get(i))) return true;
		}
	
		return false;
	}
	
	private void attemptMove(int x, int y, Point2D last) {
		Point2D finish = new Point2D.Double(last.getX()+x, last.getY()+y);
		if (validPoint(finish)) {
			Line2D candidateLine = new Line2D.Double(last, finish);
			if (!intersectsPath(candidateLine)) {
				this.path.add(candidateLine);
				this.findLongestPath(finish);
				this.path.remove(this.path.size()-1);
			}
		}	
	}
	
	public int findLongestPath(Point2D start) {
		int[] moves = {1,-1, 2, -2};
		
		this.currentDepth += 1;
		if (this.currentDepth > this.maxDepth) {
			this.maxDepth = this.currentDepth;
		}
		
		for (int i=0;i<moves.length;i++) {
			int xMove = moves[i];
			int yMove = -1;
			if (Math.abs(xMove) == 1) {
				yMove = 2;
				attemptMove(xMove, yMove, start);
				yMove = -2;
				attemptMove(xMove, yMove, start);
			}
			else if (Math.abs(xMove) == 2) {
				yMove = 1;
				attemptMove(xMove, yMove, start);
				yMove = -1;
				attemptMove(xMove, yMove, start);
			}
			else {
				System.err.println("We should never have a move that's not 1 or 2.");
			}
		}
		this.currentDepth -= 1;
		return this.maxDepth;
	}
	
	public void reset() {
		if (this.currentDepth != 0) {
			System.err.println("current depth non-zero on reset is " + this.currentDepth);
		}
		if (this.path.size() != 0) {
			System.err.println("non-empty path on reset " + this.path.size() + " remaining");
		}
		this.maxDepth = 0;
		
	}

}
