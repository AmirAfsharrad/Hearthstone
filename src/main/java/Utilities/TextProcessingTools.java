package Utilities;

public class TextProcessingTools {
    public static boolean stringFirstWordMatch(String mainString, String doesMatchString){
        return mainString.startsWith(doesMatchString);
    }

    public static boolean isInBrackets(String string){
        return string.startsWith("[") && string.endsWith("]");
    }

    public static String unBracket(String string){
        return string.substring(1, string.length()-1);
    }
}
