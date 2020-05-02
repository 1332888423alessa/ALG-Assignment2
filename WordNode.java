import java.util.*;

public class WordNode{
	
	public String word;
	public int frequency;
	public List<String> neighbors = new ArrayList<String>();
	public WordNode next;
	
	public WordNode (String word,int frequency){
		this.word = word;
		this.frequency = frequency;
	}
	
	public void setWord(String newWord) {
		word = newWord;
	}
	
	public int increaseFrequency() {
		return this.frequency++;
	}

	//WordNodeList.ceateNeighbors(wordList) contructs neighbors, this function outputs neighbors
	public String getNeighbors(){
		StringBuffer neighborstr = new StringBuffer("[");
		for(String data:neighbors){
			neighborstr.append(data + ",");
		}
		neighborstr.append("]");
		return neighborstr.toString();
	}
}