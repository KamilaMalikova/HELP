package com.example.helptest.invoice;

public class LineGenerator {
    private static final int line = 48;

    public static String createLine(){
        return " ".repeat(line);
    }

    public static String createLine(String text){
        return space(text, true)+text+space(text, false);
    }
    public static String createLine(String left, String right){
        return left+space(left.length()+right.length())+right;
    }
    private static String space(String text, boolean before){
        int space;
        if (before){
            space = (line-text.length())/2;

        }else {
            space = line -(line-text.length())/2;
        }
        return " ".repeat(space);
    }

    private static String space(int length){
        String space = " ".repeat(line-length);
         return space;
    }

    public static String dottedLine(){
        return "-".repeat(line)+"\n";
    }

    public static String customString(String text, int len, boolean before){
        if (text.length() >= len) return text;
        String repeat = " ".repeat(len - text.length());
        if (before){
            return repeat +text;
        }else {
            return text+ repeat;
        }
    }
}
