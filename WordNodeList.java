import java.util.*;

public class WordNodeList{
	
	private static WordNode wordsHead;

	//Construct neighbors. As all words are ordered in wordList, so loop the list to find neighbors
	public static void ceateNeighbors(List<WordNode> wordList,List<LenSameWordNode> lenSameWordNodes){
		if(wordList==null || wordList.isEmpty()) return;
		for(WordNode wordNode:wordList){
			String word = wordNode.word;
			int len = word.length();
			int index = -1;
			for(int k=0;k<lenSameWordNodes.size();k++){
				if(lenSameWordNodes.get(k).len==len){
					index = k;
					break;
				}
			}
			if(index!=-1){
				//Find the words list required includes all words with same length
				List<String> words = lenSameWordNodes.get(index).words;
				//Then find neighbors in this list for current word
				for(String w:words) {
					if (isNeighbors(word, w) && (!word.equals(w))) {
						wordNode.neighbors.add(w);
					}
				}
			}
		}
	}
	
		//Determine whether the two strings are neighbors
	private static boolean isNeighbors(String word1,String word2){
		char[] char1 =word1.toCharArray();
		char[] char2 =word2.toCharArray();
		char1[0] = '*';//change both prefix to *, check if the remainings are the same
		char2[0] = '*';
		String prefix1 = new String(char1);
		String prefix2 = new String(char2);
		boolean prefix = prefix1.equals(prefix2);

		char[] char3 =word1.toCharArray();
		char[] char4 =word2.toCharArray();
		char3[char3.length-1] = '*';//change both suffix to *, check if the remainings are the same
		char4[char4.length-1] = '*';
		String suffix1 = new String(char3);
		String suffix2 = new String(char4);
		boolean suffix = suffix1.equals(suffix2);

		return prefix||suffix;
	}
		
}