
public class StemLoopNode {

	private int id;
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StemLoopNode) {
			return ((StemLoopNode) obj).id == id;
		}		
		return false;
	}
}
