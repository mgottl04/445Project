public class IndexingPair {
	StemLoopNode nodeA;
	StemLoopNode nodeB;

	IndexingPair(StemLoopNode nodeA, StemLoopNode nodeB) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
	}
	
	public StemLoopNode getNodeA(){
		return this.nodeA;
	}
	
	public StemLoopNode getNodeB(){
		return this.nodeB;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IndexingPair) {
			if (nodeA != null && nodeB != null)
				return (nodeA.equals(((IndexingPair) obj).nodeA) && nodeB.equals(((IndexingPair) obj).nodeB));
		}		
		return false;
	}
}
