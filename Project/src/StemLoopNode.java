
public class StemLoopNode {

	private int id;
	public enum NodeType {
		INTERNAL, LEFT_LEAF, RIGHT_LEAF, TERM_LEAF
	}
	public StemLoopNode(StemLoopModelNode root) {
		
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StemLoopNode) {
			return ((StemLoopNode) obj).id == id;
		}		
		return false;
	}


	public NodeType getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
