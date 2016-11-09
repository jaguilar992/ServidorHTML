package servidorhtml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Antonio
 */
public class Regexp {
    //private static final String REGEXP = "([^A-Z\\s+].+[^\\s+HTTP/\\d+\\.\\d+])";
    private static final String REGEXP = "([^A-Z\\s+].*[^HTP/\\d+\\.\\d+])";
    private static final String REGEXP2 = "(^[A-Z]*)";
    public static String get_filename(String line){
	Pattern p = Pattern.compile(Regexp.REGEXP);
        if(line!=null){
	Matcher m = p.matcher(line);
	
	if (m.find()) {
	    return m.group().replace(" ", "");
	}else{
	    return null;
	}
      }else{
        return null;
        }
    }
    
    public static String get_method(String line){
	Pattern p = Pattern.compile(Regexp.REGEXP2);
	if(line!=null){
        
        Matcher m = p.matcher(line);
	
	if (m.find()) {
	    return m.group().replace(" ", "");
	}else{
	    return null;
	}
      }else{
        return null;
        }
    }
}

