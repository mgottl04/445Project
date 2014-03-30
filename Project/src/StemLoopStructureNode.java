import java.security.InvalidParameterException;

public class StemLoopStructureNode {

	private final String boundPair;
	private final String leftChildren;
	private final String rightChildren;
	private final String terminalChildren;

	public StemLoopStructureNode(String bp, String lc, String rc, String tc) {
		boundPair = bp;
		leftChildren = lc;
		rightChildren = rc;
		terminalChildren = tc;
		// TODO Auto-generated constructor stub
	}

	public String getRightChildren() {
		return rightChildren;
	}

	public String getLeftChildren() {
		return leftChildren;
	}

	public String getBoundPair() {
		return boundPair;
	}

	public String getTerminalChildren() {
		return terminalChildren;
	}

	public static StemLoopStructureNode getInstance(String bp, String lc,
			String rc, String tc) {
		if (!tc.equals("") && (!lc.equals("") || !rc.equals("")))
			throw new InvalidParameterException();
		return new StemLoopStructureNode(bp, lc, rc, tc);
	}

	public boolean hasTerminalChildren() {
		return !terminalChildren.equals("");
	}

	public boolean hasLeftChildren() {
		return !leftChildren.equals("");
	}

	public boolean hasRightChildren() {
		return !rightChildren.equals("");
	}

}
