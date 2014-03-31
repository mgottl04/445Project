import java.util.List;

public class StemLoopModel {
	private List<StemLoopModelNode> nodes;

	public StemLoopModel(List<StemLoopModelNode> n) {
		nodes = n;
	}

	public static StemLoopModel getModel(String seq, String struct) {
		return new StemLoopModel(DotBracketParser.getNodes(seq, struct));

	}

	public List<StemLoopModelNode> getNodes() {
		// TODO Auto-generated method stub
		return nodes;
	}

	public void getRoot() {
		// TODO Auto-generated method stub
		
	}

}
