import java.util.ArrayList;
import java.util.List;

public class DotBracketParser {

	public static List<StemLoopModelNode> getNodes(String seq, String struct) {
		List<StemLoopModelNode> result = new ArrayList<StemLoopModelNode>();
		// get indices of (('s
		List<Integer> openIndices = new ArrayList<Integer>();
		List<Integer> closeIndices = new ArrayList<Integer>();

		int i = 0;
		for (char c : struct.toCharArray()) {
			if (c == '(') {
				openIndices.add(i);
			} else if (c == ')') {
				closeIndices.add(0, i);
			}
			i++;
		}

		StemLoopModelNode root = new StemLoopModelNode("root",
				seq.substring(0, openIndices.get(0)), seq.substring(
						closeIndices.get(0) + 1, seq.length()), "");
		result.add(root);
		for (int j = 0; j < openIndices.size() - 1; j++) {
			String bp = String.valueOf(seq.charAt(openIndices.get(j)))
					+ String.valueOf(seq.charAt(closeIndices.get(j)));
			String lc = seq.substring(openIndices.get(j) + 1,
					openIndices.get(j + 1));
			String rc = seq.substring(closeIndices.get(j + 1) + 1,
					closeIndices.get(j));
			StemLoopModelNode node = new StemLoopModelNode(bp, lc, rc,
					"");
			result.add(node);
		}
		StemLoopModelNode terminus = new StemLoopModelNode(
				String.valueOf(seq.charAt(openIndices.get(openIndices.size() - 1)))
						+ String.valueOf(seq.charAt(closeIndices
								.get(closeIndices.size() - 1))), "", "",
				seq.substring(openIndices.get(openIndices.size() - 1) + 1,
						closeIndices.get(closeIndices.size() - 1)));
		result.add(terminus);
		return result;
	}
}
