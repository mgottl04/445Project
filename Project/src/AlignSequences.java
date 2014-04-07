import java.util.ArrayList;

public class AlignSequences {

	public double alignSequences(String structA, String seqA, String idA,
			String structB, String seqB, String idB) throws Exception {

		// Decompose into stem loops
		ArrayList<StemLoop> loopsA = DotBracketParser.getStemLoops(structA,
				seqA, idA);
		ArrayList<StemLoop> loopsB = DotBracketParser.getStemLoops(structB,
				seqB, idB);

		// Insert an empty StemLoop at the beginning of each
		loopsA.add(0, new StemLoop());
		loopsB.add(0, new StemLoop());

		// Create costs table
		double[][] P = new double[loopsA.size()][loopsB.size()];

		// Fill costs table
		for (int i = 0; i < loopsA.size(); i++) {
			for (int j = 0; j < loopsB.size(); j++) {
				P[i][j] = StemLoopAligner.alignStemLoops(loopsA.get(i),
						loopsB.get(j));
			}
		}

		// ** Apply classical string global alignment to the two sequences **

		// Create DP table
		TableEntry[][] T = new TableEntry[loopsA.size()][loopsB.size()];

		// Fill the table
		for (int i = 0; i < loopsA.size(); i++) {
			for (int j = 0; j < loopsB.size(); j++) {
				if (i == 0 && j == 0) {
					T[i][j] = new TableEntry(0, TableEntry.Pointer.NULL);
				} else if (i == 0) {
					T[i][j] = new TableEntry(T[i][j - 1].score + P[i][j],
							TableEntry.Pointer.LEFT);
				} else if (j == 0) {
					T[i][j] = new TableEntry(T[i - 1][j].score + P[i][j],
							TableEntry.Pointer.UP);
				} else {
					double paired_score = T[i - 1][j - 1].score + P[i][j];
					double i_to_gap_score = T[i - 1][j].score + P[i][0];
					double j_to_gap_score = T[i][j - 1].score + P[0][j];

					if (paired_score >= i_to_gap_score) {
						if (paired_score >= j_to_gap_score) {
							T[i][j] = new TableEntry(paired_score,
									TableEntry.Pointer.DIAGONAL);
						} else {
							T[i][j] = new TableEntry(j_to_gap_score,
									TableEntry.Pointer.LEFT);
						}
					} else {
						if (i_to_gap_score >= j_to_gap_score) {
							T[i][j] = new TableEntry(i_to_gap_score,
									TableEntry.Pointer.UP);
						} else {
							T[i][j] = new TableEntry(j_to_gap_score,
									TableEntry.Pointer.LEFT);
						}
					}
				}
			}
		}

		// Get edit distance
		double editDistance = T[loopsA.size()][loopsB.size()].score;

		// Recover alignment
		String seqA_loops = "";
		String seqB_loops = "";

		int current_row = loopsA.size();
		int current_col = loopsB.size();
		TableEntry.Pointer pointer = T[current_row][current_col].pointer;
		while (pointer != TableEntry.Pointer.NULL) {
			if (pointer == TableEntry.Pointer.DIAGONAL) {
				seqA_loops += current_row - 1;
				seqB_loops += current_col - 1;
				current_row--;
				current_col--;
			} else if (pointer == TableEntry.Pointer.LEFT) {
				seqA_loops += '-';
				seqB_loops += current_col - 1;
				current_col--;
			} else {
				seqA_loops += current_row - 1;
				seqB_loops += '-';
				current_row--;
			}
			pointer = T[current_row][current_col].pointer;
		}

		seqA_loops = new StringBuilder(seqA_loops).reverse().toString();
		seqB_loops = new StringBuilder(seqB_loops).reverse().toString();

		return editDistance;
	}
}
