public class TableEntry {
	
	TableEntry(double score, Pointer pointer) {
		this.score = score;
		this.pointer = pointer;
	}

	double score;
	Pointer pointer;

	enum Pointer {
		UP, LEFT, DIAGONAL, NULL
	}
}
