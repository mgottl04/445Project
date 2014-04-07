import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DotBracketParser {
	static List<String> ids;

	public static ArrayList<StemLoop> getStemLoops(String struct, String seq,
			String id) {
		ids = new ArrayList<String>();
		ids.removeAll(ids);
		ArrayList<Loop> loops = new ArrayList<Loop>();
		getLoops(loops, struct, 0);

		List<String> seqs = new ArrayList<String>();
		List<String> structs = new ArrayList<String>();

		for (Loop ind : loops) {
			seqs.add("");
			structs.add("");
		}
		int seqSize = seq.length();
		char[] seqArray = seq.toCharArray();
		char[] structArray = struct.toCharArray();
		for (int j = 0; j < seqSize; j++) {
			int k = 0;
			for (Loop indexs : loops) {
				if ( j >= 306 && j <= 323) {
					System.out.println("somethingsomething");
				}
				
				if (j >= indexs.indexA && j <= indexs.indexB) {
					String newSeq = seqs.get(k).concat(
							String.valueOf(seqArray[j]));
					seqs.add(k, newSeq);
					seqs.remove(k + 1);
					String newStruct = structs.get(k).concat(
							String.valueOf(structArray[j]));
					structs.add(k, newStruct);
					structs.remove(k + 1);
					break;
				}
				k++;
			}
		}
		List<StemLoop> result = new ArrayList<StemLoop>();

		for (int i = 0; i < loops.size(); i++) {
			Loop l = loops.get(i);
			if (l.isMulti) {
				int lastOpen = 0;
				int firstClose = 0;
				char[] sct = structs.get(i).toCharArray();
				for (int j = 0; j < sct.length; j++) {
					if (sct[j] == '(')
						lastOpen = j;
					if (sct[j] == ')') {
						firstClose = j;
						break;
					}
				}
				String newStruct = structs.get(i).substring(0, lastOpen + 1)
						+ structs.get(i).substring(firstClose,
								structs.get(i).length());
				String oldStruct = structs.get(i);

				int opens = 0;
				int closes = 0;
				int newOpens = 0;
				int newCloses = 0;
				for (char c : newStruct.toCharArray()) {
					if (c == ')')
						newCloses += 1;
					if (c == '(')
						newOpens += 1;

				}
				for (char c : oldStruct.toCharArray()) {
					if (c == ')')
						closes += 1;
					if (c == '(')
						opens += 1;

				}
				if (opens != newOpens || closes != newCloses) {
					System.out.println("you're fucked");
				}
				structs.add(i, newStruct);
				structs.remove(i + 1);
				String newSeq = seqs.get(i).substring(0, lastOpen + 1)
						+ seqs.get(i).substring(firstClose,
								seqs.get(i).length());
				seqs.add(i, newSeq);
				seqs.remove(i + 1);
			}
		}
		for (int j = 0; j < seqs.size() - 1; j++) {
			result.add(new StemLoop(StemLoopModel.getModel(seqs.get(j),
					structs.get(j)), id));
		}
		return (ArrayList<StemLoop>) result;
	}

	private static void getLoops(ArrayList<Loop> loops, String struct,
			int offset) {
		Stack<Integer> openStack = new Stack<Integer>();
		char prevC = 0;
		boolean inLoop = false;
		boolean isMulti = false;
		int lastPopped = -1;
		for (int i = offset; i < struct.length(); i++) {
			char c = struct.charAt(i);
			if (c == '.') {
				continue;
			}
			if (c == '(') {
				inLoop = true;
				if (prevC == ')') {
					isMulti = true;
					getLoops(loops, struct, i);
				}
				openStack.push(i);
			}
			if (!openStack.isEmpty() && c == ')') {
				lastPopped = openStack.pop();
			}

			if (inLoop && openStack.isEmpty()) {
				Loop currLoop = new Loop(lastPopped, i, isMulti);
				if (!ids.contains(String.valueOf(lastPopped) + "%"
						+ String.valueOf(i))) {
					ids.add(String.valueOf(lastPopped) + "%"
							+ String.valueOf(i));
					loops.add(currLoop);
				}
				inLoop = false;
			}
			prevC = c;
		}
	}

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
		if (openIndices.get(0) != 0) {
			StemLoopModelNode root = new StemLoopModelNode("root",
					seq.substring(0, openIndices.get(0)), seq.substring(
							closeIndices.get(0) + 1, seq.length()), "");
			result.add(root);
		}
		for (int j = 0; j < openIndices.size() - 1; j++) {
			String bp = String.valueOf(seq.charAt(openIndices.get(j)))
					+ String.valueOf(seq.charAt(closeIndices.get(j)));
			String lc = seq.substring(openIndices.get(j) + 1,
					openIndices.get(j + 1));
			String rc = seq.substring(closeIndices.get(j + 1) + 1,
					closeIndices.get(j));
			StemLoopModelNode node = new StemLoopModelNode(bp, lc, rc, "");
			result.add(node);
		}
		StemLoopModelNode terminus = new StemLoopModelNode(String.valueOf(seq
				.charAt(openIndices.get(openIndices.size() - 1)))
				+ String.valueOf(seq.charAt(closeIndices.get(closeIndices
						.size() - 1))), "", "", seq.substring(
				openIndices.get(openIndices.size() - 1) + 1,
				closeIndices.get(closeIndices.size() - 1)));
		result.add(terminus);
		return result;
	}
}
