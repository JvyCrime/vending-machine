package iaik.utils;

import java.text.MessageFormat;

/* JADX INFO: loaded from: classes2.dex */
public class RFC2253NameParserException extends Exception {
    private static final long serialVersionUID = -915071745628736574L;

    public RFC2253NameParserException() {
    }

    public RFC2253NameParserException(String str) {
        super(str);
    }

    public RFC2253NameParserException(String str, Object[] objArr) {
        super(MessageFormat.format(str, objArr));
    }
}
