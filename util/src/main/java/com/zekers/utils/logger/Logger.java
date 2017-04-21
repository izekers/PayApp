package com.zekers.utils.logger;

import android.util.Log;

import com.zekers.pjutils.BaseApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 打Log并上传信息
 * Created by zekers on 2016/11/28.
 */
public class Logger {
    //    private static IErrorCollection mCollection= UmengCollection.getInstance();//上传LOG接口
    private static IErrorCollection mCollection = new EmptyCollection();//上传LOG接口
    private static boolean mDebug = true;        //是否输出Log
    private static String mTag = "bingo";       //默认LOG
    private static boolean mIsStatck = true;      //是否打印出当前栈
    private static final int JSON_INDENT = 2;

    public static void init(boolean debug, String tag) {
        mDebug = debug;
        mTag = tag;
    }

    public static void init(boolean debug, String tag, boolean isStatck) {
        mDebug = debug;
        mTag = tag;
        mIsStatck = isStatck;
    }

    public static void init(boolean debug, String tag, boolean isStatck, IErrorCollection collection) {
        mDebug = debug;
        mTag = tag;
        mIsStatck = isStatck;
        mCollection = collection;
    }

    private Logger() {
    }

    public static void v(String msg) {
        if (!mDebug)
            return;
        e(mTag, msg);
    }

    public static int v(String tag, String msg) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.v(tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.v(tag, msg, tr);
    }

    public static void d(String msg) {
        if (!mDebug)
            return;
        d(mTag, msg);
    }

    public static int d(String tag, String msg) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.d(tag, msg);
    }


    public static int d(String tag, String msg, Throwable tr) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.d(tag, msg, tr);
    }

    public static void i(String msg) {
        if (!mDebug)
            return;
        i(mTag, msg);
    }

    public static int i(String tag, String msg) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.i(tag, msg);
    }


    public static int i(String tag, String msg, Throwable tr) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.i(tag, msg, tr);
    }

    public static void w(String msg) {
        if (!mDebug)
            return;
        w(mTag, msg);
    }

    public static int w(String tag, String msg) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.w(tag, msg);
    }


    public static int w(String tag, String msg, Throwable tr) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.w(tag, msg, tr);
    }


    public static int w(String tag, Throwable tr) {
        if (!mDebug)
            return -1;
        printStack(tag);
        return Log.w(tag, tr);
    }

    public static void e(String msg, Throwable tr) {
        String err = "错误信息：" + msg + "\n" + Logger.getErrorStackTrace(tr);
        mCollection.reportError(BaseApplication.Instance, err);
        if (!mDebug)
            return;
        e(mTag, msg);
    }

    public static void e(String msg) {
        mCollection.reportError(BaseApplication.Instance, msg);
        if (!mDebug)
            return;
        e(mTag, msg);
    }

    public static int e(String tag, String msg) {
        mCollection.reportError(BaseApplication.Instance, msg);
        if (!mDebug)
            return -1;
        return Log.e(tag, ""+msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        String err = "错误信息：" + msg + "\n" + Logger.getErrorStackTrace(tr);
        mCollection.reportError(BaseApplication.Instance, err);
        if (!mDebug)
            return -1;
        return Log.e(tag, msg, tr);
    }

    //打印json
    public static void json(String msg) {
        if (!mDebug)
            return;
        json(null, msg);
    }

    //打印json
    public static void json(String tag, String msg) {
        if (!mDebug) return;
        Log.i(tag, getPrettyJson(msg));
    }

    //解析json
    private static String getPrettyJson(String jsonStr) {
        try {
            jsonStr = jsonStr.trim();
            if (jsonStr.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.toString(JSON_INDENT);
            }
            if (jsonStr.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonStr);
                return jsonArray.toString(JSON_INDENT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Invalid Json, Please Check: " + jsonStr;
    }

    //输出调用该log的位置
    private static void printStack(String tag) {
        if (!mIsStatck)
            return;
        StackTraceElement targetStackTraceElement = getTargetStackTraceElement();
        Log.d(tag, "(" + targetStackTraceElement.getFileName() + ":"
                + targetStackTraceElement.getLineNumber() + ")");
    }

    //输出调用该log的位置
    private static StackTraceElement getTargetStackTraceElement() {
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(Logger.class.getName());
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }//http://10.201.78.24:8088/go/BingoLink/update_android.xml,method=GET

    //获取错误信息和堆栈
    public static String getErrorStackTrace(Throwable tr) {
        String err = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            tr.printStackTrace(printStream);
            err = new String(outputStream.toByteArray());
            printStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "错误输出开始====>\n" + err + "\n<====";
    }
}
