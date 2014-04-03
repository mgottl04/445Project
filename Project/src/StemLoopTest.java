import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StemLoopTest {
	String TEST_SEQ = "GAGXUACAAUAUGUATCCG";
	String TEST_STRUCT = "..(.(((.....))).)..";
	String TEST_SEQ2 = "GCGACUACCGUCGAGUAUGGC";
	String TEST_STRUCT2 ="..(.(((.(.....)..))))"; 
	ExpectedException e = ExpectedException.none();

	@Test
	public void testDataEntry() {
		assertEquals(TEST_SEQ2.length(), TEST_STRUCT2.length());
		;
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidNodeCreation() {
		String boundPair = "CG";
		String leftChildren = "CAA";
		String rightChildren = "UU";
		String terminalChildren = "AA";
		StemLoopModelNode node = StemLoopModelNode.getInstance(boundPair,
				leftChildren, rightChildren, terminalChildren);
		node = StemLoopModelNode.getInstance("root", leftChildren,
				rightChildren, terminalChildren);
	}

	@Test
	public void testNodeCreation() {
		String boundPair = "CG";
		String terminalChildren = "AA";
		StemLoopModelNode node = StemLoopModelNode.getInstance(boundPair, "", "",
				"AA");
		assertEquals(node.getBoundPair(), boundPair);
		assertEquals(node.getTerminalChildren(), terminalChildren);
		assertTrue(node.hasTerminalChildren());	
		assertFalse(node.hasLeftChildren());
		assertFalse(node.hasRightChildren());
		
	}

	@Test
	public void testTreeCreation() {
		StemLoopModel tree = StemLoopModel.getModel(TEST_SEQ2, TEST_STRUCT2);
		String s = "aa";
		assertTrue(tree.getNodes().get(1) instanceof StemLoopModelNode);

	}
	@Test
	public void testHowJavaWorks(){
		int[] a = {4};
		int[] b = {4};
		assertFalse(a.equals(b));
	}

	
}
