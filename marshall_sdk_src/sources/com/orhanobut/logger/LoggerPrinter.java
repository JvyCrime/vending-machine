package com.orhanobut.logger;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class LoggerPrinter implements Printer {
    private static final int JSON_INDENT = 2;
    private final ThreadLocal<String> localTag = new ThreadLocal<>();
    private final List<LogAdapter> logAdapters = new ArrayList();

    LoggerPrinter() {
    }

    @Override // com.orhanobut.logger.Printer
    public Printer t(String str) {
        if (str != null) {
            this.localTag.set(str);
        }
        return this;
    }

    @Override // com.orhanobut.logger.Printer
    public void d(String str, Object... objArr) {
        log(3, (Throwable) null, str, objArr);
    }

    @Override // com.orhanobut.logger.Printer
    public void d(Object obj) {
        log(3, (Throwable) null, Utils.toString(obj), new Object[0]);
    }

    @Override // com.orhanobut.logger.Printer
    public void e(String str, Object... objArr) {
        e(null, str, objArr);
    }

    @Override // com.orhanobut.logger.Printer
    public void e(Throwable th, String str, Object... objArr) {
        log(6, th, str, objArr);
    }

    @Override // com.orhanobut.logger.Printer
    public void w(String str, Object... objArr) {
        log(5, (Throwable) null, str, objArr);
    }

    @Override // com.orhanobut.logger.Printer
    public void i(String str, Object... objArr) {
        log(4, (Throwable) null, str, objArr);
    }

    @Override // com.orhanobut.logger.Printer
    public void v(String str, Object... objArr) {
        log(2, (Throwable) null, str, objArr);
    }

    @Override // com.orhanobut.logger.Printer
    public void wtf(String str, Object... objArr) {
        log(7, (Throwable) null, str, objArr);
    }

    @Override // com.orhanobut.logger.Printer
    public void json(String str) {
        if (Utils.isEmpty(str)) {
            d("Empty/Null json content");
            return;
        }
        try {
            String strTrim = str.trim();
            if (strTrim.startsWith("{")) {
                d(new JSONObject(strTrim).toString(2));
            } else if (strTrim.startsWith("[")) {
                d(new JSONArray(strTrim).toString(2));
            } else {
                e("Invalid Json", new Object[0]);
            }
        } catch (JSONException unused) {
            e("Invalid Json", new Object[0]);
        }
    }

    @Override // com.orhanobut.logger.Printer
    public void xml(String str) {
        if (Utils.isEmpty(str)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            StreamSource streamSource = new StreamSource(new StringReader(str));
            StreamResult streamResult = new StreamResult(new StringWriter());
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            transformerNewTransformer.setOutputProperty("indent", "yes");
            transformerNewTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformerNewTransformer.transform(streamSource, streamResult);
            d(streamResult.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException unused) {
            e("Invalid xml", new Object[0]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0039 A[Catch: all -> 0x004b, TryCatch #0 {, blocks: (B:5:0x0005, B:8:0x0021, B:9:0x0025, B:12:0x002d, B:13:0x0033, B:15:0x0039, B:17:0x0045), top: B:24:0x0005 }] */
    @Override // com.orhanobut.logger.Printer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void log(int r3, java.lang.String r4, java.lang.String r5, java.lang.Throwable r6) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r6 == 0) goto L1d
            if (r5 == 0) goto L1d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4b
            r0.<init>()     // Catch: java.lang.Throwable -> L4b
            r0.append(r5)     // Catch: java.lang.Throwable -> L4b
            java.lang.String r5 = " : "
            r0.append(r5)     // Catch: java.lang.Throwable -> L4b
            java.lang.String r5 = com.orhanobut.logger.Utils.getStackTraceString(r6)     // Catch: java.lang.Throwable -> L4b
            r0.append(r5)     // Catch: java.lang.Throwable -> L4b
            java.lang.String r5 = r0.toString()     // Catch: java.lang.Throwable -> L4b
        L1d:
            if (r6 == 0) goto L25
            if (r5 != 0) goto L25
            java.lang.String r5 = com.orhanobut.logger.Utils.getStackTraceString(r6)     // Catch: java.lang.Throwable -> L4b
        L25:
            boolean r6 = com.orhanobut.logger.Utils.isEmpty(r5)     // Catch: java.lang.Throwable -> L4b
            if (r6 == 0) goto L2d
            java.lang.String r5 = "Empty/NULL log message"
        L2d:
            java.util.List<com.orhanobut.logger.LogAdapter> r6 = r2.logAdapters     // Catch: java.lang.Throwable -> L4b
            java.util.Iterator r6 = r6.iterator()     // Catch: java.lang.Throwable -> L4b
        L33:
            boolean r0 = r6.hasNext()     // Catch: java.lang.Throwable -> L4b
            if (r0 == 0) goto L49
            java.lang.Object r0 = r6.next()     // Catch: java.lang.Throwable -> L4b
            com.orhanobut.logger.LogAdapter r0 = (com.orhanobut.logger.LogAdapter) r0     // Catch: java.lang.Throwable -> L4b
            boolean r1 = r0.isLoggable(r3, r4)     // Catch: java.lang.Throwable -> L4b
            if (r1 == 0) goto L33
            r0.log(r3, r4, r5)     // Catch: java.lang.Throwable -> L4b
            goto L33
        L49:
            monitor-exit(r2)
            return
        L4b:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.orhanobut.logger.LoggerPrinter.log(int, java.lang.String, java.lang.String, java.lang.Throwable):void");
    }

    @Override // com.orhanobut.logger.Printer
    public void clearLogAdapters() {
        this.logAdapters.clear();
    }

    @Override // com.orhanobut.logger.Printer
    public void addAdapter(LogAdapter logAdapter) {
        this.logAdapters.add((LogAdapter) Utils.checkNotNull(logAdapter));
    }

    private synchronized void log(int i, Throwable th, String str, Object... objArr) {
        Utils.checkNotNull(str);
        log(i, getTag(), createMessage(str, objArr), th);
    }

    private String getTag() {
        String str = this.localTag.get();
        if (str == null) {
            return null;
        }
        this.localTag.remove();
        return str;
    }

    private String createMessage(String str, Object... objArr) {
        return (objArr == null || objArr.length == 0) ? str : String.format(str, objArr);
    }
}
