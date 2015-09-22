
public class Data {

	private String word;
	private int count;

	public Data() {
	}

	public Data(Data d) {
		// TODO Auto-generated constructor stub
		this.word = d.getWord();
		this.count = d.getCount();
	}

	public Data(String temp, int i) {
		// TODO Auto-generated constructor stub
		this.word = temp;
		this.count = i;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}

//System.out.println(sum+" "+word);
			/*
			 * Data current = new Data();
			 * 
			 * current.setWord(word.toString()); current.setCount(sum);
			 * 
			 * if (prev != null) { String previousWord = prev.getWord(); String
			 * currentWord = current.getWord();
			 * 
			 * System.out.println("Previous Word: " + previousWord +
			 * " Current Word: " + currentWord); if
			 * (previousWord.compareTo(currentWord) < 0) { list.add(prev); }
			 * 
			 * }
			 * 
			 * prev = new Data(current);
			 */


