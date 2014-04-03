public class IndexingPair {
	StemLoopNode nodeA;
	StemLoopNode nodeB;

	IndexingPair(StemLoopNode nodeA, StemLoopNode nodeB) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IndexingPair)
			return (nodeA.equals(((IndexingPair) obj).nodeA) && nodeB.equals(((IndexingPair) obj).nodeB));
		return false;
	}
}
