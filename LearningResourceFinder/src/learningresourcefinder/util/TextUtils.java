package learningresourcefinder.util;

import java.util.HashMap;
import java.util.Map;


public class TextUtils {

    public static String escapeTextForJs(String text) {
        return text.replaceAll("\"", "\'").replaceAll("'", "\\\\'").replaceAll(
                "\n", "").replaceAll("\r", "");
    }

    public static String enableHtmlCode(String text){
        if (text == null) {
            return null;
        }

        char[] s = text.toCharArray();
        int length = s.length;
        StringBuffer ret = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            if (s[i] == '&') {
                if ( ((i + 3) < length) &&
                        (s[i + 1] == 'l') &&
                        (s[i + 2] == 't') &&
                        (s[i + 3] == ';')) { // &lt; = <
                    ret.append('<');
                    i += 3;
                } else if ( ((i + 3) < length) &&
                        (s[i+1] == 'g') &&
                        (s[i+2] == 't') &&
                        (s[i+3] == ';') ) { // &gt; = >
                    ret.append('>');
                    i += 3;
                } else if ( ((i + 4) < length) &&
                        (s[i+1] == 'a') &&
                        (s[i+2] == 'm') &&
                        (s[i+3] == 'p') &&
                        (s[i+4] == ';') ) { // &amp; = &
                    ret.append('&');
                    i += 4;
                } else if ( ((i + 5) < length) &&
                        (s[i+1] == 'q') &&
                        (s[i+2] == 'u') &&
                        (s[i+3] == 'o') &&
                        (s[i+4] == 't') &&
                        (s[i+5] == ';') ) { // &quot; = "
                    ret.append('"');
                    i += 5;
                } else {
                    ret.append('&');
                }
            } else {
                ret.append(s[i]);
            }
        }
        return ret.toString();
    }

    //Java code to generate alphanumeric code from id
    public static String generateShortId(long id) {
        System.out.println("Entering generateShortId(id)");
        System.out.println("Original id: "+id);

        //To convert id to alphanumeric code.
        //We need to convert base10(decimal) to base36
        String strBaseDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String strTempVal = "";
        int mod = 0;
        // String concat is costly, instead we could have use stringbuffer or stringbuilder
        //but here it wont make much difference.
        while(id!= 0){
            mod=(int) (id % 36);
            strTempVal=strBaseDigits.substring(mod,mod+1)+strTempVal;
            id=id/36;
        }
        System.out.println("alphanumeric code generated from id : "+strTempVal);
        return strTempVal;

    }


}
