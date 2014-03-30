import java.util.ArrayList;


public class StemLoopAligner {
	
	public static double alignStemLoops(StemLoopTree a, StemLoopTree b) throws Exception {
		
		// Get indexing pairs
		ArrayList<StemLoopTreeNode[]> indexingPairsA = getIndexingPairs(a, a.getRoot());
		ArrayList<StemLoopTreeNode[]> indexingPairsB = getIndexingPairs(b, b.getRoot());
		
		// Insert a NULL indexing pair at the beginning of each
		indexingPairsA.add(0,null);
		indexingPairsB.add(0,null);
		
		// Initialize table
		double[][] D = new double[indexingPairsA.size()][indexingPairsB.size()];
		D[0][0] = 0;
		for (int i = 0; i < indexingPairsA.size(); i++) {
			StemLoopTreeNode[] currentPair = indexingPairsA.get(i);
			double currentPairCost = 0;
			for (StemLoopTreeNode node : a.subtree(currentPair[0], currentPair[1]).getAllNodes()) {
				currentPairCost += EditCosts.deleteCost(node);
			}
			D[i][0] = currentPairCost;
		}
		for (int j = 0; j < indexingPairsB.size(); j++) {
			StemLoopTreeNode[] currentPair = indexingPairsB.get(j);
			double currentPairCost = 0;
			for (StemLoopTreeNode node : b.subtree(currentPair[0], currentPair[1]).getAllNodes()) {
				currentPairCost += EditCosts.deleteCost(node);
			}
			D[0][j] = currentPairCost;
		}
		
		// Fill out the table
		for (int i = 1; i < indexingPairsA.size(); i++) {
			StemLoopTree.NodeType typeA1 = a.getType(indexingPairsA.get(i)[0]);
			StemLoopTree.NodeType typeA2 = a.getType(indexingPairsA.get(i)[1]);
			boolean bothAInternal = (typeA1 == StemLoopTree.NodeType.INTERNAL) && (typeA2 == StemLoopTree.NodeType.INTERNAL);
			for (int j = 1; i < indexingPairsB.size(); i++) {
				StemLoopTree.NodeType typeB1 = a.getType(indexingPairsB.get(j)[0]);
				StemLoopTree.NodeType typeB2 = a.getType(indexingPairsB.get(j)[1]);
				boolean bothBInternal = (typeB1 == StemLoopTree.NodeType.INTERNAL) && (typeB2 == StemLoopTree.NodeType.INTERNAL);
				
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
	
	private static ArrayList<StemLoopTreeNode[]> getIndexingPairs(StemLoopTree tree, StemLoopTreeNode node) throws Exception {
		
		if (tree.getType(node) != StemLoopTree.NodeType.INTERNAL) {
			throw new Exception("Input to getIndexPairs must be an internal node");
		}
		
		ArrayList<StemLoopTreeNode[]> indexingPairs = new ArrayList<StemLoopTreeNode[]>();
		
		// Add indexing pair for self (case 1)
		indexingPairs.add(new StemLoopTreeNode[] {node,node});
		
		if (tree.isTerminal(node)) {
			StemLoopTreeNode[] terminalLeaves = tree.getTerminalLeaves(node);
			
			// Add indexing pairs for terminal leaves (case 5)
			for (int i = 0; i < terminalLeaves.length; i++) {
				for (int j = i + 1; j < terminalLeaves.length; j++) {
					indexingPairs.add(new StemLoopTreeNode[] {terminalLeaves[i],terminalLeaves[j]});
				}
			}
		}
		
		else {
			StemLoopTreeNode[] leftLeaves = tree.getLeftLeaves(node);
			StemLoopTreeNode[] rightLeaves = tree.getRightLeaves(node);
			
			// Add indexing pairs for left/right leaf pairs (case 4)
			for (int i = 0; i < leftLeaves.length; i++) {
				for (int j = rightLeaves.length - 1; j > -1; j--) {
					indexingPairs.add(new StemLoopTreeNode[] {leftLeaves[i],rightLeaves[j]});
				}
			}
				
			// Add indexing pairs of left leaf and internal node (case 3)
			for (int i = 0; i < leftLeaves.length; i++) {
				indexingPairs.add(new StemLoopTreeNode[] {leftLeaves[i],node});
			}
				
			// Add indexing pairs of internal node and right leaf (case 2)
			for (int j = rightLeaves.length - 1; j > -1; j--) {
				indexingPairs.add(new StemLoopTreeNode[] {node,rightLeaves[j]});
			}
			
			// Recursively add indexing pairs for internal node children
			indexingPairs.addAll(getIndexingPairs(tree,tree.getInternalChild(node)));
		}
		
		return indexingPairs;
	}
	
}
