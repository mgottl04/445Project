import java.util.List;
import StemLoopNode;
import StemLoopNode.NodeType;

public class StemLoop {



	private List<StemLoopNode> nodes;
	private StemLoopModel model;

	private StemLoop(StemLoopModel m) {

		model = m;

	}

	public StemLoop(String seq, String struct) {
		// TODO Auto-generated constructor stub
	}

	public StemLoopNode getRoot() {
		return new StemLoopNode(model.getRoot());
	}

	public List<StemLoopNode> getAllNodes() {
		return this.nodes;

	}

	public NodeType getType(StemLoopNode node) {
		return node.getType();

	}

	// get left leaves (order matters) (throw exception if not internal node)
	public StemLoopNode[] getLeftLeaves(
			StemLoopNode internalNode) {
		return null;

	}

	// get right leaves (order matters) (throw exception if not internal node)
	public StemLoopNode[] getRightLeaves(
			StemLoopNode internalNode) {
		return null;

	}

	// get terminal leaves (order matters) (throw exception if not internal
	// node)
	public StemLoopNode[] getTerminalLeaves(
			StemLoopNode internalNode) {
		return null;

	}

	// get internal node child (throw exception if there is no internal child
	// node)
	public StemLoopNode getInternalChild(
			StemLoopNode internalNode) {
		return internalNode;

	}

	// is this the last internal node? (i.e. does not have an internal node
	// child) (throw exception if not internal node)
	public boolean isTerminal(StemLoopNode internalNode) {
		return internalNode.equals(nodes.get(nodes.size() - 1));
	}

	// get predecessor (return NULL if undefined)
	public StemLoopNode p(StemLoopNode node) {
		return node;

	}

	// get successor (return NULL if undefined)
	public StemLoopNode s(StemLoopNode node) {
		return node;

	}

	// get subtree defined by indexing pair
	public StemLoop subtree(StemLoopNode a, StemLoopNode b) {
		return null;

	}

	public static StemLoop getTree(String seq, String struct) {
		return new StemLoop(seq, struct);
	}
}
