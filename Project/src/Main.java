import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static final String SCORE_FILE_STRING = "editDistTest.txt";

	public static void main(String[] args) throws Exception {
		/*String seqA = "gaggaaaguccgggcUCCAUGGAAGCGCGGUGCCGGAUAACGUCCGGCGGGGGCGACCUCAGGGAAAGUGCCACAGAAAGCAAACCGCCCUCGAGGCCGAAAGGCUUCGCGGAGGGUAAGGGUGAAAGGGUGCGGUAAGAGCGCACCGCGUCUUUGGCAACAAAGGCGGCAAGGCAAACCCCACCGGGAGCAAGACCAAAUAGGGGCUGCACGGACGAGAGAUCGUCCAGGUCUGUUUCCAGACCCGCGGCCCGGGUUGGUUGCAAGAGGCGUCUCGCAAGAGGCGUCCCAGAUGAAUGGCCAUCACCUCGCAGCAAUGCGAGGAacagaacccggcuua";
		String structA = "..............((((.........((((((((((.....)))))(((((....)))).)((...(((((............((((((((((((((....))))))...))))))..)).......((((((.......))))))(((((((((....)))))))).)..)))..)))))))))))))...((((......(((((((..((((((....)))))).((((........)))).))))))).....))))......((((((((....))))))))..................(((((((....)))))))................";
		String seqB = "gaggaaagucCGGGCUCCUUCGGACAGGGCGCCAGGUAACGCCUGGGGGGCGUGAGCCCACGGAAAGUGCCACAGAAAAUAUACCGCCAGCUUCGGCUGGUAAGGGUGAAAUGGUGCGGUAAGAGCGCACCGCGCGACUGGCAACGGCUUGCGGCACGGUAAACCCCGCCCGGAGCAAGACCAAAUAGGGGAGCAUGUCCGUCGUGUCCGAACGGGCUCCCGGGUAGGUUGCUUGAGGUGGCCGGUGACGGCUAUCCCAGAUGAAUGGUUGUCGAUGacagaacccggcuuac";
		String structB = "..............((((........((((((((((.....)))))(((((....)))).)((...(((((............((((((((....))))))..)).......((((((.......))))))(((((((((....))).))))).)..)))..)))))))))))))...((((......((((((....((((.........)))))))))).....))))......((((((((....)))))))).....................................";
		*/
		
		// Using single Stem Loop sequence to test
		String seqA = "ACAACCGU";
		String structA = "((....))";
		String seqB = "AUAACCGU";
		String structB = "((....))";
		
		AlignSequences align = new AlignSequences();
		align.alignSequences(structA, seqA, "1",structB,seqB,"2");
		
	}

}
