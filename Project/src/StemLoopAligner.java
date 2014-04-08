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

		// Initialize table
		double[][] D = new double[indexingPairsA.size()][indexingPairsB.size()];
		D[0][0] = 0;
		for (int i = 1; i < indexingPairsA.size(); i++) {
			IndexingPair currentPair = indexingPairsA.get(i);
			double currentPairCost = 0;
			for (StemLoopNode node : a.subtree(currentPair.nodeA,
					currentPair.nodeB).getAllNodes()) {
				currentPairCost += EditCosts.deleteCost(node);
			}
			D[i][0] = currentPairCost;
		}
		for (int j = 1; j < indexingPairsB.size(); j++) {
			IndexingPair currentPair = indexingPairsB.get(j);
			double currentPairCost = 0;
			//System.out.println("b");
			StemLoop sub = b.subtree(currentPair.nodeA,
					currentPair.nodeB);
			for (StemLoopNode node : sub.getAllNodes()) {
				currentPairCost += EditCosts.insertCost(node);
			}
			D[0][j] = currentPairCost;
		}
		
		// Handle case where first stem loop is empty
		if (a.isEmpty()) {
			return D[0][indexingPairsB.size() - 1];
		}
		
		// Handle case where second stem loop is empty
		if (b.isEmpty()) {
			return D[indexingPairsA.size() - 1][0];
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
			int px_y_index = -1;
			int x_sy_index = -1;
			int px_sy_index = -1;
			
			// handle index at root
			if(a.p(x) == null && a.s(y) == null) {
				px_y_index = 0;
				x_sy_index = 0;
				px_sy_index = 0;
			}
			
			else {
				px_y_index = indexingPairsA.indexOf(px_y);
				x_sy_index = indexingPairsA.indexOf(x_sy);
				px_sy_index = indexingPairsA.indexOf(px_sy);
			}
							
			//int px_y_index = indexingPairsA.indexOf(px_y);
			//int x_sy_index = indexingPairsA.indexOf(x_sy);
			//int px_sy_index = indexingPairsA.indexOf(px_sy);

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
				int pu_v_index = -1;
				int u_sv_index = -1;
				int pu_sv_index = -1;
				
				// handle index at root
				if(b.p(u) == null && b.s(v) == null) {
					pu_v_index = 0;
					u_sv_index = 0;
					pu_sv_index = 0;
				}
				
				else {
					pu_v_index = indexingPairsB.indexOf(pu_v);
					u_sv_index = indexingPairsB.indexOf(u_sv);
					pu_sv_index = indexingPairsB.indexOf(pu_sv);
				}
				
				// Determine type
				StemLoopNode.NodeType typeu = b.getType(u);
				StemLoopNode.NodeType typev = b.getType(v);
				boolean bothBInternal = (typeu == StemLoopNode.NodeType.INTERNAL)
						&& (typev == StemLoopNode.NodeType.INTERNAL);

				// Compute D[i][j] depending on type

				// CASE 1
				if (bothAInternal && bothBInternal) {
					double valA = D[px_sy_index][pu_sv_index]
							+ EditCosts.relableCost(x, u);
					double valB = D[px_sy_index][u_v_index]
							+ EditCosts.deleteCost(x);
					double valC = D[x_y_index][pu_sv_index]
							+ EditCosts.insertCost(u);

					D[i][j] = multiMin(valA, valB, valC);
				}

				// CASE 3
				else if (bothAInternal) {
					double valA;
					if (px_sy_index < 0 || u_v_index < 0)
						valA = -Math.log(0);
					else
						valA = D[px_sy_index][u_v_index] + EditCosts.deleteCost(x);
					
					double valB;
					if (x_y_index < 0 || pu_v_index < 0)
						valB = -Math.log(0);
					else
						valB = D[x_y_index][pu_v_index] + EditCosts.insertCost(u);;
					
					double valC;
					if (x_y_index < 0 || u_sv_index < 0)
						valC = -Math.log(0);
					else
						valC = D[x_y_index][u_sv_index] + EditCosts.insertCost(v);
					
					double valD;
					if (px_sy_index < 0 || pu_v_index < 0)
						valD = -Math.log(0);
					else
						valD = D[px_sy_index][pu_v_index] + EditCosts.alteringCost(x, u);
					
					double valE;
					if (px_sy_index < 0 || u_sv_index < 0)
						valE = -Math.log(0);
					else
						valE = D[px_sy_index][u_sv_index] + EditCosts.alteringCost(x, v);
					
					double valF;
					if (px_sy_index < 0 || pu_sv_index < 0)
						valF = -Math.log(0);
					else
						valF = D[px_sy_index][pu_sv_index] + EditCosts.arcBreakingCost(x, u, v);

					D[i][j] = multiMin(valA, valB, valC, valD, valE, valF);
				}

				// CASE 4                                             
				else if (bothBInternal) {
					
					double valA;
					if ( x_y_index < 0 || pu_sv_index < 0)
						 valA = -Math.log(0);
					else
						 valA = D[x_y_index][pu_sv_index] + EditCosts.insertCost(u);
					
					double valB;
					if ( px_y_index < 0 || u_v_index < 0)
						valB = -Math.log(0);
					else
						valB = D[px_y_index][u_v_index] + EditCosts.deleteCost(x);
					
					double valC;
					if ( x_sy_index < 0 || u_v_index < 0)
						valC = -Math.log(0);
					else
						valC = D[x_sy_index][u_v_index] + EditCosts.deleteCost(y);
					
					double valD;
					if ( px_y_index < 0 || pu_sv_index < 0)
						valD = -Math.log(0);
					else
						valD = D[px_y_index][pu_sv_index] + EditCosts.completionCost(x, u);
					
					double valE;
					if ( x_sy_index < 0 || pu_sv_index < 0)
						valE = -Math.log(0);
					else
						valE = D[x_sy_index][pu_sv_index] + EditCosts.completionCost(y, u);
					
					double valF;
					if ( px_sy_index < 0 ||  pu_sv_index < 0)
						valF = -Math.log(0);
					else
						valF = D[px_sy_index][pu_sv_index] + EditCosts.arcCreationCost(x, y, u);

					D[i][j] = multiMin(valA, valB, valC, valD, valE, valF);
				}

				// CASE 2
				else {
					double valA;
   					if ( px_y_index < 0 ||  u_v_index < 0)
						valA = -Math.log(0);
					else
						valA = D[px_y_index][u_v_index] + EditCosts.deleteCost(x);
					
					double valB;
					if (x_sy_index < 0 || u_v_index  < 0)
						valB = -Math.log(0);
					else
						valB = D[x_sy_index][u_v_index] + EditCosts.deleteCost(y);
					
					double valC;
					if ( x_y_index < 0 || pu_v_index < 0)
						valC = -Math.log(0);
					else
						valC = D[x_y_index][pu_v_index] + EditCosts.insertCost(u);
					
					double valD;
					if (x_y_index < 0 || u_sv_index < 0)
						valD = -Math.log(0);
					else
						valD = D[x_y_index][u_sv_index] + EditCosts.insertCost(v);
					
					double valE;
					if ( px_y_index < 0 || pu_v_index < 0)
						valE = -Math.log(0);
					else
						valE = D[px_y_index][pu_v_index] + EditCosts.relableCost(x, u) ;
					
					double valF;
					if (x_sy_index < 0 || u_sv_index < 0)
						valF = -Math.log(0);
					else
						valF = D[x_sy_index][u_sv_index] + EditCosts.relableCost(y, v);

					D[i][j] = multiMin(valA, valB, valC, valD, valE, valF);
				}
			}
		}

		// Find best result among terminal indexing pairs
		double bestScore = -Math.log(0);
		for (IndexingPair pairA : termIndexingPairsA) {
			int indexA = indexingPairsA.indexOf(pairA);
			for (IndexingPair pairB : termIndexingPairsB) {
				int indexB = indexingPairsB.indexOf(pairB);
				bestScore = Math.min(bestScore,D[indexA][indexB]);
			}
		}
		
 		return bestScore;
	}

	private static void getIndexingPairs(StemLoop tree, StemLoopNode node,
			ArrayList<IndexingPair> indexingPairs,
			ArrayList<IndexingPair> termIndexingPairs) throws Exception {

		if (tree.isEmpty()) {
			return;
		}

		if (tree.getType(node) != StemLoopNode.NodeType.INTERNAL) {
			throw new Exception(
					"Input to getIndexPairs must be an internal node");
		}

		// Add indexing pair for self (case 1)
		indexingPairs.add(new IndexingPair(node, node));

		if (tree.isTerminal(node)) {
			StemLoopNode[] terminalLeaves = tree.getTerminalLeaves(node);

			// FIXING BUG IN PAPER - add indexing pairs for node + flanking leaves
			indexingPairs.add(new IndexingPair(terminalLeaves[0],node));
			indexingPairs.add(new IndexingPair(node,terminalLeaves[terminalLeaves.length - 1]));
			
			// Add indexing pairs for terminal leaves (case 5)
			for (int i = 0; i < terminalLeaves.length; i++) {
				for (int j = terminalLeaves.length - 1; j > i; j--) {
					indexingPairs.add(new IndexingPair(terminalLeaves[i],
							terminalLeaves[j]));
					if (j == i + 1) {
						termIndexingPairs.add(new IndexingPair(
								terminalLeaves[i], terminalLeaves[j]));
					}
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

	private static double multiMin(double... ds) {
		double min = -Math.log(0);
		for (double d : ds) {
			min = Math.min(min, d);
		}
		return min;
	}
}
