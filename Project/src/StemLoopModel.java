import java.util.ArrayList;
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

	public StemLoopModelNode getRoot() {
		return nodes.get(1);

	}

	public String getTerminalLeaves() {
		return nodes.get(nodes.size() - 1).getTerminalChildren();
		// TODO Auto-generated method stub

	}

	public StemLoopNode[] modelNodesToNodes(String stemLoopId) {
		List<StemLoopNode> result = new ArrayList<StemLoopNode>();
		// Try to get this shit in order
		int i = 0;
		for (StemLoopModelNode n : nodes) {
			if (!n.getBoundPair().equals("")) {
				result.add(new StemLoopNode(n.getBoundPair(),
						StemLoopNode.NodeType.INTERNAL, stemLoopId
								+ String.valueOf(i)));
				i++;
			}
			for (char c : n.getLeftChildren().toCharArray()) {
				result.add(new StemLoopNode(String.valueOf(c),
						StemLoopNode.NodeType.LEFT_LEAF, stemLoopId
								+ String.valueOf(i)));
				i++;
			}
			for (char c : n.getRightChildren().toCharArray()) {
				result.add(new StemLoopNode(String.valueOf(c),
						StemLoopNode.NodeType.RIGHT_LEAF, stemLoopId
								+ String.valueOf(i)));
				i++;
			}
			for (char c : n.getTerminalChildren().toCharArray()) {
				result.add(new StemLoopNode(String.valueOf(c),
						StemLoopNode.NodeType.TERM_LEAF, stemLoopId
								+ String.valueOf(i)));
				i++;
			}

		}
		StemLoopNode[] resultArray = new StemLoopNode[result.size()];
		for (i = 0; i < result.size(); i++) {
			resultArray[i] = result.get(i);
		}
		return resultArray;
	}
}
