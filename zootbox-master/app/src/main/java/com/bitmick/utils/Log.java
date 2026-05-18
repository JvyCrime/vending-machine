package com.bitmick.utils;

import java.io.BufferedWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class Log {
    public static final String ANSI_BLACK = "\u001b[30m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001b[40m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001b[44m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001b[46m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001b[42m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_RED_BACKGROUND = "\u001b[41m";
    public static final String ANSI_RESET = "\u001b[0m";
    public static final String ANSI_WHITE = "\u001b[37m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_YELLOW = "\u001b[33m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001b[43m";
    public static final int LOG_LEVEL_DEBUG = 4;
    public static final int LOG_LEVEL_ERROR = 1;
    public static final int LOG_LEVEL_EVERYTHING = 8;
    public static final int LOG_LEVEL_WARNING = 2;
    private static long m_prev_log_ts = Utils.currentTimeMillis();
    private static String m_filter = null;
    private static int m_level = 255;
    private static BufferedWriter m_stream = null;

    public static void close() {
    }

    public static void initialize(BufferedWriter bufferedWriter) {
        m_prev_log_ts = Utils.currentTimeMillis();
        m_stream = bufferedWriter;
    }

    public static void filter(String str) {
        m_filter = str;
    }

    public static void level(int i) {
        m_level = i;
    }

    public static void _log(int i, String str, String str2) {
        if ((i & m_level) != 0) {
            String str3 = m_filter;
            if (str3 == null || str.contains(str3)) {
                long jCurrentTimeMillis = Utils.currentTimeMillis();
                String str4 = String.format("(+%10dms) %15s: %s\n\r", Long.valueOf(jCurrentTimeMillis - m_prev_log_ts), str, str2);
                System.out.print(str4);
                m_prev_log_ts = jCurrentTimeMillis;
                BufferedWriter bufferedWriter = m_stream;
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.write(str4);
                        m_stream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void d(String str, String str2) {
        _log(4, str, str2);
    }

    public static void l(String str, String str2) {
        _log(8, str, str2);
    }

    public static void w(String str, String str2) {
        _log(2, str, String.format("*** warning: %s ***", str2));
    }

    public static void e(String str, String str2) {
        _log(1, str, String.format("*** error: %s ***", str2));
    }
}
