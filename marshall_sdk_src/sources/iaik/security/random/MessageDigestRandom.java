package iaik.security.random;

import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public abstract class MessageDigestRandom extends SecRandom {
    private static final long serialVersionUID = -7313451639035522365L;

    MessageDigestRandom(SecRandomSpi secRandomSpi) {
        super(secRandomSpi);
    }

    protected MessageDigestRandom(MessageDigest messageDigest) {
        super(new SecRandomSpi(new o(messageDigest)));
    }
}
