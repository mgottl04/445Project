import java.security.InvalidParameterException;
import java.util.ArrayList;
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

		for (StemLoopNode n : nodes)
			if (n.getType() == StemLoopNode.NodeType.INTERNAL)
				return n;
		return null;
	}

	public StemLoopNode[] getAllNodes() {
		return this.nodes;

	}

	// get left leaves (order matters) (throw exception if not internal node)
	public StemLoopNode[] getLeftLeaves(StemLoopNode internalNode) {
		List<StemLoopNode> result = new ArrayList<StemLoopNode>();
		boolean seen = false;
		for (StemLoopNode node : nodes) {
			if (seen && node.getType() == StemLoopNode.NodeType.INTERNAL) {
				break;
			}
			if (node.equals(internalNode)) {
				seen = true;
			}
			if (seen && node.getType() == StemLoopNode.NodeType.LEFT_LEAF) {
				result.add(node);
			}

		}
		return (StemLoopNode[]) result.toArray();

	}

	// get right leaves (order matters) (throw exception if not internal node)
	public StemLoopNode[] getRightLeaves(StemLoopNode internalNode) {
		List<StemLoopNode> result = new ArrayList<StemLoopNode>();
		boolean seen = false;
		for (StemLoopNode node : nodes) {
			if (seen && node.getType() == StemLoopNode.NodeType.INTERNAL) {
				break;
			}
			if (node.equals(internalNode)) {
				seen = true;
			}
			if (seen && node.getType() == StemLoopNode.NodeType.RIGHT_LEAF) {
				result.add(node);
			}

		}
		return (StemLoopNode[]) result.toArray();
	}

	// get terminal leaves (order matters) (throw exception if not internal
	// node)
	public StemLoopNode[] getTerminalLeaves(StemLoopNode internalNode) {
		boolean seen = false;
		List<StemLoopNode> result = new ArrayList<StemLoopNode>();
		for (StemLoopNode node : nodes) {
			if (seen && node.getType() == StemLoopNode.NodeType.INTERNAL) {
				break;
			}
			if (node.equals(internalNode)) {
				seen = true;
			}
			if (seen && node.getType() == StemLoopNode.NodeType.TERM_LEAF) {
				result.add(node);
			}

		}
		return (StemLoopNode[]) result.toArray();

	}

	// is this the last internal node? (i.e. does not have an internal node
	// child) (throw exception if not internal node)
	public boolean isTerminal(StemLoopNode internalNode) {
		boolean seen = false;
		for (StemLoopNode node : nodes) {
			if (seen && node.getType() == StemLoopNode.NodeType.INTERNAL) {
				return false;
			}
			if (node.equals(internalNode)) {
				seen = true;
			}
		}

		return true;
	}

	// node.getType() is public. There's no point for this, I reckon.
	public StemLoopNode.NodeType getType(StemLoopNode node) {
		return node.getType();

	}

	// get predecessor (return NULL if undefined)
	public StemLoopNode p(StemLoopNode node) {
		int i = -1;
		int parentIndex = 0;
		for (StemLoopNode n : nodes) {
			i++;
			if (n.equals(node)) {
				break;
			}
			if (n.getType() == StemLoopNode.NodeType.INTERNAL) {
				parentIndex = i;
			}

		}
		if (i == 0) {
			// this means root's predecessor, which is undefined
			return null;
		}
		StemLoopNode potentialPredecessor = nodes[i - 1];
		if (potentialPredecessor.getType() == node.getType())
			return potentialPredecessor;

		return nodes[parentIndex];

	}

	// get successor (return NULL if undefined)
	public StemLoopNode s(StemLoopNode node) {
		int i = -1;
		int parentIndex = 0;
		for (StemLoopNode n : nodes) {
			i++;
			if (n.equals(node)) {
				break;
			}
			if (n.getType() == StemLoopNode.NodeType.INTERNAL) {
				parentIndex = i;
			}

		}
		if (i == 0) {
			// this means root's successor, which is undefined
			return null;
		}
		StemLoopNode potentialSuccesor = nodes[i + 1];
		if (potentialSuccesor.getType() == node.getType())
			return potentialSuccesor;
		return nodes[parentIndex];
	}

	// TODO What is this?
	// get internal node child (throw exception if there is no internal child
	// node)
	public StemLoopNode getInternalChild(StemLoopNode internalNode) {
		if (internalNode.getType() != StemLoopNode.NodeType.INTERNAL) {
			throw new InvalidParameterException("expected an internal node");
		}
		boolean seen = false;
		for (StemLoopNode n : nodes) {
			if (seen && n.getType() == StemLoopNode.NodeType.INTERNAL) {
				return n;
			}
			if (n.equals(internalNode)) {
				seen = true;
			}
		}
		return null;

	}

	public StemLoop subtree(StemLoopNode nodeA, StemLoopNode nodeB) {
		// TODO Auto-generated method stub
		return null;
	}

}
