import java.util.*;
import java.util.regex.Matcher;
import java.io.*;

public class WordMatch {

    public static void main (String[] args)throws Exception {
        //Read all the valid words in the file and store them in StringBuffer
        StringBuffer wordsBuffer = new StringBuffer("");
        String inputFileName1 = args[0]; //Get input file name
        String outputFileName1 = args[1]; //Get output file name
        String inputFileName2 = args[2]; //Get input patterns
        String outputFileName2 = args[3]; //Get output file with matched pattern
        //Time start to read file
        long startTime = System.currentTimeMillis();
        //Read data from input file
        readIn1(inputFileName1,wordsBuffer);
        //Split words
        String[] words = wordsBuffer.toString().split("\\s+");
        //heap sort
        HeapSortUtil.heatSort(words);
        List<WordNode> wordList = new ArrayList<WordNode>();
        //Time spent on sort
        long endTime = System.currentTimeMillis();
        System.out.println("Total time cost on heap sort(msec)=" + (endTime-startTime));

        //Put words with same langth into a List
        List<LenSameWordNode> lenSameWordNodes = new ArrayList<LenSameWordNode>();

        //Delete repeated words after sort, copy it to new list
        creatWordList(words,wordList,lenSameWordNodes);
        endTime = System.currentTimeMillis();
        System.out.println("Total time cost on deleat repeated words(msec)=" + (endTime-startTime));
        //construct neighbors for each word
        startTime = System.currentTimeMillis();
        WordNodeList.ceateNeighbors(wordList,lenSameWordNodes);
        endTime = System.currentTimeMillis();
        System.out.println("Total time cost on construct neighbors(msec)=" + (endTime-startTime));
        writeData(outputFileName1,wordList);
        endTime = System.currentTimeMillis();
        System.out.println("Total time cost(msec)=" + (endTime-startTime));
        //Task 1 completed
        System.out.println("Task1 Completed.");
        //Task2
        List<String> patternList = new ArrayList<String> ();
        //read input file 2
        startTime = System.currentTimeMillis();
        loadPattern(inputFileName2,patternList);
        for(String p: patternList) {
        	List<Task2Result> result = Helper.regex2(wordsBuffer.toString(),p);
        	createOrderedWordsList(result);
        	writeData(outputFileName2,result,p);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Total time cost(msec)=" + (endTime-startTime));
        System.out.println("Task2 Completed.");
    }

    public static void loadData(String curFileName,StringBuffer wordsBuffer)throws IOException {
        //Read file
        Scanner infile1 = new Scanner(new File(curFileName));
        while(infile1.hasNext()) {
            String str = infile1.nextLine();
            String str1 = str.replaceAll("[\\pP\\p{Punct}]","");//clear punctuations
            String eachLine1[] = str1.split("\\s+");
            for(String word: eachLine1) {
                //Words are not empty and do not contain numbers
                if(!isEmpty(word) && !isContainNumber(word))
                    wordsBuffer.append(word.toLowerCase()+" ");
            }
        }
        infile1.close();
    }
    //Read in1.txt
    public static void readIn1(String fileName1,StringBuffer wordsBuffer)throws IOException {
    	Scanner infile = new Scanner(new File(fileName1));
    	while(infile.hasNext()) {
    		String curFileName = infile.nextLine();
    		loadData(curFileName,wordsBuffer);
    	}
    }

    //Read in2.txt
    public static void loadPattern(String fileName2, List<String> patternList)throws IOException{
    	Scanner infile2 = new Scanner(new File(fileName2));
    	while(infile2.hasNext()) {
    		String str = infile2.nextLine();
    		String curPattern = str;
    		patternList.add(curPattern);
    	}
    	infile2.close();
    }
    

    public static void creatWordList(String[] words,List<WordNode> wordList,List<LenSameWordNode> lenSameWordNodes) {
        if(words!=null && words.length>0) {
            WordNode wordNode1 = new WordNode(words[0],1);
            wordList.add(wordNode1);
            //Add the first LenSameWordNode, its length = words[0].length  
            LenSameWordNode lswn = new LenSameWordNode();
            lswn.len = words[0].length();
            lswn.words.add(words[0]);
            lenSameWordNodes.add(lswn);

            for(int i=1;i<words.length;i++){
                String word2 = words[i];
                if(word2.equals(wordNode1.word)){
                    wordNode1.increaseFrequency();
                }else{
                    wordNode1 = new WordNode(words[i],1);
                    wordList.add(wordNode1);
                    //Check the if current word.length is already exists in lenSameWordNodes 
                    int wlen = words[i].length();
                    int index = -1;
                    for(int k=0;k<lenSameWordNodes.size();k++){
                        if(lenSameWordNodes.get(k).len==wlen){
                            index = k;
                            break;
                        }
                    }
                    //If it does not exist, add a new LenSameWordNode, its length = wlen.length
                    if(index==-1){
                        LenSameWordNode newlswn = new LenSameWordNode();
                        newlswn.len = words[i].length();
                        newlswn.words.add(words[i]);
                        lenSameWordNodes.add(newlswn);
                    }else{
                        LenSameWordNode originLswn = lenSameWordNodes.get(index);
                        originLswn.words.add(words[i]);
                    }
                }
            }
        }
    }
    
    //Determine if the word is empty
    private static boolean isEmpty(String word){
        if(word==null) return true;
        if("".equals(word.trim())) return true;
        return false;
    }

    //Determine if the word contains numbers
    private static boolean isContainNumber(String word){
        boolean containNumber = false;
        if(word==null) return false;
        for(int i=0;i<word.length();i++) {
            if (Character.isDigit(word.charAt(i))) {
                containNumber = true;
                break;
            }
        }
        return containNumber;
    }

    //Write the results to a file
    public static void writeData(String fileName, List<WordNode> wordList)throws IOException {
        try {
            PrintWriter outFile = new PrintWriter(new File(fileName));
            for (WordNode p:wordList){
				outFile.println(p.word + "[" + p.frequency + "] " + p.getNeighbors());
			}
            outFile.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    //Write the results to a file (not covered)
    public static void writeData(String fileName,List<Task2Result> result,String pattern){
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(new FileWriter(fileName,true));
            //The input pattern
            outFile.println(pattern);
            //Matching result
            if(result.isEmpty()){
                outFile.println("\t No words in the lexicon match the pattern");
            }else {
                for (Task2Result tr : result) {
                    outFile.println("\t" + tr.key + "  " + tr.count);
                }
            }
            outFile.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(outFile!=null) outFile.close();
        }
    }
    
    //show list in order (bubble sort)
    private static void createOrderedWordsList(List<Task2Result> result) {
    	int left = 0;
        int right = result.size()-1;
        for(int i=right; i>left; i--) { 
            for (int j=left; j<i; j++) {
                if (result.get(j).key.compareTo(result.get(j+1).key)>0) {
                    swap(result, j, j+1);
                }
            }
        }
    }
    //swap function
    private static void swap(List<Task2Result> result,int i,int j) {
    	Task2Result temp = result.get(i);
        result.set(i, result.get(j));
        result.set(j, temp);
    }
 
}
