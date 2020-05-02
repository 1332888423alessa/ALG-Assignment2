import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
	//List
	public static List<Task2Result> regex2(String s, String pattern){
        List<Task2Result> result = new ArrayList<Task2Result>();
        //Whether or not to match exactly
        boolean exactlyMatch = false;
        //pattern's length
        int patternLen = pattern.length();
        if(!pattern.contains("*")){
            exactlyMatch = true;
            pattern = pattern + "*";
        }
        //When exactly match, group(0) will only catch chars which matches pattern
        //The whole word cannot be grouped, so add a *
        String startWithString = "";
        if(!pattern.startsWith("?") && !pattern.startsWith("*")){
            startWithString = pattern.charAt(0) + "";
            pattern = "*" + pattern;
        }
        //? before word
        if(pattern.startsWith("?")){
            //startWithString = pattern.charAt(0) + "";
            pattern = "*" + pattern;
        }
        //System.out.println("pattern1=" + pattern);
        pattern = pattern.replace("?","[a-zA-Z]{1}");
        pattern = pattern.replace("*","[a-zA-Z]{0,}");
        //System.out.println("pattern2=" + pattern);
        Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        while(m.find()){
            String ms = m.group(0);
            //System.out.println("group(0)"+ms);
            boolean isAdd = true;
            //If there's no * in pattern, only have ?, so it's exactly match, words length should be the same
            if(exactlyMatch){
                if(ms.length()!=patternLen) isAdd = false;
            }

            if(!"".equals(startWithString)){
                if(!ms.toLowerCase().startsWith(startWithString.toLowerCase())) isAdd = false;
            }
            //If the element exists
            boolean isExist = false;
            if(isAdd){
                for (int i=0;i<result.size();i++){
                    Task2Result tr = result.get(i);
                    if(tr.key.equals(ms)){
                        tr.count = tr.count + 1;
                        isExist = true;
                        break;
                    }
                }
                if(!isExist) result.add(new Task2Result(ms,1));
            }
        }
        return result;
    }
}