import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static final String SCORE_FILE_STRING = "editDistTest.txt";

	public static void main(String[] args) throws Exception {
		String seqA = "gaggaaaguccgggcUCCAUGGAAGCGCGGUGCCGGAUAACGUCCGGCGGGGGCGACCUCAGGGAAAGUGCCACAGAAAGCAAACCGCCCUCGAGGCCGAAAGGCUUCGCGGAGGGUAAGGGUGAAAGGGUGCGGUAAGAGCGCACCGCGUCUUUGGCAACAAAGGCGGCAAGGCAAACCCCACCGGGAGCAAGACCAAAUAGGGGCUGCACGGACGAGAGAUCGUCCAGGUCUGUUUCCAGACCCGCGGCCCGGGUUGGUUGCAAGAGGCGUCUCGCAAGAGGCGUCCCAGAUGAAUGGCCAUCACCUCGCAGCAAUGCGAGGAacagaacccggcuua";
		String structA = "..............((((.........((((((((((.....)))))(((((....)))).)((...(((((............((((((((((((((....))))))...))))))..)).......((((((.......))))))(((((((((....)))))))).)..)))..)))))))))))))...((((......(((((((..((((((....)))))).((((........)))).))))))).....))))......((((((((....))))))))..................(((((((....)))))))................";
		String seqB = "gaggaaagucCGGGCUCCUUCGGACAGGGCGCCAGGUAACGCCUGGGGGGCGUGAGCCCACGGAAAGUGCCACAGAAAAUAUACCGCCAGCUUCGGCUGGUAAGGGUGAAAUGGUGCGGUAAGAGCGCACCGCGCGACUGGCAACGGCUUGCGGCACGGUAAACCCCGCCCGGAGCAAGACCAAAUAGGGGAGCAUGUCCGUCGUGUCCGAACGGGCUCCCGGGUAGGUUGCUUGAGGUGGCCGGUGACGGCUAUCCCAGAUGAAUGGUUGUCGAUGacagaacccggcuuac";
		String structB = "..............((((........((((((((((.....)))))(((((....)))).)((...(((((............((((((((....))))))..)).......((((((.......))))))(((((((((....))).))))).)..)))..)))))))))))))...((((......((((((....((((.........)))))))))).....))))......((((((((....)))))))).....................................";

		String seqC = "AUUAACCGU";
		String structC = "(.(....))";
		String seqD = "AUAACCGU";
		String structD = "((....))";

		AlignLoops align = new AlignLoops();
		double editDistance = align.alignLoops(structC, seqC, "1", structD, seqD, "2");
		System.out.println(editDistance);
		System.out.println(align.getAlignment());
	}

}
