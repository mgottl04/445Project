import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class StemLoop {

	StemLoopNode[] nodes;
	private StemLoopModel model;
	private String id;
	private boolean isEmpty;

	public StemLoop(StemLoopModel m, String i) {
		model = m;
		nodes = m.modelNodesToNodes(i);
		this.id = i;
		isEmpty = false;
	}

	public StemLoop() {
		isEmpty = true;
	}

	public boolean isEmpty() {
		return isEmpty;
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
		List<StemLoopNode> result = new ArrayList<StemLoopNode>();
		StemLoopNode.NodeType typeA = nodeA.getType();
		StemLoopNode.NodeType typeB = nodeB.getType();
		// case 1 : nodeB == nodeA
		if (nodeA.equals(nodeB)) {
			for (StemLoopNode n : nodes) {
				result.add(n);
				if (n.equals(nodeA))
					break;
			}
		}
		// case2 : nodeA is parent to nodeB
		else if (getParent(nodeB).equals(nodeA)) {
			boolean aSeen = false;
			boolean bSeen = false;
			if (nodeB.getType() == StemLoopNode.NodeType.RIGHT_LEAF) {
				for (StemLoopNode n : nodes) {
					if (n.equals(nodeB))
						bSeen = true;
					if (!aSeen) {
						result.add(n);
					} else {
						if (n.getType() == StemLoopNode.NodeType.LEFT_LEAF)
							continue;
						if (n.getType() == StemLoopNode.NodeType.INTERNAL)
							break;
						if (n.getType() == StemLoopNode.NodeType.RIGHT_LEAF) {
							if (!bSeen)
								continue;
							else {
								result.add(n);
							}

						}
					}
					if (n.equals(nodeA))
						aSeen = true;
				}

			} else if (nodeB.getType() == StemLoopNode.NodeType.LEFT_LEAF) {
				for (StemLoopNode n : nodes) {
					if (n.equals(nodeB))
						bSeen = true;
					if (!aSeen || bSeen) {
						result.add(n);
					} else {
						continue;
					}
					if (n.equals(nodeA))
						aSeen = true;
				}
			}
		}
		// case3 : nodeB is parent to nodeA
		else if (getParent(nodeA).equals(nodeB)) {
			boolean bSeen = false;
			boolean aSeen = false;
			if (nodeA.getType() == StemLoopNode.NodeType.RIGHT_LEAF) {
				for (StemLoopNode n : nodes) {
					if (n.equals(nodeA))
						aSeen = true;
					if (!bSeen) {
						result.add(n);
					} else {
						if (n.getType() == StemLoopNode.NodeType.LEFT_LEAF)
							continue;
						if (n.getType() == StemLoopNode.NodeType.INTERNAL)
							break;
						if (n.getType() == StemLoopNode.NodeType.RIGHT_LEAF) {
							if (!aSeen)
								continue;
							else {
								result.add(n);
							}

						}
					}
					if (n.equals(nodeB))
						bSeen = true;
				}

			} else if (nodeA.getType() == StemLoopNode.NodeType.LEFT_LEAF) {
				for (StemLoopNode n : nodes) {
					if (n.equals(nodeA))
						aSeen = true;
					if (!bSeen || aSeen) {
						result.add(n);
					} else {
						continue;
					}
					if (n.equals(nodeB))
						bSeen = true;
				}
			}
		}
		// case4 : nodeA & nodeB both same type of leaves to same parent
		if (getParent(nodeA).equals(getParent(nodeB))
				&& nodeA.getType() == nodeB.getType()) {
			boolean aSeen = false;
			boolean bSeen = false;

			for (StemLoopNode n : nodes) {
				if ((aSeen & !bSeen) || (!aSeen && bSeen)) {
					if (n.equals(nodeB))
						bSeen = true;
					if (n.equals(nodeA))
						aSeen = true;
					if ((aSeen & !bSeen) || (!aSeen && bSeen))
						continue;
				}
				result.add(n);
				if (n.equals(nodeB))
					bSeen = true;
				if (n.equals(nodeA))
					aSeen = true;

			}
		}
		// case5 : nodeA & nodeB are leaves to same parent, but different types
		// of nodes
		else {
			boolean aSeen = false;
			boolean bSeen = false;
			if (nodeA.getType() == StemLoopNode.NodeType.LEFT_LEAF) {
				for (StemLoopNode n : nodes) {
					if (n.equals(nodeB))
						bSeen = true;
					if (bSeen) {
						if (n.getType() == StemLoopNode.NodeType.INTERNAL)
							break;
					}
					if (aSeen & !bSeen)
						continue;

					if (n.equals(nodeA))
						aSeen = true;
				}
			} else {
				for (StemLoopNode n : nodes) {
					if (n.equals(nodeA))
						aSeen = true;
					if (aSeen) {
						if (n.getType() == StemLoopNode.NodeType.INTERNAL)
							break;
					}
					if (bSeen & !aSeen)
						continue;

					if (n.equals(nodeB))
						bSeen = true;
				}
			}
		}

		return new StemLoop(model, (StemLoopNode[]) result.toArray(), id
				+ "sub" + nodeA.getId() + nodeB.getId());
	}

	private StemLoop(StemLoopModel m, StemLoopNode[] n, String i) {
		model = m;
		nodes = n;
		id = i;
	}

	private StemLoopNode getParent(StemLoopNode node) {
		StemLoopNode parent = null;
		for (StemLoopNode n : nodes) {
			if (n.equals(node))
				break;
			if (n.getType() == StemLoopNode.NodeType.INTERNAL) {
				parent = n;
			}
		}
		return parent;
	}
}
