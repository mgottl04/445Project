
public class StemLoopTree {

	public enum NodeType {
		INTERNAL,
		LEFT_LEAF,
		RIGHT_LEAF,
		TERM_LEAF
	}
	
	public StemLoopTreeNode getRoot() {
		
	}
	
	public StemLoopTreeNode[] getAllNodes() {
		
	}
	
	public NodeType getType(StemLoopTreeNode node) {
		
	}
	
	// get left leaves (order matters) (throw exception if not internal node)
	public StemLoopTreeNode[] getLeftLeaves(StemLoopTreeNode internalNode) {
		
	}
	
	// get right leaves (order matters) (throw exception if not internal node)
	public StemLoopTreeNode[] getRightLeaves(StemLoopTreeNode internalNode) {
		
	}	
	
	// get terminal leaves (order matters) (throw exception if not internal node)
	public StemLoopTreeNode[] getTerminalLeaves(StemLoopTreeNode internalNode) {
		
	}
	
	// get internal node child (throw exception if there is no internal child node)
	public StemLoopTreeNode getInternalChild(StemLoopTreeNode internalNode) {
		
	}
	
	// is this the last internal node? (i.e. does not have an internal node child) (throw exception if not internal node)
	public boolean isTerminal(StemLoopTreeNode internalNode) {
		
	}
	
	// get predecessor (return NULL if undefined)
	public StemLoopTreeNode p(StemLoopTreeNode node) {
		
	}
	
	// get successor (return NULL if undefined)
	public StemLoopTreeNode s(StemLoopTreeNode node) {
		
	}
	
	// get subtree defined by indexing pair
	public StemLoopTree subtree(StemLoopTreeNode a, StemLoopTreeNode b) {
		
	}
}
