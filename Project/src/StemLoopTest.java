import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StemLoopTest {
	String TEST_SEQ = "GAGXUACAAUAUGUATCCG";
	String TEST_STRUCT = "..(.(((.....))).)..";
	String TEST_SEQ2 = "GCGACUACCGUCGAGUAUGGC";
	String TEST_STRUCT2 = "..(.(((.(.....)..))))";
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
		StemLoopModelNode node = StemLoopModelNode.getInstance(boundPair, "",
				"", "AA");
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
	public void testHowJavaWorks() {
		int[] a = { 4 };
		int[] b = { 4 };
		assertFalse(a.equals(b));
		String j = "ab";
		System.out.println(j.substring(0, 1));
		System.out.println(j.substring(1, 2));
	}
	
	@Test
	public void testMakeTree()	{
		String seq1 = "ACAACCGU";
		String str1 = "((....))";
		StemLoopModel tree = StemLoopModel.getModel(seq1, str1);
		
		System.out.println("StemLoopModel: ");
		for(int i =0; i<tree.getNodes().size(); i++){
			System.out.println("Node no: "+ i);
			System.out.println("BoundPair: "+ tree.getNodes().get(i).getBoundPair());
			System.out.println("LeftChildren: "+ tree.getNodes().get(i).getLeftChildren());
			System.out.println("RightChildren: "+ tree.getNodes().get(i).getRightChildren());
			System.out.println("Terminal: "+ tree.getNodes().get(i).getTerminalChildren());
			System.out.println("");
		}
		
		StemLoopNode[] tStemLoopNode = tree.modelNodesToNodes("0");
		for(int i=0; i<tStemLoopNode.length;i++){
			System.out.println("Node no: "+ i);
			System.out.println("Node Type: "+ tStemLoopNode[i].getType());
			System.out.println("Base: "+ tStemLoopNode[i].getBase());
			System.out.println("");
		}
		
	}

}
