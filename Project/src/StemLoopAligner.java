import java.util.ArrayList;

public class StemLoopAligner {

	public static double alignStemLoops(StemLoop a, StemLoop b)
			throws Exception {

		// Get indexing pairs and terminal indexing pairs
		ArrayList<IndexingPair> indexingPairsA = new ArrayList<IndexingPair>();
		ArrayList<IndexingPair> termIndexingPairsA =  new ArrayList<IndexingPair>();
		ArrayList<IndexingPair> indexingPairsB = new ArrayList<IndexingPair>();
		ArrayList<IndexingPair> termIndexingPairsB = new ArrayList<IndexingPair>();
		getIndexingPairs(a,a.getRoot(),indexingPairsA,termIndexingPairsA);
		getIndexingPairs(b,b.getRoot(),indexingPairsB,termIndexingPairsB);

		// Insert a NULL indexing pair at the beginning of each
		indexingPairsA.add(0, null);
		indexingPairsB.add(0, null);

		// Create hash from indexing pair to table index

		// Initialize table
		double[][] D = new double[indexingPairsA.size()][indexingPairsB.size()];
		D[0][0] = 0;
		for (int i = 0; i < indexingPairsA.size(); i++) {
			IndexingPair currentPair = indexingPairsA.get(i);
			double currentPairCost = 0;
			for (StemLoopNode node : a.subtree(currentPair.nodeA,
					currentPair.nodeB).getAllNodes()) {
				currentPairCost += EditCosts.deleteCost(node);
			}
			D[i][0] = currentPairCost;
		}
		for (int j = 0; j < indexingPairsB.size(); j++) {
			IndexingPair currentPair = indexingPairsB.get(j);
			double currentPairCost = 0;
			for (StemLoopNode node : b.subtree(currentPair.nodeA,
					currentPair.nodeB).getAllNodes()) {
				currentPairCost += EditCosts.deleteCost(node);
			}
			D[0][j] = currentPairCost;
		}

		// Fill out the table
		for (int i = 1; i < indexingPairsA.size(); i++) {

			// Process first indexing pair
			IndexingPair x_y = indexingPairsA.get(i);
			StemLoopNode x = x_y.nodeA;
			StemLoopNode y = x_y.nodeB;

			// Set up indexing pairs for look-up
			IndexingPair px_y = new IndexingPair(a.p(x), y);
			IndexingPair x_sy = new IndexingPair(x, a.s(y));
			IndexingPair px_sy = new IndexingPair(a.p(x), a.s(y));
			int x_y_index = i;
			int px_y_index = indexingPairsA.indexOf(px_y);
			int x_sy_index = indexingPairsA.indexOf(x_sy);
			int px_sy_index = indexingPairsA.indexOf(px_sy);

			// Determine type
			StemLoopNode.NodeType typex = a.getType(x);
			StemLoopNode.NodeType typey = a.getType(y);
			boolean bothAInternal = (typex == StemLoopNode.NodeType.INTERNAL)
					&& (typey == StemLoopNode.NodeType.INTERNAL);

			for (int j = 1; j < indexingPairsB.size(); j++) {

				// Process second indexing pair
				IndexingPair u_v = indexingPairsB.get(j);
				StemLoopNode u = u_v.nodeA;
				StemLoopNode v = u_v.nodeB;

				// Set up indexing pairs for look-up
				IndexingPair pu_v = new IndexingPair(b.p(u), v);
				IndexingPair u_sv = new IndexingPair(u, b.s(v));
				IndexingPair pu_sv = new IndexingPair(b.p(u), b.s(v));
				int u_v_index = j;
				int pu_v_index = indexingPairsB.indexOf(pu_v);
				int u_sv_index = indexingPairsB.indexOf(u_sv);
				int pu_sv_index = indexingPairsB.indexOf(pu_sv);

				// Determine type
				StemLoopNode.NodeType typeu = b.getType(u);
				StemLoopNode.NodeType typev = b.getType(v);
				boolean bothBInternal = (typeu == StemLoopNode.NodeType.INTERNAL)
						&& (typev == StemLoopNode.NodeType.INTERNAL);

				// Compute D[i][j] depending on type

				if (bothAInternal && bothBInternal) {
					double valA = D[px_sy_index][pu_sv_index]
							+ EditCosts.relableCost(x, u);
					double valB = D[px_sy_index][u_v_index]
							+ EditCosts.deleteCost(x);
					double valC = D[x_y_index][pu_sv_index]
							+ EditCosts.deleteCost(u);

					D[i][j] = multiMin(valA, valB, valC);
				}

				else if (bothAInternal) {
					double valA = D[px_sy_index][u_v_index];
					valA = valA < 0 ? valA = Math.log(0) : valA
							+ EditCosts.deleteCost(x);
					double valB = D[x_y_index][pu_v_index];
					valB = valB < 0 ? valB = Math.log(0) : valB
							+ EditCosts.insertCost(u);
					double valC = D[x_y_index][u_sv_index];
					valC = valC < 0 ? valC = Math.log(0) : valC
							+ EditCosts.insertCost(v);
					double valD = D[px_sy_index][pu_v_index];
					valD = valD < 0 ? valD = Math.log(0) : valD
							+ EditCosts.alteringCost(x, u);
					double valE = D[px_sy_index][u_sv_index];
					valE = valE < 0 ? valE = Math.log(0) : valE
							+ EditCosts.alteringCost(x, v);
					double valF = D[px_sy_index][pu_sv_index];
					valF = valF < 0 ? valF = Math.log(0) : valF
							+ EditCosts.arcBreakingCost(x, u, v);

					D[i][j] = multiMin(valA, valB, valC, valD, valE, valF);
				}

				else if (bothBInternal) {
					double valA = D[x_y_index][pu_sv_index];
					valA = valA < 0 ? valA = Math.log(0) : valA
							+ EditCosts.insertCost(u);
					double valB = D[px_y_index][u_v_index];
					valB = valB < 0 ? valB = Math.log(0) : valB
							+ EditCosts.deleteCost(x);
					double valC = D[x_sy_index][u_v_index];
					valC = valC < 0 ? valC = Math.log(0) : valC
							+ EditCosts.deleteCost(y);
					double valD = D[px_y_index][pu_sv_index];
					valD = valD < 0 ? valD = Math.log(0) : valD
							+ EditCosts.completionCost(x, u);
					double valE = D[x_sy_index][pu_sv_index];
					valE = valE < 0 ? valE = Math.log(0) : valE
							+ EditCosts.completionCost(y, u);
					double valF = D[px_sy_index][pu_sv_index];
					valF = valF < 0 ? valF = Math.log(0) : valF
							+ EditCosts.arcCreationCost(x, y, u);

					D[i][j] = multiMin(valA, valB, valC, valD, valE, valF);
				}

				else {
					double valA = D[px_y_index][u_v_index];
					valA = valA < 0 ? valA = Math.log(0) : valA
							+ EditCosts.deleteCost(x);
					double valB = D[x_sy_index][u_v_index];
					valB = valB < 0 ? valB = Math.log(0) : valB
							+ EditCosts.deleteCost(y);
					double valC = D[x_y_index][pu_v_index];
					valC = valC < 0 ? valC = Math.log(0) : valC
							+ EditCosts.insertCost(u);
					double valD = D[x_y_index][u_sv_index];
					valD = valD < 0 ? valD = Math.log(0) : valD
							+ EditCosts.insertCost(v);
					double valE = D[px_y_index][u_v_index];
					valE = valE < 0 ? valE = Math.log(0) : valE
							+ EditCosts.relableCost(x, u);
					double valF = D[x_sy_index][u_sv_index];
					valF = valF < 0 ? valF = Math.log(0) : valF
							+ EditCosts.relableCost(y, v);

					D[i][j] = multiMin(valA, valB, valC, valD, valE, valF);
				}
			}
		}

		// Return results
		return 0.0;
	}

	private static void getIndexingPairs(StemLoop tree, StemLoopNode node,
			ArrayList<IndexingPair> indexingPairs,
			ArrayList<IndexingPair> termIndexingPairs) throws Exception {

		if (tree.getType(node) != StemLoopNode.NodeType.INTERNAL) {
			throw new Exception(
					"Input to getIndexPairs must be an internal node");
		}

		// Add indexing pair for self (case 1)
		indexingPairs.add(new IndexingPair(node, node));

		if (tree.isTerminal(node)) {
			StemLoopNode[] terminalLeaves = tree.getTerminalLeaves(node);

			// Add indexing pairs for terminal leaves (case 5)
			for (int i = 0; i < terminalLeaves.length; i++) {
				for (int j = i + 1; j < terminalLeaves.length; j++) {
					indexingPairs.add(new IndexingPair(terminalLeaves[i],
							terminalLeaves[j]));
					if ()
				}
			}
		}

		else {
			StemLoopNode[] leftLeaves = tree.getLeftLeaves(node);
			StemLoopNode[] rightLeaves = tree.getRightLeaves(node);

			// Add indexing pairs for left/right leaf pairs (case 4)
			for (int i = 0; i < leftLeaves.length; i++) {
				for (int j = rightLeaves.length - 1; j > -1; j--) {
					indexingPairs.add(new IndexingPair(leftLeaves[i],
							rightLeaves[j]));
				}
			}

			// Add indexing pairs of left leaf and internal node (case 3)
			for (int i = 0; i < leftLeaves.length; i++) {
				indexingPairs.add(new IndexingPair(leftLeaves[i], node));
			}

			// Add indexing pairs of internal node and right leaf (case 2)
			for (int j = rightLeaves.length - 1; j > -1; j--) {
				indexingPairs.add(new IndexingPair(node, rightLeaves[j]));
			}

			// Recursively add indexing pairs for internal node children
			getIndexingPairs(tree, tree.getInternalChild(node), indexingPairs,
					termIndexingPairs);
		}
	}

<<<<<<< HEAD
=======
	private static double doCase1(double[][] table, StemLoop a, StemLoop b,
			StemLoopNode x, StemLoopNode y, StemLoopNode u, StemLoopNode v) {

		StemLoopNode predx = a.p(x);
		StemLoopNode succy = a.s(y);
		StemLoopNode predu = b.p(u);
		StemLoopNode succv = b.s(v);

		IndexingPair u_v = new IndexingPair(u, v);
		IndexingPair px_y = new IndexingPair(predx, y);
		return 0.0;
	}

>>>>>>> 572e07e638ce2bdb36b52b42ea0c6ac6fbd7ee99
	private static double multiMin(double... ds) {
		double min = -Math.log(0);
		for (double d : ds) {
			min = Math.min(min, d);
		}
		return min;
	}
}
