import java.awt.geom.Point2D;

public class PathStart {

	public static void main(String[] args) {

		int probSize = 8;
		PathFind pc = new PathFind(probSize);
		int maxLen = 0;
		int symmetricalMax = probSize/2 + probSize % 2;
		for (int i=0; i<symmetricalMax; i++) {
			for (int j=0; j<=i;j++) {
				System.out.format("Searching on %d, %d ...", i, j);
				int maxInstance = pc.findLongestPath(new Point2D.Double(i,j)) - 1;
				System.out.format(" Max of %d found.\n", maxInstance);
				pc.reset();
				maxLen = Math.max(maxLen, maxInstance);
			}
		}
		System.out.println("Max over all start points: " + maxLen);
	}

}
