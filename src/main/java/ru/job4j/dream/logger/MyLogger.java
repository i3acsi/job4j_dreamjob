package ru.job4j.dream.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {
    private static final Logger log = LoggerFactory.getLogger(MyLogger.class);
    private static final String LN = System.lineSeparator();

    public static void logInfo(String msg) {
        log.info(msg);
    }

    public static void logException(String msg){
        log.error(msg);
    }
}
