import java.util.List;


public class StemLoopTree {

	public enum NodeType {
		INTERNAL,
		LEFT_LEAF,
		RIGHT_LEAF,
		TERM_LEAF
	}

	private List<StemLoopStructureNode> nodes;
	
	private StemLoopTree(String seq, String struct) {
		
	}

	public StemLoopStructureNode getRoot() {
		return null;
		
	}
	
	public List<StemLoopStructureNode> getAllNodes() {
		return this.nodes;
		
	}
	
	public NodeType getType(StemLoopStructureNode node) {
		return null;
		
	}
	
	// get left leaves (order matters) (throw exception if not internal node)
	public StemLoopStructureNode[] getLeftLeaves(StemLoopStructureNode internalNode) {
		return null;
		
	}
	
	// get right leaves (order matters) (throw exception if not internal node)
	public StemLoopStructureNode[] getRightLeaves(StemLoopStructureNode internalNode) {
		return null;
		
	}	
	
	// get terminal leaves (order matters) (throw exception if not internal node)
	public StemLoopStructureNode[] getTerminalLeaves(StemLoopStructureNode internalNode) {
		return null;
		
	}
	
	// get internal node child (throw exception if there is no internal child node)
	public StemLoopStructureNode getInternalChild(StemLoopStructureNode internalNode) {
		return internalNode;
		
	}
	
	// is this the last internal node? (i.e. does not have an internal node child) (throw exception if not internal node)
	public boolean isTerminal(StemLoopStructureNode internalNode) {
		return internalNode.equals(nodes.get(nodes.size()-1));
	}
	
	// get predecessor (return NULL if undefined)
	public StemLoopStructureNode p(StemLoopStructureNode node) {
		return node;
		
	}
	
	// get successor (return NULL if undefined)
	public StemLoopStructureNode s(StemLoopStructureNode node) {
		return node;
		
	}
	
	// get subtree defined by indexing pair
	public StemLoopTree subtree(StemLoopStructureNode a, StemLoopStructureNode b) {
		return null;
		
	}

	public static StemLoopTree getTree(String seq, String struct) {		
		return new StemLoopTree(seq, struct);
	}
}
