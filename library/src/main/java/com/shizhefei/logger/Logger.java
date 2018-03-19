package com.shizhefei.logger;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.shizhefei.logger.file.LogFileParam;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.MissingFormatArgumentException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.shizhefei.logger.LogLevel.TYPE_DEBUG;
import static com.shizhefei.logger.LogLevel.TYPE_ERROR;
import static com.shizhefei.logger.LogLevel.TYPE_INFO;
import static com.shizhefei.logger.LogLevel.TYPE_VERBOSE;
import static com.shizhefei.logger.LogLevel.TYPE_WARM;
import static com.shizhefei.logger.LogLevel.TYPE_WTF;
import static com.shizhefei.logger.utils.ObjectUtil.objectToString;
import static com.shizhefei.logger.utils.Utils.DIVIDER_BOTTOM;
import static com.shizhefei.logger.utils.Utils.DIVIDER_CENTER;
import static com.shizhefei.logger.utils.Utils.DIVIDER_NORMAL;
import static com.shizhefei.logger.utils.Utils.DIVIDER_TOP;
import static com.shizhefei.logger.utils.Utils.largeStringToList;
import static com.shizhefei.logger.utils.Utils.printDividingLine;

/**
 * Created by pengwei08 on 2015/7/20.
 */
class Logger implements Printer {

  private LogConfigImpl mLogConfig;
  private Log2FileConfigImpl log2FileConfig;
  private final ThreadLocal<String> localTags = new ThreadLocal<String>();

  protected Logger() {
    mLogConfig = LogConfigImpl.getInstance();
    log2FileConfig = Log2FileConfigImpl.getInstance();
    mLogConfig.addParserClass(Constant.DEFAULT_PARSE_CLASS);
  }

  /**
   * 设置临时tag
   */
  public Printer setTag(String tag) {
    if (!TextUtils.isEmpty(tag) && mLogConfig.isEnable()) {
      localTags.set(tag);
    }
    return this;
  }

  /**
   * 打印字符串
   */
  private synchronized void logString(@LogLevel.LogLevelType int type, String msg, Object... args) {
    logString(type, msg, null, false, args);
  }

  private void logString(@LogLevel.LogLevelType int type, String msg, String tag, boolean isPart,
      Object... args) {
    if (!isPart || TextUtils.isEmpty(tag)) {
      tag = generateTag();
    }
    if (!isPart) {
      if (args.length > 0) {
        try {
          msg = String.format(msg, args);
        } catch (MissingFormatArgumentException e) {
        }
      }
      writeToFile(tag, msg, type);
    }
    if (!mLogConfig.isEnable()) {
      return;
    }
    if (type < mLogConfig.getLogLevel()) {
      return;
    }
    if (msg.length() > Constant.LINE_MAX) {
      if (mLogConfig.isShowBorder()) {
        printLog(type, tag, printDividingLine(DIVIDER_TOP));
        printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + getTopStackInfo());
        printLog(type, tag, printDividingLine(DIVIDER_CENTER));
      }
      for (String subMsg : largeStringToList(msg)) {
        logString(type, subMsg, tag, true, args);
      }
      if (mLogConfig.isShowBorder()) {
        printLog(type, tag, printDividingLine(DIVIDER_BOTTOM));
      }
      return;
    }
    if (mLogConfig.isShowBorder()) {
      if (isPart) {
        for (String sub : msg.split(Constant.BR)) {
          printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + sub);
        }
      } else {
        printLog(type, tag, printDividingLine(DIVIDER_TOP));
        printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + getTopStackInfo());
        printLog(type, tag, printDividingLine(DIVIDER_CENTER));
        for (String sub : msg.split(Constant.BR)) {
          printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + sub);
        }
        printLog(type, tag, printDividingLine(DIVIDER_BOTTOM));
      }
    } else {
      printLog(type, tag, msg);
    }
  }

  /**
   * 打印对象
   */
  private void logObject(@LogLevel.LogLevelType int type, Object object) {
    logString(type, objectToString(object));
  }

  /**
   * 自动生成tag
   */
  private String generateTag() {
    String tempTag = localTags.get();
    if (!TextUtils.isEmpty(tempTag)) {
      localTags.remove();
      return tempTag;
    }
    return mLogConfig.getTagPrefix();
  }

  /**
   * 获取当前activity栈信息
   */
  private StackTraceElement getCurrentStackTrace() {
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    int stackOffset = getStackOffset(trace, LogUtils.class);
    if (stackOffset == -1) {
      stackOffset = getStackOffset(trace, Logger.class);
      if (stackOffset == -1) {
        return null;
      }
    }
    if (mLogConfig.getMethodOffset() > 0) {
      stackOffset += mLogConfig.getMethodOffset();
    }
    StackTraceElement caller = trace[stackOffset];
    return caller;
  }

  /**
   * 获取最顶部stack信息
   */
  private String getTopStackInfo() {
    String customTag = mLogConfig.getFormatTag(getCurrentStackTrace());
    if (customTag != null) {
      return customTag;
    }
    StackTraceElement caller = getCurrentStackTrace();
    String stackTrace = caller.toString();
    stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
    String tag = "%s.%s%s";
    String callerClazzName = caller.getClassName();
    callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
    tag = String.format(tag, callerClazzName, caller.getMethodName(), stackTrace);
    return tag;
  }

  private int getStackOffset(StackTraceElement[] trace, Class cla) {
    for (int i = Constant.MIN_STACK_OFFSET; i < trace.length; i++) {
      StackTraceElement e = trace[i];
      String name = e.getClassName();
      if (cla.equals(Logger.class) && i < trace.length - 1 && trace[i + 1].getClassName()
          .equals(Logger.class.getName())) {
        continue;
      }
      if (name.equals(cla.getName())) {
        return ++i;
      }
    }
    return -1;
  }

  @Override public void d(String message, Object... args) {
    logString(TYPE_DEBUG, message, args);
  }

  @Override public void d(Object object) {
    logObject(TYPE_DEBUG, object);
  }

  @Override public void e(String message, Object... args) {
    logString(TYPE_ERROR, message, args);
  }

  @Override public void e(Object object) {
    logObject(TYPE_ERROR, object);
  }

  @Override public void w(String message, Object... args) {
    logString(TYPE_WARM, message, args);
  }

  @Override public void w(Object object) {
    logObject(TYPE_WARM, object);
  }

  @Override public void i(String message, Object... args) {
    logString(TYPE_INFO, message, args);
  }

  @Override public void i(Object object) {
    logObject(TYPE_INFO, object);
  }

  @Override public void v(String message, Object... args) {
    logString(TYPE_VERBOSE, message, args);
  }

  @Override public void v(Object object) {
    logObject(TYPE_VERBOSE, object);
  }

  @Override public void wtf(String message, Object... args) {
    logString(TYPE_WTF, message, args);
  }

  @Override public void wtf(Object object) {
    logObject(TYPE_WTF, object);
  }

  /**
   * 采用orhanobut/logger的json解析方案
   * source:https://github.com/orhanobut/logger/blob/master/logger/src/main/java/com/orhanobut/logger/LoggerPrinter.java#L152
   */
  @Override public void json(String json) {
    int indent = 4;
    if (TextUtils.isEmpty(json)) {
      d("JSON{json is empty}");
      return;
    }
    try {
      if (json.startsWith("{")) {
        JSONObject jsonObject = new JSONObject(json);
        String msg = jsonObject.toString(indent);
        d(msg);
      } else if (json.startsWith("[")) {
        JSONArray jsonArray = new JSONArray(json);
        String msg = jsonArray.toString(indent);
        d(msg);
      }
    } catch (JSONException e) {
      e(e.toString() + "\n\njson = " + json);
    }
  }

  /**
   * 采用orhanobut/logger的xml解析方案
   * source:https://github.com/orhanobut/logger/blob/master/logger/src/main/java/com/orhanobut/logger/LoggerPrinter.java#L180
   */
  @Override public void xml(String xml) {
    if (TextUtils.isEmpty(xml)) {
      d("XML{xml is empty}");
      return;
    }
    try {
      Source xmlInput = new StreamSource(new StringReader(xml));
      StreamResult xmlOutput = new StreamResult(new StringWriter());
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.transform(xmlInput, xmlOutput);
      d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
    } catch (TransformerException e) {
      e(e.toString() + "\n\nxml = " + xml);
    }
  }

  /**
   * 打印日志
   */
  private void printLog(@LogLevel.LogLevelType int type, String tag, String msg) {
    //if (!mLogConfig.isShowBorder()) {
      //msg = getTopStackInfo() + ": " + msg;
      //msg = msg;
    //}
    switch (type) {
      case TYPE_VERBOSE:
        Log.v(tag, msg);
        break;
      case TYPE_DEBUG:
        Log.d(tag, msg);
        break;
      case TYPE_INFO:
        Log.i(tag, msg);
        break;
      case TYPE_WARM:
        Log.w(tag, msg);
        break;
      case TYPE_ERROR:
        Log.e(tag, msg);
        break;
      case TYPE_WTF:
        Log.wtf(tag, msg);
        break;
      default:
        break;
    }
  }

  /**
   * 写入log到文件
   */
  private void writeToFile(String tagName, String logContent, @LogLevel.LogLevelType int logLevel) {
    if (!log2FileConfig.isEnable()) {
      return;
    }
    if (log2FileConfig.getFileFilter() != null && !log2FileConfig.getFileFilter()
        .accept(logLevel, tagName, logContent)) {
      return;
    }
    if (logLevel < log2FileConfig.getLogLevel()) {
      return;
    }
    String path = log2FileConfig.getLogPath();
    if (TextUtils.isEmpty(path)) {
      if (Build.VERSION.SDK_INT >= 23) {
        Log.e(tagName, "LogUtils write to logFile error. No sdcard access permission?");
        return;
      }
      throw new IllegalArgumentException("Log2FilePath is an invalid path");
    }
    File logFile = new File(path, log2FileConfig.getLogFormatName());
    LogFileParam param =
        new LogFileParam(System.currentTimeMillis(), logLevel, Thread.currentThread().getName(),
            tagName);
    if (log2FileConfig.getEngine() != null) {
      log2FileConfig.getEngine().writeToFile(logFile, logContent, param);
    } else {
      throw new NullPointerException("LogFileEngine must not Null");
    }
  }
}
