public class StemLoopNode {

	private String id;
	private NodeType type;
	private String base;

	public enum NodeType {
		INTERNAL, LEFT_LEAF, RIGHT_LEAF, TERM_LEAF
	}

	public StemLoopNode(String b, NodeType t, String i) {
		base = b;
		id = i;
		type = t;

	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StemLoopNode) {
			return ((StemLoopNode) obj).id == id;
		}
		return false;
	}

	public NodeType getType() {
		return type;

	}

	public String getBase() {
		return base;
	}

	public String getId() {
		return id;
	}

	public StemLoopNode getPredecessor() {
		// TODO Auto-generated method stub
		return null;
	}



}
