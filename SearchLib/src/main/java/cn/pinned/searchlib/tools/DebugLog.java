package cn.pinned.searchlib.tools;

import android.util.Log;

import cn.pinned.searchlib.BuildConfig;


public class DebugLog{

    static String className;
    static String methodName;
    static int lineNumber;

//    public static boolean DEBUG = BuildConfig.DEBUG;
    public static boolean DEBUG = true;
    private DebugLog(){
        /* Protect from instantiations */
    }

    public static boolean isDebuggable() {
        return DEBUG;
    }

    private static String createLog( String log ) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        className = className.substring(0, className.length() - 5);
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
        if (className.endsWith("Test")) {
            className = "UnitTest";
        }
    }

    public static void e(String message){
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods  
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void i(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }



    public static void d(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }
    
    public static void v(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

}
