package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
public final class SHA512_256 extends SHA64bit {
    private static final long[] p = {2463787394917988140L, -6965556091613846334L, 2563595384472711505L, -7622211418569250115L, -7626776825740460061L, -4729309413028513390L, 3098927326965381290L, 1060366662362279074L};

    public SHA512_256() {
        super("SHA512/256", 32, 128, p);
    }
}
