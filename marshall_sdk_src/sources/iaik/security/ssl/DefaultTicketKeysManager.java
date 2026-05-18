package iaik.security.ssl;

import iaik.security.ssl.TicketKeyBag;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class DefaultTicketKeysManager extends TicketKeysManager implements Cloneable {
    private Hashtable a;

    @Override // iaik.security.ssl.TicketKeysManager
    public void setTicketKeys(TicketKeyBag ticketKeyBag) {
        TicketKeyBag ticketKeyBag2;
        if (this.a == null) {
            this.a = new Hashtable(5);
        }
        if (this.a.size() > 2) {
            Enumeration enumerationKeys = this.a.keys();
            TicketKeyBag ticketKeyBag3 = null;
            while (enumerationKeys.hasMoreElements()) {
                TicketKeyBag.KeyName keyName = (TicketKeyBag.KeyName) enumerationKeys.nextElement();
                if (!keyName.equals(TicketKeyBag.a) && (ticketKeyBag2 = (TicketKeyBag) this.a.get(keyName)) != null && (ticketKeyBag3 == null || ticketKeyBag2.getActivationTime() < ticketKeyBag3.getActivationTime())) {
                    ticketKeyBag3 = ticketKeyBag2;
                }
            }
            this.a.remove(ticketKeyBag3.getKeyName());
        }
        this.a.put(TicketKeyBag.a, ticketKeyBag);
        this.a.put(ticketKeyBag.getKeyName(), ticketKeyBag);
    }

    @Override // iaik.security.ssl.TicketKeysManager
    public TicketKeyBag getTicketKeys(TicketKeyBag.KeyName keyName) throws NoSuchAlgorithmException {
        long validityPeriod;
        long activationTime;
        Hashtable hashtable = this.a;
        if (hashtable == null) {
            return null;
        }
        if (keyName != null) {
            return (TicketKeyBag) hashtable.get(keyName);
        }
        TicketKeyBag ticketKeyBag = (TicketKeyBag) hashtable.get(TicketKeyBag.a);
        if (ticketKeyBag != null) {
            validityPeriod = ticketKeyBag.getValidityPeriod();
            activationTime = ticketKeyBag.getActivationTime();
        } else {
            validityPeriod = 0;
            activationTime = 0;
        }
        if (ticketKeyBag != null && (validityPeriod <= 0 || (System.currentTimeMillis() / 1000) - activationTime <= validityPeriod)) {
            return ticketKeyBag;
        }
        synchronized (this.a) {
            if (ticketKeyBag != null) {
                try {
                    ticketKeyBag = (TicketKeyBag) this.a.get(TicketKeyBag.a);
                    validityPeriod = ticketKeyBag.getValidityPeriod();
                    activationTime = ticketKeyBag.getActivationTime();
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (ticketKeyBag == null || (validityPeriod > 0 && (System.currentTimeMillis() / 1000) - activationTime > validityPeriod)) {
                ticketKeyBag = new TicketKeyBag();
                ticketKeyBag.setValidityPeriod(validityPeriod);
                setTicketKeys(ticketKeyBag);
            }
        }
        return ticketKeyBag;
    }

    @Override // iaik.security.ssl.TicketKeysManager
    public Object clone() {
        DefaultTicketKeysManager defaultTicketKeysManager = (DefaultTicketKeysManager) super.clone();
        Hashtable hashtable = this.a;
        if (hashtable != null) {
            defaultTicketKeysManager.a = (Hashtable) hashtable.clone();
        }
        return defaultTicketKeysManager;
    }
}
