import java.util.ArrayList;


public class StemLoopAligner {
	
	public static double alignStemLoops(StemLoop a, StemLoop b) throws Exception {
		
		// Get indexing pairs
		ArrayList<StemLoopNode[]> indexingPairsA = getIndexingPairs(a, a.getRoot());
		ArrayList<StemLoopNode[]> indexingPairsB = getIndexingPairs(b, b.getRoot());
		
		// Insert a NULL indexing pair at the beginning of each
		indexingPairsA.add(0,null);
		indexingPairsB.add(0,null);
		
		// Initialize table
		double[][] D = new double[indexingPairsA.size()][indexingPairsB.size()];
		D[0][0] = 0;
		for (int i = 0; i < indexingPairsA.size(); i++) {
			StemLoopNode[] currentPair = indexingPairsA.get(i);
			double currentPairCost = 0;
			for (StemLoopNode node : a.subtree(currentPair[0], currentPair[1]).getAllNodes()) {
				currentPairCost += EditCosts.deleteCost(node);
			}
			D[i][0] = currentPairCost;
		}
		for (int j = 0; j < indexingPairsB.size(); j++) {
			StemLoopNode[] currentPair = indexingPairsB.get(j);
			double currentPairCost = 0;
			for (StemLoopNode node : b.subtree(currentPair[0], currentPair[1]).getAllNodes()) {
				currentPairCost += EditCosts.deleteCost(node);
			}
			D[0][j] = currentPairCost;
		}
		
		// Fill out the table
		for (int i = 1; i < indexingPairsA.size(); i++) {
			StemLoop.NodeType typeA1 = a.getType(indexingPairsA.get(i)[0]);
			StemLoop.NodeType typeA2 = a.getType(indexingPairsA.get(i)[1]);
			boolean bothAInternal = (typeA1 == StemLoop.NodeType.INTERNAL) && (typeA2 == StemLoop.NodeType.INTERNAL);
			for (int j = 1; i < indexingPairsB.size(); i++) {
				StemLoop.NodeType typeB1 = a.getType(indexingPairsB.get(j)[0]);
				StemLoop.NodeType typeB2 = a.getType(indexingPairsB.get(j)[1]);
				boolean bothBInternal = (typeB1 == StemLoop.NodeType.INTERNAL) && (typeB2 == StemLoop.NodeType.INTERNAL);
				
				if (bothAInternal && bothBInternal) {
					// do case 1
				}
				
				else if (bothAInternal) {
					// do case 3
				}
				
				else if (bothBInternal) {
					// do case 4
				}
				
				else {
					// do case 2
				}
			}
		}
		
		
		
		return 0.0;
	}
	
	private static ArrayList<StemLoopNode[]> getIndexingPairs(StemLoop tree, StemLoopNode node) throws Exception {
		
		if (tree.getType(node) != StemLoop.NodeType.INTERNAL) {
			throw new Exception("Input to getIndexPairs must be an internal node");
		}
		
		ArrayList<StemLoopNode[]> indexingPairs = new ArrayList<StemLoopNode[]>();
		
		// Add indexing pair for self (case 1)
		indexingPairs.add(new StemLoopNode[] {node,node});
		
		if (tree.isTerminal(node)) {
			StemLoopNode[] terminalLeaves = tree.getTerminalLeaves(node);
			
			// Add indexing pairs for terminal leaves (case 5)
			for (int i = 0; i < terminalLeaves.length; i++) {
				for (int j = i + 1; j < terminalLeaves.length; j++) {
					indexingPairs.add(new StemLoopNode[] {terminalLeaves[i],terminalLeaves[j]});
				}
			}
		}
		
		else {
			StemLoopNode[] leftLeaves = tree.getLeftLeaves(node);
			StemLoopNode[] rightLeaves = tree.getRightLeaves(node);
			
			// Add indexing pairs for left/right leaf pairs (case 4)
			for (int i = 0; i < leftLeaves.length; i++) {
				for (int j = rightLeaves.length - 1; j > -1; j--) {
					indexingPairs.add(new StemLoopNode[] {leftLeaves[i],rightLeaves[j]});
				}
			}
				
			// Add indexing pairs of left leaf and internal node (case 3)
			for (int i = 0; i < leftLeaves.length; i++) {
				indexingPairs.add(new StemLoopNode[] {leftLeaves[i],node});
			}
				
			// Add indexing pairs of internal node and right leaf (case 2)
			for (int j = rightLeaves.length - 1; j > -1; j--) {
				indexingPairs.add(new StemLoopNode[] {node,rightLeaves[j]});
			}
			
			// Recursively add indexing pairs for internal node children
			indexingPairs.addAll(getIndexingPairs(tree,tree.getInternalChild(node)));
		}
		
		return indexingPairs;
	}
	
}
