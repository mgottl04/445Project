
public class StemLoopTree {

	public enum NodeType {
		INTERNAL,
		LEFT_LEAF,
		RIGHT_LEAF,
		TERM_LEAF
	}
	
	public StemLoopTreeNode getRoot() {
		
	}
	
	public NodeType getType(StemLoopTreeNode node) {
		
	}
	
	// get left leaves (order matters) (throw exception if not internal node)
	public StemLoopTreeNode[] getLeftLeaves(StemLoopTreeNode node) {
		
	}
	
	// get right leaves (order matters) (throw exception if not internal node)
	public StemLoopTreeNode[] getRightLeaves(StemLoopTreeNode node) {
		
	}	
	
	// get terminal leaves (order matters) (throw exception if not internal node)
	public StemLoopTreeNode[] getTerminalLeaves(StemLoopTreeNode node) {
		
	}
	
	// get internal node child (throw exception if not internal node)
	public StemLoopTreeNode getInternalChild(StemLoopTreeNode node) {
		
	}
	
	// get predecessor
	public StemLoopTreeNode p(StemLoopTreeNode node) {
		
	}
	
	// get successor
	public StemLoopTreeNode s(StemLoopTreeNode node) {
		
	}
	
	// get subtree defined by indexing pair
	public StemLoopTree subtree(StemLoopTreeNode a, StemLoopTreeNode b) {
		
	}
}
