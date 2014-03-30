import java.util.ArrayList;


public class StemLoopAligner {
	
	public static double alignStemLoops(StemLoopTree a, StemLoopTree b) throws Exception {
		
		// Get indexing pairs
		ArrayList<StemLoopTreeNode[]> indexingPairsA = getIndexingPairs(a, a.getRoot());
		ArrayList<StemLoopTreeNode[]> indexingPairsB = getIndexingPairs(b, b.getRoot());
		
		return 0.0;
	}
	
	private static ArrayList<StemLoopTreeNode[]> getIndexingPairs(StemLoopTree tree, StemLoopTreeNode node) throws Exception {
		
		if (tree.getType(node) != StemLoopTree.NodeType.INTERNAL) {
			throw new Exception("Input to getIndexPairs must be an internal node");
		}
		
		ArrayList<StemLoopTreeNode[]> indexingPairs = new ArrayList<StemLoopTreeNode[]>();
		
		// Add indexing pair for self (case 1)
		indexingPairs.add(new StemLoopTreeNode[] {node,node});
		
		// Handle leaves
		StemLoopTreeNode[] leftLeaves = tree.getLeftLeaves(node);
		StemLoopTreeNode[] rightLeaves = tree.getRightLeaves(node);
		StemLoopTreeNode[] terminalLeaves = tree.getTerminalLeaves(node);
		
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
		
		// Add indexing pairs for terminal leaves (case 5)
		for (int i = 0; i < terminalLeaves.length; i++) {
			for (int j = i + 1; j < terminalLeaves.length; j++) {
				indexingPairs.add(new StemLoopTreeNode[] {terminalLeaves[i],terminalLeaves[j]});
			}
		}
		
		// Recursively add indexing pairs for internal node children
		indexingPairs.addAll(getIndexingPairs(tree,tree.getInternalChild(node)));
		
		return indexingPairs;
	}
	
}
