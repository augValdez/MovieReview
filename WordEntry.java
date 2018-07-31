
public class WordEntry {

	private String word;
	private double score;
	private int appearances;

	public WordEntry(String word, double score, int appearances) {
		this.word = word;
		this.score = score;
		this.appearances = appearances;	
	}

	public String getWord() {
		return word;		
	}

	public double getScore() {
		return score;	
	}

	public int getAppearances() {
		return appearances;	
	}

	public void SetWord(String word) {
		this.word = word;		
	}

	public void setScore(double score) {
		this.score = score;	
	}

	public void setAppearances(int appear) {
		appearances = appear;	
	}

	public double getAverage() {
		return score/appearances;
	}

}
