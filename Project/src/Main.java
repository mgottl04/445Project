import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static final String SCORE_FILE_STRING = "editDistTest.txt";

	public static void main(String[] args) throws Exception {
		String tseq1 = "gaggaaaguccgggcUCCAUGGAAGCGCGGUGCCGGAUAACGUCCGGCGGGGGCGACCUCAGGGAAAGUGCCACAGAAAGCAAACCGCCCUCGAGGCCGAAAGGCUUCGCGGAGGGUAAGGGUGAAAGGGUGCGGUAAGAGCGCACCGCGUCUUUGGCAACAAAGGCGGCAAGGCAAACCCCACCGGGAGCAAGACCAAAUAGGGGCUGCACGGACGAGAGAUCGUCCAGGUCUGUUUCCAGACCCGCGGCCCGGGUUGGUUGCAAGAGGCGUCUCGCAAGAGGCGUCCCAGAUGAAUGGCCAUCACCUCGCAGCAAUGCGAGGAacagaacccggcuua";
		String tstruc1 = "..............((((.........((((((((((.....)))))(((((....)))).)((...(((((............((((((((((((((....))))))...))))))..)).......((((((.......))))))(((((((((....)))))))).)..)))..)))))))))))))...((((......(((((((..((((((....)))))).((((........)))).))))))).....))))......((((((((....))))))))..................(((((((....)))))))................";
		String tseq2 = "gaggaaagucCGGGCUCCUUCGGACAGGGCGCCAGGUAACGCCUGGGGGGCGUGAGCCCACGGAAAGUGCCACAGAAAAUAUACCGCCAGCUUCGGCUGGUAAGGGUGAAAUGGUGCGGUAAGAGCGCACCGCGCGACUGGCAACGGCUUGCGGCACGGUAAACCCCGCCCGGAGCAAGACCAAAUAGGGGAGCAUGUCCGUCGUGUCCGAACGGGCUCCCGGGUAGGUUGCUUGAGGUGGCCGGUGACGGCUAUCCCAGAUGAAUGGUUGUCGAUGacagaacccggcuuac";
		String tstruct2 = "..............((((........((((((((((.....)))))(((((....)))).)((...(((((............((((((((....))))))..)).......((((((.......))))))(((((((((....))).))))).)..)))..)))))))))))))...((((......((((((....((((.........)))))))))).....))))......((((((((....)))))))).....................................";
		ArrayList<StemLoop> loops1 = DotBracketParser.getStemLoops(tstruc1,
				tseq1, "t1");
		ArrayList<StemLoop> loops2 = DotBracketParser.getStemLoops(tstruct2,
				tseq2, "t2");
		loops1 = null;
		loops1 = new ArrayList<StemLoop>();
		loops1.remove(0);

		// Relable
		String seqA = "UACAACCGU";
		String structA = ".((....))";
		String seqB = "UAUAACCGU";
		String structB = ".((....))";

		AlignLoops align = new AlignLoops();
		double editDistance = align.alignLoops(structA, seqA, "1", structB,
				seqB, "2");
		System.out.println(editDistance);
		System.out.println(align.getAlignment());
		System.out.println();

		// Delete single base
		String seqC = "AUUAACCGU";
		String structC = "(.(....))";
		String seqD = "AUAACCGU";
		String structD = "((....))";

		align = new AlignLoops();
		editDistance = align.alignLoops(structC, seqC, "1", structD, seqD, "2");
		System.out.println(editDistance);
		System.out.println(align.getAlignment());
		System.out.println();

		// Delete base pair
		String seqE = "AUAACCGU";
		String structE = "((....))";
		String seqF = "UAACCG";
		String structF = "(....)";

		align = new AlignLoops();
		editDistance = align.alignLoops(structE, seqE, "1", structF, seqF, "2");
		System.out.println(editDistance);
		System.out.println(align.getAlignment());
		System.out.println();

		// Arc completion
		String seqG = "UUAUAACCGUA";
		String structG = "(.((....)))";
		String seqH = "UUAUAACCGUAA";
		String structH = "((((....))))";

		align = new AlignLoops();
		editDistance = align.alignLoops(structG, seqG, "1", structH, seqH, "2");
		System.out.println(editDistance);
		System.out.println(align.getAlignment());
		System.out.println();

		// Arc creation
		String seqI = "UUAUAACCGUGAA";
		String structI = "(.((....))..)";
		String seqJ = "UUAUAACCGUAGA";
		String structJ = "((((....))).)";

		align = new AlignLoops();
		editDistance = align.alignLoops(structI, seqI, "1", structJ, seqJ, "2");
		System.out.println(editDistance);
		System.out.println(align.getAlignment());
		System.out.println();

		// Double Arc creation insertion
		String seqK = "UUAUAACCGUGAAUUAUAACCGUGAA";
		String structK = "(.((....))..)(.((....))..)";
		String seqL = "UUAUAACCGUAGAUUAUAACCGUAGA";
		String structL = "((((....))).)((((....))).)";

		align = new AlignLoops();
		editDistance = align.alignLoops(structK, seqK, "1", structL, seqL, "2");
		System.out.println(editDistance);
		System.out.println(align.getAlignment());
		System.out.println();
	}
}
