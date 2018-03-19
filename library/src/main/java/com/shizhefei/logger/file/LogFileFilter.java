package com.shizhefei.logger.file;

import com.shizhefei.logger.LogLevel;

/**
 * Created by pengwei on 2017/3/31.
 * 日志过滤器
 */

public interface LogFileFilter {
    boolean accept(@LogLevel.LogLevelType int level, String tag, String logContent);
}