package iaik.security.ssl;

import iaik.security.ssl.TicketKeyBag;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public abstract class TicketKeysManager implements Cloneable {
    private static TicketKeysManager a = new DefaultTicketKeysManager();

    public abstract TicketKeyBag getTicketKeys(TicketKeyBag.KeyName keyName) throws NoSuchAlgorithmException;

    public abstract void setTicketKeys(TicketKeyBag ticketKeyBag);

    public static TicketKeysManager getDefault() {
        return a;
    }

    public static void setDefault(TicketKeysManager ticketKeysManager) {
        a = ticketKeysManager;
    }

    public Object clone() {
        try {
            return (TicketKeysManager) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
