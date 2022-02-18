package user50.sample;

import java.text.SimpleDateFormat;

public class Log {

    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String TIME_PATTERN_WITH_DATE = "yyyy-MM-dd " + TIME_PATTERN;

    public static void i(String message){
        System.out.println(getTime(TIME_PATTERN) + " : " + message);
    }

    public static void e(String message){
        System.err.println(getTime(TIME_PATTERN) + " : " + message);
    }

    public static void i(String tag, String message){
        System.out.println(getTime(TIME_PATTERN) + " " + tag + " : " + message);
    }

    public static void e(String tag, String message){
        System.err.println(getTime(TIME_PATTERN) + " " + tag + " : " + message);
    }

    public static void n(){
        System.out.println();
    }

    private static String getTime(String timePattern){
        return new SimpleDateFormat(timePattern).format(System.currentTimeMillis());
    }

}
