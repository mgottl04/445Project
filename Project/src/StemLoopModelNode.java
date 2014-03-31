import java.security.InvalidParameterException;

public class StemLoopModelNode {

	private final String boundPair;
	private final String leftChildren;
	private final String rightChildren;
	private final String terminalChildren;

	public StemLoopModelNode(String bp, String lc, String rc, String tc) {
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

	public static StemLoopModelNode getInstance(String bp, String lc,
			String rc, String tc) {
		if (!tc.equals("") && (!lc.equals("") || !rc.equals("")))
			throw new InvalidParameterException();
		return new StemLoopModelNode(bp, lc, rc, tc);
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
