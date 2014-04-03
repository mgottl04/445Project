import java.util.List;

public class StemLoop {

	StemLoopNode[] nodes;
	private StemLoopModel model;
	private String id;

	private StemLoop(StemLoopModel m, String i) {
		model = m;
		nodes = m.modelNodesToNodes(i);
		this.id = i;

	}

	public StemLoopNode getRoot() {
		return new StemLoopNode(model.getRoot().getBoundPair(),
				StemLoopNode.NodeType.INTERNAL, model.getRoot()
						.getLeftChildren());
	}

	public StemLoopNode[] getAllNodes() {
		return this.nodes;

	}

	// get left leaves (order matters) (throw exception if not internal node)
	public StemLoopNode[] getLeftLeaves(StemLoopNode internalNode) {
		return null;

	}

	// get right leaves (order matters) (throw exception if not internal node)
	public StemLoopNode[] getRightLeaves(StemLoopNode internalNode) {
		return null;

	}

	// get terminal leaves (order matters) (throw exception if not internal
	// node)
	public String getTerminalLeaves(StemLoopNode internalNode) {
		return model.getTerminalLeaves();

	}

	private StemLoopNode[] getStemLoopNodes(char[] nodes) {
		return null;
	}

	// get internal node child (throw exception if there is no internal child
	// node)
	public StemLoopNode getInternalChild(StemLoopNode internalNode) {
		return internalNode;

	}

	// is this the last internal node? (i.e. does not have an internal node
	// child) (throw exception if not internal node)
	public boolean isTerminal(StemLoopNode internalNode) {
		return internalNode.equals(nodes.get(nodes.size() - 1));
	}

	// get predecessor (return NULL if undefined)
	public StemLoopNode p(StemLoopNode node) {
		return node.getPredecessor();

	}

	// get successor (return NULL if undefined)
	public StemLoopNode s(StemLoopNode node) {
		return node.getPredecessor();

	}

	// DONE
	// DONE

	// DONE

	// DONE

	// DONE

	// DONE

	public StemLoopNode.NodeType getType(StemLoopNode node) {
		return node.getType();

	}

	public StemLoop subtree(StemLoopNode nodeA, StemLoopNode nodeB) {
		// TODO Auto-generated method stub
		return null;
	}
}
