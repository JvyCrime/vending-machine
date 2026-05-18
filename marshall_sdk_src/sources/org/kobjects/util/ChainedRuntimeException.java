package org.kobjects.util;

/* JADX INFO: loaded from: classes2.dex */
public class ChainedRuntimeException extends RuntimeException {
    Exception chain;

    public static ChainedRuntimeException create(Exception exc, String str) {
        try {
            return ((ChainedRuntimeException) Class.forName("org.kobjects.util.ChainedRuntimeExceptionSE").newInstance())._create(exc, str);
        } catch (Exception unused) {
            return new ChainedRuntimeException(exc, str);
        }
    }

    ChainedRuntimeException() {
    }

    ChainedRuntimeException(Exception exc, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str == null ? "rethrown" : str);
        sb.append(": ");
        sb.append(exc.toString());
        super(sb.toString());
        this.chain = exc;
    }

    ChainedRuntimeException _create(Exception exc, String str) {
        throw new RuntimeException("ERR!");
    }

    public Exception getChained() {
        return this.chain;
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        super.printStackTrace();
        Exception exc = this.chain;
        if (exc != null) {
            exc.printStackTrace();
        }
    }
}
