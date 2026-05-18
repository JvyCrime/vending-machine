package iaik.utils;

import java.io.IOException;
import java.util.EventListener;

/* JADX INFO: loaded from: classes2.dex */
public interface EOFListener extends EventListener {
    void notifyEOF() throws IOException;
}
