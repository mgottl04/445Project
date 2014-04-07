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

		String seqC = "UAUAACCGU";
		String structC = ".((....))";

		AlignSequences align = new AlignSequences();
		align.alignSequences(structC, seqC, "1", structB, seqB, "2");

	}

}
