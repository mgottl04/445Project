import java.util.List;


public class StemLoopTreeModel {
	private List<StemLoopStructureNode> nodes;
	public StemLoopTreeModel(List<StemLoopStructureNode> n) {
		nodes = n;
	}
	public static StemLoopTreeModel getModel(String seq, String struct) {
		return new StemLoopTreeModel(DotBracketParser.getNodes(seq, struct));
		
		
	}


}
