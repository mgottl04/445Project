public class EditCosts {

	public static double insertCost(StemLoopNode node) {
		return 1.25;
	}

	public static double deleteCost(StemLoopNode node) {
		return 0.75;
	}

	public static double relableCost(StemLoopNode a, StemLoopNode b) {

		// Both internal
		if (a.getType() == StemLoopNode.NodeType.INTERNAL) {
			boolean p1_match = (a.getBase()).charAt(0) == (b.getBase()).charAt(0);
			boolean p2_match = (a.getBase()).charAt(1) == (b.getBase()).charAt(1);
			if (p1_match && p2_match) {
				return 0.0;
			}
			else if (p1_match || p2_match) {
				return 0.25;
			}
			else {
				return 0.4;
			}
		}

		// Both leaves
		else {
			if (a.getBase().equals(b.getBase())) {
				return 0.0;
			} else {
				return 0.25;
			}
		}
	}

	public static double alteringCost(StemLoopNode a, StemLoopNode b) {
		return 1;
	}

	public static double completionCost(StemLoopNode a, StemLoopNode b) {
		return 1;
	}

	public static double arcBreakingCost(StemLoopNode a, StemLoopNode b,
			StemLoopNode c) {
		return 0.5;

	}

	public static double arcCreationCost(StemLoopNode a, StemLoopNode b,
			StemLoopNode c) {
		return 0.5;
	}
}
