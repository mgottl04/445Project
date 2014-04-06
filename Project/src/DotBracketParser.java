import java.util.ArrayList;
import java.util.List;

public class DotBracketParser {

	public static ArrayList<StemLoop> getStemLoops(String struct, String seq,
			String id) {
		// UUGGAUGUUGGCCUAGUUCUGUGUGGAAGACUAGUGAUUUUGUUGUUUUUAGAUAACUAAAUCGACAACAAAUCACAGUCUGCCAUAUGGCACAGGCCAUGCCUCUACAG
		// .((((.((((((((.((.((((((((.(((((.(((((((.(((((((((((....)))))..)))))))))))))))))).)))))))).)))))))).)).))))...
		List<Integer> openStack = new ArrayList<Integer>();
		List<Integer> closeStack = new ArrayList<Integer>();
		List<int[]> indices = new ArrayList<int[]>();
		int i = 0;
		char prevC = 0;
		for (char c : struct.toCharArray()) {
			if (c == '.') {
				i++;
				continue;
			}
			if (c == '(') {
				if (prevC == ')') {
					int closes = closeStack.size();
					int endClose = closeStack.get(closes - 1);
					int startOpen = openStack
							.get(openStack.size() - closes - 1);
					int[] indexRangeThing = { startOpen, endClose };
					indices.add(indexRangeThing);
					closeStack.clear();
					for (int j = 0; j < closes; j++) {
						openStack.remove(openStack.size() - j - 1);

					}
				}
				openStack.add(i);
			}
			if (c == ')')
				closeStack.add(i);
			prevC = c;
			i++;
		}
		int[] finalIndexThing = { openStack.get(0),
				closeStack.get(closeStack.size() - 1) };
		indices.add(finalIndexThing);
		List<String> seqs = new ArrayList<String>();
		List<String> structs = new ArrayList<String>();

		for (int[] ind : indices) {
			seqs.add("");
			structs.add("");
		}
		int seqSize = seq.length();
		char[] seqArray = seq.toCharArray();
		char[] structArray = struct.toCharArray();
		for (int j = 0; j < seqSize; j++) {
			int k = 0;
			for (int[] indexs : indices) {
				if (j >= indexs[0] && j <= indexs[1]) {
					seqs.get(j).concat(String.valueOf(seqArray[j]));
					structs.get(j).concat(String.valueOf(structArray[j]));
					break;
				}
				k++;
			}
		}
		List<StemLoop> result = new ArrayList<StemLoop>();
		int lastOpen = 0;
		int firstClose = 0;
		String lastStruct = structs.get(result.size() - 1);
		String lastSeq = seqs.get(result.size() - 1);
		int f = 0;
		for (char c : lastStruct.toCharArray()) {
			if (c == '(')
				lastOpen = f;
			if (c == ')') {
				firstClose = f;
				break;
			}
			f++;
		}

		structs.remove(result.size() - 1);
		structs.add(lastStruct.substring(0, lastOpen + 1).concat(
				lastStruct.substring(firstClose, lastStruct.length())));
		seqs.remove(result.size() - 1);
		seqs.add(lastSeq.substring(0, lastOpen + 1).concat(
				lastSeq.substring(firstClose, lastSeq.length())));
		for (int j = 0; j < seqs.size() - 1; j++) {
			result.add(new StemLoop(StemLoopModel.getModel(seqs.get(j),
					structs.get(j)), id));
		}
		return (ArrayList<StemLoop>) result;
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
